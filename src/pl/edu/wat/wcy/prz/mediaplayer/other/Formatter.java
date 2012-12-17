package pl.edu.wat.wcy.prz.mediaplayer.other;

import javafx.util.Duration;

public class Formatter {

	public static String getFormatDuration(double millis) {		
		int second = (int) (millis / 1000) % 60;
		int minutes = (int) (millis / (1000 * 60)) % 60;
		int hours = (int) (millis / (1000 * 60)) / 60;
		
		if(minutes < 10 && hours <= 0) return String.format("%2d:%02d", minutes, second);
		else if( hours <= 0) return String.format("%02d:%02d", minutes, second);
		else if( hours < 10) return String.format("%2d:%02d:%02d", hours, minutes, second);
		else return String.format("%2d:%02d:%02d", hours, minutes, second);
	}
	
	public static String getFormatDuration(Duration duration) {
		return getFormatDuration(duration.toMillis());
	}
	
	public static String getFullFormatDuration(double _millis, boolean visibleMillis) {
		int millis = (int)_millis % 1000;
		int second = (int) (_millis / 1000) % 60;
		int minutes = (int) (_millis / (1000 * 60));
		
		if (visibleMillis) return String.format("%02d:%02d:%03d", minutes, second, millis);
		else return String.format("%02d:%02d", minutes, second);
	}
	
	public static String getFullFormatDuration(double _millis) {
		return getFullFormatDuration(_millis, true);
	}
	
	public static String getComparisonTimeString(double millisCurrent, double millsTotal) {
		return getFullFormatDuration(millisCurrent) + " / " + getFullFormatDuration(millsTotal);
	}
	
	public static String getComparisonTimeString(double millisCurrent, double millisTotal, boolean visibleMillis) {
		return getFullFormatDuration(millisCurrent, visibleMillis) + " / " + getFullFormatDuration(millisTotal, visibleMillis);
	}

}
