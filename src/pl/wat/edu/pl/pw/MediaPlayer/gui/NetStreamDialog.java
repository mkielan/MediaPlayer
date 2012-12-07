package pl.wat.edu.pl.pw.MediaPlayer.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NetStreamDialog extends JDialog {

	protected URI uri = null;
	
	public NetStreamDialog(JDialog parent) {
		super(parent, "Add online stream", true);
		setLayout(new BorderLayout());
		setSize(400, 110);
		setLocationRelativeTo(null);
		setResizable(false);
		
		JLabel label = new JLabel("Give URL of the network:");
		final JTextField text = new JTextField(20);
		
		
		JButton addButton = new JButton("Add");
		addButton.setToolTipText("Add stream");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					uri = new URI(text.getText());
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
				
				setVisible(false);
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setToolTipText("Cancel adding stream");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(addButton);
		buttonPanel.add(cancelButton);
		
		add(label, BorderLayout.PAGE_START);
		add(text, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.PAGE_END);	
		
		pack();
	}
	
	public URI getURI() {
		return uri;
	}
	public static void main(String[] args) {
		NetStreamDialog dialog = new NetStreamDialog(null);
		dialog.setVisible(true);
	}
}
