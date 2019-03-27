package gui;

import javax.swing.SwingUtilities;

/**
 * App sadrzi main koji pokrece aplikaciju
 * 
 * @author kdellija
 * @version 1.0
 * @since february, 2019
 *
 */

public class App {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				new AppFrame();
				
			}
		});
	}

}