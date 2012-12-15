package pl.wat.edu.pl.pw.MediaPlayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

import javax.swing.JPanel;

import pl.wat.edu.pl.pw.MediaPlayer.gui.components.ControlPanel;
import pl.wat.edu.pl.pw.MediaPlayer.gui.components.TimeSlider.TimePanel;
import pl.wat.edu.pl.pw.MediaPlayer.other.Property;

/**Media Player based on JavaFX.
 * @author Mariusz Kielan
 */
public class JFXMediaPlayer {
	protected Group root = null;
	protected Scene scene = null;
	protected MediaPlayer mediaPlayer = null;
	protected MediaView mediaView = null;
	protected boolean playing = false;
	protected boolean mute;
	protected boolean repeat;
	protected double volumeLevel;
	private PlayListModel playlistModel;
	private Property property;
	
	protected ControlPanel controlPanel;
	protected JFXPanel fxPanel;
	protected JPanel playerPanel;
	
	private ReentrantLock lock = new ReentrantLock();
	private Condition con = lock.newCondition();
	
	public JFXMediaPlayer() {
		init();
		
		
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	lock.lock();
            	initScene();
				playlistModel.loadFromDB();
				con.signal();
            	lock.unlock();
            	
            }
		});
		
		//blokada dopóki nie sk¹ñczy inicjowanie sceny
		//i ³adowanie danych z bazy na start
		lock.lock();
		try {
			con.await();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		lock.unlock();
	}
	
	public JFXMediaPlayer(PlayListModel playlistModel) {
		this();
		setPlaylistModel(playlistModel);
	}
	
	private void init() {
		property = new Property();
		mute = property.getPlayerMute();
		volumeLevel = property.getPlayerVolumeLevel();
		repeat = property.getPlayerRepeat();
		
		playing = false;
		controlPanel = new ControlPanel(this);
		playlistModel = new PlayListModel();
		fxPanel = new JFXPanel();
		
		playerPanel = new JPanel(new BorderLayout());
		playerPanel.setMinimumSize(new Dimension(300, 100));
		
		playerPanel.add(fxPanel);
		playerPanel.add(controlPanel, BorderLayout.SOUTH);
	}
	
	private void initScene() {
		root = new Group();
		scene = new Scene(root, Color.BLACK);
		
		fxPanel.setScene(scene);
	}
	
	/**Return playlist model.*/
	public PlayListModel getPlayListModel() {
		return playlistModel;
	}
	
	private void setPlaylistModel(PlayListModel playlistModel) {
		this.playlistModel = playlistModel;
		
		if(!playlistModel.isEmpty()) {
			mediaPlayer = playlistModel.getCurrent().getMediaPlayer();
		}
		controlPanel.setPlayButton(false);
	}
	
	/**Play current selected media. */
	public void play() {
		try {			
			mediaPlayer.setMute(mute);
			
			mediaPlayer.play();
			
			setPlaying(true);
		}
		catch (NullPointerException exc) {
			System.err.println("No Media Player");
			if(!playlistModel.isEmpty()) {
				mediaPlayer = playlistModel.play(0);
				
				adjustNewPlayer();
				
				mediaPlayer.play();
				setPlaying(true);
			}
		}
	}

	/**Set media view for player.*/ 
	private void setVideoView() {
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
    			MediaView newView = new MediaView(JFXMediaPlayer.this.playlistModel.getCurrent().getMediaPlayer());
    			JFXMediaPlayer.this.root.getChildren().remove(mediaView);
    			mediaView = newView;
    			
    			changeSizeMediaView();
    			
    			JFXMediaPlayer.this.root.getChildren().add(mediaView);
    			JFXMediaPlayer.this.fxPanel.setSize((int)mediaView.getScene().getWidth(),(int)mediaView.getScene().getHeight());
            }
		});
	}

	/**Stop current running media.s*/
	public void stop() {
		try {
			mediaPlayer.stop();
		}
		catch (NullPointerException exc) {
			System.err.println("No Media Player");
		}
		finally {
			setPlaying(false);
		}
	}

	/**Pause current running media.*/
	public void pause() {
		try {
			mediaPlayer.pause();
		}
		catch (NullPointerException exc) {
			System.err.println("No Media Player");
		}
		finally {
			setPlaying(false);
		}
	}
	
	/**Run next media to play.*/
	public void play(int i) {
		try {
			if(mediaPlayer != null) mediaPlayer.stop();
			mediaPlayer = playlistModel.play(i);
			
			adjustNewPlayer();
			
			mediaPlayer.play();
			setPlaying(true);
		}
		catch(NullPointerException exc) {
			setNoPlayingState();
			System.err.println("No media.");
		}
	}
	
	/**Run next media to play.*/
	public void next() {
		try {
			mediaPlayer.stop();
			mediaPlayer = playlistModel.playNext();
			
			adjustNewPlayer();
		}
		catch(NullPointerException exc) {
			setNoPlayingState();
			System.err.println("No next media.");
		}
	}
	
	/**Run prev media to play */
	public void prev() {
		try {
			mediaPlayer.stop();
			mediaPlayer = playlistModel.playPrev();

			adjustNewPlayer();
		}
		catch(NullPointerException exc) {
			setNoPlayingState();
			System.err.println("No prev media.");			
		}
	}
	
	private void setNoPlayingState() {
		controlPanel.setPlayButton(false);
		playing = false;
	}

	/**Set mute for player.
	 * @param mute boolean
	 */
	public void setMute(boolean mute) {
		this.mute = mute;
		try {
			mediaPlayer.setMute(mute);
		}
		catch (NullPointerException exc) {
			System.err.println("No current plaing media.");
		}
	}
	
	/**Get volume level of plyer.
	 * @return {@link Double}
	 */
	public double getVolume() {
		return volumeLevel;
	}
	
	/**
	 * @param volume boolean
	 */
	public void setVolume(double volume) {
		volumeLevel = volume;
		try {
			mediaPlayer.setVolume(volumeLevel);
		}
		catch (NullPointerException exc) {
			System.err.println("No current plaing media.");
		}
	}
	
	public boolean isMute() {
		return mute;
	}
	
	public boolean isRepeat() {
		return repeat;
	}
	
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	
	private void setPlaying(boolean playing) {
		this.playing = playing;
		controlPanel.setPlayButton(playing);
	}
	
	public JPanel getPlayerPanel() {
		return playerPanel;
	}
	
	/**Return panel to display movie.*/
	public Component getVisualComponent() {
		return fxPanel;
	}
	
	/**Return component to control media player.*/
	public Component getControlPanel() {
		return controlPanel;
	}
	
	public void addPlayListListenerToControlPanel(Playlist playlist) {
		controlPanel.addPlayListListener(playlist);
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public boolean isSetMediaView() {
		return (mediaView == null) ? false : true;
	}
		
	public void changeSizeMediaView() {
		try {
			double xy = (double)mediaPlayer.getMedia().getWidth()/(double)mediaPlayer.getMedia().getHeight();
			double ab = (double)fxPanel.getWidth()/(double)fxPanel.getHeight();
			
			if(ab <= xy) {
				mediaView.setFitWidth(fxPanel.getWidth());
				mediaView.setFitHeight(0);
				double current_y = ((double)(fxPanel.getWidth() * mediaPlayer.getMedia().getHeight()))/mediaPlayer.getMedia().getWidth();
				mediaView.setY(((double)fxPanel.getHeight() - current_y) / 2.0);
			}
			else {
				mediaView.setFitWidth(0);
				mediaView.setFitHeight(fxPanel.getHeight());
				double current_x = fxPanel.getHeight() * xy;
				mediaView.setX(((double)fxPanel.getWidth() - current_x) / 2.0);
			}
		}
		catch(NullPointerException exc) {
			System.err.println("No current mediaView");
		}
	}
	
	private void adjustNewPlayer() {
		setVideoView();
		controlPanel.getTimePanel().adjustToPlayer(mediaPlayer);
		
		mediaPlayer.setMute(mute);
		mediaPlayer.setVolume(volumeLevel);
		mediaPlayer.setOnEndOfMedia(new OnEndOfMedia(this));
		mediaPlayer.currentTimeProperty().addListener(new ChangeTimeListener(controlPanel.getTimePanel()));
		if(playing) {
			mediaPlayer.play();
			setPlaying(true);
		}
	}
	
	class OnEndOfMedia implements Runnable {
		JFXMediaPlayer player;
		
		public OnEndOfMedia(JFXMediaPlayer player) {
			this.player = player;
		}
		
		public void run() {
			if(!repeat) {
				player.next();
			}
			else {
				mediaPlayer.seek(mediaPlayer.getStartTime());
				controlPanel.getTimePanel().adjustToPlayer(mediaPlayer);
				mediaPlayer.play();
			}
		}
	}
	
	class ChangeTimeListener implements InvalidationListener {
		TimePanel timePanel;
		
		public ChangeTimeListener(TimePanel timePanel) {
			this.timePanel = timePanel;
		}
		
		@Override
		public void invalidated(Observable arg0) {
			timePanel.update();
		}
	}
}
