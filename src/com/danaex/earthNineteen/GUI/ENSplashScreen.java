package com.danaex.earthNineteen.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ENSplashScreen extends JWindow
{

    private static final long serialVersionUID = 0x932530088722efb6L;

    public ENSplashScreen()
    {
        setBackground(Color.WHITE);
        
        JLabel icon = new JLabel(new ImageIcon(getClass().getResource("/img/splash_screen.png")));
        
        icon.setBorder(new LineBorder(Color.BLACK));
        
        getContentPane().add(icon);
        
        int x = (int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 400D) / 2D);
        int y = (int)((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 400D) / 2D);
        setBounds(x, y, 400, 400);
    }
}
