package pl.edu.wat.wcy.prz.mediaplayer.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import pl.edu.wat.wcy.prz.mediaplayer.JFXMediaPlayer;
import pl.edu.wat.wcy.prz.mediaplayer.gui.components.playlist.PlayList;
import pl.edu.wat.wcy.prz.mediaplayer.gui.components.playlist.PlayListModel;
import pl.edu.wat.wcy.prz.mediaplayer.gui.components.playlist.PlayListToolBar;

public class PlayerFrame extends JFrame implements ComponentListener {
	protected JSplitPane mainSplitPanel;
	protected JPanel playerPanel;
	protected JPanel playListPanel;
	protected static JFXMediaPlayer player;
	protected int skinID;
	
	private Dimension size = new Dimension(600, 400);
	protected static final Dimension minimumSize = new Dimension(500, 300);
	
	private ReentrantLock lock = new ReentrantLock();
	private Condition con = lock.newCondition();

	public PlayerFrame() {
		this(null);
	}

	public PlayerFrame(JFXMediaPlayer mediaPlayer) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PlayerFrame.class.getResource("/images/video_player.png")));
		addComponentListener(this);
		player = mediaPlayer;

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				lock.lock();

				initComponent();
				con.signal();
            	lock.unlock();
			};
		});
		
		//blokada dop�ki nie sk��czy inicjowanie Componentu
		lock.lock();
		try {
			con.await();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		lock.unlock();
	}

	protected void initComponent() {
		this.setTitle("MediaPlayer");
		this.setSize(size);
		this.setMinimumSize(minimumSize);
		PlayListModel playlistModel;

		if(player == null) {
			player = new JFXMediaPlayer();
			playlistModel = player.getPlayListModel();
		}
		else {
			playlistModel = player.getPlayListModel();
		}

		playerPanel = player.getPlayerPanel();
		
		PlayList playList = new PlayList(player, playlistModel);
		player.addPlayListListenerToControlPanel(playList);

		playListPanel = new JPanel(new BorderLayout());
		playListPanel.add(playList, BorderLayout.CENTER);

		playListPanel.add(new PlayListToolBar(playList, this), BorderLayout.PAGE_END);


		mainSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playerPanel, playListPanel);
		mainSplitPanel.setSize(600, 300);
		mainSplitPanel.setDividerLocation(420);
		
		this.setContentPane(mainSplitPanel);
		
		setSkin(2);
	}

	public JFXMediaPlayer getPlayer() {
		return player;
	}

	public void setSkin(int typeID) {

		try {
			if (typeID == 0) 
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			else if(typeID == 1)
				UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			else if(typeID == 2)
				UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			else if(typeID == 3)
				UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
			else if(typeID == 4)
				UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
			else if(typeID == 5)
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			
			SwingUtilities.updateComponentTreeUI(this);
			
			skinID = typeID;
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	public int getSkinID(){
		return skinID;
	}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) {
		int d = (int)Math.round(this.getSize().getWidth() - size.width);
		mainSplitPanel.setDividerLocation(
				mainSplitPanel.getDividerLocation()
				+ d);
		
		size = this.getSize();
	}

	@Override
	public void componentShown(ComponentEvent e) {}

	public static void main(String[] args) {
		final PlayerFrame frame = new PlayerFrame();
		frame.setVisible(true);
		frame.setSkin(2);

		UIManager.put("swing.boldMetal", Boolean.FALSE);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Tray.createAndShowGUIForTray(player,frame);
			}
		});
	}
}
