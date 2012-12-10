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

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import pl.wat.edu.pl.pw.MediaPlayer.other.Formatter;

public class TimeSliderUI extends ComponentUI {
	private int margin = 5;
	protected static final Dimension preferredSize = new Dimension(30, 25);
	
	TimeSlider timeSlider;
	TimeSliderModel timeSliderModel;
	
	protected Color textColor = Color.BLACK;
	protected Color gradientFirstColor = Color.BLUE;
	protected Color gradientSecondColor = Color.WHITE;
	protected Color sliderStrapBackground = Color.GRAY;
	
	private String timeString;
	
	protected boolean stringPainting = true;
	
	public void installUI(JComponent c) {
		timeSlider = (TimeSlider) c;
		timeSliderModel = timeSlider.getModel();
		
		timeSlider.addMouseListener(new TimeMouseAdapter());
		timeSlider.addMouseMotionListener(new TimeMouseMontionListener());
	}
	
	public Dimension getPreferredSize() {
		return preferredSize;
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		int positionX = margin;
		int positionY = timeSlider.getHeight()/2 - 8;
		Graphics2D g2d = (Graphics2D) g;
		
		GradientPaint gradient = new GradientPaint(15, 20, gradientSecondColor, 15, 9, gradientFirstColor,
				true);
		
		
		int maxLenght = (timeSlider.getSize().width > 50) ? timeSlider.getSize().width - margin * 2: 50;
		int lenght = (int)((timeSlider.getModel().getValue() / timeSlider.getModel().getMaximum()) * (double)maxLenght);
		
		g2d.setColor(sliderStrapBackground);
		g2d.drawRoundRect(positionX, positionY, maxLenght, 17, 7, 13);
		
		g2d.setColor(gradientSecondColor);
		g2d.fillRoundRect(positionX, positionY + 1, maxLenght, 15, 7, 15);
		
		g2d.setPaint(gradient);
		g2d.fillRoundRect(positionX+1, positionY + 1, lenght - 1, 15, 7, 5);
		
		if(stringPainting) {
			timeString = (timeSlider.getModel().isPlayer()) ? Formatter.getComparisonTimeString(timeSlider.getModel().getValue(), timeSlider.getModel().getMaximum(), false) : Formatter.getComparisonTimeString(0, 0, false);
			g2d.setXORMode(gradientSecondColor);
			g2d.setColor(textColor);
			g2d.setFont(new Font(Font.SERIF, Font.BOLD, 12));
			g2d.drawString(timeString, positionX + maxLenght/2 - 30, positionY + 13);
		}
	}
	
	class TimeMouseMontionListener implements MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			if(timeSlider.getModel().getValueIsAdjusting()) byMouseReload(e.getX());
		}

		@Override
		public void mouseMoved(MouseEvent e) {}
	}
	
	class TimeMouseAdapter extends MouseAdapter {
			@Override
			public void mousePressed(MouseEvent e) {
				timeSlider.getModel().setValueIsAdjusting(true);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				timeSlider.getModel().setValueIsAdjusting(false);
				byMouseReload(e.getX());
			}
	}
	
	protected void byMouseReload(double x) {
		if(timeSlider.isEnabled()) {
			double newValue = ((x+3.0) / (double) timeSlider.getSize().width) * timeSliderModel.getMaximum();
			
			timeSliderModel.seek(newValue);
		}
	}
}
