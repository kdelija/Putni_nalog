package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.NumberFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import model.PutniNalog;


/**
 *  ZavrsiNalogTab stvara formular za zatvaranje postijeceg PutnogNaloga
 *  
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 * 
 */
public class ZavrsiNalogTab extends JPanel{
	
	private JTextField brNalogaField;
	private JTextField zaposlenikField;
	private JTextField voziloField;
	private JTextField odredisteField;
	private JFormattedTextField pocetniKmField;
	private JFormattedTextField zavrsniKmField;
	private JFormattedTextField ukupanPutKmField;

	

	// Date picker model
	private JTextField datumNalogaField;
	private UtilDateModel datumPolaskaModel;
	private UtilDateModel datumPovratkaModel;

	private JDatePanelImpl datumPolaskaPanel;
	private JDatePanelImpl datumPovratkaPanel;

	private JDatePickerImpl datumPolaskaPicker;
	private JDatePickerImpl datumPovratkaPicker;
	
	
	//JButtons
	private JButton endBtn;
	private JButton dismisBtn;
	private JButton calcKm;
	
	private PutniNalog putniNalog;
//	private int id;
	private ZavrsniNalogTabListener listener;


	
	public ZavrsiNalogTab() {
	
		
		initComps();
		layoutComps();
		activateForm();
	
	}
	/**
	 * Inicijalizira komponente
	 */
	private void initComps() {
		
		
		int lenght = 30;
		brNalogaField = new JTextField(lenght);
		zaposlenikField = new JTextField(lenght);
		voziloField = new JTextField(lenght);
		odredisteField = new JTextField(lenght);
		datumNalogaField = new JTextField(lenght);
//		pocetniKmField = new JTextField(lenght);
//		zavrsniKmField = new JTextField(lenght);
//		ukupanPutKmField = new JTextField(lenght);
		
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		pocetniKmField = new JFormattedTextField(formatter);
		zavrsniKmField = new JFormattedTextField(formatter);
		ukupanPutKmField = new JFormattedTextField(formatter);
	//	ukupanPutKmField.setEnabled(false);
		disableEditing(ukupanPutKmField);

		
		
		//Onemogucava promojenu texta u JtextFieldu
		disableEditing(brNalogaField);
		disableEditing(zaposlenikField);
		disableEditing(voziloField);
		disableEditing(datumNalogaField);
		disableEditing(odredisteField);


		
		
		
		// Date picker model
		
		datumPolaskaModel = new UtilDateModel();
		datumPovratkaModel = new UtilDateModel();
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datumPolaskaPanel = new JDatePanelImpl(datumPolaskaModel, p);
		datumPovratkaPanel = new JDatePanelImpl(datumPovratkaModel, p);

		datumPolaskaPicker = new JDatePickerImpl(datumPolaskaPanel, new DateLabelFormatter());
		datumPovratkaPicker = new JDatePickerImpl(datumPovratkaPanel, new DateLabelFormatter());
		
		datumPolaskaPicker.getJFormattedTextField().setBackground(Color.WHITE);
		datumPovratkaPicker.getJFormattedTextField().setBackground(Color.WHITE);

		
		
		// Btns
		endBtn = new JButton("Završi nalog");
		dismisBtn = new JButton("Odbaci");
		calcKm = new JButton("Izračunaj prijeđene Km:");
		
		Color color = UIManager.getColor ( "Panel.background" );
		calcKm.setBackground(color);
		calcKm.setContentAreaFilled(false);
		
		
	}
	
