package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;

/**
 * MenuBar je dio AppFramea koji sadrzi funkcionalnos spremanja i ucitavanja baze u datoteku
 * Povezivanje i odspajanje sa MySql bazom
 * Ucitavanje i spremanje na  MySql bazu
 * 
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 *
 */

public class MenuBar extends JMenuBar {
	

	private static JMenu fileMenu;
	private static JMenuItem saveItem;
	private static JMenuItem openItem ;
	private static JMenuItem exitItem ;
	private static JMenuItem save2DBSItem;
	private static JMenuItem load4DBSItem;
	private static JMenuItem disconnect4DBSItem;
	private static JFileChooser fileChooser;

	public MenuBar() {
		initMenuBar();
	}

	private void initMenuBar() {

		fileMenu = new JMenu("File");
		saveItem = new JMenuItem("Save as...");
		openItem = new JMenuItem("Open...");
		exitItem = new JMenuItem("Exit");
		save2DBSItem = new JMenuItem("Save2DBS");
		load4DBSItem = new JMenuItem("Load4DBS");
		disconnect4DBSItem = new JMenuItem("Disconnect4DBS");

		fileMenu.add(saveItem);
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		fileMenu.add(save2DBSItem);
		fileMenu.add(load4DBSItem);
		fileMenu.addSeparator();
		fileMenu.add(disconnect4DBSItem);
		fileMenu.add(exitItem);

		this.add(fileMenu);
	}
	
	/**
	 * Aktivira komponente MenuBara
	 * @param controller služi za dohvacanje metoda baze podataka
	 * @param tablePanel služi za prikaz podataka 
	 */
	public static void activateMenuBar(Controller controller, DataBaseTab tablePanel) {

		// Setting mnemonics to FileMenu
		fileMenu.setMnemonic(KeyEvent.VK_F);
		// Setting accelerators 
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		save2DBSItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		load4DBSItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		disconnect4DBSItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		
		// Activate save to database item
		save2DBSItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller.connect2DBS();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Can not connect to DBS", "Databse status",JOptionPane.ERROR_MESSAGE);
				}
				controller.save2DBS();
				JOptionPane.showMessageDialog(null, "Success -> saved to DBS", "Databse status",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		// Activate load from database item
		load4DBSItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller.connect2DBS();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Can not connect to DBS", "Databse status",JOptionPane.ERROR_MESSAGE);
				}
				controller.load4DBS();
				controller.refreshDataBaseTable(tablePanel);
	//			controller.refreshViewPanel(viewPanel);
				
			}
		});
		
		// Activate disconnect item
		disconnect4DBSItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.disconnect4DBS();
			}
		});
		

		// Activate saveItem
		saveItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				fileChooser = new JFileChooser("Save file dialog");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("database files", "cli");
				fileChooser.setFileFilter(filter);
				fileChooser.setMultiSelectionEnabled(false);
				int returnVal = fileChooser.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					System.out.println(file.toString());
					try {
						controller.save2FileDBS(file);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("You save file: " + fileChooser.getSelectedFile().getName() + " : "
							+ fileChooser.getSelectedFile().getPath());
				} else if (returnVal == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null, "Canceled by user", "Cancel dialog", JOptionPane.CANCEL_OPTION);

				}

			}
		});

		// Activate openItem
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				fileChooser = new JFileChooser("Open file dialog");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("database files", "cli");
				fileChooser.setFileFilter(filter);
				int returnVal = fileChooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						controller.getDataBase4File(file);
						controller.refreshDataBaseTable(tablePanel);
					//	controller.refreshViewPanel(viewPanel);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("You choose to open file -> " + fileChooser.getSelectedFile().getName() + " : "
							+ fileChooser.getSelectedFile().getPath());
				} else if (returnVal == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null, "Canceled by user", "Cancel dialog", JOptionPane.CANCEL_OPTION);
				}

			}
		});

		// Activate exitItem

		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.disconnect4DBS();
				System.exit(0);
			}
		});
	}

}


