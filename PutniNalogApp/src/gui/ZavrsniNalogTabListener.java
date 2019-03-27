package gui;

import java.util.EventListener;


/**
 *  ZavrsniNalogTabListener osluškuje događanja u ZavrsiNalogTab-u 
 *  
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 * 
 */
public interface ZavrsniNalogTabListener extends EventListener{
	
	public void newZavrsniNalogEventOccured(ZavrsniNalogEvent ze);

}
