package aniExtraGUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class ESlider extends JSlider{
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public ESlider(int minval, int maxval, int val){
	 super(SwingConstants.HORIZONTAL,minval,maxval,val);
	 styleMe();
	 
 }
 
 public void styleMe(){

		
		//setFont(new Font("Verdana", Font.BOLD, 10)); 
 }
}
