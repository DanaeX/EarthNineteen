package com.danaex.earthNineteen.GUI;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.danaex.earthNineteen.core.FileFinder;

public class ENFrame extends JFrame {


	private static final long serialVersionUID = 0xbb8d1d9059e54cdfL;
	
	private ImagePanel imgPanel;
	private MenuPanel menuPanel;
	
	private CardLayout cl;
	private FileFinder ff;
	
	private JMenuBar menuBar  = new JMenuBar();
	
	private MenuBarListener mbl = new MenuBarListener();
	
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem homeItem = new JMenuItem("Home");
	private JMenuItem galleryItem = new JMenuItem("Gallery");
	private JMenu galleryMenu = new JMenu("Gallery");
	private JMenuItem addItem = new JMenuItem("Add Images");

	public ENFrame(FileFinder filefinder) {

		ff = filefinder;
		
		boolean noFile= false;
		if (ff.noFile && ff.promptFiles()) {
			noFile = true;
			galleryItem.setEnabled(false);
		}
	
		setLocationByPlatform(true);
		setSize(1280, 720);
		setExtendedState(6);
		setTitle("EarthNineteen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		homeItem.addActionListener(mbl);
		homeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));
		
		galleryItem.addActionListener(mbl);
		galleryItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
		
		fileMenu.add(homeItem);
		fileMenu.add(galleryItem);
		
		addItem.addActionListener(mbl);
		addItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK));
		
		galleryMenu.add(addItem);
		
		menuBar.add(fileMenu);
		menuBar.add(galleryMenu);
		
		setJMenuBar(menuBar);
		
		cl = new CardLayout();
		
		setLayout(cl);
		
		add(menuPanel = new MenuPanel(this), "menuPanel");
		add(imgPanel = new ImagePanel(ff, this), "imgPanel");
		
		if(noFile) { disableGallery();}
		
		menuPanel.disableChat();
		
		cl.show(getContentPane(), "menuPanel");
	}

	public void openGallery() {
		
		imgPanel.resetIndex();
		
		updateTitle();
		
		cl.show(getContentPane(), "imgPanel");
	}

	public void returnHome() {
		cl.show(getContentPane(), "menuPanel");
		updateTitle();
	}
	
	public void updateTitle() {
		setTitle("EarthNineteen - " + ff.getImageNum() + " pic(s) and " + ff.getVideoNum( )+ " video(s).");
	}
	
	public class MenuBarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == homeItem) {
				returnHome();
			}
			if (e.getSource() == galleryItem) {
				openGallery();
			}
			if (e.getSource() == addItem) {
				if(ff.addFiles()) {
					imgPanel.reloadImages();
					menuPanel.enableGallery();
				}
			}
		}
	}

	public void disableGallery() {
		menuPanel.disableGallery();
	}

}