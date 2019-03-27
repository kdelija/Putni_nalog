package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gui.DataBaseTab;
import gui.NoviNalogEvent;
import gui.ZavrsniNalogEvent;
import model.DataBase;
import model.PutniNalog;


/**
 * Controller komunicira s bazom podataka DataBase koja predstavlja skup
 * objekata tipa PutniNalog. Predstavlja granicu između View(gui) i Modela.
 * Gui koristi metode Controllera za pristup bazi podataka 
 *  
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 * 
 */
public class Controller {
	
	private DataBase dataBase;
	
	public Controller() {
	
		dataBase = new DataBase();
	}
	

	public DataBase getDBS() {

		return dataBase;
	}
	
	
	/**
	 * Metoda dohvaca sve elemente tipa PutniNalog iz baze podataka
	 * @return Lista svih putnih naloga 
	 */
	public List<PutniNalog> getAllFromDataBase() {
		
		return dataBase.getAllPutniNalogs();
		
	}
	
	/**
	 * Metoda dohvaca određeni element iz baze podataka
	 * @param int indetifikacijski broj putnog naloga
	 * @return Putni nalog sa određenim jedinstvenim Id-om 
	 */
	public PutniNalog getPutniNalogFromDB(int id) {
		
		return dataBase.getPutniNalog(id);
	}

	
	/**
	 * Metoda kojom se dodaje novokreirani Putni Nalog u bazu podataka
	 * @param fe je objekt klase NoviNalogEvent koji sedrži sve podatke iz novog eventa
	 */
	public void setNewPutniNalog2DBS(NoviNalogEvent fe) {
		
		int size = dataBase.getAllPutniNalogs().size();
		
		int id = 0;
		String name = fe.getName();
		int brojNaloga = fe.getBrojNaloga();
		String datumNaloga = fe.getDatumNaloga();
		String datumPolaska = fe.getDatumPolaska();
		String datumPovratka = fe.getDatumPovratka();
		String odrediste = fe.getOdrediste();
		String svrha = fe.getSvrha();
		String vozilo = fe.getVozilo();
		int predujam = fe.getPredujam();
		int pocetniKm = fe.getPocetniKm();
		int zavrsniKm = fe.getZavrsniKm();
		String teretTroska = fe.getTeretTroska();
		int prijedenoKm = fe.getPrijedenoKm();
		String stanjeNaloga = "Nalog u obradi";
		
		if (size == 0) {
			
			id = 1;
			
		} else if (size != 0) {
			
			id =  dataBase.getAllPutniNalogs().get(size-1).getId() + 1;
		}
		
		PutniNalog putniNalog2 = new PutniNalog(name, brojNaloga, datumNaloga, datumPolaska, datumPovratka, odrediste, svrha, vozilo, predujam, pocetniKm, zavrsniKm, teretTroska, prijedenoKm, id);
	//	PutniNalog putniNalog = new PutniNalog(id, name, stanjeNaloga, brojNaloga, datumNaloga, datumPolaska, datumPovratka, odrediste, svrha, vozilo, predujam, pocetniKm, zavrsniKm, teretTroska, prijedenoKm);
		dataBase.addPutniNalog2DB(putniNalog2);
	}
	
	public void removePutniNalogFromDB(int index) {
		
		dataBase.removePutninalog(index);
	}

	/**
	 * Metoda kojom dopunjava i zatvara putni nalog
	 * @param ze je objekt klase ZavrsniNalogEvent koji sedrži sve podatke iz završnog eventa
	 */
	public void updateAndClosePutniNalogFromDB(ZavrsniNalogEvent ze) {
		
		int id = ze.getId();
//		int row = ze.getRow();
		String datumPolaska = ze.getDatumPolaska();
		String datumPovratka = ze.getDatumPovratka();
		int zavrsniKm = ze.getZavrsniKm();
		int pocetniKm = ze.getPocetniKm();
		int ukupanPutKm = ze.getUkupanPutKm();
		
		PutniNalog pNalog = dataBase.getPutniNalog(id);
		pNalog.updateAndClosePutniNalog(datumPolaska, datumPovratka, pocetniKm, zavrsniKm, ukupanPutKm);
		
	}
	
	/**
	 * Metoda kojom se spaja na MySql bazu podataka
	 */
	public void connect2DBS() throws Exception {
		dataBase.connect2MySQL();
	}
	
	/**
	 * Metoda koja sprema lokalnu bazu na MySql-bazu
	 */
	public void save2DBS() {
		dataBase.save2MySQL();
	}
	
	/**
	 * Metoda koja dohvaca MySql-bazu i prikazuje je lokalno
	 */
	public void load4DBS() {
		dataBase.load4MySQL();
	}
	
	/**
	 * Metoda kojom se odspaja sa MySql baze
	 */
	public void disconnect4DBS() {
		dataBase.disconnect4MySQL();
	}
	
	/**
	 * Metoda kojom se briše Putni Nalog iz MySql baze podataka
	 */
	public void removePutniNalog4MySQL(int id) {
		
		dataBase.removePutniNalog4MySQL(id);
	}
	
	/**
	 * Metoda kojom se briše Putni Nalog iz lokalne baze podataka
	 */
	public void removePutninalogLocaly(int id) {
		
		dataBase.removePutninalogLocaly(id);
		
	}
	
	/**
	 * Metoda kojom se refresha DataBaseTab
	 */
	public void refreshDataBaseTable(DataBaseTab tablePanel) {

		tablePanel.refreshTableView();
	}
	
	public void save2FileDBS(File file) throws IOException {
		dataBase.save2File(file);
	}

	public void getDataBase4File(File file) throws ClassNotFoundException, IOException {
		dataBase.open4File(file);
	}
	
	
	
	
	

}
