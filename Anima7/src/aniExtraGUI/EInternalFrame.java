package aniExtraGUI;

import java.awt.Color;

import javax.swing.BorderFactory;
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
		setBackground(new Color(67,67,67));
		setForeground(new Color(202,202,202));
		javax.swing.plaf.basic.BasicInternalFrameUI ui = 
			    new javax.swing.plaf.basic.BasicInternalFrameUI(this); 
			this.setUI(ui); 
//this.setBorder(null);
setBorder(BorderFactory.createMatteBorder(
        2, 2, 2, 2, new Color(0,0,0)));
	}
	
			

}
