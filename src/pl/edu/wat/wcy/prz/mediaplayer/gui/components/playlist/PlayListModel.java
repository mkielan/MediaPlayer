package pl.edu.wat.wcy.prz.mediaplayer.gui.components.playlist;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.swing.DefaultListModel;

import pl.edu.wat.wcy.prz.mediaplayer.Production;
import pl.edu.wat.wcy.prz.mediaplayer.other.SQLiteConnection;

/**
 * @author Mariusz Kielan
 */
public class PlayListModel extends DefaultListModel<Production> {
	public final static int NOT_PLAYING = -1;

	private int nowPlaying;
	
	public PlayListModel() {
		super();
		
		nowPlaying = NOT_PLAYING;
	}
	
	public PlayListModel(final File[] mediaFiles) {
		this();
		for (File file : mediaFiles) {
			insertElement(new Production(file));
		}
	}
	
	public MediaPlayer play(int i) {
		try{
			if(nowPlaying != NOT_PLAYING) get(nowPlaying).setPlaying(false);
			nowPlaying = i;
			get(nowPlaying).setPlaying(true);
			return get(i).getMediaPlayer();
		}
		catch (ArrayIndexOutOfBoundsException exc) {
			nowPlaying = NOT_PLAYING;
			return null;
		}
	}
	
	public MediaPlayer playNext() {
		try{
			if(nowPlaying != NOT_PLAYING) get(nowPlaying).setPlaying(false);
			get(++nowPlaying).setPlaying(true);
			return get(nowPlaying).getMediaPlayer();
		}
		catch (ArrayIndexOutOfBoundsException exc) {
			nowPlaying = NOT_PLAYING;
			return null;
		}
	}
	
	public MediaPlayer playPrev() {
		try{
			if(nowPlaying != NOT_PLAYING) get(nowPlaying).setPlaying(false);
			
			get(--nowPlaying).setPlaying(true);
			return get(nowPlaying).getMediaPlayer();
		}
		catch (ArrayIndexOutOfBoundsException exc) {
			nowPlaying = NOT_PLAYING;
			return null;
		}
	}
	
	public int getCurrentInt() {
		return nowPlaying;
	}
	
	public Production getCurrent() {
		try{
			return get(nowPlaying);
		}
		catch (ArrayIndexOutOfBoundsException exc) {
			if(!isEmpty()) return get(0);
			return null;
		}
	}
	
	public void replace(int first, int second) {
		if(first != second) {
			Production fst = get(first);
			Production snd = get(second);
			
			if(first < second) {
				removeElementAt(second);
				removeElementAt(first);
				insertElementAt(snd,first);
				insertElementAt(fst,second);
			}
			else {
				removeElementAt(first);
				removeElementAt(second);
				insertElementAt(fst,second);
				insertElementAt(snd,first);
			}
		}
	}
	
	public Production getNext() {
		try{
			return get(nowPlaying);
		}
		catch (ArrayIndexOutOfBoundsException exc) {
			return null;
		}
	}
	
	public void insertElement(Production production) {
		insertElementAt(production, size());
	}
	
	public void removeAllElements() {
		super.removeAllElements();
		nowPlaying = NOT_PLAYING;
	}
	
	public boolean removeElement(Object element) {
		int elementIndex = getIndex((Production)element);
		if(elementIndex == NOT_PLAYING) return false;
		else if(elementIndex <= nowPlaying) {
			if(size() <= 1) nowPlaying = NOT_PLAYING;
			else nowPlaying--;
			
			return super.removeElement(element);
		}
		else return super.removeElement(element);
	}
	
	public int getIndex(Production production) {
		for (int i = 0; i < size() ; i++) {
			if(get(i).equals(production)) return i;
		}
		return NOT_PLAYING;
	}
	
	public double getTotalListDuration() {
		double ret = 0.0;
		
		for (int i = 0; i < size(); i++) {
			Duration duration = get(i).getTotalDuraton();
			if(duration != null) ret += duration.toMillis();
		}
		return ret;
	}
	
	/**Media isn't play when method return NOT_PLAYING. */
	public int isPlaying() {
		return nowPlaying;
	}
	
	public void loadFromDB() {
		SQLiteConnection connection = new SQLiteConnection();
		String[] uries = connection.getPlaylistFromDB();
		
		for (String uri : uries) {
			try {
				final File file = new File((new URI(uri)).getPath());
				addElement(new Production(file));
				
			} catch (URISyntaxException e) {
				System.err.println("File is not added.");
			} catch (MediaException e) {
				System.err.println("File is not founded.");
			}
		}
		
		connection.endConnection();
		connection = null;
		System.gc();
	}
	
	public void saveToDB() {
		SQLiteConnection connection = new SQLiteConnection();
		
		connection.deleteAllRecords("Playlist");
		for(int i = 0; i < size(); i++) {
			connection.insertIntoPlaylistDB(get(i).getURI().toString());
		}
		
		connection.endConnection();
		connection = null;
		System.gc();
	}
}