	/**
	 * Postavlja se logacija i izgled komponenti
	 */
	private void layoutComps() {
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		// First row
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 8;
		gbc.ipadx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(20, 30, 5, 15);
		add(new JLabel("Broj naloga:"), gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(brNalogaField, gbc);
		
		// Second row
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Zaposlenik:"), gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(zaposlenikField, gbc);
		
		// Third row
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Datum naloga:"), gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(datumNalogaField, gbc);
		
		// Fourth row
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Datum polaska:"), gbc);
		
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(datumPolaskaPicker, gbc);
		gbc.fill = GridBagConstraints.NONE;
		
		
		// Fifth row
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Datum povratka:"), gbc);
		
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(datumPovratkaPicker, gbc);
		gbc.fill = GridBagConstraints.NONE;
		
		// Sixth row
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Odredište:"), gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(odredisteField, gbc);
		
		// Seventh row
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Vozilo:"), gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(voziloField, gbc);
		
		// Eighth row
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Početno km:"), gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(pocetniKmField, gbc);
		gbc.fill = GridBagConstraints.NONE;

		
		// Ninth row
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Završno km:"), gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(zavrsniKmField, gbc);
		gbc.fill = GridBagConstraints.NONE;

		
		//Tenth row
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(calcKm, gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(ukupanPutKmField, gbc);
		gbc.fill = GridBagConstraints.NONE;

		
		// Eleventh row
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.insets = new Insets(60, 70, 30, 0);
		gbc.ipadx = 0;	
		gbc.ipady = 5;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(endBtn, gbc);
		
		gbc.gridx = 1;
		gbc.insets = new Insets(60, 70, 30, 0);
		gbc.anchor = GridBagConstraints.LINE_START;
		add(dismisBtn, gbc);
		
	}
	
	private void activateForm() {
		
		
		endBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (putniNalog != null) {
							
					if (pocetniKmField.getValue() != null 
							&& zavrsniKmField.getValue() != null 
							&& ukupanPutKmField.getValue() != null 
							&& !datumPolaskaPicker.getJFormattedTextField().getText().equals("") 
							&& !datumPovratkaPicker.getJFormattedTextField().getText().equals("")) {
						
						System.out.println("");
						System.out.println("Sve je u redu");
						System.out.println("");
						
						int id = putniNalog.getId();
						String datumPolaska = datumPolaskaPicker.getJFormattedTextField().getText();
						String datumPovratka = datumPovratkaPicker.getJFormattedTextField().getText();
						int zavrsniKm = (Integer) zavrsniKmField.getValue();
						int pocetniKm = (Integer) pocetniKmField.getValue();
						int ukupanPutKm = (Integer) ukupanPutKmField.getValue();
					
						if(listener != null) {
							
							ZavrsniNalogEvent zaNalogEvent = new ZavrsniNalogEvent(this, id, datumPolaska, datumPovratka, zavrsniKm, pocetniKm, ukupanPutKm);
							listener.newZavrsniNalogEventOccured(zaNalogEvent);
						}
						
						
						clearPanel();
						putniNalog = null;
						
					} else {
						
						JOptionPane.showMessageDialog(null,"Ispunite sva polja te potom kliknite na Izračunaj prijeđene Km button", "Error", JOptionPane.PLAIN_MESSAGE);				
					}
					
					//test
//					System.out.println("poc km " + pocetniKmField.getValue());
//					System.out.println("zavrs km " + zavrsniKmField.getValue() );
//					System.out.println("izrac km  " + ukupanPutKmField.getValue());
//					System.out.println("poc datum " + datumPolaskaPicker.getJFormattedTextField().getText().getClass());
//					System.out.println("kraj datiun " + datumPovratkaPicker.getJFormattedTextField().getText());
						
				} 
				
				
			}
		});
		
		
		
		dismisBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				clearPanel();
				putniNalog = null;

			
			}
		});
		
		calcKm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// provijerava da li su dva polja prazna
				if(pocetniKmField.getValue() != null && zavrsniKmField != null) {
					
					int zavrskikm = (Integer) zavrsniKmField.getValue();
					int pocetnikm =	(Integer) pocetniKmField.getValue();
					
					// povijrava da li je zavrsno polje vece ili jednako pocentom polju
					if(zavrskikm >= pocetnikm) {
						
						ukupanPutKmField.setValue(zavrskikm - pocetnikm);
					} else {
						
						JOptionPane.showMessageDialog(null,"Završno stanje kilometera mora biti veće od početnog", "Error", JOptionPane.PLAIN_MESSAGE);				

					}
				} else {
					
					JOptionPane.showMessageDialog(null,"Ispunite Početno Km polje i Završno Km polje", "Error", JOptionPane.PLAIN_MESSAGE);				

				}
			}
		});
		
	}
	
	/**
	 * Postavlja podatke iz pNaloga na  ZavrsniNalogTab
	 * @param pNalog PutniNalog Iz kojeg se dohvacaju podatci
	 * @param id
	 */
	public void setPutniNalog(PutniNalog pNalog, int id) {
		
		int brojNaloga = pNalog.getBrojNaloga();
		String name = pNalog.getName();
		String datumNaloga = pNalog.getDatumNaloga();
		String datumPolaska = pNalog.getDatumPolaska();
		String datumPovratka = pNalog.getDatumPovratka();
		String odrediste = pNalog.getOdrediste();
		String vozilo = pNalog.getVozilo();
		
		brNalogaField.setText(Integer.toString(brojNaloga));
		zaposlenikField.setText(name);
		datumNalogaField.setText(datumNaloga);
		datumPolaskaPicker.getJFormattedTextField().setText(datumPolaska);
		datumPovratkaPicker.getJFormattedTextField().setText(datumPovratka);
		odredisteField.setText(odrediste);
		voziloField.setText(vozilo);
		
		this.putniNalog = pNalog;
//		this.id = id;
		System.out.println("Putni nalog u zavrsnom tabu je " + putniNalog);
	
		
	}

	
	private void disableEditing(JTextField textField) {
		
		textField.setBackground(Color.WHITE);
		textField.setEditable(false);
		
	}
	
	/**
	 * Briše sve vrijednosti u formularu
	 */
	private void clearPanel() {
		
		brNalogaField.setText("");
		zaposlenikField.setText("");
		datumNalogaField.setText("");
		datumPolaskaPicker.getJFormattedTextField().setText("");
		datumPovratkaPicker.getJFormattedTextField().setText("");
		odredisteField.setText("");
		voziloField.setText("");
		pocetniKmField.setValue(null);
		zavrsniKmField.setValue(null);
		ukupanPutKmField.setValue(null);
		
	}

	public void setZavrsniNalogTabListener(ZavrsniNalogTabListener listener) {
		this.listener = listener;
	}
	
	


}
