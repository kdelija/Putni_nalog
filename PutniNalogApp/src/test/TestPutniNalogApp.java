package test;

import model.DataBase;
import model.PutniNalog;

/**
 * TestPutniNalogApp testira konekciju sa MySQL bazom bez koristenja gui-a
 * 
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 *
 */
public class TestPutniNalogApp {
	
	private static DataBase dataBase = new DataBase();
	
	public static void main(String[] args) throws Exception {
		
		
		dataBase.connect2MySQL();
		
//		dataBase.addPutniNalog2DB(new PutniNalog("Mariouu Ivankovic", 555, "15-25-1991", "15-25-1991", "15-25-1991", "vukovar", "utec iz dalamcije", "Yugo", 15, 20, 30, "Zaposlenik", 10, 0));
//		dataBase.addPutniNalog2DB(new PutniNalog("Lukaku Krizanovic", 555, "15-25-2019", "15-25-1991", "15-25-1991", "vukovar", "utec iz dalamcije", "Yugo", 15, 20, 30, "Zaposlenik", 10, 1));
//		dataBase.addPutniNalog2DB(new PutniNalog("Mariouu Ivankovic", 555, "15-25-1991", "15-25-1991", "15-25-1991", "vukovar", "utec iz dalamcije", "Yugo", 15, 20, 30, "Zaposlenik", 10, 2));
//		dataBase.addPutniNalog2DB(new PutniNalog("Krizo Matovilac", 555, "15-25-1991", "15-25-1991", "15-25-1991", "vukovar", "utec iz dalamcije", "Yugo", 15, 20, 30, "Zaposlenik", 10, 4));
//
//		dataBase.save2MySQL();
		
//		dataBase.removePutniNalog4MySQL(5);
		//dataBase.load4MySQL();
		dataBase.disconnect4MySQL();
		
		
	}
}
