package pl.wat.edu.pl.pw.MediaPlayer.gui.components.TimeSlider;

import java.awt.BorderLayout;

import javafx.scene.media.MediaPlayer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TimePanel extends JPanel {
	protected JLabel currentTime;
	protected JLabel totalTime;
	
	protected TimeSlider timeSlider;
	
	protected MediaPlayer player;
	
	private ChangeListener changeListener = null;
	
	public TimePanel() {
		super(new BorderLayout());
		currentTime = new JLabel();
		totalTime = new JLabel();
		timeSlider = new TimeSlider();
		
		startView();
		setTimeLabelsVisible(false);
		
		add(currentTime, BorderLayout.LINE_START);
		add(timeSlider, BorderLayout.CENTER);
		add(totalTime, BorderLayout.LINE_END);
	}
	
	protected void startView() {
		currentTime.setText("00:00");
		totalTime.setText("00:00");
		timeSlider.setDefault();
	}
	
	public void adjustToPlayer(final MediaPlayer player) {
		this.player = player;
		
		timeSlider.adjustToPlayer(player);
		totalTime.setText(Formatter.getFormatDuration(player.getTotalDuration()));
		currentTime.setText(Formatter.getFormatDuration(player.getCurrentTime()));
		
		if(changeListener != null) {
			timeSlider.removeChangeListener(changeListener);
			changeListener = null;
			System.gc();
		}
		changeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (!timeSlider.getValueIsAdjusting()) {					
					
					currentTime.setText(Formatter.getFormatDuration(player.getCurrentTime()
							.toMillis()));
					timeSlider.setToolTipText(Formatter.getComparisonTimeString(player
							.getCurrentTime().toMillis(), player.getTotalDuration()
							.toMillis()));
				}
			}
		};
		timeSlider.addChangeListener(changeListener);
	}
	
	public void setTimeLabelsVisible(boolean visible) {
		currentTime.setVisible(visible);
		totalTime.setVisible(visible);
	}
	
	public void update() {
		try {
			currentTime.setText(Formatter.getFormatDuration(player.getCurrentTime()));
			timeSlider.update();
		}
		catch (NullPointerException exc){
			timeSlider.setDefault();
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        		TimePanel pan = new TimePanel();
        		JFrame frame = new JFrame("Test TimePanelComponent");
        		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		frame.add(pan);
        		frame.setVisible(true);
        		frame.setSize(600, 70);
            }
        });
	}
}