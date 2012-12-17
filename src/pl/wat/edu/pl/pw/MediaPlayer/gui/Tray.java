package pl.wat.edu.pl.pw.MediaPlayer.gui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import pl.wat.edu.pl.pw.MediaPlayer.JFXMediaPlayer;
import pl.wat.edu.pl.pw.MediaPlayer.other.Property;

public class Tray {
	static JFXMediaPlayer mediaPlayer = null;
	static PlayerFrame frame = null;
	private static int i = 0;
	
	public static void createAndShowGUIForTray(final JFXMediaPlayer mediaPlayer, final PlayerFrame frame) {
		
		// Check the SystemTray support
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		}

		final PopupMenu popup = new PopupMenu();
		final TrayIcon trayIcon = new TrayIcon(createImage("/images/tray.png", "tray icon"));
		trayIcon.setImageAutoSize(true);
		
		final SystemTray tray = SystemTray.getSystemTray();

		// Create a popup menu components
		MenuItem aboutItem = new MenuItem("About");
		MenuItem nextItem = new MenuItem("Next");
		MenuItem prevItem = new MenuItem("Prev");
		MenuItem pauseItem = new MenuItem("Play / Pause");
		//MenuItem addFileItem = new MenuItem("Add File");
		
		
		Menu displayMenu = new Menu("Display");

		MenuItem errorItem = new MenuItem("Error");
		MenuItem warningItem = new MenuItem("Warning");
		MenuItem infoItem = new MenuItem("Info");
		MenuItem noneItem = new MenuItem("None");
		MenuItem exitItem = new MenuItem("Exit");
		
		
		Menu chooseSkin = new Menu("Skin");
		MenuItem skin0 = new MenuItem("Windows");
		MenuItem skin1 = new MenuItem("Acryl");
		MenuItem skin2 = new MenuItem("HiFi");
		MenuItem skin3 = new MenuItem("Bernstein");
		MenuItem skin4 = new MenuItem("Noire");
		MenuItem skin5 = new MenuItem("Nimbus");

		// Add components to popup menu
		popup.add(aboutItem);
		popup.addSeparator();
		popup.add(pauseItem);
		popup.add(nextItem);
		popup.add(prevItem);
		popup.addSeparator();
		popup.add(chooseSkin);
		
		chooseSkin.add(skin0);
		chooseSkin.add(skin1);
		chooseSkin.add(skin2);
		chooseSkin.add(skin3);
		chooseSkin.add(skin4);
		chooseSkin.add(skin5);
		//popup.add(addFileItem);		
		//popup.addSeparator();
		//popup.add(displayMenu);
		
		displayMenu.add(errorItem);
		displayMenu.add(warningItem);
		displayMenu.add(infoItem);
		displayMenu.add(noneItem);
		popup.add(exitItem);

		trayIcon.setPopupMenu(popup);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("Nie dodano ikony Tray.");
			return;
		}

		trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// = new PlayerFrame(mediaPlayer);
				frame.setVisible(true);
				//JOptionPane.showMessageDialog(null,
				//"<HTML><B>Odtwarzacz multimediów z harmonogramem</B></HTML>\n");
			}
		});
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								null,
								"<HTML><B>Odtwarzacz multimediów z harmonogramem</B></HTML>\n"
										+ "<HTML><font color=red><b>Projekt i wykonanie:</b></HTML>\n"
										+ "<HTML><font color=green><ul><li>Kielan Mariusz<li>Kotowski £ukasz</ul></font></HTML>"
						);
			}
		});

		nextItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.next();
			}
		});

		prevItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.prev();
			}
		});

		
		pauseItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				i++;
				if (i%2 == 1) {
					mediaPlayer.pause();
				}else {
					mediaPlayer.play();
				}
			}
		});
		
		skin0.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSkin(0);
			}
		});
		skin1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSkin(1);
			}
		});
		
		skin2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSkin(2);
			}
		});
		
		
		skin3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSkin(3);
			}
		});
		
		skin4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSkin(4);
			}
		});
		
		skin5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSkin(5);
			}
		});
		
		// tutaj mamy obs³ugê zdarzeñ po kliknieciu na np error itd.

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuItem item = (MenuItem) e.getSource();
				// TrayIcon.MessageType type = null;
				System.out.println(item.getLabel());
				if ("Error".equals(item.getLabel())) {
					// type = TrayIcon.MessageType.ERROR;
					trayIcon.displayMessage("Sun TrayIcon Demo",
					"This is an error message", TrayIcon.MessageType.ERROR);
				} else if ("Warning".equals(item.getLabel())) {
					// type = TrayIcon.MessageType.WARNING;
					trayIcon.displayMessage("Sun TrayIcon Demo",
					"This is a warning message", TrayIcon.MessageType.WARNING);
				} else if ("Info".equals(item.getLabel())) {
					// type = TrayIcon.MessageType.INFO;
					trayIcon.displayMessage("Sun TrayIcon Demo",
					"This is an info message", TrayIcon.MessageType.INFO);
				} else if ("None".equals(item.getLabel())) {
					// type = TrayIcon.MessageType.NONE;
					trayIcon.displayMessage("Sun TrayIcon Demo",
					"This is an ordinary message", TrayIcon.MessageType.NONE);
				}
			}
		};

		errorItem.addActionListener(listener);
		warningItem.addActionListener(listener);
		infoItem.addActionListener(listener);
		noneItem.addActionListener(listener);
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.stop();
				frame.setVisible(false);
				tray.remove(trayIcon);
				Property prop = new Property();
				prop.setPlayerMute(mediaPlayer.isMute());
				prop.setPlayerRepeat(mediaPlayer.isRepeat());
				prop.setPlayerVolumeLevel(mediaPlayer.getVolume());
				mediaPlayer.getPlayListModel().saveToDB();
				System.exit(0);
			}
		});
	}


	
	// Obtain the image URL
	protected static Image createImage(String path, String description) {
		URL imageURL = Tray.class.getResource(path);
		if (imageURL == null) {
			System.err.println("Nie znaleziono zasobów: " + path);
			return null;
		} else {
			return (new ImageIcon(imageURL, description)).getImage();
		}
	}

	public static void main(String[] args) {
		/* Use an appropriate Look and Feel */
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}

		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				createAndShowGUIForTray(mediaPlayer,frame);
			}

		});

	}
}
