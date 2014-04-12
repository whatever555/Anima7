package com.animation.shop;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import aniExtraGUI.EButton;


public class TimelineButton extends EButton {
Main parent;
int layerID;
int x,y,w,h;
int frameID;
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
	this.addActionListener(new MyActionListener(myLayer.layerID,frameID));
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

private class MyActionListener implements ActionListener {

	 int x,y;
       public MyActionListener(int y,int x) {
       	  this.y = y;
       	  this.x = x;
       }
       

       public void actionPerformed(ActionEvent e) {
    	   
    	 	parent.timeline.shiffleTable(x,y,1,false);
       	
       }
   }


	

}
