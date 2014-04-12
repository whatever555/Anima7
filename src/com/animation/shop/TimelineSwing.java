package com.animation.shop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import processing.core.PImage;

public class TimelineSwing extends JPanel{
/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
Main parent;
int w,h;
int lc,fc;//layer count, frame count
int boxWidth = 30;
int boxHeight=20;
int xoff = 160;
int yoff=boxHeight;
int loopC = 1;
PImage trans;


int stkCol = 67;
Color inactive = new Color(220,220,220);
Color active   = new Color(250,250,250);
Color highlightInactive = new Color(200,200,250);
Color highlightActive = new Color(180,180,250);
Color selectedFrameCol=new Color(120,120,200);
Color selectedKeyCol=new Color(70,70,200);
Color bgCol = new Color(127,127,127);
Image emptyIcon;
Font f1 = new Font("Verdana", Font.PLAIN, 10);
Font f2 = new Font("Verdana", Font.PLAIN, 8);
ImageIcon eyeIcon;
ImageIcon maskIcon;
ImageIcon upIcon;
ImageIcon downIcon;

ArrayList<Integer> layerIdMap = new ArrayList<Integer>();
ArrayList<Integer> layerIndexMap = new ArrayList<Integer>();

ArrayList<TimelineLayer> layers = new ArrayList<TimelineLayer>();



	public TimelineSwing(int lc, int fc,Main parent){

		Image eyeImage =null;
		Image maskImage =null;
		Image downImage =null;
		Image upImage =null;
		try {
			eyeImage = ImageIO.read(getClass().getResource("/data/icons/tools/eye.png"));
			maskImage = ImageIO.read(getClass().getResource("/data/icons/tools/mask.png"));
			downImage = ImageIO.read(getClass().getResource("/data/icons/tools/down.png"));
			upImage = ImageIO.read(getClass().getResource("/data/icons/tools/up.png"));
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		eyeIcon = new ImageIcon(eyeImage);
		maskIcon = new ImageIcon(maskImage);
		upIcon = new ImageIcon(upImage);
		downIcon = new ImageIcon(downImage);
		this.parent=parent;
		this.lc=lc;
		this.fc=fc;
		this.w = ((fc*boxWidth)+xoff)+100;
		this.h=((lc*boxHeight)+yoff)+50;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		try {
			emptyIcon = ImageIO.read(getClass().getResource("/data/icons/actions/emptyIcon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void addNewLayer(int y, boolean maskBool){
		
		TimelineLayer tl = new TimelineLayer(parent,y,maskBool);
		layers.add(tl);
		parent.canvas.saveImageToDisk(parent.canvas.emptyImage, tl.layerID, 0);
		
		

		revalidate();
		repaint();

	}
	
	
public void addNewLayer(int y, boolean maskBool,int maskOf, String label){
		
		layers.add(new TimelineLayer(parent,y,maskBool,maskOf, label));
	

		revalidate();
		repaint();

	}
	JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT,0,1));
	
	public void createTopBar(){
		TButton addNewLayerBut = new TButton("");
		addNewLayerBut.setPreferredSize(new Dimension(xoff,parent.timelineButtonHeight));
	
		topBar.add(addNewLayerBut);
		for(int x=0;x<parent.MAXFRAMES;x++){
			TButton  frameNumberLabel = new TButton(""+(x+1));
			frameNumberLabel.setPreferredSize(new Dimension(parent.timelineButtonWidth,parent.timelineButtonHeight));
			frameNumberLabel.setFont(f2);
			topBar.add(frameNumberLabel);
			
		}
		this.add(topBar,0);
	}

	JPanel mainLine = new JPanel();
	public void showMe(){
	mainLine.setLayout(new BoxLayout(mainLine, BoxLayout.Y_AXIS));
	
		this.setBackground(bgCol);
		
		
		createTopBar();
		
	
		this.add(mainLine);
		revalidate();
		repaint();
	}
	
	
	public void loadEmptyLayers(){
	//	this.removeAll();
		for(int y=0;y<lc;y++){
			
			addNewLayer(y,false);
		
		
		}
		revalidate();
		repaint();
	}
	


	public void toggleKeyFrame(int y, int x){
		
		parent.canvas.currentFrameGraphic.beginDraw();
		parent.canvas.currentFrameGraphic.clear();
		parent.canvas.currentFrameGraphic.endDraw();
		
		parent.canvas.tempInkGraphic.beginDraw();
		parent.canvas.tempInkGraphic.clear();
		parent.canvas.tempInkGraphic.endDraw();
		
		int index =parent.canvas.getLayerIndex(y);
		
		if(layers.get(index).jbs.get(x).isKey){
			layers.get(index).jbs.get(x).isKey=false;
			parent.canvas.layDownFrames(-1);
			parent.canvas.saveAction(y,x,"Keyframe Removed");
			layers.get(index).jbs.get(x).setIcon(new ImageIcon(emptyIcon));
			if(x>=parent.lastFrame)
				parent.getLastFrame();
			}else{
				layers.get(index).jbs.get(x).isKey=true;
			
			parent.canvas.saveAction(y,x,"Keyframe Added");
			if(x>parent.lastFrame)
				parent.lastFrame=x;
			parent.canvas.layDownFrames(-1);
			}
		
	}

	public void shiffleTable(int x, int y,int clickBut,boolean newFile){
		
		
		int lastFrame =parent.CURRENTFRAME;
		int lastLayer =parent.CURRENTLAYER;
		

    	if(y==-1)
    		y=parent.CURRENTLAYER;
    	if(x==-1)
    		x=parent.CURRENTFRAME;
    	
		if(x<parent.MAXFRAMES&&x>=0&&y>=0){

if(!newFile)
			 parent.canvas.finaliseFrame(lastLayer,lastFrame);
			 
			if(clickBut==2){
				toggleKeyFrame(y,x);
			}
			
			
			
			
			for(int p = 0;p<parent.MAXLAYERS;p++){
				
				if(!layers.get(p).jbs.get(lastFrame).isKey){
					layers.get(p).jbs.get(lastFrame).setBackground(inactive);
				}else{	
					layers.get(p).jbs.get(lastFrame).setBackground(active);
					
				}

				if(!layers.get(p).jbs.get(x).isKey){
					layers.get(p).jbs.get(x).setBackground(highlightInactive);
					
				}else{
					layers.get(p).jbs.get(x).setBackground(highlightActive);
					
				}
				
				
				}
			
			int index=parent.canvas.getLayerIndex(y);
			if(!layers.get(index).jbs.get(x).isKey)
			{
				layers.get(index).jbs.get(x).setBackground(selectedFrameCol);
			
			}else{
				layers.get(index).jbs.get(x).setBackground(selectedKeyCol);
				
				
			}
		
			
				
					parent.CURRENTFRAME = x;
					parent.CURRENTLAYER = y;
					
			
				repaint();
		parent.canvas.showNewFrame(y,x,-1);
	}
		
	}
	
}