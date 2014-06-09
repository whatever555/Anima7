package aniExtraGUI;

import javax.swing.JInternalFrame;

public class EInternalFrame extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	javax.swing.plaf.basic.BasicInternalFrameUI ui = 
		    new javax.swing.plaf.basic.BasicInternalFrameUI(this); 
	public EInternalFrame(){
		super();
		styleMe();
		
		
	}
	public EInternalFrame(String title){
		super(title);
		styleMe();
	}

	public void styleMe(){
	
			this.setUI(ui); 
		

	}
	
			

}
