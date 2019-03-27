package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.NumberFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


/**
 *  NoviNalogTab stvara formular za stvaranje novog PutnogNaloga
 *  
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 * 
 */
public class NoviNalogTab extends JPanel {
	
	
	// Text Fields
	private JTextField brNalogaField;
	private JTextField mjestoNalogaField;
	private JTextField odredisteField;
	private JTextField svrhaField;
	private JFormattedTextField predujamField;
	private JTextField zaposlenikField;
	private JTextField voziloField;
	
	
	
	// JComboBox zaposlenici
	private JComboBox<String> zaposlenikCombo;
	private DefaultComboBoxModel<String> zaposleniciModel;
	
	// JComboBox vozila
	private JComboBox<String> vozilaCombo;
	private DefaultComboBoxModel<String> vozilaModel;
	private ArrayList<String> vozila;
	
	//JComboBox troskovi
	private JComboBox<String> troskoviCombo;
	private DefaultComboBoxModel<String> troskoviModel;

	
	//JButtons
	private JButton saveBtn;
	private JButton dismisBtn;
	private JButton addZaposlenikBtn;
	private JButton addVoziloBtn;
	
	
	// Date picker model
	private UtilDateModel datumNalogaModel;
	private UtilDateModel datumPolaskaModel;
	private UtilDateModel datumPovratkaModel;

	private JDatePanelImpl datumNalogaPanel;
	private JDatePanelImpl datumPolaskaPanel;
	private JDatePanelImpl datumPovratkaPanel;

	private JDatePickerImpl datumNalogaPicker;
	private JDatePickerImpl datumPolaskaPicker;
	private JDatePickerImpl datumPovratkaPicker;
	
	private String datumNaloga;
	private String datumPolaska;
	private String datumPovratka; 
	
	private NoviNalogTabListener listener;

	

	
	public NoviNalogTab() {
		
	
		initComps();
		layoutComps();
		activateForm();
		

	}
	/**
	 * Inicijalizira komponente
	 */
	private void initComps() {
		
		int lenght = 30;
		
		
		// Text fields
		brNalogaField = new JTextField(lenght);
		brNalogaField.setBackground(Color.WHITE);
		brNalogaField.setEditable(false);
		mjestoNalogaField = new JTextField(lenght);

		odredisteField = new JTextField(lenght);
		svrhaField = new JTextField(lenght);
//		predujamField = new JTextField(lenght);
		
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		predujamField = new JFormattedTextField(formatter);
		
		
		// Btns
		saveBtn = new JButton("Spremi");
		dismisBtn = new JButton("Odbaci");
		addVoziloBtn = new JButton("Dodaj vozilo:");
		setBtn2BackgroudColor(addVoziloBtn);
		addZaposlenikBtn = new JButton("Dodaj zaposlenika:");
		setBtn2BackgroudColor(addZaposlenikBtn);
		
		datumNalogaModel = new UtilDateModel();
		datumPolaskaModel = new UtilDateModel();
		datumPovratkaModel = new UtilDateModel();

		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datumNalogaPanel = new JDatePanelImpl(datumNalogaModel, p);
		datumPolaskaPanel = new JDatePanelImpl(datumPolaskaModel, p);
		datumPovratkaPanel = new JDatePanelImpl(datumPovratkaModel, p);

		datumNalogaPicker = new JDatePickerImpl(datumNalogaPanel, new DateLabelFormatter());
		datumPolaskaPicker = new JDatePickerImpl(datumPolaskaPanel, new DateLabelFormatter());
		datumPovratkaPicker = new JDatePickerImpl(datumPovratkaPanel, new DateLabelFormatter());
		
		datumNalogaPicker.getJFormattedTextField().setBackground(Color.WHITE);
		datumPolaskaPicker.getJFormattedTextField().setBackground(Color.WHITE);
		datumPovratkaPicker.getJFormattedTextField().setBackground(Color.WHITE);

		
		zaposlenikCombo = new JComboBox<>();
		zaposlenikCombo.setBackground(Color.WHITE);
		zaposleniciModel = new DefaultComboBoxModel<>();
		
		vozilaCombo = new JComboBox<>();
		vozilaCombo.setBackground(Color.WHITE);
		vozilaModel = new DefaultComboBoxModel<>();
		vozila = new ArrayList<>();
		vozila.add("Vozila:");
		vozila.add("Volkswagen Golf ZD-336-VJ");
		vozila.add("Opel Astra ZG-668-AM");
		vozila.add("Nissan Qashqai ZD-789-KL");
		
		troskoviCombo = new JComboBox<>();
		troskoviCombo.setBackground(Color.WHITE);
		troskoviModel = new DefaultComboBoxModel<>();
		
	}
	
