package aniExtraGUI;

import java.awt.Color;

import javax.swing.JComboBox;

public class EComboBox extends JComboBox{
	
	public EComboBox(String[] str){
		super(str);
		styleMe();
	}
	
	public void styleMe(){
		setBackground(new Color(67,67,67));
		setForeground(new Color(202,202,202));
		
	}
	
}
