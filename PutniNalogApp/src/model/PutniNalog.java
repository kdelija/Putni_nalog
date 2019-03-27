package model;

import java.io.Serializable;


/**
 * PutniNalog sadrži podatke potrebne za instanciranje objekta putni nalog
 * 
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 *
 */
public class PutniNalog implements Serializable{
	
	private static int cnt = 1;
	private int id;
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
	private ExpensesCategory teretTroska;
	private int prijedenoKm;
	private String stanjeNaloga;
	
	
	
	public PutniNalog(String name, int brojNaloga, String datumNaloga, String datumPolaska, String datumPovratka,
			String odrediste, String svrha, String vozilo, int predujam, int pocetniKm, int zavrsniKm,
			String teretTroska, int prijedenoKm, int id) {
		
		this.name = name;
		this.brojNaloga = brojNaloga;
		this.datumNaloga = datumNaloga;
		this.datumPolaska = datumPolaska;
		this.datumPovratka = datumPovratka;
		this.odrediste = odrediste;
		this.svrha = svrha;
		this.vozilo = vozilo;
		this.predujam = predujam;
		this.pocetniKm = 0;
		this.zavrsniKm = 0;
		this.prijedenoKm = 0;
		this.teretTroska = expCategory(teretTroska);
		this.stanjeNaloga = "Nalog u obradi";
	//	this.izvjestaj = null;
		
		this.id = id;
		cnt++;

	}
	
	public PutniNalog(Integer id2, String name2, String stanjeNaloga2, int brojNaloga2, String datumNaloga2, String datumPolaska2, String datumPovratka2,
			String odrediste2, String svrha2, String vozilo2, int predujam2, int pocetniKm2, int zavrsniKm2,
			String teretTroska2, int prijedenoKm2) {
		
		this.id = id2;
		this.name = name2;
		this.brojNaloga = brojNaloga2;
		this.datumNaloga = datumNaloga2;
		this.datumPolaska = datumPolaska2;
		this.datumPovratka = datumPovratka2;
		this.odrediste = odrediste2;
		this.svrha = svrha2;
		this.vozilo = vozilo2;
		this.predujam = predujam2;
		this.pocetniKm = pocetniKm2;
		this.zavrsniKm = zavrsniKm2;
		this.prijedenoKm = prijedenoKm2;
		this.teretTroska = ExpensesCategory.valueOf(teretTroska2);
		this.stanjeNaloga = stanjeNaloga2;
		cnt++;
		
	}
	
	public String toString() {
		
		String data = id + " <-> " + name + " <-> " + stanjeNaloga + " <-> " + brojNaloga + " <-> " + datumNaloga + " <-> " + vozilo;
		return data;
	}

	/**
	 * Metoda za zatvaranje putnog naloga
	 */
	public void updateAndClosePutniNalog(String datumPolaska, String datumPovratka, int pocetniKm, int zavrsniKm, int prijedenoKm) {
		
		this.datumPolaska = datumPolaska;
		this.datumPovratka = datumPovratka;
		this.pocetniKm = pocetniKm;
		this.zavrsniKm = zavrsniKm;
		this.prijedenoKm = prijedenoKm;
		this.stanjeNaloga = "Završen nalog";
	}
	
	
	private ExpensesCategory expCategory(String exp) {
		
		switch (exp) {
		case "Zaposlenik":
			return ExpensesCategory.ZAPOSLENIK;
		case "Poduzece":
			return ExpensesCategory.PODUZECE;
		}
		
		return null;
	}


	public int getId() {
		return id;
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


	public ExpensesCategory getTeretTroska() {
		return teretTroska;
	}


	public int getPrijedenoKm() {
		return prijedenoKm;
	}


	public String getStanjeNaloga() {
		return stanjeNaloga;
	}
	
	
	
}
