package com.animation.shop;
import java.awt.Color;

import aniExtraGUI.EButton;


public class TButton extends EButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TButton(){
		 super();
		this.setBorder(null);
		
	 }
	
	public TButton(String str){
		 super();
		//this.setBorder(null);
		this.setText(str);
		
	 }
}
