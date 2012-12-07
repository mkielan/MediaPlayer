package pl.wat.edu.pl.pw.MediaPlayer.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;


import pl.wat.edu.pl.pw.MediaPlayer.gui.components.LFMPasswordPanel;
import pl.wat.edu.pl.pw.MediaPlayer.other.LastFmApi;

public class LastFmWindow extends JFrame implements ActionListener{

	LFMPasswordPanel passwordPanel;
	private int skinID = 0;
	LastFmApi lastFmApi;
	private JButton loginButton, nextEventButton;
	static boolean logged = false;
	
	public LastFmWindow(PlayerFrame frame) {
		initLFMGui();
		lastFmApi = new LastFmApi();
		skinID = frame.getSkinID();
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void setLogged(boolean logged2) {
		logged = logged2;
	}
	
	public void initLFMGui(){
		setTitle("Last.FM user Component");
		setSize(600, 300);
		setLayout(null);
		setLocationRelativeTo(null);
		setFocusable(true);
	}
	
	
	
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Random random = new Random();
		int e = random.nextInt(5);
		Font boldFont = new Font("SansSerif", Font.BOLD, 12);
		Font italicFont = new Font("SansSerif", Font.PLAIN, 12);
        g.setFont(boldFont);
        
		if(skinID == 2 || skinID == 4){
        	g.setColor(Color.white);
		}
		
		
        if(logged == false){
			g.drawString("Login to Your account!", 20, 55);
			loginButton = new JButton("Login");
			loginButton.setToolTipText("Login to Your LastFM account");
			add(loginButton);
			loginButton.setBounds(480, 10, 95, 20);
			loginButton.addActionListener(this);
		}
        
		
		if(logged == true){
			
			loginButton.setText("Logout");
			loginButton.setToolTipText("Logout");
			g.setColor(Color.red);
			g.drawString("Hello "+lastFmApi.getUserName(passwordPanel.getUser()), 20, 52);
			
			
			g.setFont(boldFont);
			g.setColor(Color.black);
			
			if(skinID == 2 || skinID == 4){
	        	g.setColor(Color.white);
			}
			
			g.setFont(italicFont);
			g.drawString("Name:"+lastFmApi.getUserName(passwordPanel.getUser()), 20, 70);
			g.drawString("Country:"+lastFmApi.getUserCountry(passwordPanel.getUser()), 20, 85);
			g.drawString("Age:"+lastFmApi.getUserAge(passwordPanel.getUser()), 20, 100);
			g.drawString("Best album:"+lastFmApi.getUserTopAlbums(lastFmApi.getUserName(passwordPanel.getUser())), 20, 115);
			
			
			g.setFont(boldFont);
			g.setColor(Color.gray);
			g.drawString("Events from LastFM community chosen 4u:", 20, 160);
			g.setColor(Color.black);
			
			if(skinID == 2 || skinID == 4){
	        	g.setColor(Color.white);
			}
			
			
			g.setFont(italicFont);
			g.drawString(lastFmApi.getEventInfo(0,e), 20, 180);
			g.drawString(lastFmApi.getEventInfo(1,e), 20, 200);
			g.drawString(lastFmApi.getEventInfo(2,e), 20, 220);
			
			nextEventButton = new JButton("Next event");
			nextEventButton.setToolTipText("Next event");
			add(nextEventButton);
			nextEventButton.setBounds(480, 130, 95, 20);
			nextEventButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					repaint();
				}
			});
		}
		
			
		Toolkit.getDefaultToolkit().sync();
        g.dispose();
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(passwordPanel == null){
			passwordPanel = new LFMPasswordPanel(this);
		}
		passwordPanel.setVisible(true);
	}

	
	public static void main(String[] args) {
		LastFmWindow lastFmWindow = new LastFmWindow(null);

	}
	
}
