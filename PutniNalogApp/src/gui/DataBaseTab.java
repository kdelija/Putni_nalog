package gui;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import model.DataBase;
import model.PutniNalog;

/**
 * DataBaseTab prikazuje bazu podataka u tablicnom obliku
 * Sadrzi tražilicu 
 *  
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 * 
 */
public class DataBaseTab extends JPanel{
	
	// Search panel 
	private JPanel searchPanel;
	private JTextField jtfFilter;
	
	
	// Table Panel
	private AbstractTableModel tableModel;
	
	private DataBase dataBase;
	private JTable table;
	private JPopupMenu popup;
	private JMenuItem deleteItem = new JMenuItem("Delete row");
	private JMenuItem zavrsiNalog = new JMenuItem("Zavrsi Nalog");

	private DataBaseTabListener listener;

	
	// search components
	private TableRowSorter<TableModel> rowSorter;

	
	public DataBaseTab(DataBase dataBase) {
		
		this.dataBase = dataBase;
		setLayout(new BorderLayout());
		initComps();
		add(searchPanel, BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		
	}
	
	/**
	 * Briše red u tablici
	 * @param row red za brisanje
	 */
	public void removeDBRow(int row) {
		
		tableModel.fireTableRowsDeleted(row, row);
	}
	
	/**
	 * Inicijalizira komponente
	 * Postavlja table i tableModel
	 * Postavlja TableRowSorter
	 */
	private void initComps() {
		
		// Search Panel
		searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jtfFilter = new JTextField(40);
		searchPanel.add(new JLabel("Pretražite bazu podataka: "));
		searchPanel.add(jtfFilter);
			

		
		tableModel = new AbstractTableModel() {

			String[] columNames = { "ID", "Name", "Stanje Naloga", "Br. Naloga", "Datum Naloga", "Polazak", "Povratak",
									"Odrediste", "Svrha", "Vozilo", "Predujam", "PocetniKm", "ZavrsniKm", "PrijedeniKm", "Trošak"};

			@Override
			public String getColumnName(int col) {
				return columNames[col];
			}

			@Override
			public Object getValueAt(int row, int col) {
				switch (col) {

				case (0):
					return dataBase.getAllPutniNalogs().get(row).getId();
				case (1):
					return dataBase.getAllPutniNalogs().get(row).getName();
				case (2):
					return dataBase.getAllPutniNalogs().get(row).getStanjeNaloga();
				case (3):
					return dataBase.getAllPutniNalogs().get(row).getBrojNaloga();
				case (4):
					return dataBase.getAllPutniNalogs().get(row).getDatumNaloga();
				case (5):
					return dataBase.getAllPutniNalogs().get(row).getDatumPolaska();
				case (6):
					return dataBase.getAllPutniNalogs().get(row).getDatumPovratka();
				case (7):
					return dataBase.getAllPutniNalogs().get(row).getOdrediste();
				case (8):
					return dataBase.getAllPutniNalogs().get(row).getSvrha();
				case (9):
					return dataBase.getAllPutniNalogs().get(row).getVozilo();
				case (10):
					return dataBase.getAllPutniNalogs().get(row).getPredujam();
				case (11):
					return dataBase.getAllPutniNalogs().get(row).getPocetniKm();
				case (12):
					return dataBase.getAllPutniNalogs().get(row).getZavrsniKm();
				case (13):
					return dataBase.getAllPutniNalogs().get(row).getPrijedenoKm();
				case (14):
					return dataBase.getAllPutniNalogs().get(row).getTeretTroska();
				
				}
				return null;
			}

			@Override
			public int getRowCount() {
				int sz = dataBase.getAllPutniNalogs().size();
				return sz;

				
			}

			@Override
			public int getColumnCount() {
				return columNames.length;
			}
		};
		
		table = new JTable(tableModel);
		table.requestFocus();
		
		table.getColumnModel().getColumn(0).setPreferredWidth(39);
		table.getColumnModel().getColumn(1).setPreferredWidth(104);
		table.getColumnModel().getColumn(2).setPreferredWidth(116);
		table.getColumnModel().getColumn(3).setPreferredWidth(97);
		table.getColumnModel().getColumn(4).setPreferredWidth(123);
		table.getColumnModel().getColumn(5).setPreferredWidth(117);
		table.getColumnModel().getColumn(6).setPreferredWidth(119);
		table.getColumnModel().getColumn(7).setPreferredWidth(198);
		table.getColumnModel().getColumn(8).setPreferredWidth(155);
		table.getColumnModel().getColumn(9).setPreferredWidth(192);
		table.getColumnModel().getColumn(10).setPreferredWidth(84);
		table.getColumnModel().getColumn(11).setPreferredWidth(79);
		table.getColumnModel().getColumn(12).setPreferredWidth(73);
		table.getColumnModel().getColumn(13).setPreferredWidth(64);
		table.getColumnModel().getColumn(14).setPreferredWidth(91);



		
		JTableUtilities.setCellsAlignment(table, SwingConstants.CENTER);
		popup = createPopUpMenu();
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mev) {
				
				int row = table.rowAtPoint(mev.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);
				
				if(mev.getButton() == MouseEvent.BUTTON3) {
					popup.show(table, mev.getX(), mev.getY());
				}
			}
		});
		
		
		//search+
		rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        
        jtfFilter.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        });

	}
	/**
	 * Osvježava prikaz u tablici
	 */
	public void refreshTableView() {
		
		tableModel.fireTableDataChanged();
	}
	
	private static class JTableUtilities {
		
	    public static void setCellsAlignment(JTable table, int alignment) {
	    	
	        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	        rightRenderer.setHorizontalAlignment(alignment);

	        TableModel tableModel = table.getModel();

	        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++)
	        {
	            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
	        }
	    }
	}
	
	/*
	 *  Kreira menu s izbrisi red i dovrsi nalog gumbima
	 */
	private JPopupMenu createPopUpMenu() {
		
		JPopupMenu popup = new JPopupMenu("Table popup");
		popup.add(deleteItem);
		popup.addSeparator();
		popup.add(zavrsiNalog);
		activateDeleteRowFunctionality();
		return popup;
		
	}
	
	/**
	 * Aktivira i daje funkcionalnost izbrisi red i dovrsi nalog gumbima
	 */
	private void activateDeleteRowFunctionality() {
		
		deleteItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ev) {
				
				int row = table.getSelectedRow();
				int id = (Integer) table.getValueAt(row, 0);
				String name = (String) table.getValueAt(row, 1);
				int choice = JOptionPane.showConfirmDialog(null,"Potvrdite za brisanje odabranog reda " + id + ": " + name + " iz baze podataka", "Warning", JOptionPane.OK_CANCEL_OPTION);				
				
				if (choice == JOptionPane.YES_OPTION) {
					
	//				int row = table.getSelectedRow();
					if(listener != null) {
						listener.dataBaseTabDelteEventOccured(id);
					}
					System.out.println("Selected row to delete -> " + row);
				}
				
			}
		});
		
		zavrsiNalog.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int row = table.getSelectedRow();
				int id = (Integer) table.getValueAt(row, 0);
				if(listener != null) {
					listener.dataBaseTabCompleteEventOccured(id);
				}
				
			}
		});
	}
	
	
	public void setDataBaseTabListener(DataBaseTabListener lst) {
		this.listener = lst;
	}

}
