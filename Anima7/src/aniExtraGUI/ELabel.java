package aniExtraGUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class ELabel extends JLabel{
public ELabel(){
	super();

	styleMe();
}
public ELabel(String str){
	super(str);
	styleMe();
}
public void styleMe(){
	setForeground(new Color(202,202,202));
//	setFont(new Font("Verdana", Font.BOLD, 10)); 
	
}
}
