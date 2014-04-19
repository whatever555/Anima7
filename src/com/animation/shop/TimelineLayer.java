package com.animation.shop;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;

import aniExtraGUI.EButton;
import aniExtraGUI.EPanel;


public class TimelineLayer extends EPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Main parent;
	int layerID;
	public String layerName;
	int xoff;
	int yoff;
	boolean isMask = false;
	boolean hasMask = false;
	int maskOf = -1;
	int myMask = -1;
	ArrayList<TimelineButton> jbs = new ArrayList<TimelineButton>();
	public TButton layerNameLabel;
	EPanel labelArea = new EPanel();
	EPanel lineArea;
	boolean activeMask = false;
	boolean visible = true;
	public String BLENDING = "Normal";
	public int alphaLevel=255;
	
	public TimelineLayer(Main parent, int layerID, boolean isMask) {
		this.isMask = isMask;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		lineArea = new EPanel();
		//lineArea.setBackground(parent.timeline.bgCol);
		lineArea.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.parent = parent;
		this.layerName = translate("Layer")+" " + (layerID + 1);
		if (isMask)
			this.layerName = translate("Mask for")+" " + (parent.CURRENTLAYER + 1);
		this.layerID = layerID;
		this.layerNameLabel = new TButton();
		layerNameLabel.setText(this.layerName + ":");
		this.layerNameLabel.setFont(parent.timeline.f1);
		parent.layerIndex++;
		
		
		
		LabelMe();
		
		updateFrameLength();

		jbs.get(0).isKey=true;
	
		
		showMe();
		if (!isMask) {
			this.add(lineArea);
			parent.timeline.mainLine.add(this);
		//	parent.timeline.mainLine.setComponentZOrder(this,parent.timeline.layers.size());
			//this.setPreferredSize(new Dimension(200,parent.timelineButtonHeight));
			
		} else {
			activeMask=true;
			//lineArea.setBackground(new Color(207, 207, 207));
			this.maskOf = parent.CURRENTLAYER;
			int maskOfIndex = parent.canvas.getLayerIndex(parent.CURRENTLAYER);

			parent.timeline.layers.get(maskOfIndex).hasMask = true;
			parent.timeline.layers.get(maskOfIndex).myMask = layerID;

			parent.timeline.layers.get(maskOfIndex).removeAll();
			parent.timeline.layers.get(maskOfIndex).add(lineArea);
			parent.timeline.layers.get(maskOfIndex).add(
					parent.timeline.layers.get(maskOfIndex).lineArea);
			
			parent.timeline.layers.get(maskOfIndex).revalidate();
			parent.timeline.layers.get(maskOfIndex).repaint();
			parent.timeline.revalidate();
			parent.timeline.repaint();

		}
	}
	
	public TimelineLayer(Main parent, int layerID, boolean isMask,int maskOf, String label) {
		this.isMask = isMask;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		lineArea = new EPanel();
	//	lineArea.setBackground(parent.timeline.bgCol);
		lineArea.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.parent = parent;
		this.layerName = label;
		this.layerID = layerID;
		this.layerNameLabel = new TButton(this.layerName + ":");
		this.layerNameLabel.setFont(parent.timeline.f1);
		parent.layerIndex++;
		LabelMe();
		updateFrameLength();
		showMe();
		if (!isMask) {
			this.add(lineArea);
			parent.timeline.mainLine.add(this);
			//parent.timeline.mainLine.setComponentZOrder(this,parent.timeline.layers.size());
		//	this.setPreferredSize(new Dimension(200,parent.timelineButtonHeight));
		} else {
			activeMask=true;
		//	lineArea.setBackground(new Color(255, 255, 250));
			this.maskOf = maskOf;
			int maskOfIndex = parent.canvas.getLayerIndex(maskOf);

			parent.timeline.layers.get(maskOfIndex).hasMask = true;
			parent.timeline.layers.get(maskOfIndex).myMask = layerID;

			parent.timeline.layers.get(maskOfIndex).removeAll();
			parent.timeline.layers.get(maskOfIndex).add(lineArea);
			parent.timeline.layers.get(maskOfIndex).add(
					parent.timeline.layers.get(maskOfIndex).lineArea);
			//this.setBackground(new Color(0, 0, 0));
			parent.timeline.layers.get(maskOfIndex).revalidate();
			parent.timeline.layers.get(maskOfIndex).repaint();
			parent.timeline.revalidate();
			parent.timeline.repaint();

		}
	}


	 public String translate(String str){
		 return parent.translate(str);
	 }
	public void deleteMe() {
		if (hasMask) {
			parent.timeline.layers.get(parent.canvas.getLayerIndex(myMask))
					.deleteMe();
		}
		if (isMask) {
			int maskOfIndex = parent.canvas.getLayerIndex(maskOf);
			parent.timeline.layers.get(maskOfIndex).hasMask = false;
			parent.timeline.layers.get(maskOfIndex).remove(lineArea);
			parent.timeline.layers.get(maskOfIndex).revalidate();
			parent.timeline.layers.get(maskOfIndex).repaint();
			parent.CURRENTLAYER = parent.timeline.layers.get(maskOfIndex).layerID;
		} else {

			remove(lineArea);

			parent.CURRENTLAYER = parent.timeline.layers.get(0).layerID;

		}
		int myIndex=parent.canvas.getLayerIndex(layerID);
		parent.timeline.layers.remove(myIndex);
		if (!hasMask) {
			parent.CURRENTLAYER = parent.timeline.layers.get(0).layerID;
		}
		parent.MAXLAYERS--;

		for(int i=myIndex;i<parent.timeline.layers.size();i++){
			if(!parent.timeline.layers.get(i).isMask)
			parent.timeline.mainLine.setComponentZOrder(parent.timeline.layers.get(i),
					parent.timeline.mainLine.getComponentZOrder(parent.timeline.layers.get(i))-1);
		}
	}

	public void showMe() {
	
		for (int i = 0; i < jbs.size(); i++){
			
			lineArea.add(jbs.get(i));
		}
	}

	public void updateLayerName(String name) {
		this.layerName = name;
		this.layerNameLabel = new TButton(layerName);
		this.layerNameLabel.repaint();
	}

	TButton showHideButton = new TButton("");
	public void LabelMe() {
		labelArea.setBackground(null);
		layerNameLabel.setBackground(null);
		layerNameLabel.setBorder(null);
		layerNameLabel.setMinimumSize(new Dimension(140,parent.timelineButtonHeight));
		EButton optionsBut = new EButton();
		optionsBut.setText("E");
		optionsBut.setPreferredSize(new Dimension(15,parent.timelineButtonHeight));
		optionsBut.setBorder(null);
		optionsBut.addActionListener(new MyActionListener("Options"));
		
		if (!isMask){
			
			
			TButton upBut = new TButton("");
			TButton downBut = new TButton("");
			upBut.setIcon(parent.timeline.upIcon);
			downBut.setIcon(parent.timeline.downIcon);
			upBut.setPreferredSize(new Dimension(
					15, parent.timelineButtonHeight/2));
			downBut.setPreferredSize(new Dimension(
					15, parent.timelineButtonHeight/2));
			labelArea.add(upBut);
			labelArea.add(downBut);

			upBut.addActionListener(new MyActionListener("moveUp"));
			downBut.addActionListener(new MyActionListener("moveDown"));
		}
		
		showHideButton.setBackground(null);
		showHideButton.setPreferredSize(new Dimension(
				parent.timelineButtonWidth, parent.timelineButtonHeight));
		if (!isMask){
			showHideButton.setIcon(parent.timeline.eyeIcon);
			
		}
		else{
			showHideButton.setIcon(parent.timeline.maskIcon);
		}
		showHideButton.addActionListener(new MyActionListener("showHide"));
		
		labelArea.add(optionsBut);
		labelArea.add(showHideButton);
		labelArea.add(layerNameLabel);
		labelArea.setPreferredSize(new Dimension(parent.timeline.xoff,
				parent.timelineButtonHeight));

		labelArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		lineArea.add(this.labelArea);
	}
