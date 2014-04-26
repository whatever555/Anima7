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

		setBackground(new Color(67,67,67));
		setForeground(new Color(202,202,202));
		//setFont(new Font("Verdana", Font.BOLD, 10)); 
 }
}
