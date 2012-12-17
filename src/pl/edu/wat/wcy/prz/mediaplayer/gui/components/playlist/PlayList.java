package pl.edu.wat.wcy.prz.mediaplayer.gui.components.playlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javafx.scene.media.MediaPlayer;

import javax.swing.JComponent;

import pl.edu.wat.wcy.prz.mediaplayer.JFXMediaPlayer;
import pl.edu.wat.wcy.prz.mediaplayer.Production;

/**
 * 
 * @author Mariusz Kielan
 */
public class PlayList extends JComponent implements ActionListener {
	private PlayListModel listModel;
	protected JFXMediaPlayer player;
	
	public PlayList(JFXMediaPlayer player) {
		setUI(new PlayListUI());
		this.player = player;
		listModel = new PlayListModel();
		
	}
	
	public PlayList(JFXMediaPlayer player, PlayListModel model) {
		this.player = player;
		listModel = model;		
		setUI(new PlayListUI());
	}
	
	public PlayList(JFXMediaPlayer player, File[] mediaFiles) {
		this(player, new PlayListModel(mediaFiles));
	}
	
	public MediaPlayer play(int i) {
		return listModel.play(i);
	}
	
	public MediaPlayer playNext() {
		return listModel.playNext();
	}
	
	public MediaPlayer playPrev() {
		return listModel.playPrev();
	}
	
	public Production getCurrent() {
		return listModel.getCurrent();
	}
	
	public Production getNext() {
		return listModel.getNext();
	}
	
	public void add(File mediaFile) {
		listModel.insertElement(new Production(mediaFile));
	}
	
	public void add(File[] mediaFiles) {
		for (File file : mediaFiles) {
			listModel.insertElement(new Production(file));
		}
	}
	
	public void add(Production production) {
		listModel.addElement(production);
	}
	
	public void removeAllElements() {
		listModel.removeAllElements();
	}
	
	public void removeSelected() {
		List<Production> toRemove = ((PlayListUI)ui).list.getSelectedValuesList();
		for (Production production : toRemove) {
			listModel.removeElement(production);
		}
	}
	
	public boolean isEmpty() {
		return listModel.isEmpty();
	}
	
	public PlayListModel getModel() {
		return listModel;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
}
