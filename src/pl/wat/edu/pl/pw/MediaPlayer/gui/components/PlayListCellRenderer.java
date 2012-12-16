package pl.wat.edu.pl.pw.MediaPlayer.gui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import pl.wat.edu.pl.pw.MediaPlayer.Production;
import pl.wat.edu.pl.pw.MediaPlayer.other.Formatter;

public class PlayListCellRenderer extends DefaultListCellRenderer {
	public static final Font PLAYING_FONT = new Font(Font.DIALOG, Font.BOLD, 12);
	public static final Font NOPLAYING_FONT = new Font(Font.DIALOG, Font.PLAIN, 12);
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object value, int index, boolean isSelected, boolean cellHasFocus) {
		PlayListModel model = (PlayListModel) list.getModel();
		Production prod = (Production)value;
		JPanel listCell = new JPanel(new BorderLayout());
		
		
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		label.setText((index+1) + ". " + prod.getName());
		listCell.add(label, BorderLayout.CENTER);
		
		JLabel timeLabel = new JLabel(Formatter.getFormatDuration(prod.getTotalDuraton()));
		timeLabel.setBackground(label.getBackground());
		
		timeLabel.setOpaque(true);
		listCell.add(timeLabel, BorderLayout.LINE_END);
		
		if(model.isPlaying() == index) {
			label.setFont(PLAYING_FONT);
			label.setFont(PLAYING_FONT);
		}
		else {
			label.setFont(NOPLAYING_FONT);
			timeLabel.setFont(NOPLAYING_FONT);
		}
		return listCell;
	}
}
