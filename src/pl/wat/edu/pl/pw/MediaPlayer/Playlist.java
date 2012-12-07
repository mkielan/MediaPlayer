package pl.wat.edu.pl.pw.MediaPlayer;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.List;

import javafx.scene.media.MediaPlayer;

import javax.swing.JList;

/**
 * 
 * @author Mariusz Kielan
 */
public class Playlist extends JList<Production> implements MouseListener, MouseMotionListener, ActionListener {
	private Point startMovePoint = null;
	private PlayListModel listModel;
	private JFXMediaPlayer player;
	
	public Playlist(JFXMediaPlayer player) {
		super(new PlayListModel());
		this.player = player;
		listModel = new PlayListModel();

		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public Playlist(JFXMediaPlayer player, PlayListModel model) {
		super(model);
		this.player = player;
		listModel = model;		

		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public Playlist(JFXMediaPlayer player, File[] mediaFiles) {
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
		List<Production> toRemove = getSelectedValuesList();
		for (Production production : toRemove) {
			listModel.removeElement(production);
		}
	}
	
	public boolean isEmpty() {
		return listModel.isEmpty();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			if(!player.isPlaying() || listModel.getCurrentInt() != getSelectedIndex()) {
				player.play(getSelectedIndex());
				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		startMovePoint = e.getPoint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		startMovePoint = null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(startMovePoint != null) {
			Point current = e.getPoint();
			int moveElement = locationToIndex(startMovePoint);
			int second = locationToIndex(current);
			
			if(second != moveElement) {
				listModel.replace(moveElement, second);
				startMovePoint = e.getPoint();
				repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
}
