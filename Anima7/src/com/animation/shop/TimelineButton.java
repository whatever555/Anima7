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
boolean unedited=true;

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

public TimelineButton(TimelineButton tb){
	this.parent=tb.parent;
	this.myLayer=tb.myLayer;
	this.frameID=tb.frameID;
	this.w=parent.timelineButtonWidth;
	this.h=parent.timelineButtonHeight;
	this.x=tb.x;
	this.y=tb.y;
	this.layerID=tb.layerID;
	this.w=tb.w;
	this.h=tb.h;
	this.isKey=tb.isKey;
	this.audioFile=tb.audioFile;
	this.hasAudio=tb.hasAudio;
	this.selected=tb.selected;
	this.unedited=tb.unedited;
	fixMe();
	myLayer.add(this);
	this.addActionListener(parent.timelineButtonActionListener);
	this.setActionCommand(myLayer.layerID+"-"+frameID);
	
	this.setBackground(tb.getBackground());
}

public void updateVars(TimelineButton tb){
	this.parent=tb.parent;
	this.myLayer=tb.myLayer;
	this.frameID=tb.frameID;
	this.w=parent.timelineButtonWidth;
	this.h=parent.timelineButtonHeight;
	this.x=tb.x;
	this.y=tb.y;
	this.layerID=tb.layerID;
	this.w=tb.w;
	this.h=tb.h;
	this.isKey=tb.isKey;
	this.audioFile=tb.audioFile;
	this.hasAudio=tb.hasAudio;
	this.selected=tb.selected;
	this.unedited=tb.unedited;
	this.setBackground(tb.getBackground());
	//fixMe();
	//myLayer.add(this);
	//this.addActionListener(parent.timelineButtonActionListener);
//	this.setActionCommand(myLayer.layerID+"-"+frameID);

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

public void removeMe(){
	audioFile="";
	hasAudio=false;
	isKey=false;
	selected=false;
	hasImage=false;

	
}


	

}
