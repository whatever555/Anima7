package com.animation.shop;
import java.awt.Color;

import aniExtraGUI.EButton;


public class TButton extends EButton{
	public TButton(){
		 super();
		 this.setBackground(new Color(200,200,200));
		this.setBorder(null);
		
	 }
	
	public TButton(String str){
		 super();
		 this.setBackground(new Color(200,200,200));
		this.setBorder(null);
		this.setText(str);
		
	 }
}
