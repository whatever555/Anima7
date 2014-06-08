package com.animation.shop;

import java.util.Date;

import processing.core.PImage;

public class UserAction {

	Date timeOfAction;
	String type,name;
	int y,x;
	PImage image;
	String[] keyStrings = new String[]{"Keyframe Added","Keyframe Removed","KEYADDFIX","KEYREMOVEFIX"};
	String[] emptyCanvasStrings = new String[]{"Empty Canvas","Create","Change Frame"};
	Main parent;
	TimelineButton keyFrame;
	
	public UserAction(String name,int y,int x,PImage image,Main parent){
		
		 this.timeOfAction= new java.util.Date();
		 this.y=y;
		 this.x=x;
		 this.name=name;
		 this.image = image.get();
		 this.parent=parent;
		 if(arrayContains(keyStrings,name)){
			 TimelineButton tmp = parent.timeline.layers.get(y).jbs.get(x);
			this.keyFrame = new TimelineButton(tmp);
			 type = "KEY";
		 }else
		 if(arrayContains(emptyCanvasStrings,name)){
				 type = "EMPTY";
		}else{
			type="PAINT";
		}
		 
		 
	}
	

	public void revertToMe(){
		if(type.equals("PAINT")){
			parent.canvas.saveImageToDisk(image,y,x);
		}else
			if(type.equals("EMPTY")){

				parent.canvas.saveImageToDisk(image,y,x);
			}else
				if(type.equals("KEY")){
					if(name.equals("Keyframe Added") || name.equals("KEYADDFIX") ){
						parent.canvas.saveImageToDisk(image,y,x);
					}
						parent.timeline.layers.get(y).jbs.get(x).updateVars(keyFrame) ;

						parent.timeline.shiffleTable(x, y, 0,true);
				}
		
		
		
		
	}
	
	public boolean arrayContains(String[] ar, String str){
		for(int i=0;i<ar.length;i++)
		{
			if(ar[i].equals(str))
				return true;
		}
		return false;
	}
	
}
