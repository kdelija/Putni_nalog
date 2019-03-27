package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * DataBase zadrži listu tipa PutniNalog
 * Metode za povezivanje i odspajanje sa MySql bazom
 * Ucitavanje i spremanje na  MySql bazu
 * Spremanja i ucitavanja baze u datoteku
 * 
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 *
 */
public class DataBase {
	
	private List<PutniNalog> putniNalog;
	private Connection con;

	
	public DataBase() {
		
		putniNalog = new LinkedList<>();
	}
	
	/**
	 * Dodaje PutniNalog u listu
	 * @param pn PutniNalog
	 */
	public void addPutniNalog2DB(PutniNalog pn) {
		
		putniNalog.add(pn);
	}
	
	/**
	 * Vraca listu iz baze podataka
	 * @return Lista koja samo može pregledavat
	 */
	public List<PutniNalog> getAllPutniNalogs() {
		
		return Collections.unmodifiableList(putniNalog);
	}
	
	
	/**
	 * Dohvaca PutniNalog određenog id-a
	 * @param id Naloga koji se dohvaca
	 * @return PutniNalog
	 */
	public PutniNalog getPutniNalog(int id) {
		
		PutniNalog putNalog = null;
		
		for(PutniNalog pn : putniNalog) {

			if (pn.getId() == id) {

			 putNalog = pn;
			
			}
		}

		return putNalog;
	}
	
	/**
	 * Metoda koja spaja aplikaciju sa mysql-bazom
	 * @throws Exception
	 */
	public void connect2MySQL() throws Exception {
		
		if(con !=null) return;
		try {
			System.out.println("Pokusavam se spojiti");
			// This initialise class com.mysql.cj.jdbc.Driver on the classpath
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); // see https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-connect-drivermanager.html
			// Put your dbs connection data
			String url = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7281043";
			String user = "sql7281043";
			String password = "JhEvCvUeR8";
			con = DriverManager.getConnection(url, user, password);

		} catch (Exception e) {
			throw new Exception("Driver could not be found!");
		}

