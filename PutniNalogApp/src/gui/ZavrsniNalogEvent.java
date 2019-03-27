package gui;

import java.util.EventObject;


/**
 *  ZavrsniNalogEvent sprema sve podatke kreiranje aktiviranjem ZavrsniNalogTabListener
 *  Služi za dohvacanje podataka dobivenih u formularu u ZavrsiNalogTab-u
 *  
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 * 
 */
public class ZavrsniNalogEvent extends EventObject {
	
	private int id;
	private String datumPolaska;
	private String datumPovratka;
	private int zavrsniKm;
	private int pocetniKm;
	private int ukupanPutKm;
//	private int row;
	
	
	public ZavrsniNalogEvent(Object source, int id, String datumPolaska, String datumPovratka, int zavrsniKm, int pocetniKm,
			int ukupanPutKm) {
		
		super(source);
		this.id = id;
//		this.row = row;
		this.datumPolaska = datumPolaska;
		this.datumPovratka = datumPovratka;
		this.zavrsniKm = zavrsniKm;
		this.pocetniKm = pocetniKm;
		this.ukupanPutKm = ukupanPutKm;
	}


	public int getId() {
		return id;
	}


//	public int getRow() {
//		return row;
//	}


	public String getDatumPolaska() {
		return datumPolaska;
	}


	public String getDatumPovratka() {
		return datumPovratka;
	}


	public int getZavrsniKm() {
		return zavrsniKm;
	}


	public int getPocetniKm() {
		return pocetniKm;
	}


	public int getUkupanPutKm() {
		return ukupanPutKm;
	}
	
	


}
