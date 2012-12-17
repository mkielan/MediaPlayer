package pl.edu.wat.wcy.prz.mediaplayer.gui.components.playlist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.plaf.ComponentUI;

import pl.edu.wat.wcy.prz.mediaplayer.Production;
import pl.edu.wat.wcy.prz.mediaplayer.other.Formatter;

public class PlayListUI extends ComponentUI implements MouseListener, MouseMotionListener, ListDataListener {

	private Point startMovePoint = null;
	protected static final Dimension preferredSize = new Dimension(200, 400);
	private PlayList playlist;
	private PlayListModel listModel;
	protected JList<Production> list;
	private JPanel totalPanel;
	private JLabel total;
	
	
	public void installUI(JComponent c) {
		playlist = (PlayList)c;
		playlist.setLayout(new BorderLayout());
		
		listModel = (PlayListModel) playlist.getModel();
		listModel.addListDataListener(this);
		
		
		list = new JList<Production>(listModel);
		list.setCellRenderer(new PlayListCellRenderer());
		list.addMouseListener(this);
		list.addMouseMotionListener(this);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		c.add(scrollPane, BorderLayout.CENTER);
		
		total = new JLabel("Total " + Formatter.getFormatDuration(listModel.getTotalListDuration()));
		
		totalPanel = new JPanel();
		totalPanel.add(total);
		
		c.add(totalPanel, BorderLayout.PAGE_END);
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		total.setText("Total " + Formatter.getFormatDuration(listModel.getTotalListDuration()));
		super.paint(g, c);
	}
	
	public void uninstallUI(JComponent c) {
		c.setLayout(null);
		
		c.remove(list);
		c.remove(totalPanel);
		
		total = null;
		totalPanel = null;
		
		list = null;
		playlist = null;
		listModel = null;
		System.gc();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			if(!playlist.player.isPlaying() || listModel.getCurrentInt() != list.getSelectedIndex()) {
				playlist.player.play(list.getSelectedIndex());
				playlist.repaint();
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
			int moveElement = list.locationToIndex(startMovePoint);
			int second = list.locationToIndex(current);
			
			if(second != moveElement) {
				listModel.replace(moveElement, second);
				startMovePoint = e.getPoint();
				list.repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void contentsChanged(ListDataEvent e) {
		playlist.repaint();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		playlist.repaint();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		playlist.repaint();
	}
}
