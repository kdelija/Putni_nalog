package gui;

import java.util.EventObject;


/**
 *  NoviNalogEvent sprema sve podatke kreiranje aktiviranjem NoviNalogTabListener
 *  Služi za dohvacanje podataka dobivenih u formularu u NovomNalogu
 *  
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 * 
 */
public class NoviNalogEvent extends EventObject{
	
	private String name;
	private int brojNaloga;
	private String datumNaloga;
	private String datumPolaska;
	private String datumPovratka;
	private String odrediste;
	private String svrha;
	private String vozilo;
	private int predujam;
	private int pocetniKm;
	private int zavrsniKm;
	private String teretTroska;
	private int prijedenoKm;
	
	
	public NoviNalogEvent(Object source, String name, int brojNaloga, String datumNaloga2, String datumPolaska2,
			String datumPovratka2, String odrediste, String svrha, String vozilo, int predujam, int pocetniKm,
			int zavrsniKm, String teretTroska, int prijedenoKm) {
		super(source);
		this.name = name;
		this.brojNaloga = brojNaloga;
		this.datumNaloga = datumNaloga2;
		this.datumPolaska = datumPolaska2;
		this.datumPovratka = datumPovratka2;
		this.odrediste = odrediste;
		this.svrha = svrha;
		this.vozilo = vozilo;
		this.predujam = predujam;
		this.pocetniKm = pocetniKm;
		this.zavrsniKm = zavrsniKm;
		this.teretTroska = teretTroska;
		this.prijedenoKm = prijedenoKm;
	}


	public String getName() {
		return name;
	}


	public int getBrojNaloga() {
		return brojNaloga;
	}


	public String getDatumNaloga() {
		return datumNaloga;
	}


	public String getDatumPolaska() {
		return datumPolaska;
	}


	public String getDatumPovratka() {
		return datumPovratka;
	}


	public String getOdrediste() {
		return odrediste;
	}


	public String getSvrha() {
		return svrha;
	}


	public String getVozilo() {
		return vozilo;
	}


	public int getPredujam() {
		return predujam;
	}


	public int getPocetniKm() {
		return pocetniKm;
	}


	public int getZavrsniKm() {
		return zavrsniKm;
	}


	public String getTeretTroska() {
		return teretTroska;
	}


	public int getPrijedenoKm() {
		return prijedenoKm;
	}

	
	
	
	
	

}