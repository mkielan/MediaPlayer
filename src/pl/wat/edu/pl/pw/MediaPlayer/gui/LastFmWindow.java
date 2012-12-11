package pl.wat.edu.pl.pw.MediaPlayer.gui;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.Timer;

import pl.wat.edu.pl.pw.MediaPlayer.gui.components.LFMPasswordPanel;
import pl.wat.edu.pl.pw.MediaPlayer.other.LastFmApi;
import pl.wat.edu.pl.pw.MediaPlayer.other.OpenBrowser;

public class LastFmWindow extends JFrame implements ActionListener{

	LFMPasswordPanel passwordPanel;
	private int skinID = 3;
	LastFmApi lastFmApi;
	private JButton loginButton, nextEventButton;
	static boolean logged = false;
	protected int x;
	JPanel panel;
	JLabel t1,t2,t3,t4,
			t5,t6,t7,t8,
			t9,t10;
	private Random random;
	private Timer timer;
		

	public LastFmWindow(PlayerFrame frame) {
		initLFMGui();
		lastFmApi = new LastFmApi();
		skinID = frame.getSkinID();
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
		unLoggedGUI();
		
	}


	private void unLoggedGUI() {
		setLayout(null);
		panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setLayout(null);
		add(panel);
		
		t1 = new JLabel("Login to Your account!");
		t1.setBounds(20, 10, 200, 20);
		panel.add(t1);
		
		loginButton = new JButton("Login");
		loginButton.setToolTipText("Login to Your LastFM account");
		panel.add(loginButton);
		
		loginButton.setBounds(480, 10, 95, 20);
		loginButton.addActionListener(this);
		
		t2 = new JLabel("");
		t2.setBounds(20, 40, 200, 20);
		panel.add(t2);
		
		t3 = new JLabel("");
		t3.setBounds(20, 55, 200, 20);
		panel.add(t3);
		
		t4 = new JLabel("");
		t4.setBounds(20, 70, 200, 20);
		panel.add(t4);
		
		t5 = new JLabel("");
		t5.setBounds(20, 85, 360, 20);
		panel.add(t5);
		
		t6 = new JLabel("");
		t6.setBounds(20, 120, 360, 20);
		panel.add(t6);
		
		t7 = new JLabel("");
		t7.setBounds(20, 145, 500, 20);
		panel.add(t7);
		
		t8 = new JLabel("");
		t8.setBounds(20, 162, 500, 20);
		panel.add(t8);
		
		t9 = new JLabel("");
		t9.setBounds(20, 178, 500, 20);
		panel.add(t9);
		
		
		JLabel [] jl = {t2,t3,t4,t5,t7,t8};
		for(JLabel jLabel : jl){
			jLabel.setForeground(Color.black);
		}
		
		this.setContentPane(new JScrollPane (panel));
		
	}
	
	private void loggedGUIPart1(){
		t1.setForeground(Color.red);
		t1.setText("Hello "+lastFmApi.getUserName(passwordPanel.getUser()));
		t2.setText("Name: "+lastFmApi.getUserName(passwordPanel.getUser()));
		t3.setText("Country: "+lastFmApi.getUserCountry(passwordPanel.getUser()));
		t4.setText("Age: "+lastFmApi.getUserAge(passwordPanel.getUser()));
		t5.setText("Best album: "+lastFmApi.getUserTopAlbums(lastFmApi.getUserName(passwordPanel.getUser())));
		
		t6.setForeground(Color.green);
		t6.setText("Events from LastFM community chosen 4u:");
	}
	
	private void loggedGUIPart2() {
		nextEventButton = new JButton("Next event");
		nextEventButton.setBounds(480, 120, 95, 20);
		panel.add(nextEventButton);
		nextEventButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loggedGUIPart2();
			}
		});
		
		random = new Random();
		x = random.nextInt(5);
		t7.setText(lastFmApi.getEventInfo(0,x));
		t8.setText(lastFmApi.getEventInfo(1, x));
		t9.setForeground(Color.blue);
		
		final String tmp = lastFmApi.getEventInfo(2, x);
		t9.setText(tmp);
		t9.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				t9.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				t9.setCursor(new Cursor(Cursor.HAND_CURSOR));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenBrowser.openURL(tmp);
			}
		});
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(passwordPanel == null){
			passwordPanel = new LFMPasswordPanel(this);
		}
		passwordPanel.setVisible(true);
		loggedGUIPart1();
		loggedGUIPart2();
		
		timer = new Timer(8000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loggedGUIPart2();
			}
		}); 
		
		timer.start();
	}
}
