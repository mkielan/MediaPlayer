package pl.wat.edu.pl.pw.MediaPlayer.gui.components.TimeSlider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TimeSlider extends JComponent/*extends JSlider*/ implements MouseMotionListener {
	private boolean pressed = false;
	private int margin = 5;
	private int value;
	private int minimum;
	private int maximum;
	
	private final ArrayList<ChangeListener> changeListenerList = new ArrayList<ChangeListener>();
	
	protected boolean stringPainting = true;
	private boolean valueIsAdjusting = true;
	
	protected Color textColor = Color.BLACK;
	protected Color gradientFirstColor = Color.BLUE;
	protected Color gradientSecondColor = Color.WHITE;
	protected Color sliderStrapBackground = Color.GRAY;
	
	private MediaPlayer mediaPlayer = null;
	
	public TimeSlider() {
		super();
		addMouseMotionListener(this);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pressed = true;
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				pressed = false;
				byMouseReload(e.getX());
			}
		});
		
		setPreferredSize(new Dimension(30, 25));
		setDefault();
		
		setMinimum(0);
		setMaximum(2000);
	}
	
	public void adjustToPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
		
		setMaximum((int)mediaPlayer.getTotalDuration().toMillis());
		setEnabled(true);
	}
	
	public void setDefault() {
		setEnabled(false);
		value = 0;
	}
	
	private void setMinimum(int m) {
		minimum = m;
		repaint();
	}
	
	public int getMinimum() {
		return minimum;
	}
	
	public void setMaximum(int m) {
		maximum = m;
		repaint();
	}
	
	public int getMaximum() {
		return maximum;
	}
	
	public int getValue() {
		return value;
	}
	
	private void setValue(int v) {
		if(!pressed) {
			value = v;
			fireStateChanged();
			repaint();
		}
	}
	
	public void update() {
		try {
			value = (int) mediaPlayer.getCurrentTime().toMillis();
			repaint();
		}
		catch (NullPointerException exc) {
			System.out.println("No player!");
		}
	}
	
	public void addChangeListener(ChangeListener listener) {
		changeListenerList.add(listener);
	}
	
	public void fireStateChanged() {
		for (ChangeListener listener : changeListenerList) {
			listener.stateChanged(new ChangeEvent(this));
		}
	}
	
	public ChangeListener[] getChangeListener() {
		return changeListenerList.toArray(null);//TODO czy tak?
	}
	
	public void removeChangeListener(ChangeListener listener) {
		changeListenerList.remove(listener);
	}
	
	public void setEnabled(boolean arg) {
		super.setEnabled(arg);
		setValueIsAdjusting(arg);
	}

	public void setValueIsAdjusting(boolean arg) {
		valueIsAdjusting = arg;
	}
	
	public boolean getValueIsAdjusting() {
		return valueIsAdjusting && mediaPlayer != null;
	}
	
	@Override
	public void paint(Graphics g) {
		int positionX = margin;
		int positionY = getHeight()/2 - 8;
		Graphics2D g2d = (Graphics2D) g;
		
		GradientPaint gradient = new GradientPaint(15, 20, gradientSecondColor, 15, 9, gradientFirstColor,
				true);
		
		
		int maxLenght = (getSize().width > 50) ? getSize().width - margin * 2: 50;
		int lenght = (int)(((double)getValue() / (double)getMaximum()) * (double)maxLenght);
		
		g2d.setColor(sliderStrapBackground);
		g2d.drawRoundRect(positionX, positionY, maxLenght, 17, 7, 13);
		
		g2d.setColor(gradientSecondColor);
		g2d.fillRoundRect(positionX, positionY + 1, maxLenght, 15, 7, 15);
		
		g2d.setPaint(gradient);
		g2d.fillRoundRect(positionX+1, positionY + 1, lenght - 1, 15, 7, 5);
		
		if(stringPainting) {
			g2d.setXORMode(gradientSecondColor);
			g2d.setColor(textColor);
			g2d.setFont(new Font(Font.SERIF, Font.BOLD, 12));
			g2d.drawString(getTimeText(mediaPlayer), positionX + maxLenght/2 - 30, positionY + 13);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(pressed) byMouseReload(e.getX());
		}

	@Override
	public void mouseMoved(MouseEvent e) {}
	
	protected static String getTimeText(MediaPlayer mediaPlayer) {
		if(mediaPlayer == null) return Formatter.getComparisonTimeString(0, 0, false);
		else return Formatter.getComparisonTimeString(mediaPlayer.getCurrentTime().toMillis(), mediaPlayer.getTotalDuration().toMillis(), false);
	}
	
	protected void byMouseReload(double x) {
		if(getValueIsAdjusting()) {
			double d = (x+3.0) / (double) getSize().width;
			int newValue = (int)(d *((double)getMaximum()));
			
			if(newValue>= minimum && newValue <= maximum) {
				setValue(newValue);
				double time = (double)newValue;
				mediaPlayer.seek(new Duration(time));
			}
			else if(newValue < minimum) {
				setValue(minimum);
				mediaPlayer.seek(new Duration(0));
			}
			else {
				setValue(maximum);
				mediaPlayer.seek(mediaPlayer.getTotalDuration());
			}
		}
	}
}
