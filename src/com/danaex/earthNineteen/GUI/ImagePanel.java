package com.danaex.earthNineteen.GUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.danaex.earthNineteen.core.FileFinder;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 0xe5d3ca358622638bL;
	
	private FileFinder ff;
	
	private JToolBar toolBar = new JToolBar();
	
	private ToolBarListener tbl = new ToolBarListener();
	
	private JButton previousButton = new JButton(new ImageIcon((new ImageIcon(getClass().getResource("/img/previous_arrow.png")))
			.getImage().getScaledInstance(25, 25, 1)));
	
	private JButton nextButton = new JButton(new ImageIcon((new ImageIcon(getClass().getResource("/img/next_arrow.png"))).getImage()
			.getScaledInstance(25, 25, 1)));
	
	private ArrayList<Image> images = new ArrayList<Image>();
	
	private int index = 0;

	public ImagePanel(FileFinder filefinder) {
		
		ff = filefinder;
		
		images = loadImages();
		
		setLayout(new BorderLayout());
		
		previousButton.addActionListener(tbl);
		previousButton.setEnabled(false);
		
		nextButton.addActionListener(tbl);
		if (images.size() < 2) {
			nextButton.setEnabled(false);
		}
		
		toolBar.add(previousButton);
		toolBar.add(nextButton);
		
		add(toolBar, BorderLayout.NORTH);
	}

	private ArrayList<Image> loadImages() {
		
		ArrayList<Path> pathList = ff.getImagesList();
		ArrayList<Image> imgList = new ArrayList<Image>();
		
		for (Path p : pathList) {
			
			try {
				
				Image img = ImageIO.read(p.toUri().toURL());
				imgList.add(img);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return imgList;
	}

	public void reloadImages() {
		images = loadImages();
		repaint();
	}

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Image img = images.get(index);
		
		int w = img.getWidth(this);
		int h = img.getHeight(this);
		
		if (h > w) {
			
			for (; h > getHeight() - 100; h--) {
			}
			
			w = (w * h) / img.getHeight(this);
			
		} else if (w > h) {
			
			for (; w > getWidth() - 500; w--) {
			}
			
			h = (h * w) / img.getWidth(this);
			
		} else if (w == h) {
			
//			for (; w > getHeight() - 100 || h > getHeight() - 100; h--) {
//				w--;
//			}
			
			
			while(w > getWidth() - 100 || h > getHeight() - 100) {
				w--;
				h--;
			}

		}
		
		int x = (getWidth() - w) / 2;
		int y = ((getHeight() + toolBar.getHeight()) - h) / 2;
		
		g.drawImage(img, x, y, w, h, this);
	}

	public void resetIndex() {
		
		index = 0;
		previousButton.setEnabled(false);
		
	}

	class ToolBarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == previousButton) {
				
				index--;
				
				if (index == 0) {
					previousButton.setEnabled(false);
				}
				
				if (!nextButton.isEnabled()) {
					nextButton.setEnabled(true);
				}
				
				repaint();
				
			} else if (e.getSource() == nextButton) {
				
				index++;
				
				if (index == images.size() - 1) {
					nextButton.setEnabled(false);
				}
				
				if (!previousButton.isEnabled()) {
					previousButton.setEnabled(true);
				}
				
				repaint();
				
			}
		}
	}
}