package com.danaex.earthNineteen;

import javax.swing.SwingUtilities;

import com.danaex.earthNineteen.GUI.ENFrame;
import com.danaex.earthNineteen.GUI.ENSplashScreen;
import com.danaex.earthNineteen.core.FileFinder;

public class EarthNineteen {

	public static void main(String args[]) {
		
		ENSplashScreen splashScreen = new ENSplashScreen();
		
		splashScreen.setVisible(true);
		
		FileFinder ff = new FileFinder(System.getProperty("user.home"));
		
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				ENFrame en = new ENFrame(ff);
				en.setVisible(true);
			}
		});
		
		splashScreen.dispose();
		
	}
}