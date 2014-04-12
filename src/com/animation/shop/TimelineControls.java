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
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class TimelineControls extends JInternalFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color inactiveCol =new Color(240,240,240);
	Color activeCol =new Color(190,190,190);
	JButton toggleTrans ;
	Main parent;
	JSpinner fpsSpinber;
	JButton loopButton ;
	JSpinner onLeftSpinner ;
	JSpinner onRightSpinner ;
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

		this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(
				238, 238, 238)));

		JPanel panelArea = new JPanel();

		panelArea.setLayout(new BoxLayout(panelArea, BoxLayout.Y_AXIS));
		

		JPanel onionSkinArea = new JPanel();
		onionSkinArea.setLayout(new BoxLayout(onionSkinArea, BoxLayout.Y_AXIS));
		JPanel fpsArea = new JPanel(new FlowLayout(FlowLayout.LEADING));

		JPanel onionSkinArea1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JPanel onionSkinArea2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JPanel onionSkinArea3 = new JPanel(new FlowLayout(FlowLayout.LEADING));

		onionSkinArea.add(onionSkinArea1);
		onionSkinArea.add(onionSkinArea2);
		onionSkinArea.add(onionSkinArea3);

		JLabel tmpLbl = new JLabel("Onion Skin");

		onionSkinArea1.add(tmpLbl);
		// onionSkinArea1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		JLabel fpsLabel = new JLabel("FPS: ");

		SpinnerNumberModel onLeftModel = new SpinnerNumberModel(0, 0, 6,
				1);
		onLeftSpinner = new JSpinner(onLeftModel);

		SpinnerNumberModel onRightModel = new SpinnerNumberModel(0, 0, 6,
				1);
		onRightSpinner = new JSpinner(onRightModel);

		onionSkinArea2.add(new JLabel("Left: "));
		onionSkinArea2.add(onLeftSpinner);

		onionSkinArea2.add(new JLabel("Right: "));
		onionSkinArea2.add(onRightSpinner);

		SpinnerNumberModel fpsModel = new SpinnerNumberModel(parent.FPS, 1, 120,
				1);
		fpsSpinber = new JSpinner(fpsModel);

		fpsArea.setAlignmentX(LEFT_ALIGNMENT);

		onionSkinArea.setAlignmentX(LEFT_ALIGNMENT);

		JPanel transparencyOfLayersPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		toggleTrans = new JButton("Toggle Layer Transparency");
		toggleTrans.setFont(parent.smallFont);
		toggleTrans.setBackground(activeCol);
		transparencyOfLayersPanel.setPreferredSize(new Dimension(180, 20));
		toggleTrans.setPreferredSize(new Dimension(180, 20));
		transparencyOfLayersPanel.add(toggleTrans);
		transparencyOfLayersPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		
		JPanel playControlPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JButton playButton = new JButton();
		playButton.setPreferredSize(new Dimension(20, 20));
		JButton pauseButton = new JButton();
		pauseButton.setPreferredSize(new Dimension(20, 20));
		JButton stopButton = new JButton();
		stopButton.setPreferredSize(new Dimension(20, 20));
		loopButton = new JButton();
		loopButton.setPreferredSize(new Dimension(20, 20));
		JButton muteButton = new JButton();
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
