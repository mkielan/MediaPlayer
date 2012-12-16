package pl.wat.edu.pl.pw.MediaPlayer.gui.components;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import pl.wat.edu.pl.pw.MediaPlayer.gui.LastFmWindow;

public class LFMPasswordPanel extends JDialog {
	
	private JTextField userTextField;
	private JPasswordField userPasswordField;
	private JButton okButton;
	private JLabel textWelcome, textNICK, textPASSWORD;
	JFrame owner;
	
	
	public LFMPasswordPanel(JFrame owner) {
		super(owner, "Login", true);
		this.owner = owner;
		initLFMPasswordGui();
	}
	
	
	public void initLFMPasswordGui(){
		setTitle("Login window");
		setSize(300, 220);
		setLocationRelativeTo(null);
		setLayout(null);
		
		textWelcome = new JLabel("Please login to your Last.fm account");
		add(textWelcome);
		textWelcome.setBounds(10, 3, 360, 40);
		
		textNICK = new JLabel("Nick:");
		add(textNICK);
		textNICK.setBounds(10, 40, 40, 40);
		
		userTextField = new JTextField();
		add(userTextField);
		userTextField.setBounds(100, 45, 100, 30);
		
		textPASSWORD = new JLabel("Password:");
		add(textPASSWORD);
		textPASSWORD.setBounds(10, 80, 80, 40);
		
		userPasswordField = new JPasswordField();
		add(userPasswordField);
		userPasswordField.setBounds(100, 85, 100, 30);
		
		okButton = new JButton("Login");
		add(okButton);
		okButton.setBounds(115, 130, 85, 20);
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ok();
			}
		});
		
		KeyboardFocusManager  
	    .getCurrentKeyboardFocusManager()  
	    .addKeyEventDispatcher(new KeyEventDispatcher() {  
	    public boolean dispatchKeyEvent(KeyEvent e) {  
	        boolean keyHandled = false;  
	        if (e.getID() == KeyEvent.KEY_PRESSED) {  
	            if (e.getKeyCode() == KeyEvent.VK_ENTER) {  
	                ok();  
	                keyHandled = true;  
	            }  
	        }  
	        return keyHandled;  
	    }  
	}); 
		
	}

	private void ok(){
		this.setVisible(false);
	}
	
	public String getUser(){
		return userTextField.getText();
	}
	
	public String getPassword(){
		return new String (userPasswordField.getPassword());
	}
	
	public void setFocus(){
		userTextField.requestFocusInWindow();
	}
	
}
