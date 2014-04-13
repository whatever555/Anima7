package com.animation.shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aniExtraGUI.EButton;
import aniExtraGUI.EInternalFrame;
import aniExtraGUI.ELabel;
import aniExtraGUI.EPanel;
import aniExtraGUI.ESpinner;


public class TimelineControls extends EInternalFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color inactiveCol =new Color(240,240,240);
	Color activeCol =new Color(190,190,190);
	EButton toggleTrans ;
	Main parent;
	ESpinner fpsSpinber;
	EButton loopButton ;
	ESpinner onLeftSpinner ;
	ESpinner onRightSpinner ;
	public TimelineControls(Main parent) {
		this.parent = parent;
		showMe();
	}

	public void showMe() {

		this.setTitle("Timeline Controls");
		this.setLayout(new BorderLayout());
		this.setClosable(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(1);

	
		EPanel panelArea = new EPanel();

		panelArea.setLayout(new BoxLayout(panelArea, BoxLayout.Y_AXIS));
		

		EPanel onionSkinArea = new EPanel();
		onionSkinArea.setLayout(new BoxLayout(onionSkinArea, BoxLayout.Y_AXIS));
		EPanel fpsArea = new EPanel();

		EPanel onionSkinArea1 = new EPanel();
		EPanel onionSkinArea2 = new EPanel();
		EPanel onionSkinArea3 = new EPanel();

		fpsArea.setLayout(new FlowLayout(FlowLayout.LEADING));
		onionSkinArea1.setLayout(new FlowLayout(FlowLayout.LEADING));
		onionSkinArea2.setLayout(new FlowLayout(FlowLayout.LEADING));
		onionSkinArea3.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		onionSkinArea.add(onionSkinArea1);
		onionSkinArea.add(onionSkinArea2);
		onionSkinArea.add(onionSkinArea3);

		ELabel tmpLbl = new ELabel("Onion Skin");

		onionSkinArea1.add(tmpLbl);
		// onionSkinArea1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		ELabel fpsLabel = new ELabel("FPS: ");

		SpinnerNumberModel onLeftModel = new SpinnerNumberModel(0, 0, 6,
				1);
		onLeftSpinner = new ESpinner(onLeftModel);

		SpinnerNumberModel onRightModel = new SpinnerNumberModel(0, 0, 6,
				1);
		onRightSpinner = new ESpinner(onRightModel);

		onionSkinArea2.add(new ELabel("Left: "));
		onionSkinArea2.add(onLeftSpinner);

		onionSkinArea2.add(new ELabel("Right: "));
		onionSkinArea2.add(onRightSpinner);

		SpinnerNumberModel fpsModel = new SpinnerNumberModel(parent.FPS, 1, 120,
				1);
		fpsSpinber = new ESpinner(fpsModel);

		fpsArea.setAlignmentX(LEFT_ALIGNMENT);

		onionSkinArea.setAlignmentX(LEFT_ALIGNMENT);

		EPanel transparencyOfLayersPanel = new EPanel();
		transparencyOfLayersPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		toggleTrans = new EButton();
		toggleTrans.setText("Toggle Layer Transparency");
		toggleTrans.setFont(parent.smallFont);
		toggleTrans.setBackground(activeCol);
		transparencyOfLayersPanel.setPreferredSize(new Dimension(180, 20));
		toggleTrans.setPreferredSize(new Dimension(180, 20));
		transparencyOfLayersPanel.add(toggleTrans);
		transparencyOfLayersPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		
		EPanel playControlPanel = new EPanel();
		playControlPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		EButton playButton = new EButton();
		playButton.setPreferredSize(new Dimension(20, 20));
		EButton pauseButton = new EButton();
		pauseButton.setPreferredSize(new Dimension(20, 20));
		EButton stopButton = new EButton();
		stopButton.setPreferredSize(new Dimension(20, 20));
		loopButton = new EButton();
		loopButton.setPreferredSize(new Dimension(20, 20));
		EButton muteButton = new EButton();
		muteButton.setPreferredSize(new Dimension(20, 20));
		

		try {

			Image img = ImageIO.read(getClass().getResource(
					"/data/icons/actions/play.png"));
			playButton.setIcon(new ImageIcon(img));img = ImageIO.read(getClass().getResource(
					"/data/icons/actions/pause.png"));
			pauseButton.setIcon(new ImageIcon(img));
			img = ImageIO.read(getClass().getResource(
					"/data/icons/actions/stop.png"));
			stopButton.setIcon(new ImageIcon(img));
			img = ImageIO.read(getClass().getResource(
					"/data/icons/actions/loop.png"));
			loopButton.setIcon(new ImageIcon(img));
			img = ImageIO.read(getClass().getResource(
					"/data/icons/actions/mute.png"));
			muteButton.setIcon(new ImageIcon(img));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		playButton.addActionListener(new MyActionListener("play"));
		pauseButton.addActionListener(new MyActionListener("pause"));
		loopButton.addActionListener(new MyActionListener("loop"));
		stopButton.addActionListener(new MyActionListener("stop"));
		fpsSpinber.addChangeListener(new MyChangeListener("fps"));
		onRightSpinner.addChangeListener(new MyChangeListener("onion"));
		onLeftSpinner.addChangeListener(new MyChangeListener("onion"));

		toggleTrans.addActionListener(new MyActionListener("transToggle"));
		
		playControlPanel.setAlignmentX(LEFT_ALIGNMENT);

		playControlPanel.add(playButton);
		playControlPanel.add(pauseButton);
		playControlPanel.add(stopButton);
		playControlPanel.add(loopButton);
		playControlPanel.add(muteButton);


		fpsArea.add(fpsLabel);
		fpsArea.add(fpsSpinber);

		panelArea.add(fpsArea);
		panelArea.add(onionSkinArea);
		panelArea.add(transparencyOfLayersPanel);
		panelArea.add(playControlPanel);
		this.add(panelArea);

	}
	
	

    private class MyChangeListener implements ChangeListener {
    	String changeName;
    	public MyChangeListener(String changeName) {
            this.changeName = changeName;
         }
    	
	 public void stateChanged(ChangeEvent e) {
		 if(parent.LOADED){
		 parent.canvas.finaliseFrame(parent.CURRENTLAYER,parent.CURRENTFRAME);
	     if(changeName.equals("fps")){
parent.FPS = (Integer)(fpsSpinber.getValue());
	     }else
	    		if(changeName.equals("onion")){
	        		parent.onionLeft = (Integer) onLeftSpinner.getValue();
	        		parent.onionRight = (Integer) onRightSpinner.getValue();
	        		System.out.println("ONION MOFO");
	        		parent.timeline.shiffleTable(parent.CURRENTFRAME,parent.CURRENTLAYER,0,false);
	        		
	        	}

   		 parent.canvas.showNewFrame(parent.CURRENTLAYER,parent.CURRENTFRAME,-1);
		 
		 }
	    }
	 
    }
	

    private class MyActionListener implements ActionListener {

        String myActionName;

        public MyActionListener(String actName) {
           this.myActionName = actName;
        }
        

        public void actionPerformed(ActionEvent e) {

   		 parent.canvas.finaliseFrame(parent.CURRENTLAYER,parent.CURRENTFRAME);
        	if(myActionName.equals("transToggle")){
        		System.out.println("its a hit");
        		if(parent.canvas.transValue==255){
        			parent.canvas.transValue = 125;
        			toggleTrans.setBackground(activeCol);
        		}
        			else{
        			parent.canvas.transValue = 255;
        			toggleTrans.setBackground(inactiveCol);
        			
        			}
        	}else
    		if(myActionName.equals("play")){
    			if(!parent.playPreviewBool){
        			
    			parent.playPreviewBool=true;
    			
    			new Thread() {
    				public void run() {

    	    			parent.playPreview();

    				}
    			}.start();
    			
    			
    		}
    		}
    			else
        			if(myActionName.equals("pause")){

            			parent.playPreviewBool=false;
        			}
        			else
            			if(myActionName.equals("loop")){
            				if(parent.loopPreview){
                			parent.loopPreview=false;
                			loopButton.setBackground(inactiveCol);
            				}else{
parent.loopPreview=true;
loopButton.setBackground(new Color(190,190,190));
            				}
            			}
    		else
    			if(myActionName.equals("stop")){
    				parent.playPreviewBool=false;
    				new Thread() {
        				public void run() {
        					try {
        						Thread.sleep(300);

                				parent.timeline.shiffleTable(0,0,0,false);
        					} catch (InterruptedException e) {
        					}
        					

        				}
        			}.start();
    			}

      		 parent.canvas.showNewFrame(parent.CURRENTLAYER,parent.CURRENTFRAME,-1);
    			}
        }

  
}
