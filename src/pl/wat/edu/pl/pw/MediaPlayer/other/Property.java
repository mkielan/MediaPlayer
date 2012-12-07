package pl.wat.edu.pl.pw.MediaPlayer.other;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Property{
	Properties properties;

	protected static final boolean DEFAULT_MUTE = false;
	protected static final boolean DEFAULT_REPEAT = false;
	protected static final double DEFAULT_VOLUME_LEVEL = 0.50;
	
	public Property() {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(".properties"));
		} catch (FileNotFoundException e) {
			
			System.err.println("Nie znaleziono pliku properties!\n" +
					"Pozwól, ¿e utworze go dla Ciebie :)");
			setPropertyMethod();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private void setPropertyMethod(){
		try {
			properties.setProperty("database", "player.sqlite");
			properties.setProperty("dbuser", "userName");
			properties.setProperty("dbpassword", "p@ssword");
			properties.setProperty("lfpassword", "p@ssword1");
			properties.setProperty("lfuser", "javaProject");
			properties.setProperty("apiKey", "a530f137a6f93a3a2164aed9039ca4e2");
			properties.setProperty("apiSecret", "is 604e5fd33dd6156fea0bcc648d79aad1");
			properties.setProperty("playerVolumeLevel", Double.toString(DEFAULT_VOLUME_LEVEL));
			properties.setProperty("playerMute", Boolean.toString(DEFAULT_MUTE));
			properties.setProperty("playerRepeat", Boolean.toString(DEFAULT_REPEAT));

			properties.store(new FileOutputStream(".properties"), null);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected String getDatabaseName(){
		return properties.getProperty("database");
	}

	protected String getDBUserName(){
		return properties.getProperty("dbuser");
	}      

	protected String getDBPassword(){
		return properties.getProperty("dbpassword");
	}

	protected String getLFPassword() {
		return properties.getProperty("lfpassword");
	}

	protected String getLFUser(){
		return properties.getProperty("lfuser");
	}

	protected String getApiKey(){
		
		return properties.getProperty("apiKey");
	}

	protected String getApiSecret(){
		return properties.getProperty("apiSecret");
	}
	
	public double getPlayerVolumeLevel() {
		try {
			return Double.parseDouble(properties.getProperty("playerVolumeLevel"));
		}
		catch(NumberFormatException exc) {
			return DEFAULT_VOLUME_LEVEL;
		}	
	}
	
	public boolean getPlayerMute() {
		return Boolean.parseBoolean(properties.getProperty("playerMute"));
	}
	
	public boolean getPlayerRepeat() {
		return Boolean.parseBoolean(properties.getProperty("playerRepeat"));
	}
	
	public void setPlayerVolumeLevel(double volume) {
		properties.setProperty("playerVolumeLevel", String.format("%.2f", volume).replace(",", "."));
		storeProperties();
	}
	
	public void setPlayerMute(boolean mute) {
		properties.setProperty("playerMute", Boolean.toString(mute));
		storeProperties();
	}
	
	public void setPlayerRepeat(boolean repeat) {
		properties.setProperty("playerRepeat", Boolean.toString(repeat));
		storeProperties();
	}
	
	private void storeProperties() {
		try { 
			properties.store(new FileOutputStream(".properties"), null); 
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
	}
}