public void moveUp(){
	int myIndex = parent.canvas.getLayerIndex(layerID);
	if(myIndex>0){
		int swapIndex = myIndex-1;
		while(swapIndex>=0){
			if(!parent.timeline.layers.get(swapIndex).isMask){
			
				
				int myZIndex = parent.timeline.mainLine.getComponentZOrder(parent.timeline.layers.get(myIndex));
				int swapZIndex = parent.timeline.mainLine.getComponentZOrder(parent.timeline.layers.get(swapIndex));
				System.out.println("MYINDEX: "+myIndex+"  SWAPIndex: "+swapZIndex);
				
				parent.timeline.mainLine.setComponentZOrder(parent.timeline.layers.get(swapIndex),myZIndex);
				parent.timeline.mainLine.setComponentZOrder(parent.timeline.layers.get(myIndex),swapZIndex);
				
				
				TimelineLayer tmp = parent.timeline.layers.get(swapIndex);
				parent.timeline.layers.set(swapIndex,parent.timeline.layers.get(myIndex));
				parent.timeline.layers.set(myIndex,tmp);
				
			
				parent.timeline.revalidate();
				parent.timeline.repaint();
				
				parent.timeline.layers.get(swapIndex).revalidate();
				parent.timeline.layers.get(myIndex).revalidate();
				parent.timeline.layers.get(swapIndex).repaint();
				parent.timeline.layers.get(myIndex).repaint();
				break;
			}
			swapIndex--;
		}
	}
}

