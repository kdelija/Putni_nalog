package gui;

import java.util.EventListener;


/**
 *  NoviNalogTabListener osluškuje događanja u NoviNalogTab-u 
 *  
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 * 
 */
public interface NoviNalogTabListener extends EventListener {
	
	public void novNalogEventOccured(NoviNalogEvent fe);


}	
