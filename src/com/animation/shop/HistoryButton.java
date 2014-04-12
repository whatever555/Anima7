package com.animation.shop;



import processing.core.PApplet;
import processing.core.PImage;

final class HistoryButton extends PApplet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Main parent;
	int actionValue;
	String actionName;
	PImage img;
	PImage img2;
	protected HistoryButton(final Main parent,String actionName,final int actionValue){
		System.out.println();
		
		this.parent=parent;
		this.actionName=actionName;
		this.actionValue=actionValue;
	img = loadImage("icons/actions/"+(actionName.replaceAll("\\s",""))+".png");
	img2 = parent.historicImages.get(actionValue);
	
		
	}
	
	public void draw(){
		noStroke();
		fill(190);
		rect(0,0,182,20,3);
			
		fill(255);
		rect(1,1,30,30,3);
			if(img!=null)
			image(img,20,20,14,14);

			if(img2!=null)
			image(img2,1,1,35,35);
		
			fill(0);
			textSize(16);
			text(actionName,34,16);
			noLoop();
		
			
	}
	
	public void setup(){
		size(180,20);
		
	}
	
	public void mouseDragged() {
		fill(100);
		rect(0,0,142,20,3);
			if(img!=null)
			image(img,1,1,18,18);
			else
			println("its null");
		
			fill(0);
			text(actionName,25,12);
	}
	public void mouseReleased(){
				if(actionValue>parent.currentActionIndex){
				while(parent.currentActionIndex!=actionValue){
					parent.canvas.redo();
					//System.out.println("REDOING: "+parent.currentActionIndex);
				}
				}
				else{
				while(parent.currentActionIndex!=actionValue){
					
					parent.canvas.undo();
					//System.out.println("UNDOING: "+parent.currentActionIndex);
				}	
				}

			 
	}
}
