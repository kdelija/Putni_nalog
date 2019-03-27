package gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Controller;
import model.PutniNalog;


/**
 * AppFrame pozicionira sve klase u glavnom prozoru.
 * Preko AppFramea se odvija komunikacija izmedu ostalih klasa
 * 
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 *
 */

public class AppFrame extends JFrame{
	
	private JTabbedPane tabbedPane;
	private NoviNalogTab noviNalogTab;
	private DataBaseTab dataBaseTab;
	private ZavrsiNalogTab zavrsiNalogTab;
	private Controller controller;

	
	/**
	 * Konstruktor odreduje velicinu prozora i postavlja sve
	 * elemente 
	 */
	public AppFrame() {
		
		super("Putni nalog app");
//		setSize(850, 600);

		setSize(700, 890);
		setLocationRelativeTo(null);
		// Postavlja prozor za cijeli ekran
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
	//	setUndecorated(true);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		setResizable(false);
		initAll();
		layoutAll();
		activateComps();
		connectAndGetMySqlDB();
	
	}
	/**
	 * Metoda postavlja Layout Menagera i aktivira MenuBar
	 */
	private void layoutAll() {
		
		setLayout(new BorderLayout());
		add(tabbedPane);
		this.setJMenuBar(new MenuBar());
		MenuBar.activateMenuBar(controller, dataBaseTab);

		
	}
	
	/**
	 * Metoda pri otvaranju aplikacije povezuje aplijaciju sa MySql-bazom
	 * i postavlja elemente iz baze u DataBaseTab
	 */
	private void connectAndGetMySqlDB() {
		
		try {
			controller.connect2DBS();
		} catch (Exception e) {
			int connection = JOptionPane.showConfirmDialog(null, "Spajanje s bazom podataka neuspiješno. Da li želite ponovno pokušati", "Error", JOptionPane.YES_NO_OPTION); 
			if (connection == JOptionPane.YES_OPTION) {
				connectAndGetMySqlDB();
			} else {
				JOptionPane.showMessageDialog(null,"Pokušajte kasnije preko File - Load4DBS", "Info", JOptionPane.WARNING_MESSAGE);				

			}
		}	
		
		controller.load4DBS();
		//postavlja brNaloga u noviNalogTab
		generateBrNalogaFieldNumber(controller.getAllFromDataBase());
		
	}
	
	/**
	 * Inicijalizira sve komponente
	 */
	private void initAll() {
		
		controller = new Controller();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		noviNalogTab = new NoviNalogTab();
		dataBaseTab = new DataBaseTab(controller.getDBS());
		zavrsiNalogTab = new ZavrsiNalogTab();
		
		// Baza podatka tab
		tabbedPane.addTab("Baza podataka", null, dataBaseTab, "Alt+2 hotkey");
		// Alt + 1
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
				
		
		// Novi nalog tab
		tabbedPane.addTab("Novi putni nalog",null, noviNalogTab, "Alt+1 hotkey");
		// Alt + 2
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		
		
		//Zavrsi nalog tab
		tabbedPane.addTab("Završi nalog", null, zavrsiNalogTab, "Alt+3 hotkey");
		// Alt + 3
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		
		tabbedPane.setSelectedIndex(1);
		


	}
	
	/**
	 * Automatski gegerira Br Naloga za novi nalog tab
	 * @param lista od koje dobavlja BrNaloga zadnjeg putnog naloga u bazi
	 */
	private void generateBrNalogaFieldNumber(List<PutniNalog> list) {
		
		if (list.size() == 0) {
			
			noviNalogTab.setBrNalogaField("5000");
		} else {
			
			int brNaloga = list.get(list.size()-1).getBrojNaloga() + 1;
			String brString = Integer.toString(brNaloga);
			noviNalogTab.setBrNalogaField(brString);
		}
		
	}
	
	
	/**
	 * Metoda postavlja slusace na objekte AppFrame-a
	 * i dodjeljuje im funkcionalnost komuniciranja sa bazom podataka
	 */
	public void activateComps() {

		
		noviNalogTab.setNalogPanelListener(new NoviNalogTabListener() {
			
			@Override
			public void novNalogEventOccured(NoviNalogEvent fe) {
				

			//	int sz = controller.getAllFromDataBase().size();
				controller.setNewPutniNalog2DBS(fe);
				controller.save2DBS();
				dataBaseTab.refreshTableView();
				tabbedPane.setSelectedIndex(0);
				generateBrNalogaFieldNumber(controller.getAllFromDataBase());

			}
		});
		
		zavrsiNalogTab.setZavrsniNalogTabListener(new ZavrsniNalogTabListener() {
			
			@Override
			public void newZavrsniNalogEventOccured(ZavrsniNalogEvent ze) {
			
				controller.updateAndClosePutniNalogFromDB(ze);
				controller.save2DBS();
				tabbedPane.setSelectedIndex(0);

			}
		});
		
		dataBaseTab.setDataBaseTabListener(new DataBaseTabListener() {
			
			@Override
			public void dataBaseTabDelteEventOccured(int id) {
				
				controller.removePutniNalog4MySQL(id);
				controller.removePutninalogLocaly(id);
				dataBaseTab.refreshTableView();
			}

			@Override
			public void dataBaseTabCompleteEventOccured(int id) {
				
			//	System.out.println("zavrsi nalog btn radi");
				PutniNalog pNalog = controller.getPutniNalogFromDB(id);
				
				if (pNalog.getStanjeNaloga().equals("Nalog u obradi")) {
					
					zavrsiNalogTab.setPutniNalog(pNalog, id);
					tabbedPane.setSelectedIndex(2);
				} else if (pNalog.getStanjeNaloga().equals("Završen nalog")) {
					
					JOptionPane.showMessageDialog(null,"Nalog je već završen", "Error", JOptionPane.WARNING_MESSAGE);				

				}

			}
		});
		
		// Odspaja aplikaciju od MySql baze kod zatvaranja prozora
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
		     
		    	controller.disconnect4DBS();
		        e.getWindow().dispose();
		    }
		});
	}
	
	
	

}