public void moveDown(){
	int myIndex = parent.canvas.getLayerIndex(layerID);
	if(myIndex<parent.timeline.layers.size()-1){
		int swapIndex = myIndex+1;
		while(swapIndex<parent.timeline.layers.size()){
			if(!parent.timeline.layers.get(swapIndex).isMask){

				
				int myZIndex = parent.timeline.mainLine.getComponentZOrder(parent.timeline.layers.get(myIndex));
				int swapZIndex = parent.timeline.mainLine.getComponentZOrder(parent.timeline.layers.get(swapIndex));
				System.out.println("MYINDEX: "+myIndex+"  SWAPIndex: "+swapZIndex);
				
				parent.timeline.mainLine.setComponentZOrder(parent.timeline.layers.get(swapIndex),myZIndex);
				parent.timeline.mainLine.setComponentZOrder(parent.timeline.layers.get(myIndex),swapZIndex);
				
				
				TimelineLayer tmp = parent.timeline.layers.get(swapIndex);
				parent.timeline.layers.set(swapIndex,parent.timeline.layers.get(myIndex));
				parent.timeline.layers.set(myIndex,tmp);
				
			
				parent.timeline.revalidate();
				parent.timeline.repaint();
				
				parent.timeline.layers.get(swapIndex).revalidate();
				parent.timeline.layers.get(myIndex).revalidate();
				parent.timeline.layers.get(swapIndex).repaint();
				parent.timeline.layers.get(myIndex).repaint();
				break;
			}
			swapIndex++;
		}
	}
}

public void showHide(){
	if(visible){
		visible=false;
		showHideButton.setBackground(new Color(255,100,100));
	}else{
		visible=true;
		showHideButton.setBackground(null);
		
	}
}
	
public void showOptions(){
	parent.LOF.setLayer(this);
	parent.LOF.setVisible(true);
}
	public void updateFrameLength() {
		while (jbs.size() > parent.MAXFRAMES){
			jbs.get(jbs.size()-1).setVisible(false);
			jbs.remove(jbs.size()-1);
			
			
		}
		while (jbs.size() < parent.MAXFRAMES){
			jbs.add(new TimelineButton(parent, this, jbs.size()));
			lineArea.add(jbs.get(jbs.size()-1));
			
			
		}
	}

	 private class MyActionListener implements ActionListener {

	        String myActionName;

	        public MyActionListener(String actName) {
	           this.myActionName = actName;
	        }
	        

	        public void actionPerformed(ActionEvent e) {
	        	if(myActionName.equals("moveUp"))
	        		moveUp();
	        	else if(myActionName.equals("moveDown"))
	        		moveDown();
	        	else if(myActionName.equals("showHide"))
	        		showHide();
	        	else if(myActionName.equals("Options"))
	        		showOptions();
	        	parent.timeline.shiffleTable(parent.CURRENTFRAME,parent.CURRENTLAYER,-1,false);
	        }
	 }
	 
	        }

