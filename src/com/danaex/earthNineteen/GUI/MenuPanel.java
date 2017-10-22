package com.danaex.earthNineteen.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 0xa99b08c90f5a0bd0L;
	
	private ENFrame main;
	
	private JButton images;
	private JButton chat;
	
	private Image background;

	public MenuPanel(ENFrame frame) {
		
		main = frame;
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = 2;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridheight = 1;
		gc.gridwidth = 0;
		gc.insets = new Insets(0, 0, 400, 0);
		
		images = new JButton("G\351rez vos photos");
		images.addActionListener(new ImageButtonListener());
		images.setPreferredSize(new Dimension(400, 60));
		
		add(images, gc);
		
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridheight = 1;
		gc.gridwidth = 0;
		gc.insets = new Insets(0, 0, 0, 0);
		
		chat = new JButton("Commmuniquez avec votre partenaire de Earth 19");
		
		chat.addActionListener(new ChatButtonListener());
		chat.setPreferredSize(new Dimension(400, 60));
		
		add(chat, gc);
		
		try {
			
			background = ImageIO.read(main.getClass().getResource("/img/background.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void enableGallery() {
		images.setEnabled(true);
	}
	
	public void disableGallery() {
		images.setEnabled(false);
	}
	
	public void disableChat() {
		chat.setEnabled(false);
	}

	public void paintComponent(Graphics graphics) {
		
		graphics.drawImage(background, -200, 0, null);
		
	}

	public class ChatButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent actionevent) {
		}
		
	}

	public class ImageButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			main.openGallery();
		}
		
	}

}
