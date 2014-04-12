package aniExtraGUI;

import java.awt.Color;

import javax.swing.JInternalFrame;

public class EInternalFrame extends JInternalFrame {
	
	public EInternalFrame(){
		super();
		styleMe();
	}
	public EInternalFrame(String title){
		super(title);
		styleMe();
	}

public void styleMe(){

	setBackground(new Color(255,120,120));
}
}
