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
import javax.swing.JComponent;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aniExtraGUI.EButton;
import aniExtraGUI.ELabel;
import aniExtraGUI.EPanel;
import aniExtraGUI.ESpinner;

public class TopPanel extends EPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	Main parent;
	
	
	Color bgCol = new Color(37,37,37);
	Color fCol  = new Color(167,167,167);
	Image maxIcon,minIcon;
	 boolean maxBool=false;
	 final EButton minCanvas = new EButton();

		EPanel colorOptionsPanel = new EPanel();
	 
	public TopPanel(Main parent){
this.parent=parent;
brushPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
fillPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
selectPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
squareSelectExtrasPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

		showMe();
	}
	
	public void showMe(){

		this.setBackground(bgCol);
		this.setForeground(fCol);
		this.setLayout(new FlowLayout(FlowLayout.LEADING));

		addColorSelect();
		createSelectOptions();
		createBrushOptions();
		createFillOptions();

		createMinMaxCanvasBut();
		

		this.add(colorOptionsPanel);
		this.add(fillPanel);
		this.add(brushPanel);
		this.add(selectPanel);
		this.add(minCanvas);
		brushPanel.setBackground(bgCol);
		selectPanel.setBackground(bgCol);
		fillPanel.setBackground(bgCol);
		selectPanel.setVisible(false);
		brushPanel.setVisible(false);
		fillPanel.setVisible(false);

	}
	
	public void addColorSelect(){
		colorOptionsPanel.setVisible(true);
		colorOptionsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		
		colorButton = new ColorButton(this.parent);
		
		
		
		
		colorButton.setVisible(true);
	
		colorOptionsPanel.add(colorButton);
		colorOptionsPanel.setVisible(true);
		colorOptionsPanel.setBackground(bgCol);
		

	}
	
	ColorButton colorButton;
	ESpinner fillSpinner;
	ESpinner alphaSpinner;
	ESpinner sizeSpinner;
	ESpinner featherSpinner;
	ESpinner roundCornerSpinner;
	ESpinner inaccuracySpinner;
	
	EPanel brushPanel = new EPanel();
	EPanel fillPanel = new EPanel();
	EPanel selectPanel = new EPanel();
	EPanel squareSelectExtrasPanel  = new EPanel();
	EPanel inaccuracyPanel = new EPanel();

	public void createMinMaxCanvasBut(){
		
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

minCanvas.setPreferredSize(new Dimension(20,20));
minCanvas.setMaximumSize(new Dimension(30,30));
minCanvas.setBounds(0,0,30,30);

			
			
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
				
		ELabel brushSizeLabel = new ELabel(translate("Brush Size"));

		brushSizeLabel.setForeground(fCol);
		
		SpinnerNumberModel sizeModel = new SpinnerNumberModel(parent.PENSIZE,1, 255,
				1);
		sizeSpinner = new ESpinner(sizeModel);
		
		sizeSpinner.addChangeListener(new MyChangeListener());
		sizeSpinner.setBackground(bgCol);
		
		
		ELabel brushAlphaLabel = new ELabel(translate("Brush Alpha"));
		brushAlphaLabel.setForeground(fCol);
		
		SpinnerNumberModel alphaModel = new SpinnerNumberModel(parent.PENALPHA,1, 255,
				1);
		alphaSpinner = new ESpinner(alphaModel);
		
		alphaSpinner.setForeground(fCol);
		alphaSpinner.addChangeListener(new MyChangeListener());

	

		alphaSpinner.setBackground(bgCol);
		sizeSpinner.setBackground(bgCol);
		
		
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

		selectPanel.setVisible(false);
		brushPanel.setVisible(false);
		fillPanel.setVisible(false);
	}
	
	
	public void createFillOptions(){
		
		ELabel fillLabel = new ELabel(translate("Fill Inaccuracy"));

		fillLabel.setForeground(fCol);
		
		SpinnerNumberModel fillModel = new SpinnerNumberModel(parent.fillInaccuracy,0, 700,
				1);
		fillSpinner = new ESpinner(fillModel);
		
		fillSpinner.addChangeListener(new MyChangeListener());
		
		fillSpinner.setBackground(bgCol);
		

		fillPanel.add(fillLabel);
		fillPanel.add(fillSpinner);
		
		
	}
	
	 public String translate(String str){
		 return parent.translate(str);
	 }
	public void createSelectOptions(){
		//this.removeAll();
		

inaccuracyPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
inaccuracyPanel.setPreferredSize(new Dimension(180,20));
inaccuracyPanel.setBounds(0,-5,180,20);
inaccuracyPanel.setBackground(null);
		squareSelectExtrasPanel.setBackground(bgCol);
		
		ELabel featherLabel = new ELabel(translate("Feather:"));
		ELabel roundCornerLabel = new ELabel(translate("Rounded Corners:"));
		ELabel inaccuracyLabel = new ELabel(translate("Inaccuracy:"));
		
		SpinnerNumberModel featherModel = new SpinnerNumberModel(parent.FEATHERSIZE,0, 99,
				1);
		
		featherSpinner = new ESpinner(featherModel);
		
		SpinnerNumberModel roundCornerModel = new SpinnerNumberModel(parent.ROUNDCORNERSIZE,0, 99,
				1);
		
		roundCornerSpinner = new ESpinner(roundCornerModel);
		
		SpinnerNumberModel inaccuracyModel = new SpinnerNumberModel(parent.selectInaccuracy,0, 500,
				1);
		
		inaccuracySpinner = new ESpinner(inaccuracyModel);

		featherSpinner.addChangeListener(new MyChangeListener());
		roundCornerSpinner.addChangeListener(new MyChangeListener());
		inaccuracySpinner.addChangeListener(new MyChangeListener());
		
		EPanel spacer = new EPanel();
		spacer.setPreferredSize(new Dimension(20,30));

		spacer.setBackground(bgCol);
		spacer.setBorder(null);

		EButton selectRectButton = new EButton();
		selectRectButton.setPreferredSize(new Dimension(20,20));
		
		EButton selectCircButton = new EButton();
		selectCircButton.setPreferredSize(new Dimension(20,20));
		
		EButton squiggleButton = new EButton();
		squiggleButton.setPreferredSize(new Dimension(20,20));
		
		EButton wandButton = new EButton();
		wandButton.setPreferredSize(new Dimension(20,20));
		
		selectRectButton.addActionListener(new MyActionListener("selectRect"));
		selectCircButton.addActionListener(new MyActionListener("selectCirc"));
		squiggleButton.addActionListener(new MyActionListener("selectShape"));
		wandButton.addActionListener(new MyActionListener("selectWand"));
		
		try {
			img = ImageIO.read(getClass().getResource("/data/icons/tools/selectRect.png"));
			selectRectButton.setIcon(new ImageIcon(img));
			img = ImageIO.read(getClass().getResource("/data/icons/tools/selectCirc.png"));
			selectCircButton.setIcon(new ImageIcon(img));
			img = ImageIO.read(getClass().getResource("/data/icons/tools/squiggle.png"));
			squiggleButton.setIcon(new ImageIcon(img));
			img = ImageIO.read(getClass().getResource("/data/icons/tools/wand.png"));
			wandButton.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
inaccuracyPanel.setVisible(false);
		inaccuracyPanel.add(inaccuracyLabel);
		inaccuracyPanel.add(inaccuracySpinner);
		
		selectPanel.add(featherLabel);
		selectPanel.add(featherSpinner);
		selectPanel.add(roundCornerLabel);
		selectPanel.add(roundCornerSpinner);
		selectPanel.add(selectRectButton);
		selectPanel.add(selectCircButton);
		selectPanel.add(squiggleButton);
		selectPanel.add(wandButton);

		selectPanel.add(inaccuracyPanel);
		
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
	    	
	    	parent.selectInaccuracy = (Integer) inaccuracySpinner.getValue();
	    	if(parent.selectInaccuracy<0)
	    		parent.selectInaccuracy=0;
	    	
	    	parent.fillInaccuracy = (Integer) fillSpinner.getValue();
	    	if(parent.fillInaccuracy<0)
	    		parent.fillInaccuracy=0;
	    	//parent.penOps.brushAlphaSlider.setValue(parent.PENALPHA);
	    
	}
	
	}
	
	
	private class MyActionListener implements ActionListener {
	    String myAction;
    	public MyActionListener(String myAction) {
    		this.myAction=myAction;
         }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(myAction.equals("selectRect")){
			inaccuracyPanel.setVisible(false);
			parent.currentTool = "selectRect";
		}else
			if(myAction.equals("selectCirc")){
				inaccuracyPanel.setVisible(false);
				parent.currentTool = "selectCirc";
			}else

				if(myAction.equals("selectShape")){
					inaccuracyPanel.setVisible(false);
					parent.currentTool = "selectShape";
				}
				else

					if(myAction.equals("selectWand")){
						parent.currentTool = "selectWand";
						inaccuracyPanel.setVisible(true);
					}
		
	}
	
	}

}