		System.out.println("Success -> connected to DBS -> " + con);

	}
	
	/**
	 *Metoda koja odspaja aplikaciju sa mysql-bazom
	 */
	public void disconnect4MySQL() {
		
		if (con != null) {

			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Disconnected from DBS -> " + con);
		}
	}
	/**
	 * Metoda sprema lokalnu bazu u mysql-bazu
	 */
	public void save2MySQL() {
		
//		String checkSql = "select count(*) as count from Client where id=?";
//		String insertSql = "insert into Client (id, name, mail, education, region, employed) values (?,?,?,?,?,?)";
//		String updateSql = "update Client set name=?, mail =?, education =?, region = ?, employed=? where id=?";
		
		String checkSql = "select count(*) as count from Client where id=?";
		String insertSql = "insert into Client (id, name, stanjeNaloga, brojNaloga, datumNaloga, datumPolaska, datumPovratka, odrediste, svrha, vozilo, predujam, pocetniKm, zavrsniKm, prijedenoKm, teretTroska) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String updateSql = "update Client set name=?, stanjeNaloga=?, brojNaloga=?, datumNaloga=?, datumPolaska=?, datumPovratka=?, odrediste=?, svrha=?, vozilo=?, predujam=?, pocetniKm=?, zavrsniKm=?, prijedenoKm=?, teretTroska=? where id=?";
		try {
			PreparedStatement checkStatement = con.prepareStatement(checkSql);
			PreparedStatement insertStatement = con.prepareStatement(insertSql);
			PreparedStatement updateStatement = con.prepareStatement(updateSql);
			for(PutniNalog putniNalog : putniNalog) {
				int id = putniNalog.getId();
				checkStatement.setInt(1, id);
				ResultSet checkRes = checkStatement.executeQuery();
				checkRes.next();
				String name = putniNalog.getName();
				String stanjeNaloga = putniNalog.getStanjeNaloga();
				int brojNaloga = putniNalog.getBrojNaloga();
				String datumNaloga = putniNalog.getDatumNaloga();
				String datumPolaska = putniNalog.getDatumPolaska();
				String datumPovratka = putniNalog.getDatumPovratka();
				String odrediste = putniNalog.getOdrediste();
				String svrha = putniNalog.getSvrha();
				String vozilo = putniNalog.getVozilo();
				int predujam = putniNalog.getPredujam();
				int pocetniKm = putniNalog.getPocetniKm();
				int zavrsniKm = putniNalog.getZavrsniKm();
				int prijedenoKm = putniNalog.getPrijedenoKm();
				ExpensesCategory teretTroska = putniNalog.getTeretTroska();
				
				int count = checkRes.getInt(1);
				System.out.println("Client with id " + id + " has -> " + count + " records in dbs...");
				if (count == 0) {
					System.out.println("Insert client with id -> " + id);
					int col = 1;
					insertStatement.setInt(col++, id);
					insertStatement.setString(col++, name);
					insertStatement.setString(col++, stanjeNaloga);
					insertStatement.setInt(col++, brojNaloga);
					insertStatement.setString(col++, datumNaloga);
					insertStatement.setString(col++, datumPolaska);
					insertStatement.setString(col++, datumPovratka);
					insertStatement.setString(col++, odrediste);
					insertStatement.setString(col++, svrha);
					insertStatement.setString(col++, vozilo);
					insertStatement.setInt(col++, predujam);
					insertStatement.setInt(col++, pocetniKm);
					insertStatement.setInt(col++, zavrsniKm);
					insertStatement.setInt(col++, prijedenoKm);
					insertStatement.setString(col++, teretTroska.name());

					
					insertStatement.executeUpdate();
					
				} else {
					System.out.println("Update client with id -> " + id);
					int col = 1;
					updateStatement.setString(col++, name);
					updateStatement.setString(col++, stanjeNaloga);
					updateStatement.setInt(col++, brojNaloga);
					updateStatement.setString(col++, datumNaloga);
					updateStatement.setString(col++, datumPolaska);
					updateStatement.setString(col++, datumPovratka);
					updateStatement.setString(col++, odrediste);
					updateStatement.setString(col++, svrha);
					updateStatement.setString(col++, vozilo);
					updateStatement.setInt(col++, predujam);
					updateStatement.setInt(col++, pocetniKm);
					updateStatement.setInt(col++, zavrsniKm);
					updateStatement.setInt(col++, prijedenoKm);
					updateStatement.setString(col++, teretTroska.name());
					updateStatement.setInt(col++, id);
					
					updateStatement.executeUpdate();
				}
			}
			
			checkStatement.close();
			insertStatement.close();
			updateStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Metoda dohvaca mysql bazu i zamjenjuje je sa lokalnom
	 */
	public void load4MySQL() {
		putniNalog.clear();
		String selectSql = "select id, name, stanjeNaloga, brojNaloga, datumNaloga, datumPolaska, datumPovratka, odrediste, svrha, vozilo, predujam, pocetniKm, zavrsniKm, prijedenoKm, teretTroska from Client order by id";
		try {
			Statement selectStatement = con.createStatement();
			ResultSet selectRes = selectStatement.executeQuery(selectSql);
			while(selectRes.next()) {
				int id = selectRes.getInt("id");

				String name = selectRes.getString("name");
				String stanjeNaloga = selectRes.getString("stanjeNaloga");
				int brojNaloga = selectRes.getInt("brojNaloga");
				String datumNaloga = selectRes.getString("datumNaloga");
				String datumPolaska = selectRes.getString("datumPolaska");
				String datumPovratka = selectRes.getString("datumPovratka");
				String odrediste = selectRes.getString("odrediste");
				String svrha = selectRes.getString("svrha");
				String vozilo = selectRes.getString("vozilo");
				int predujam = selectRes.getInt("predujam");
				int pocetniKm = selectRes.getInt("pocetniKm");
				int zavrsniKm = selectRes.getInt("zavrsniKm");
				int prijedenoKm = selectRes.getInt("prijedenoKm");
				String teretTroska = selectRes.getString("teretTroska");
				
				PutniNalog  pNalog = new PutniNalog(id, name, stanjeNaloga, brojNaloga, datumNaloga, datumPolaska, datumPovratka, odrediste, svrha, vozilo, predujam, pocetniKm, zavrsniKm, teretTroska, prijedenoKm);
				putniNalog.add(pNalog);
			}
			
			selectRes.close();
			selectStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(PutniNalog pn : putniNalog) {
			System.out.println(pn.toString());
		}
	}

	public void removePutninalog(int index) {
		
		putniNalog.remove(index);
	}
	
	/**
	 * Metoda briše putni nalog određenog id-a sa lokalne baze
	 * @param id
	 */
	public void removePutninalogLocaly(int id) {
		
		for(PutniNalog pn : putniNalog) {
			
			if (pn.getId() == id) {
				
				putniNalog.remove(pn);
				System.out.println("izbrisan putni nalog s id: " + pn.getId());
			}
		}
	}
	
	
	/**
	 * Metoda briše putni nalog određenog id-a sa mysql baze
	 * @param id
	 */
	public void removePutniNalog4MySQL(int id) {
		
	      String deleteSql = "delete from Client where id = ?";
	      
		try {
			
			PreparedStatement preparedStmt = con.prepareStatement(deleteSql);
			preparedStmt.setInt(1, id);
			preparedStmt.execute();
			preparedStmt.close();
	
			removePutninalogLocaly(id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		for(PutniNalog pn : putniNalog) {
			System.out.println(pn.toString());
		}
		
		
	}
	
	public void save2File(File file) throws IOException {

		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		PutniNalog[] pNalog = putniNalog.toArray(new PutniNalog[putniNalog.size()]);

		oos.writeObject(pNalog);
		oos.close();

	}

	public void open4File(File file) throws ClassNotFoundException, IOException {

		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		PutniNalog[] pNalog = (PutniNalog[]) ois.readObject();
		putniNalog.clear();
		putniNalog.addAll(Arrays.asList(pNalog));
		ois.close();
	}

	
	
}
