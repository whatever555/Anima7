package aniExtraGUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

public class EPanel extends JPanel {
public EPanel(){
	super();
	styleMe();
}


public void styleMe(){

	setBackground(new Color(67,67,67));
	setForeground(new Color(202,202,202));
	setFont(new Font("Verdana", Font.BOLD, 10)); 
}
}
