package com.animation.shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.ScrollPane;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * 
 */

/**
 * @author eddie
 *
 */
public class PenOptions extends JInternalFrame implements ChangeListener {
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

	JLabel brushSizeLabel2 ;
	JLabel brushAlphaLabel2 ;
	JSlider brushSizeSlider;
	JSlider brushAlphaSlider;
	ScrollPane sc;
	public void showMe(){

		int WIDTH = 260;
		int HEIGHT= 220;
		
		sc = new ScrollPane();

		sc.setBounds(0,0,WIDTH-20,HEIGHT);
		//sc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//sc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setTitle("Brush Options");
		this.setLayout(new BorderLayout());
		this.setVisible(false);
		
		
		this.setClosable(true); 
		// tlFrame.setIconifiable(true); 
		this.setResizable(true);
		//tlFrame.setMaximumSize(new java.awt.Dimension(800, 200)); 
		 this.setDefaultCloseOperation(1);
		
		
		Panel penOpsPanel = new Panel(null);
		penOpsPanel.setBounds(0,0,WIDTH,HEIGHT-60);
		//penOpsPanel.setLayout(null);
		
		
	
		
		JPanel brushSizePanel = new JPanel();
		brushSizePanel.setBounds(0,5,WIDTH,130);
		brushSizePanel.setLayout(null);
		//brushSizePanel.setBackground(Color.lightGray);
		penOpsPanel.add(brushSizePanel);
		
		
		JPanel colorOptionsPanel = new JPanel();
		colorOptionsPanel.setVisible(true);
		colorOptionsPanel.setBounds(0,135,WIDTH,40);
		colorOptionsPanel.setLayout(null);
		penOpsPanel.add(colorOptionsPanel);
		
		

		
		Font font = new Font("Verdana", Font.ITALIC, 8);
		


		

		//#### BRUSH OPTIONS PANEL ###############################################################	
		
		
		JLabel brushSizeLabel = new JLabel("Brush Size");
		brushSizeLabel.setBounds(2,5,100,30);
		brushSizePanel.add(brushSizeLabel);
		
		brushSizeLabel2 = new JLabel("Brush Size");
		brushSizeLabel2.setBounds(120,5,100,30);
		brushSizePanel.add(brushSizeLabel2);
		
		
		brushSizeLabel2.setText(""+parent.PENSIZE);
		
		brushSizeSlider = new JSlider(SwingConstants.HORIZONTAL,0, WIDTH, parent.PENSIZE);
		brushSizeSlider.addChangeListener(this);

		//Turn on labels at major tick marks.
		brushSizeSlider.setMajorTickSpacing(100);
		brushSizeSlider.setMinorTickSpacing(25);
		brushSizeSlider.setPaintLabels(true);
		brushSizeSlider.setBounds(0,35,WIDTH-2,30);
		brushSizeSlider.setFont(font);
		brushSizeSlider.setVisible(true);
		brushSizePanel.add(brushSizeSlider);
		
		
		JLabel brushAlphaLabel = new JLabel("Brush Alpha");
		brushAlphaLabel.setBounds(2,60,100,30);
		brushSizePanel.add(brushAlphaLabel);
		
		brushAlphaLabel2 = new JLabel("Brush Alpha");
		brushAlphaLabel2.setBounds(120,60,100,30);
		brushSizePanel.add(brushAlphaLabel2);
		
		
		brushAlphaLabel2.setText(""+parent.PENALPHA);
		
		brushAlphaSlider = new JSlider(SwingConstants.HORIZONTAL,1, 255, parent.PENALPHA);
		brushAlphaSlider.addChangeListener(this);

		//Turn on labels at major tick marks.
		brushAlphaSlider.setMajorTickSpacing(100);
		brushAlphaSlider.setMinorTickSpacing(25);
		brushAlphaSlider.setPaintLabels(true);
		brushAlphaSlider.setBounds(0,90,WIDTH-2,30);
		
		brushAlphaSlider.setFont(font);

		brushAlphaSlider.setVisible(true);
		brushSizePanel.add(brushAlphaSlider);
		
		

		
	//#### COLOR PANEL ###############################################################	
		
		
		colorButton = new ColorButton(this.parent);
		
				
		colorButton.setBounds(2,5,20,20);
		
		JLabel changeColorButLabel = new JLabel("Selected Color");
		changeColorButLabel.setBounds(32,5,180,20);
		colorOptionsPanel.add(changeColorButLabel);
		
		
		
		colorButton.setVisible(true);
	
		colorOptionsPanel.add(colorButton);
		colorOptionsPanel.setVisible(true);
		
		

	
		
		this.setResizable(true);
		sc.setVisible(true);
		penOpsPanel.setVisible(true);
		
		
		this.add(sc);
		
		sc.add(penOpsPanel);
		
		this.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(67,67,67)));
		
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
	    JSlider source = (JSlider)e.getSource();
	    if (!source.getValueIsAdjusting()) {
	        
	    	parent.PENSIZE = brushSizeSlider.getValue();
	    	if(parent.PENSIZE<=0)
	    		parent.PENSIZE=1;
			brushSizeLabel2.setText(""+parent.PENSIZE);
			parent.topPanel.sizeSpinner.setValue(parent.PENSIZE);
			
			parent.PENALPHA = brushAlphaSlider.getValue();
	    	if(parent.PENALPHA<=0)
	    		parent.PENALPHA=1;
			brushAlphaLabel2.setText(""+parent.PENALPHA);
			parent.topPanel.alphaSpinner.setValue(parent.PENALPHA);
	    }
	}
	
}
