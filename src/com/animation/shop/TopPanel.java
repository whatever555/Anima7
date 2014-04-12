package com.animation.shop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TopPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	Main parent;
	
	
	Color bgCol = new Color(47,47,47);
	Color fCol  = new Color(167,167,167);
	Image maxIcon,minIcon;
	 boolean maxBool=false;
	 final JButton minCanvas = new JButton();
		
	 
	public TopPanel(Main parent){
this.parent=parent;
		showMe();
	}
	
	public void showMe(){

		this.setBackground(bgCol);
		this.setForeground(fCol);
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		createBrushOptions();
		createSelectOptions();
		createFillOptions();
		this.add(selectPanel);
		this.add(brushPanel);
		this.add(fillPanel);
		brushPanel.setBackground(bgCol);
		selectPanel.setBackground(bgCol);
		fillPanel.setBackground(bgCol);
		selectPanel.setVisible(false);
		brushPanel.setVisible(false);
		fillPanel.setVisible(false);

//		addMinMaxCanvasBut();
	}
	
	
	
	ColorButton colorButton;
	JSpinner fillSpinner;
	JSpinner alphaSpinner;
	JSpinner sizeSpinner;
	JSpinner featherSpinner;
	JSpinner roundCornerSpinner;
	JPanel brushPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	JPanel fillPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	JPanel squareSelectExtrasPanel  = new JPanel(new FlowLayout(FlowLayout.LEADING));
	
	
	public void addMinMaxCanvasBut(){
		
		final javax.swing.plaf.InternalFrameUI ifu= parent.canvasFrame.getUI();
		final JComponent np =((javax.swing.plaf.basic.BasicInternalFrameUI)ifu).getNorthPane();

		  np.setPreferredSize(new Dimension(parent.CANVASWIDTH+20,20));

			try {
				maxIcon = ImageIO.read(getClass().getResource("/data/icons/tools/fullscreen.png"));
				minIcon = ImageIO.read(getClass().getResource("/data/icons/tools/fullscreen_exit.png"));
				minCanvas.setIcon(new ImageIcon(maxIcon));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

minCanvas.setPreferredSize(new Dimension(30,30));
minCanvas.setMaximumSize(new Dimension(30,30));
minCanvas.setBounds(0,0,30,30);
brushPanel.add(minCanvas);
			
			
			minCanvas.setVisible(false);
		  
		minCanvas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(!maxBool){
					minCanvas.setIcon(new ImageIcon(minIcon));
					np.setPreferredSize(new Dimension(parent.screenWidth,0));
					
					parent.canvasFrame.setBounds(20,0,parent.screenWidth,parent.screenHeight);

					maxBool=true;
				}else{
					minCanvas.setIcon(new ImageIcon(maxIcon));
				
					
				 np.setPreferredSize(new Dimension(parent.CANVASWIDTH+20,20));
			
				parent.canvasFrame.setBounds(20,0,parent.CANVASWIDTH+20,parent.CANVASHEIGHT+20);

				maxBool=false;
				}
				
				parent.mainPanel.repaint();
    			
			}
		});
	}
	public void createBrushOptions(){
				
		JPanel colorOptionsPanel = new JPanel();
		colorOptionsPanel.setVisible(true);
		colorOptionsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		
		colorButton = new ColorButton(this.parent);
		
		
		
		
		colorButton.setVisible(true);
	
		colorOptionsPanel.add(colorButton);
		colorOptionsPanel.setVisible(true);
		colorOptionsPanel.setBackground(bgCol);
		
		
		
		
		JLabel brushSizeLabel = new JLabel("Brush Size");

		brushSizeLabel.setForeground(fCol);
		
	
		
		
		SpinnerNumberModel sizeModel = new SpinnerNumberModel(parent.PENSIZE,1, 255,
				1);
		sizeSpinner = new JSpinner(sizeModel);
		
		//sizeSpinner.setFont(parent.smallFont);
		sizeSpinner.addChangeListener(new MyChangeListener());
		sizeSpinner.setBackground(bgCol);
		
		

		
		
		JLabel brushAlphaLabel = new JLabel("Brush Alpha");
		brushAlphaLabel.setForeground(fCol);
		

		
		
		
		SpinnerNumberModel alphaModel = new SpinnerNumberModel(parent.PENALPHA,1, 255,
				1);
		alphaSpinner = new JSpinner(alphaModel);
		
		alphaSpinner.setForeground(fCol);
		alphaSpinner.addChangeListener(new MyChangeListener());

		//alphaSpinner.setFont(parent.smallFont);

		alphaSpinner.setBackground(bgCol);
		sizeSpinner.setBackground(bgCol);
		
		
		brushPanel.add(colorOptionsPanel);
		brushPanel.add(brushSizeLabel);
		brushPanel.add(sizeSpinner);
		brushPanel.add(brushAlphaLabel);
		brushPanel.add(alphaSpinner);
		
		
	
	}
	Image img; 

	public void setSelectOptions(){
		hideAll();
		selectPanel.setVisible(true);
	}
	public void setBrushOptions(){
		hideAll();
		brushPanel.setVisible(true);
	}
	public void setFillOptions(){
		hideAll();
		fillPanel.setVisible(true);
	}
	
	public void hideAll(){

		fillPanel.setVisible(false);
		selectPanel.setVisible(false);
		brushPanel.setVisible(false);
	}
	
	
	public void createFillOptions(){
		
		JLabel fillLabel = new JLabel("Fill Inaccuracy");

		fillLabel.setForeground(fCol);
		
		SpinnerNumberModel fillModel = new SpinnerNumberModel(parent.fillInaccuracy,0, 700,
				1);
		fillSpinner = new JSpinner(fillModel);
		
		//sizeSpinner.setFont(parent.smallFont);
		fillSpinner.addChangeListener(new MyChangeListener());
		
		fillSpinner.setBackground(bgCol);
		

		fillPanel.add(colorButton);
		fillPanel.add(fillLabel);
		fillPanel.add(fillSpinner);
		
		
	}
	public void createSelectOptions(){
		//this.removeAll();
		
		squareSelectExtrasPanel = new JPanel();
		squareSelectExtrasPanel.setBackground(bgCol);
		squareSelectExtrasPanel.setPreferredSize(new Dimension(400,30));
		JLabel featherLabel = new JLabel("Feather: ");
		//featherLabel.setFont(parent.smallFont);
		featherLabel.setForeground(fCol);
		JLabel roundCornerLabel = new JLabel("Rounded Corners: ");
		//roundCornerLabel.setFont(parent.smallFont);
		roundCornerLabel.setForeground(fCol);
		
		SpinnerNumberModel featherModel = new SpinnerNumberModel(parent.FEATHERSIZE,0, 99,
				1);
		
		featherSpinner = new JSpinner(featherModel);
		//featherSpinner.setFont(parent.smallFont);
		
		SpinnerNumberModel roundCornerModel = new SpinnerNumberModel(parent.ROUNDCORNERSIZE,0, 99,
				1);
		
		roundCornerSpinner = new JSpinner(roundCornerModel);
		

		featherSpinner.addChangeListener(new MyChangeListener());
		roundCornerSpinner.addChangeListener(new MyChangeListener());
		
		JPanel spacer = new JPanel();
		spacer.setPreferredSize(new Dimension(20,20));

		spacer.setBackground(bgCol);
		spacer.setBorder(null);

		JButton selectRectButton = new JButton();
		selectRectButton.setPreferredSize(new Dimension(25,25));
		
		JButton selectCircButton = new JButton();
		selectCircButton.setPreferredSize(new Dimension(25,25));
		
		JButton squiggleButton = new JButton();
		squiggleButton.setPreferredSize(new Dimension(25,25));
		
		try {
			img = ImageIO.read(getClass().getResource("/data/icons/tools/selectRect.png"));
			selectRectButton.setIcon(new ImageIcon(img));
			img = ImageIO.read(getClass().getResource("/data/icons/tools/selectCirc.png"));
			selectCircButton.setIcon(new ImageIcon(img));;
			img = ImageIO.read(getClass().getResource("/data/icons/tools/squiggle.png"));
			selectCircButton.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		squareSelectExtrasPanel.add(featherLabel);
		squareSelectExtrasPanel.add(featherSpinner);
		squareSelectExtrasPanel.add(roundCornerLabel);
		squareSelectExtrasPanel.add(roundCornerSpinner);
		squareSelectExtrasPanel.add(spacer);
		selectPanel.add(squareSelectExtrasPanel);
		selectPanel.add(selectRectButton);
		selectPanel.add(selectCircButton);
		selectPanel.add(squiggleButton);
		selectPanel.repaint();
	}
	
	private class MyChangeListener implements ChangeListener {
    
    	public MyChangeListener() {
         }
    	
	public void stateChanged(ChangeEvent e) {
	        
	    	parent.PENSIZE = (Integer) sizeSpinner.getValue();
	    	if(parent.PENSIZE<=0)
	    		parent.PENSIZE=1;
			parent.penOps.brushSizeSlider.setValue(parent.PENSIZE);
			
			parent.PENALPHA = (Integer) alphaSpinner.getValue();
	    	if(parent.PENALPHA<=0)
	    		parent.PENALPHA=1;
	    	parent.penOps.brushAlphaSlider.setValue(parent.PENALPHA);
	    	
	    	
	    	parent.FEATHERSIZE = (Integer) featherSpinner.getValue();
	    	if(parent.FEATHERSIZE<0)
	    		parent.FEATHERSIZE=0;
	    	//parent.penOps.brushAlphaSlider.setValue(parent.PENALPHA);
	    	
	    	parent.ROUNDCORNERSIZE = (Integer) roundCornerSpinner.getValue();
	    	if(parent.ROUNDCORNERSIZE<0)
	    		parent.ROUNDCORNERSIZE=0;
	    	
	    	parent.fillInaccuracy = (Integer) fillSpinner.getValue();
	    	if(parent.fillInaccuracy<0)
	    		parent.fillInaccuracy=0;
	    	//parent.penOps.brushAlphaSlider.setValue(parent.PENALPHA);
	    
	}
	
	}

}
