package pl.wat.edu.pl.pw.MediaPlayer.gui.components.TimeSlider;


import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;

public class TimeSlider extends JComponent implements UpdateListener{
	private TimeSliderModel model = null;
	private TimeSliderUI ui;
	
	public TimeSlider() {
		super();
		model = new TimeSliderModel();
		model.addUpdateListener(this);
		
		ui = new TimeSliderUI();
		setUI(ui);
		setPreferredSize(ui.getPreferredSize());
		setEnabled(true);
	}
	
	public TimeSliderModel getModel() {
		return model;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {	
		repaint();
	}	
	
	public void update() {
		model.update();
	}
	
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}
	
	public boolean isEnabled() {
		return super.isEnabled() && model.isPlayer();
	}
}
