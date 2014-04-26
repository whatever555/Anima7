package aniExtraGUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTabbedPane;

public class ETabbedPane extends JTabbedPane{
	public ETabbedPane(){
		super();
		styleMe();
	}
	
	public void styleMe(){
		//this.getViewportView().setBackground(67,67,67);
		setForeground(new Color(202,202,202));
		setFont(new Font("Verdana", Font.BOLD, 10)); 
	}
}