	/**
	 * Postavlja se logacija i izgled komponenti
	 */
	private void layoutComps() {
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		// Zaposlenici comboBox
		zaposleniciModel.addElement("Zaposlenici:");
		zaposleniciModel.addElement("Mato Matić");
		zaposleniciModel.addElement("Ivo Ivić");
		zaposleniciModel.addElement("Vuk Delija");
		zaposlenikCombo.setModel(zaposleniciModel);
		zaposlenikCombo.setSelectedIndex(0);
		
		// Vozila comboBox
//		vozilaModel.addElement("Vozila:");
//		vozilaModel.addElement("Volkswagen Golf ZD-336-VJ");
//		vozilaModel.addElement("Opel Astra ZG-668-AM");
//		vozilaModel.addElement("Nissan Qashqai ZD-789-KL");
		addVozilaFromList2ComboBox();
		vozilaCombo.setModel(vozilaModel);
		vozilaCombo.setSelectedIndex(0);
		
		
		troskoviModel.addElement("Zaposlenik");
		troskoviModel.addElement("Poduzece");
		troskoviCombo.setModel(troskoviModel);
		troskoviCombo.setSelectedIndex(0);
		
		
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
		add(new JLabel("Mjesto naloga:"), gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(mjestoNalogaField, gbc);
		
		
		// Third row
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Datum naloga:"), gbc);
		
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(datumNalogaPicker, gbc);
		
		// Fourth row
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.ipady = 0;
		add(addZaposlenikBtn, gbc);
		
		gbc.ipady = 8;
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.ipady = 5;
		add(zaposlenikCombo, gbc);
		gbc.fill = GridBagConstraints.NONE;

		
		// Fifth row
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.ipady = 8;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Datum polaska:"), gbc);
		
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(datumPolaskaPicker, gbc);
		gbc.fill = GridBagConstraints.NONE;

		// Sixth row
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Datum povratka:"), gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(datumPovratkaPicker, gbc);
		gbc.fill = GridBagConstraints.NONE;
		
		// Seventh row
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Odredišta:"), gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(odredisteField, gbc);
		
		
		// Eighth row
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Svrha:"), gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(svrhaField, gbc);
		
		// Ninth row
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.ipady = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(addVoziloBtn, gbc);
		gbc.ipady = 8;

		
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(vozilaCombo, gbc);
		gbc.fill = GridBagConstraints.NONE;

		
		// Tenth row
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Troškove tereti:"), gbc);
		
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(troskoviCombo, gbc);
		gbc.fill = GridBagConstraints.NONE;

		
		// Eleventh row
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Predujam:"), gbc);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(predujamField, gbc);
		gbc.fill = GridBagConstraints.NONE;

		
		//Twelfth row
		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.insets = new Insets(60, 20, 25, 0);
		gbc.ipadx = 20;	
		gbc.ipady = 5;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(saveBtn, gbc);
		
		gbc.gridx = 1;
		gbc.insets = new Insets(60, 70, 25, 0);
		gbc.anchor = GridBagConstraints.LINE_START;
		add(dismisBtn, gbc);
	
		
	}

	
	private void activateForm() {
		
		saveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				datumNaloga = datumNalogaPicker.getJFormattedTextField().getText();
				datumPolaska = datumPolaskaPicker.getJFormattedTextField().getText();
				datumPovratka = datumPovratkaPicker.getJFormattedTextField().getText();
				
				if (checkFormValidation()) {
					
					int  brNaloga = Integer.parseInt( brNalogaField.getText());
					String  mjestoNaloga = mjestoNalogaField.getText();
					String  odrediste = odredisteField.getText();
					String  svrha = svrhaField.getText();
					int  predujam = (Integer) predujamField.getValue();
					String  zaposlenik = zaposleniciModel.getSelectedItem().toString();
					String  vozilo = vozilaModel.getSelectedItem().toString();
					String teretTroska = troskoviModel.getSelectedItem().toString();
					
		
					
					if(listener != null) {
						NoviNalogEvent noEvent = new NoviNalogEvent(this, zaposlenik, brNaloga, datumNaloga, datumPolaska, datumPovratka, odrediste, svrha, vozilo, predujam, 0, 0, teretTroska, 0);
						listener.novNalogEventOccured(noEvent);
					}
					
					resetNoviNalogPanel();
					JOptionPane.showMessageDialog(null,"Zaposlenik je dodan u bazu podataka", "Success", JOptionPane.PLAIN_MESSAGE);				
					
					
				} else {
					
					JOptionPane.showMessageDialog(null,"Morate ispuniti sva polja", "Error", JOptionPane.PLAIN_MESSAGE);				

				}
	
				
			}
		});
		
		dismisBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				resetNoviNalogPanel();
			
			}
		});
		
		addZaposlenikBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
			//	String s = (String)JOptionPane.showInputDialog(this,"Complete the sentence:\n"+ "\"Green eggs and...\"","Customized Dialog","Oj",null);
				
				String name = JOptionPane.showInputDialog("Uneste ime zaposlenika", "Npr. Ante Antić?");
				if (name == null || name.length() == 0) {
					JOptionPane.showMessageDialog(null,"Nije uneseno ime u listu", "Warning", JOptionPane.WARNING_MESSAGE);				
				} else {
					
					zaposleniciModel.addElement(name);
					JOptionPane.showMessageDialog(null,"Zaposlenik je dodan u listu", "Success", JOptionPane.PLAIN_MESSAGE);				

				}

			
			
			}	
		});
		
		addVoziloBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				String marka = JOptionPane.showInputDialog("Uneste marku i registraciju vozila", "Npr. Ford Focus ZG-665-PG");
				if (marka == null || marka.length() == 0) {
					JOptionPane.showMessageDialog(null,"Nije uneseno auto u listu", "Warning", JOptionPane.WARNING_MESSAGE);				
				} else {
					
					vozila.add(marka);
					addVoziloFromApp2ComboBox(marka);
					JOptionPane.showMessageDialog(null,"Auto je dodano u listu", "Success", JOptionPane.PLAIN_MESSAGE);				
				
				}

			
			
			}
		});
		
		
		
		
	}
	
	private void addVozilaFromList2ComboBox() {
		
		
		for (String st : vozila) {
			
			vozilaModel.addElement(st);	
		}
		
	}
	
	private void addVoziloFromApp2ComboBox(String vozilo) {
		
		vozilaModel.addElement(vozilo);	

	}
	
	/**
	 * Provjerava ispravnost unosa u svim poljima
	 * @return boolean 
	 */
	private boolean checkFormValidation() {
		
		boolean flag;
		
		if (!mjestoNalogaField.getText().equals("") && 
				!datumNaloga.equals("") && 
				!zaposleniciModel.getSelectedItem().toString().equals("Zaposlenici:") && 
				!datumPolaska.equals("") && 
				!datumPovratka.equals("") && 
				!odredisteField.getText().equals("") && 
				!svrhaField.getText().equals("") && 
				!vozilaModel.getSelectedItem().toString().equals("Vozila:") && 
				(predujamField.getValue() != null)) {
			
		//	System.out.println("radi");
			flag = true;
			
		} else {
			//System.out.println("ne radi");
			flag = false;
		}
		
		return flag;
	}
	
	/**
	 * Briše sve vrijednosti u formularu
	 */
	public void resetNoviNalogPanel() {
		
		mjestoNalogaField.setText("");
		zaposlenikCombo.setSelectedIndex(0);
		odredisteField.setText("");
		svrhaField.setText("");
		vozilaCombo.setSelectedIndex(0);
		troskoviCombo.setSelectedIndex(0);
		predujamField.setValue(null);
		datumNalogaPicker.getJFormattedTextField().setText("");
		datumPolaskaPicker.getJFormattedTextField().setText("");
		datumPovratkaPicker.getJFormattedTextField().setText("");

		
		
	}
	

	public void setNalogPanelListener(NoviNalogTabListener nalogPanelListener) {
		this.listener = nalogPanelListener;
	}
	
	
	/**
	 * Postavlja boju JButtona
	 * @param btn
	 */
	private void setBtn2BackgroudColor(JButton btn) {
		
		Color color = UIManager.getColor ( "Panel.background" );
		btn.setBackground(color);
		btn.setContentAreaFilled(false);
		
	}
	
	
	public void setBrNalogaField(String br) {
		
		brNalogaField.setText(br);

	}
	
	
	

}
