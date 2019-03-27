package gui;

/**
 *  DataBaseTabListener osluškuje događanja u DataBaseTab-u s dvije metode
 *  
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 * 
 */

public interface DataBaseTabListener {
	
	public void dataBaseTabDelteEventOccured(int id);
	public void dataBaseTabCompleteEventOccured(int id);

}
