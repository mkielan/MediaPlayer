package pl.wat.edu.pl.pw.MediaPlayer.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.wat.edu.pl.pw.MediaPlayer.Playlist;
import pl.wat.edu.pl.pw.MediaPlayer.gui.LastFmWindow;
import pl.wat.edu.pl.pw.MediaPlayer.gui.PlayerFrame;

public class PlayListToolBar extends JToolBar {
	
	
	LastFmWindow lastFmWindow = null;

	public PlayListToolBar(final Playlist playList, PlayerFrame parent) {
		super();
		this.setFloatable(false);

		initAdd(playList, parent);
		initDelete(playList);
		initSkinChooser(parent);
		initLastFMWindow(parent);
	}

	private void initAdd(final Playlist playList, final PlayerFrame parent) {
		ImageIcon addIcon = new ImageIcon(PlayListToolBar.class.getResource("/images/add.png"));
		JButton openButton = new JButton("", addIcon);
		openButton.setToolTipText("Add new files to playlist");

		openButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "Music & video files", "acc", "mp3", "mp4", "wav", "avc");
				fc.setFileFilter(filter);
				fc.showOpenDialog(parent);
				final File[] files = fc.getSelectedFiles();

				if (files.length > 0)
					playList.add(files);
				
				fc = null;
				System.gc();
			}
		});
		this.add(openButton);
	}

	private void initDelete(final Playlist playlist) {
		final JPopupMenu deleteMenuPopup = new JPopupMenu();
		
		JMenuItem deleteAll = new JMenuItem("Delete all items");
		deleteAll.setToolTipText("Delete all items from playlist");
		deleteAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playlist.removeAllElements();
			}
		});
		deleteMenuPopup.add(deleteAll);
		
		
		JMenuItem deleteSelected = new JMenuItem("Delete selected file");
		deleteSelected.setToolTipText("Delete selected file from praylist");
		deleteSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playlist.removeSelected();
			}
		});
		deleteMenuPopup.add(deleteSelected);
		
		
		ImageIcon deleteIcon = new ImageIcon(PlayListToolBar.class.getResource("/images/remove.png"));
		JButton deleteButton = new JButton("", deleteIcon);
		deleteButton.setToolTipText("Remove items from playlist");
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteMenuPopup.show(e.getComponent(), e.getComponent().getX()-32, 
						e.getComponent().getY()
						+ e.getComponent().getHeight()
						- 5);
			}
		});
		
		this.add(deleteButton);
	}
	
	
	private void initSkinChooser(final PlayerFrame frame){
		
		final JPopupMenu skinChooserMenuPopup = new JPopupMenu();
		
		
		JMenuItem skin0 = new JMenuItem("Windows");
		skin0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSkin(0);
			}
		});
		skinChooserMenuPopup.add(skin0);
		
		JMenuItem skin1 = new JMenuItem("Acryl");
		skin1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSkin(1);
			}
		});
		skinChooserMenuPopup.add(skin1);
		
		JMenuItem skin2 = new JMenuItem("HIFI");
		skin2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSkin(2);
			}
		});
		skinChooserMenuPopup.add(skin2);
		
		
		JMenuItem skin3 = new JMenuItem("Bernstein");
		skin3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSkin(3);
			}
		});
		skinChooserMenuPopup.add(skin3);
		
		
		JMenuItem skin4 = new JMenuItem("Noire");
		skin4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSkin(4);
			}
		});
		skinChooserMenuPopup.add(skin4);
	
		
		JMenuItem skin5 = new JMenuItem("Nimbus");
		skin5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSkin(5);
			}
		});
		skinChooserMenuPopup.add(skin5);	
		
		ImageIcon skinIcon = new ImageIcon(PlayListToolBar.class.getResource("/images/cube_config.png"));
		JButton skinButton = new JButton("", skinIcon);
		skinButton.setToolTipText("Choose skin");
		skinButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				skinChooserMenuPopup.show(e.getComponent(), e.getComponent().getX()-32, 
						e.getComponent().getY()
						+ e.getComponent().getHeight()
						- 5);
			}
		});
		
		this.add(skinButton);
		
	}
	
	private void initLastFMWindow(final PlayerFrame frame){
		ImageIcon skinIcon = new ImageIcon(PlayListToolBar.class.getResource("/images/lastfm.png"));
		JButton lfmButton = new JButton("", skinIcon);
		lfmButton.setToolTipText("Connect to your LastFM account");
		lfmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(lastFmWindow == null)
					lastFmWindow = new LastFmWindow(frame);
				else 
					lastFmWindow.setVisible(true);
			}
		});
		this.add(lfmButton);
	}
}