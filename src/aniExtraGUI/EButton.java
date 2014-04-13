package aniExtraGUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class EButton extends JButton {
public EButton(){
	super();
	setContentAreaFilled(false);
	setOpaque(true);
	styleMe();
}
public void styleMe(){
	setBackground(new Color(60,60,60));
	setForeground(new Color(202,202,202));

	setFont(new Font("Verdana", Font.BOLD, 10)); 
}
}
