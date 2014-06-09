package com.animation.shop;

import java.awt.BorderLayout;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aniExtraGUI.EInternalFrame;
import aniExtraGUI.ELabel;
import aniExtraGUI.EPanel;
import aniExtraGUI.EScrollPane;
import aniExtraGUI.ESlider;


/**
 * 
 */

/**
 * @author eddie
 *
 */
public class PenOptions extends EPanel implements ChangeListener {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Main parent;
ColorButton colorButton;
	public PenOptions(Main parent){
		this.parent=parent;
		showMe();
		
	}

	ELabel brushSizeLabel2 ;
	ELabel brushAlphaLabel2 ;
	ESlider brushSizeSlider;
	ESlider brushAlphaSlider;
	EScrollPane sc;
	
	 public String translate(String str){
		 return parent.translate(str);
	 }
	public void showMe(){

		int WIDTH = 260;
		int HEIGHT= 220;
		
		sc = new EScrollPane(parent.scrollBarForeground);

		sc.setBounds(0,0,WIDTH,HEIGHT);
		//sc.setHorizontalScrollBarPolicy(EScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//sc.setVerticalScrollBarPolicy(EScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		//this.setTitle(translate("Brush Options"));
		this.setLayout(new BorderLayout());
		//this.setVisible(false);
		
		
		//this.setClosable(true); 
		// tlFrame.setIconifiable(true); 
		//this.setResizable(true);
		//tlFrame.setMaximumSize(new java.awt.Dimension(800, 200)); 
		// this.setDefaultCloseOperation(1);
		
		
		EPanel penOpsPanel = new EPanel();
		//penOpsPanel.setBounds(0,0,WIDTH,HEIGHT-60);
		penOpsPanel.setLayout(null);
		
		
	
		
		EPanel brushSizePanel = new EPanel();
		brushSizePanel.setBounds(0,5,WIDTH,130);
		brushSizePanel.setLayout(null);
		//brushSizePanel.setBackground(Color.lightGray);
		penOpsPanel.add(brushSizePanel);
		
		
		EPanel colorOptionsPanel = new EPanel();
		colorOptionsPanel.setVisible(true);
		colorOptionsPanel.setBounds(0,135,WIDTH,40);
		colorOptionsPanel.setLayout(null);
		penOpsPanel.add(colorOptionsPanel);
		
		

		
		//Font font = new Font("Verdana", Font.ITALIC, 8);
		


		

		//#### BRUSH OPTIONS PANEL ###############################################################	
		
		
		ELabel brushSizeLabel = new ELabel(translate("Brush Size"));
		brushSizeLabel.setBounds(2,5,100,30);
		brushSizePanel.add(brushSizeLabel);
		
		brushSizeLabel2 = new ELabel(translate("Brush Size"));
		brushSizeLabel2.setBounds(120,5,100,30);
		brushSizePanel.add(brushSizeLabel2);
		
		
		brushSizeLabel2.setText(""+parent.PENSIZE);
		
		brushSizeSlider = new ESlider(0, WIDTH, parent.PENSIZE);
		brushSizeSlider.addChangeListener(this);

		//Turn on labels at major tick marks.
		brushSizeSlider.setMajorTickSpacing(100);
		brushSizeSlider.setMinorTickSpacing(25);
		brushSizeSlider.setPaintLabels(true);
		brushSizeSlider.setBounds(0,35,WIDTH-2,30);
		//brushSizeSlider.setFont(font);
		brushSizeSlider.setVisible(true);
		brushSizePanel.add(brushSizeSlider);
		
		
		ELabel brushAlphaLabel = new ELabel(translate("Brush Alpha"));
		brushAlphaLabel.setBounds(2,60,100,30);
		brushSizePanel.add(brushAlphaLabel);
		
		brushAlphaLabel2 = new ELabel(translate("Brush Alpha"));
		brushAlphaLabel2.setBounds(120,60,100,30);
		brushSizePanel.add(brushAlphaLabel2);
		
		
		brushAlphaLabel2.setText(""+parent.PENALPHA);
		
		brushAlphaSlider = new ESlider(1, 255, parent.PENALPHA);
		brushAlphaSlider.addChangeListener(this);

		//Turn on labels at major tick marks.
		brushAlphaSlider.setMajorTickSpacing(100);
		brushAlphaSlider.setMinorTickSpacing(25);
		brushAlphaSlider.setPaintLabels(true);
		brushAlphaSlider.setBounds(0,90,WIDTH-2,30);
		
	//	brushAlphaSlider.setFont(font);

		brushAlphaSlider.setVisible(true);
		brushSizePanel.add(brushAlphaSlider);
		
		

		
	//#### COLOR PANEL ###############################################################	
		
		
		colorButton = new ColorButton(this.parent);
		
				
		colorButton.setBounds(2,5,20,20);
		
		ELabel changeColorButLabel = new ELabel(translate("Selected Color"));
		changeColorButLabel.setBounds(32,5,180,20);
		colorOptionsPanel.add(changeColorButLabel);
		
		
		
		colorButton.setVisible(true);
	
		colorOptionsPanel.add(colorButton);
		colorOptionsPanel.setVisible(true);
		
		

	
		
		sc.setVisible(true);
		penOpsPanel.setVisible(true);
		
		
		this.add(sc);
		
		sc.setViewportView(penOpsPanel);
		
		
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
	    ESlider source = (ESlider)e.getSource();
	    if (!source.getValueIsAdjusting()) {
	        
	    	parent.PENSIZE = (short) brushSizeSlider.getValue();
	    	if(parent.PENSIZE<=0)
	    		parent.PENSIZE=1;
			brushSizeLabel2.setText(""+parent.PENSIZE);
			parent.topPanel.sizeSpinner.setValue((int)parent.PENSIZE);
			
			
			parent.PENALPHA = (short) brushAlphaSlider.getValue();
	    	if(parent.PENALPHA<=0)
	    		parent.PENALPHA=1;
			brushAlphaLabel2.setText(""+parent.PENALPHA);
			parent.topPanel.alphaSpinner.setValue((int)parent.PENALPHA);
			
			if(parent.currentTool.equals("Eraser")||parent.currentTool.equals("brush")){
				parent.setCursor(parent.currentTool);
			}
	    }
	}
	
}
