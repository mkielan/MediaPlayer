package pl.wat.edu.pl.pw.MediaPlayer.gui.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import pl.wat.edu.pl.pw.MediaPlayer.JFXMediaPlayer;
import pl.wat.edu.pl.pw.MediaPlayer.Playlist;
import pl.wat.edu.pl.pw.MediaPlayer.gui.components.TimeSlider.TimePanel;

/**Panel to control media player. 
 * @author Mariusz Kielan
 */
public class ControlPanel extends JPanel {
	protected JButton playButton;
	protected JButton stopButton;
	protected JButton nextButton;
	protected JButton prevButton;
	
	protected JFXMediaPlayer mediaPlayer = null;

	protected TimePanel timePanel;
	
	private ImageIcon stopButtonIcon; 
	private ImageIcon playButtonIcon; 
	private ImageIcon pauseButtonIcon;
	private ImageIcon prevButtonIcon;
	private ImageIcon nextButtonIcon;
	
	public ControlPanel() {
		init();
	}
	
	public ControlPanel(JFXMediaPlayer mediaPlayer) {
		this.setLayout(new BorderLayout());
		this.mediaPlayer = mediaPlayer;
		init();
	}
	
	private void init() {
		JPanel buttonPanel = new JPanel(new GridLayout(1,4));
		
		stopButtonIcon = createImageIcon("/images/player_stop.png");
		playButtonIcon = createImageIcon("/images/player_play.png");
		pauseButtonIcon = createImageIcon("/images/player_pause.png");
		prevButtonIcon = createImageIcon("/images/player_rev.png");
		nextButtonIcon = createImageIcon("/images/player_fwd.png");
		
		playButton = new JButton("", playButtonIcon);
		try {
			if(mediaPlayer.isPlaying() == true) {
				playButton.setIcon(pauseButtonIcon);
			}
		} catch(NullPointerException e) {}
		
		playButton.setToolTipText("Play/Pause");
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mediaPlayer != null && !mediaPlayer.getPlayListModel().isEmpty()) {
					if(playButton.getIcon().equals(pauseButtonIcon)) {
						mediaPlayer.pause();
						setPlayButton(false);
					}
					else {
						mediaPlayer.play();
						setPlayButton(true);
					}
				}
			}
		});
		
		
		stopButton = new JButton("", stopButtonIcon);
		stopButton.setMnemonic(KeyEvent.VK_S);
		stopButton.setActionCommand("disable");
		stopButton.setToolTipText("Stop");
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mediaPlayer != null) {
					mediaPlayer.stop();
					setPlayButton(false);
				}
			}
		});
		
		nextButton = new JButton("", nextButtonIcon);
		nextButton.setToolTipText("Next");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mediaPlayer != null) mediaPlayer.next();
			}
		});
		
		prevButton = new JButton("", prevButtonIcon);
		prevButton.setToolTipText("Prev");
		prevButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mediaPlayer != null) mediaPlayer.prev();
			}
		});
		
		buttonPanel.add(playButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(prevButton);
		buttonPanel.add(nextButton);
		
		timePanel = new TimePanel();
		
		PlayerKit other = new PlayerKit(mediaPlayer);
		
		this.add(timePanel, BorderLayout.BEFORE_FIRST_LINE);
		this.add(buttonPanel, BorderLayout.LINE_START);
		
		JToolBar tmp = new JToolBar();
		tmp.setFloatable(false);
		this.add(tmp, BorderLayout.CENTER);
		this.add(other, BorderLayout.LINE_END);
	}	
	
	public void addPlayListListener(Playlist playlist) {
		nextButton.addActionListener(playlist);
		prevButton.addActionListener(playlist);
		playButton.addActionListener(playlist);
	}
	
	/**Ustawiamy playing = true, gdy player pracuje. W przeciwnym wypadku false.
	 * (zmienia wygl¹d buttona)
	 * @param playing - 
	 */
	public void setPlayButton(boolean playing) {
		if(playing) {
			playButton.setIcon(pauseButtonIcon);
		}
		else {
			playButton.setIcon(playButtonIcon);
		}
	}
	
    public TimePanel getTimePanel() {
    	return timePanel;
    }
	
	private static ImageIcon createImageIcon(String path) {
		URL imgURL = ControlPanel.class.getResource(path);
		return new ImageIcon(imgURL);
	}
}
