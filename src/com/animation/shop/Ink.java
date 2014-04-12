/**
 * 
 */
package com.animation.shop;


/**
 * @author eddie
 *
 */
public class Ink {
	public float px,py,x,y,w;
	public int c,alpha;
	
	public Ink(float prevXPos, float prevYPos,float xPos, float yPos, float brushSize,int c,int alpha){
		this.x=xPos;
		this.y=yPos;
		this.px=prevXPos;
		this.py=prevYPos;
		this.w=brushSize;
		this.c=c;
		this.alpha=alpha;
	}

	
	

}
