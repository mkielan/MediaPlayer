package pl.wat.edu.pl.pw.MediaPlayer.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pl.wat.edu.pl.pw.MediaPlayer.JFXMediaPlayer;

public class PlayerKit extends JToolBar {
	protected JButton volumeButton; 
	protected JButton loopButton;
	protected final JSlider volumeSlider;
	protected JFXMediaPlayer player;
	
	
	public PlayerKit(final JFXMediaPlayer player) {
		this.player = player;
		setFloatable(false);
		
		final JPopupMenu volumePopup = new JPopupMenu();
		
		volumeSlider = new JSlider();
		volumeSlider.setMinimum(0);
		volumeSlider.setMaximum(100);
		volumeSlider.setValue((int)(player.getVolume() * 100));
		volumeSlider.setOrientation(HORIZONTAL);
		
		volumeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				if(volumeSlider.getValue() == 0) {
					setImageForVolumeButton(true);
				}
				else setImageForVolumeButton(false);
				player.setVolume(volumeSlider.getValue() / 100.0);
			}
		});
		
		volumePopup.add(volumeSlider);
		
		volumeButton = new JButton();
		volumeButton.setToolTipText("Adjust the volume level");
		volumeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					volumePopup.show(e.getComponent()
							, e.getComponent().getX()// + 30
							, e.getComponent().getY()
								+ e.getComponent().getHeight()
								- 30);
				}
				else if(e.getClickCount() == 2) {
					boolean mute = player.isMute();
					setImageForVolumeButton(!mute);
					if(mute) volumeSlider.setValue(0);
					else volumeSlider.setValue((int)player.getVolume() * 100);
					player.setMute(!mute);
				}
			}
		});
		setImageForVolumeButton(player.isMute());
		
		loopButton = new JButton();
		setImageForLoopButton();
		loopButton.setToolTipText("Song playing over and over again");
		loopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PlayerKit.this.player.setRepeat(!player.isRepeat());
				setImageForLoopButton();
			}
		});
		
		add(loopButton);
		add(volumeButton);
	}
	
	private void setImageForVolumeButton(final boolean mute) {
		ImageIcon volumeImage;
		if(mute) {		
			volumeImage = new ImageIcon(PlayerKit.class.getResource("/images/novolume.png"));
		}
		else {
			volumeImage = new ImageIcon(PlayerKit.class.getResource("/images/volume.png"));
		}
		
		volumeButton.setIcon(volumeImage);
		System.gc();
	}
	
	private void setImageForLoopButton() {
		ImageIcon loopImage;
		
		if(player.isRepeat()) loopImage = new ImageIcon(PlayerKit.class.getResource("/images/loop.png"));
		else loopImage = new ImageIcon(PlayerKit.class.getResource("/images/noloop.png"));
		
		loopButton.setIcon(loopImage);
		System.gc();
	}
}
