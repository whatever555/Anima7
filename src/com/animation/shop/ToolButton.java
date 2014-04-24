package com.animation.shop;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import aniExtraGUI.EButton;

public class ToolButton extends EButton {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
ToolsBar parent;
String toolName;
boolean selected =false;

	public ToolButton(final ToolsBar parent, final String toolName,int w, int h){
		this.parent=parent;
		this.toolName=toolName;
		showMe(w,h);
		
		
	}
	
	public void showMe(int w, int h){
		Image img;
		this.setPreferredSize(new Dimension(w,h));
		this.setMaximumSize(new Dimension(w,h));
		

		try {
			//System.out.println("/data/icons/tools/"+toolName+".png");
			img = ImageIO.read(getClass().getResource("/data/icons/tools/"+toolName+".png"));
			setIcon(new ImageIcon(img));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		
		addActionListener(new ActionListener(){  
			@Override  
			public void actionPerformed(ActionEvent e) {  
			parent.parent.canvas.finaliseFrame(parent.parent.CURRENTLAYER,parent.parent.CURRENTFRAME);
			
					
				parent.refreshList();
				selected = true;
				setBackground(Color.white);
				parent.parent.currentTool = toolName;
				
				parent.parent.canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
				
				if(toolName.equals("Eraser")){
					parent.parent.timeline.shiffleTable(parent.parent.CURRENTFRAME,parent.parent.CURRENTLAYER,(byte)0,false);
					parent.parent.topPanel.setBrushOptions();
					parent.parent.canvas.eraseInitialized=false;
					parent.parent.setCursor(parent.parent.currentTool);
					}else
				if(toolName.equals("selectRect") || toolName.equals("selectCirc")){
					parent.parent.timeline.shiffleTable(parent.parent.CURRENTFRAME,parent.parent.CURRENTLAYER,(byte)0,false);
					parent.parent.topPanel.setSelectOptions();
					parent.parent.canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
					}else
						if(toolName.equals("brush")){
							parent.parent.timeline.shiffleTable(parent.parent.CURRENTFRAME,parent.parent.CURRENTLAYER,(byte)0,false);
							parent.parent.topPanel.setBrushOptions();
							
							parent.parent.setCursor(parent.parent.currentTool);
							}
							
						else
							if(toolName.equals("bucket")){
								parent.parent.timeline.shiffleTable(parent.parent.CURRENTFRAME,parent.parent.CURRENTLAYER,(byte)0,false);
								parent.parent.topPanel.setFillOptions();
								parent.parent.setCursor("bucket");
								}
							else
								if(toolName.equals("dropper")){
									parent.parent.timeline.shiffleTable(parent.parent.CURRENTFRAME,parent.parent.CURRENTLAYER,(byte)0,false);
									parent.parent.setCursor("dropper");
									}
								else
									if(toolName.equals("move")){
										parent.parent.currentTool="moveInit";
										parent.parent.canvas.clipBoard = parent.parent.canvas.currentFrameGraphic.get();
										
										parent.parent.canvas.saveImageToDisk(parent.parent.canvas.emptyImage,parent.parent.CURRENTLAYER,parent.parent.CURRENTFRAME);
										
										parent.parent.canvas.currentFrameGraphic.beginDraw();
											parent.parent.canvas.currentFrameGraphic.clear();
											parent.parent.canvas.currentFrameGraphic.endDraw();
											parent.parent.canvas.tempInkGraphic.beginDraw();
											parent.parent.canvas.tempInkGraphic.clear();
											parent.parent.canvas.tempInkGraphic.endDraw();
										
										 	parent.parent.canvas.layDownFrames(-1);
											parent.parent.canvas.pasteFromClipBoard(true,"Transform");
											parent.parent.canvas.keyEdited=true;

											parent.parent.canvas.setCursor(new Cursor(Cursor.HAND_CURSOR));
										}
				
				

				parent.parent.canvas.showNewFrame(parent.parent.CURRENTLAYER,parent.parent.CURRENTFRAME,-1);
			}  
			});  
	}
	
	
}
