 package pl.wat.edu.pl.pw.MediaPlayer.gui.components.TimeSlider;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class TimeSliderModel {
	protected EventListenerList listenerList = new EventListenerList();
	
	/**W sekundach */
	private double value;
	
	private double minimum = 0.0;
	private double maximum;
	
	private MediaPlayer mediaPlayer;
	
	private boolean valueIsAdjusting;
	
	public TimeSliderModel() {
		setDefault();
	}
	
	public TimeSliderModel(double value, double max) {
		this.value = value;
		this.maximum = max;
		valueIsAdjusting = false;
	}
	
	protected void setDefault() {
		value = 0;
		maximum = 2000;
		valueIsAdjusting = false;
	}
	
	public double getValue() {
		return value;
	}
	
	protected void addChangeListener(ChangeListener l) {
		listenerList.add(ChangeListener.class, l);
	}
	
	protected ChangeListener[] getChangeListeners() {
		return (ChangeListener[])listenerList.getListeners(ChangeListener.class);
	}
	
	protected void addUpdateListener(UpdateListener u) {
		listenerList.add(UpdateListener.class, u);
	}
	
	protected UpdateListener[] getUpdateListeners() {
		return (UpdateListener[])listenerList.getListeners(UpdateListener.class);
	}
	
	public void setValue(double value) {
		if(this.value != value) {
			this.value = value;
			fireStateChanged();
		}
	}
	
	public void fireStateChanged() {		
		for (ChangeListener it : getChangeListeners()) {
			ChangeEvent event = new ChangeEvent(this);
			it.stateChanged(event);				
		}
	}
	
	public void fireStateUpdated() {		
		for (ChangeListener it : getUpdateListeners()) {
			ChangeEvent event = new ChangeEvent(this);
			it.stateChanged(event);				
		}
	}
	
	public void adjustToPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
		
		maximum = mediaPlayer.getTotalDuration().toMillis();
	}
	
	public double getMinimum() {
		return minimum;
	}
	
	public double getMaximum() {
		return maximum;
	}
	
	public boolean getValueIsAdjusting() {
		return valueIsAdjusting;
	}
	
	public void setValueIsAdjusting(boolean v) {
		valueIsAdjusting = v;
	}
	
	public void update() {
		try {
			value = (int) mediaPlayer.getCurrentTime().toMillis();
			fireStateUpdated();
		}
		catch (NullPointerException exc) {
			System.out.println("No player!");
		}
	}
	
	public boolean isPlayer() {
		return mediaPlayer != null;
	}
	
	protected void seek(double newValue) {
		if(newValue < getMinimum()) {
			newValue = getMinimum();
		}
		else if(newValue > getMaximum()) {
			newValue = getMaximum();
		}
		
		mediaPlayer.seek(new Duration(newValue));
	}
	
}
