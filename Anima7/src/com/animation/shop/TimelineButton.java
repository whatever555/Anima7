package com.animation.shop;

import java.awt.Dimension;

import javax.swing.SwingConstants;

import aniExtraGUI.EButton;


public class TimelineButton extends EButton {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Main parent;
int layerID;
int x,y,w,h;
int frameID;
String audioFile="";
boolean hasAudio=false;
TimelineLayer myLayer;
boolean isKey=false;
boolean selected=false;
boolean hasImage=false;
public TimelineButton(Main parent,TimelineLayer myLayer,int frameID){
	this.parent=parent;
	this.myLayer=myLayer;
	this.frameID=frameID;
	this.w=parent.timelineButtonWidth;
	this.h=parent.timelineButtonHeight;
	fixMe();
	myLayer.add(this);
	this.addActionListener(parent.timelineButtonActionListener);
	this.setActionCommand(myLayer.layerID+"-"+frameID);
}

public void fixMe(){
	//setBorder(null);
	//setBorderPainted(false);
	//setMargin(new Insets(0,0,0,0));
	setVerticalAlignment(SwingConstants.TOP);
	setAlignmentX(LEFT_ALIGNMENT);
	this.setBackground(parent.timeline.inactive);
	this.setPreferredSize(new Dimension(w,h));
	this.setMaximumSize(new Dimension(w,h));
	
}



	

}
