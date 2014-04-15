package aniExtraGUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ESpinner extends JSpinner{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ESpinner(SpinnerNumberModel snn){
		super(snn);
		styleMe();
	}
	public void styleMe(){

		setBackground(new Color(67,67,67));
		setForeground(new Color(202,202,202));
		//setFont(new Font("Verdana", Font.BOLD, 10)); 
	 }
}
