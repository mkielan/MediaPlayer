package pl.edu.wat.wcy.prz.mediaplayer;

import java.io.File;
import java.net.URI;

import pl.edu.wat.wcy.prz.mediaplayer.other.Formatter;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/** Media file to play.
 *  @author Mariusz Kielan
 */
public class Production {
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	private boolean playing = false;
	
	public Production(File file) {
		this.file = file;
		
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
	}
	
	public String getName() {
		return file.getName();
	}
	
	public URI getURI() {
		return file.toURI();
	}
	
	public String getFormatName() {
		if(playing) {
			return "<b>" + file.getName() + "</b>";
		}
		else {
			return file.getName();
		}
	}
	
	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	
	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}
	
	public String toString() {
		return file.getName()
				+ Formatter.getFormatDuration(mediaPlayer.getTotalDuration());
	}
	
	public Duration getTotalDuraton() {
		return mediaPlayer.getTotalDuration();
	}
	
	public boolean equals(Object obj) {
		if(this == obj) return true;
		else if(!(obj instanceof Production)) return false;
		else if(this.playing == ((Production)obj).playing
				&& this.file.equals(((Production)obj).file)
				&& this.media.equals(((Production)obj).media)
				&& this.mediaPlayer.equals(((Production)obj).mediaPlayer)
				) return true;
		
		return false;
	}
}
