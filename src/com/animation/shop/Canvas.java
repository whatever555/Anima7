package com.animation.shop;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;

import jpen.PButtonEvent;
import jpen.PKind;
import jpen.PLevel;
import jpen.PLevelEvent;
import jpen.PScroll;
import jpen.PScrollEvent;
import jpen.PenManager;
import jpen.event.PenAdapter;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import aniExports.AnimatedGifEncoder;
import aniExports.SWFExport;
import aniFilters.GlowFilter;
import aniFilters.LensBlurFilter;
import aniFilters.MotionBlur;
import aniFilters.RaysFilter;
import aniFilters.ShadowFilter;
import aniFilters.UnsharpFilter;

public class Canvas extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	CopyOnWriteArrayList<Ink> inks;
	public PGraphics currentFrameGraphic;
public PGraphics eraserMask;
	PGraphics tempInkGraphic;

	boolean keyEdited = false;
	boolean resizing = false;
	boolean rotating = false;
	boolean moving = false;

	int clipBoardWidth = 0, clipBoardHeight = 0;

	int clipBoardX = 0;
	int clipBoardY = 0;

	int transValue = 125;
	int onionTransValue = 125;

	int selectBeginX = 0;
	int selectBeginY = 0;
	int selectEndX = 0;
	int selectEndY = 0;

	boolean unsaved = true;
	boolean actioning = false;
	boolean SAVING = false;

	int currentlySaving = 0; // currenT Action that is being saved

	List<Integer> inkCuts;
	int cw; // canvas width
	int ch; // canvas height
	Main parent;
	public int bgColor = color(255,255,255);

	int localPenColor;

	CopyOnWriteArrayList<PImage> brush;
	int brushIndex = 0;

	PImage trans;

	int selectFeather = 0;
	int rectFeather = 0;

	float prevXPos = -1, prevYPos = -1;

	PImage clipBoard;

	PImage tempDispImage;
	boolean drawBool = false;

	boolean allowActions = true;

	PImage emptyImage;

	public Canvas(int cw, int ch, Main parent) {
		emptyImage = createImage(cw, ch, ARGB);
		this.ch = ch;
		this.cw = cw;
		this.parent = parent;
	}

	public void setup() {
		brush = new CopyOnWriteArrayList<PImage>();
		brush.add(loadImage("brushes/1/1.png"));

	
		
		size(cw, ch);

		initInk();

		
		ellipseMode(CORNER);
		background(bgColor);
		currentFrameGraphic = new PGraphics();
		currentFrameGraphic = createGraphics(cw, ch);
		eraserMask=new PGraphics();
		eraserMask = createGraphics(cw,ch);
		tempInkGraphic = new PGraphics();
		tempInkGraphic = createGraphics(cw, ch);

		currentFrameGraphic.beginDraw();
		currentFrameGraphic.background(0, 0);
		currentFrameGraphic.endDraw();
		
		eraserMask.beginDraw();
		eraserMask.background(0, 0);
		eraserMask.endDraw();
		
		
		tempInkGraphic.beginDraw();
		tempInkGraphic.background(0, 0);
		tempInkGraphic.endDraw();

		trans = loadImage("graphics/trans.png");
		PenManager pm = new PenManager(this);
		pm.pen.addListener(new ProcessingPen());
		smooth();

		saveAction(0, 0, "Create");
		layDownFrames(-1);

	}

	public void draw() {
		
		if (parent.currentTool.equals("move")) {

			parent.pasting = true;
			image(tempDispImage, 0, 0, width, height);
			image(currentFrameGraphic, 0, 0, width, height);

			if (drawBool) {
				if (moving) {
					clipBoardX += (mouseX - pmouseX);
					clipBoardY += (mouseY - pmouseY);
				}
				if (resizing) {
					if (resizeDirection.indexOf("E") > -1)
						clipBoardWidth += mouseX
								- (clipBoardX + clipBoardWidth);
					if (resizeDirection.indexOf("W") > -1) {
						clipBoardWidth -= (mouseX - clipBoardX);
						clipBoardX = mouseX;
					}
					if (resizeDirection.indexOf("S") > -1)
						clipBoardHeight += mouseY
								- (clipBoardY + clipBoardHeight);
					if (resizeDirection.indexOf("N") > -1) {

						clipBoardHeight -= (mouseY - clipBoardY);

						clipBoardY = mouseY;
					}

				}

			} else {

				if (overImage(mouseX, mouseY)) {
					checkResize(mouseX, mouseY);

				} else {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}

			fill(0, 0);
			stroke(127, 127);
			strokeWeight(1);
			if (clipBoardWidth < 0 && clipBoardHeight < 0) {
				pushMatrix();
				scale(-1, -1);
				rect(-clipBoardX, -clipBoardY, -clipBoardWidth,
						-clipBoardHeight);
				image(clipBoard, -clipBoardX, -clipBoardY, -clipBoardWidth,
						-clipBoardHeight);
				popMatrix();
			} else if (clipBoardWidth < 0) {
				pushMatrix();
				scale(-1, 1);
				rect(-clipBoardX, clipBoardY, -clipBoardWidth, clipBoardHeight);

				image(clipBoard, -clipBoardX, clipBoardY, -clipBoardWidth,
						clipBoardHeight);
				popMatrix();

			} else if (clipBoardHeight < 0) {
				pushMatrix();
				scale(1, -1);
				rect(clipBoardX, -clipBoardY, clipBoardWidth, -clipBoardHeight);
				image(clipBoard, clipBoardX, -clipBoardY, clipBoardWidth,
						-clipBoardHeight);
				popMatrix();

			} else {
				rect(clipBoardX, clipBoardY, clipBoardWidth, clipBoardHeight);
				image(clipBoard, clipBoardX, clipBoardY, clipBoardWidth,
						clipBoardHeight);

			}

		}else if(parent.currentTool.equals("Eraser")){
			background(bgColor);
			image(tempDispImage,0,0);
		if(tempDispImage2!=null){
			//image(tempDispImage2,0,0);
			//TODO FIX THIS
			PImage tmp=eraserMask.get();
			tempDispImage2.mask(tmp);
		 	image(tempDispImage2,0,0);
		}
		}
	}
	
	
	public void loadNewFile(String folder,int maxlayers,int maxframes){
		

		parent.currentActionIndex=0;
		
		parent.historicChanges= new ArrayList<SimpleRow>();
		parent.ACTIONTYPE=new ArrayList<String>();
		parent.historicImages=new ArrayList<PImage>();
			for(int l=0;l<maxlayers;l++)
				for(int f=0;f<=parent.lastFrame;f++){
				URL uri =getClass().getClassLoader().getResource(folder+"/"+parent.timeline.layers.get(l).layerID+"_"+f+".png");
				PImage tmp =loadImage(folder+"/"+parent.timeline.layers.get(l).layerID+"_"+f+".png");
				if(tmp!=null){
					
					parent.timeline.layers.get(l).jbs.get(f).isKey=true;
					String sp = savePath("src/data/images/" + parent.tmpName + "/"
							+ parent.timeline.layers.get(l).layerID + "_" + f + ".png");
					if(l==0){
						saveImageToDisk(tmp,parent.timeline.layers.get(l).layerID,f);
					}
					tmp.save(sp);
				}
				else{
					parent.timeline.layers.get(l).jbs.get(f).isKey=false;
				}
				}
			
			initImages(true);

			showNewFrame(parent.CURRENTLAYER,parent.CURRENTFRAME,-1);
			parent.timeline.shiffleTable(parent.CURRENTLAYER,parent.CURRENTFRAME,1,true);
	}

	public void setBrush(String[] loc) {
		if (unsaved)
			saveAction(parent.CURRENTLAYER, parent.CURRENTFRAME, "Paint");

		brushIndex = 0;
		brush = new CopyOnWriteArrayList<PImage>();
		for (int i = 0; i < loc.length; i++) {
			brush.add(loadImage(loc[i]));
		}
	}

	// Most of the code here is taken from the DrawingSurface demo code.
	public class ProcessingPen extends PenAdapter {
		boolean bIsDown;

		public void penButtonEvent(PButtonEvent evt) {

		}

		public void penLevelEvent(PLevelEvent evt) {

			if (drawBool && (parent.currentTool.equals("brush") || parent.currentTool.equals("Eraser")) ) {
				// Get kind of event: does it come from mouse (CURSOR), STYLUS
				// or ERASER?
				PKind type = evt.pen.getKind();
				// Discard events from mouse
				// if (type == PKind.valueOf(PKind.Type.CURSOR))
				// return;

				// Get the current cursor location
				float xPos = evt.pen.getLevelValue(PLevel.Type.X);
				float yPos = evt.pen.getLevelValue(PLevel.Type.Y);

				// Set the brush's size, and darkness relative to the pressure
				float pressure = evt.pen.getLevelValue(PLevel.Type.PRESSURE);

				// Get the tilt values (not with a Bamboo... so untested!)
				float xTilt = evt.pen.getLevelValue(PLevel.Type.TILT_X);
				float yTilt = evt.pen.getLevelValue(PLevel.Type.TILT_Y);
				// Transform them to azimuthX and altitude, two angles with the
				// projection of the pen against the X-Y plane
				// azimuthX is the angle (clockwise direction) between this
				// projection and the X axis. Range: -pi/2 to 3*pi/2.
				// altitude is the angle between this projection and the pen
				// itself. Range: 0 to pi/2.
				// Might be more pratical to use than raw x/y tilt values.
				double[] aa = { 0.0, 0.0 };
				PLevel.Type.evalAzimuthXAndAltitude(aa, xTilt, yTilt);
				// or just PLevel.Type.evalAzimuthXAndAltitude(aa, evt.pen);
				// double azimuthX = aa[0];
				// double altitude = aa[1];

				/*
				 * If the stylus is being pressed down, we want to draw a black
				 * line onto the screen. If it's the eraser, we want to create a
				 * white line, effectively "erasing" the black line
				 */

				// pg[currentLayer].beginDraw();

				if (type == PKind.valueOf(PKind.Type.STYLUS)) {
					println("STYLUS");
					if (pressure == 0) {
						return;
					}
				} else if (type == PKind.valueOf(PKind.Type.ERASER)) {
					// pg[currentLayer].stroke(255, darkness);
				} else {
					pressure = 1;

					// return; // IGNORE or CUSTOM...
				}

				if (!evt.isMovement()) {

					return;
				}

				if (!bIsDown && type != PKind.valueOf(PKind.Type.CURSOR)) {
					// Pen up, stop current line and down't draw anywthing
					prevXPos = -1;
					return;
				}

				if (prevXPos == -1) {
					prevXPos = xPos;
					prevYPos = yPos;
				}

				// Draw a line between the current and previous locations
				float brushSize = parent.PENSIZE * pressure;

				if(parent.currentTool.equals("brush"))
				inks.add(new Ink(prevXPos, prevYPos, xPos, yPos, brushSize,
						localPenColor, parent.PENALPHA));
				
				mylineBasic((int) prevXPos, (int) prevYPos, (int) xPos,
						(int) yPos, new Color(localPenColor), parent.PENALPHA,
						parent.PENSIZE, false);

				
				prevXPos = xPos;
				prevYPos = yPos;
				// pg[currentLayer].endDraw();

				// image(pg[currentLayer],0,0);
			}
		}

		// When user moves on the big round scroll button
		public void penScrollEvent(PScrollEvent evt) {
			PScroll.Type type = evt.scroll.getType();
			int value = evt.scroll.value;
			if (type == PScroll.Type.DOWN) {
				println("Scrolling down " + value);
			} else if (type == PScroll.Type.UP) {
				println("Scrolling up " + value);
			} else if (type == PScroll.Type.CUSTOM) {
				println("Scrolling custom (?) " + value);
			}
		}

		// What is it?
		void penKindEvent(PScrollEvent evt) {
			println("Kind Event: " + evt);
		}
	}

	public void getTempDispImage() {
		tempDispImage = get();
	}

	public void mouseDragged() {
		if (parent.currentTool.equals("selectRect")
				|| parent.currentTool.equals("selectCirc")) {

			image(tempDispImage, 0, 0);
			fill(0, 0);
			stroke(100);
			strokeWeight(2);
			if (parent.currentTool.equals("selectRect")) {
				rect(min(selectBeginX, mouseX), min(selectBeginY, mouseY),
						max(selectBeginX, mouseX) - min(selectBeginX, mouseX),
						max(selectBeginY, mouseY) - min(selectBeginY, mouseY),
						parent.ROUNDCORNERSIZE);
			}
			if (parent.currentTool.equals("selectCirc")) {
				ellipse(min(selectBeginX, mouseX), min(selectBeginY, mouseY),
						max(selectBeginX, mouseX) - min(selectBeginX, mouseX),
						max(selectBeginY, mouseY) - min(selectBeginY, mouseY));
			}

			stroke(200);
			strokeWeight(1);
			if (parent.currentTool.equals("selectRect")) {
				rect(min(selectBeginX, mouseX), min(selectBeginY, mouseY),
						max(selectBeginX, mouseX) - min(selectBeginX, mouseX),
						max(selectBeginY, mouseY) - min(selectBeginY, mouseY),
						parent.ROUNDCORNERSIZE);
			}
			if (parent.currentTool.equals("selectCirc")) {
				ellipse(min(selectBeginX, mouseX), min(selectBeginY, mouseY),
						max(selectBeginX, mouseX) - min(selectBeginX, mouseX),
						max(selectBeginY, mouseY) - min(selectBeginY, mouseY));
			}
		}

	}

	public boolean overImage(int x, int y) {
		int imgx = min(clipBoardX, clipBoardX + clipBoardWidth);
		int imgxw = max(clipBoardX, clipBoardX + clipBoardWidth);
		int imgy = min(clipBoardY, clipBoardY + clipBoardHeight);
		int imgyh = max(clipBoardY, clipBoardY + clipBoardHeight);
		
		return (x < imgxw && x > imgx && y < imgyh && y > imgy);
	}

	String resizeDirection = "";
	AnimatedGifEncoder aniGIF;
	SWFExport outSWF;
	public void exportImages(boolean createAnimatedGIF,boolean createSWF,String STRextension,String location){
		if(createSWF)
		outSWF = new SWFExport(cw,ch,parent.FPS,null,
				false,location+"/swf.swf");
		
		if(createAnimatedGIF){
			aniGIF = new AnimatedGifEncoder();
		aniGIF.start(location+".gif");
		System.out.println(location+".gif");
		aniGIF.setDelay(1000/parent.FPS); 
		}
		
		parent.frame.add(parent.messagePanel);
		parent.messagePanel.setVisible(true);
		parent.LOADED=false;
		
		PGraphics tmp = createGraphics(width,height);
		
		for(int x=0;x<=parent.lastFrame;x++){
			tmp.beginDraw();
			tmp.clear();
			//tmp.background(0,0);
			tmp.background(bgColor);
			for(int y=parent.MAXLAYERS-1;y>=0;y--){
				setBlending(y);
				if(parent.timeline.layers.get(y).visible )
				if(!parent.timeline.layers.get(y).isMask ){
				PImage img = loadImageFromDisk(parent.timeline.layers.get(y).layerID,x);
			
				if(parent.timeline.layers.get(y).hasMask ){
int id = parent.timeline.layers.get(getLayerIndex(parent.timeline.layers.get(y).myMask )).layerID;
					PImage maskImage = loadImageFromDisk(id,x);
					
					img = drawMaskedAdvanced(img,maskImage) ;
					
				}
				tmp.image(img,0,0);
				
				
}
			}
		
		tmp.endDraw();
		
		if(!createAnimatedGIF){
		String sp = savePath(location+"/"
				+ x +"."+STRextension);
		tmp.save(sp);
		}
		if(createAnimatedGIF)
			aniGIF.addFrame((BufferedImage) (tmp.getNative()));
		
		if(createSWF)
			outSWF.addImage(location+"/"
					+ x +"."+STRextension);
	
		
		
		}
		if(createSWF)
			outSWF.exportThis();
		if(createAnimatedGIF)
			aniGIF.finish();
		parent.LOADED=true;
		
		blendMode(NORMAL);
		currentFrameGraphic.blendMode(NORMAL);
	}
	
	public void checkResize(int x, int y) {

		int imgx = min(clipBoardX, clipBoardX + clipBoardWidth);
		int imgxw = max(clipBoardX, clipBoardX + clipBoardWidth);
		int imgy = min(clipBoardY, clipBoardY + clipBoardHeight);
		int imgyh = max(clipBoardY, clipBoardY + clipBoardHeight);

		boolean north = false, west = false, south = false, east = false;

		if (clipBoardHeight > 0) {
			north = (y < imgy + 5 && y > imgy);
			south = (y < imgyh && y > imgyh - 5);
		} else {
			south = (y < imgy + 5 && y > imgy);
			north = (y < imgyh && y > imgyh - 5);
		}

		if (clipBoardWidth > 0) {

			west = (x > imgx && x < imgx + 5);
			east = (x < imgxw && x > imgxw - 5);
		} else {
			east = (x > imgx && x < imgx + 5);
			west = (x < imgxw && x > imgxw - 5);

		}
		resizing = true;
		moving = false;
		if (north && east) {
			resizeDirection = "NE";
			parent.canvas.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
		} else if (north && west) {
			resizeDirection = "NW";
			parent.canvas.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
		} else if (south && east) {
			resizeDirection = "SE";
			parent.canvas.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
		} else if (south && west) {
			resizeDirection = "SW";
			parent.canvas.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
		} else if (east) {
			resizeDirection = "E";
			parent.canvas.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		} else if (west) {
			resizeDirection = "W";
			parent.canvas.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
		} else if (north) {
			resizeDirection = "N";
			parent.canvas.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
		} else if (south) {
			resizeDirection = "S";
			parent.canvas.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
		} else if (resizing) {
			resizing = false;
			setCursor(new Cursor(Cursor.MOVE_CURSOR));
			moving = true;
		}

	}

	public void mousePressed() {
		prevXPos = mouseX;
		prevYPos = mouseY;
		if (allowActions) {
			drawBool = true;
		}

		if (parent.currentTool.equals("selectRect")
				|| parent.currentTool.equals("selectCirc")) {

			selectBeginX = mouseX;
			selectBeginY = mouseY;
		}

		else if (parent.currentTool.equals("brush")) {
			tempInkGraphic.beginDraw();

			strokeWeight(parent.PENSIZE);
			localPenColor = parent.PENCOLOR.getRGB();
			stroke(localPenColor);

			if (!isImage(parent.CURRENTLAYER, parent.CURRENTFRAME))
				saveAction(parent.CURRENTLAYER, parent.CURRENTFRAME,
						"Empty Canvas");
		}
		

		else if (parent.currentTool.equals("Eraser")) {
			if(!eraseInitialized){
				prepareForEraser();
			}

			eraserMask.beginDraw();
			eraserMask.strokeWeight(parent.PENSIZE);
			localPenColor = parent.PENCOLOR.getRGB();
			eraserMask.stroke(localPenColor);

			if (!isImage(parent.CURRENTLAYER, parent.CURRENTFRAME))
				saveAction(parent.CURRENTLAYER, parent.CURRENTFRAME,
						"Empty Canvas");
		}
		
		

		if (parent.currentTool.equals("move"))
			if (!overImage(mouseX, mouseY)) {
				drawBool = false;
			}
	}

	public int getLayerIndex(int id){
		for(int i=0;i<parent.timeline.layers.size();i++)
			if(parent.timeline.layers.get(i).layerID == id)
				return i;
		
		println("FUCK UP FOR LAYER ID: "+id);
		return -1;
	}
	public int getKeyFrame(int x, int y) {
		
		
		while (x >= 0) {
			if (parent.timeline.layers.get(y).jbs.get(x).isKey) {
				return x;
			}
			x--;
		}

		return 0;
	}

	// OK
	public void mouseReleased() {

		drawBool = false;
		if (parent.currentTool.equals("dropper")) {
			int c = get(mouseX, mouseY);
			Color c2 = new Color((int) red(c), (int) green(c), (int) blue(c));
			parent.updateColorBoxes(c2);
			parent.PENCOLOR = c2;
		} else if (parent.currentTool.equals("selectRect")
				|| parent.currentTool.equals("selectCirc")) {
			selectEndX = mouseX;
			selectEndY = mouseY;
		} else

			if (parent.currentTool.equals("brush")) {
				tempInkGraphic.endDraw();
				printInkToGraphic();
				unsaved = true;

				new Thread() {
					public void run() {
						setSave(currentlySaving, false, parent.CURRENTLAYER,
								parent.CURRENTFRAME, "Paint");

					}
				}.start();

				currentlySaving++;
				if (currentlySaving > 999)
					currentlySaving = 0;

			} 
			if (parent.currentTool.equals("Eraser")) {
				eraseInitialized=false;
				eraserMask.endDraw();
				unsaved = true;

				new Thread() {
					public void run() {
						setSave(currentlySaving, false, parent.CURRENTLAYER,
								parent.CURRENTFRAME, "Erase");

					}
				}.start();

				currentlySaving++;
				if (currentlySaving > 999)
					currentlySaving = 0;

			} 
			else if (parent.currentTool.equals("bucket")) {
			fillGraphic(mouseX, mouseY);
		}

	}

	// OK
	public void cleanActions() {
		while (parent.historicChanges.size() > parent.currentActionIndex + 1) {
			parent.historicChanges.remove(parent.currentActionIndex + 1);
			parent.ACTIONTYPE.remove(parent.currentActionIndex + 1);
			parent.historicImages.remove(parent.currentActionIndex + 1);

		}
	}

	public void printInkToGraphic() {

		currentFrameGraphic.beginDraw();

		// todo might need a .get()
		currentFrameGraphic.image(tempInkGraphic, 0, 0);

		currentFrameGraphic.endDraw();

		tempInkGraphic.beginDraw();
		tempInkGraphic.clear();
		tempInkGraphic.endDraw();
		initInk();

	}

	public void initInk() {
		inks = new CopyOnWriteArrayList<Ink>();

	}

	public void setSave(int ss, boolean force, int cl, int cf, String message) {
		try {
			if (!force)
				Thread.sleep(1000);

			if (ss == currentlySaving && !drawBool) {

				actioning = true;
				saveAction(cl, cf, message);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	boolean saving = false;

	// OK
	public void saveAction(int cl, int cf, String label) {
		int ck = getKeyFrame(cf, getLayerIndex(cl));
		if (!saving) {
			keyEdited = true;
			saving = true;
			unsaved = false;
			if (!label.equals("Keyframe Removed")
					&& !label.equals("Keyframe Added")) {
				if (!label.equals("Empty Canvas")) {

					allowActions = false;
				} else {

				}

			} else {
				if (label.equals("Keyframe Added")) {
					saveImageToDisk(emptyImage, cl, cf);
				}
				if (label.equals("Keyframe Removed")) {
					deleteImageFromDisk(cl, cf);
				}

			}

			cleanActions();
			parent.currentActionIndex = parent.ACTIONTYPE.size();

			parent.historicImages.add(loadImageFromDisk(cl, ck));

			parent.historicChanges.add(new SimpleRow(cl, getKeyFrame(cf, getLayerIndex(cl))));
			parent.ACTIONTYPE.add(label);
			parent.historyPanel.update();

			parent.currentActionIndex = parent.ACTIONTYPE.size();

			allowActions = true;
			saving = false;
		}
	}

	// one check
	public void undo() {

		if (actioning) {
			parent.currentActionIndex--;
			actioning = false;
		}
		if (parent.currentActionIndex > 0) {
			parent.currentActionIndex--;
			int ca = parent.currentActionIndex;
			int cl = parent.historicChanges.get(ca).y;
			int cl_index = getLayerIndex(cl);
			int cf = parent.historicChanges.get(ca).x;
			int ck = getKeyFrame(cf, cl_index);

			if (parent.ACTIONTYPE.size() > ca + 1)
				if (parent.ACTIONTYPE.get(ca + 1) != null) {
					if (parent.ACTIONTYPE.get(ca + 1).equals("Keyframe Added")) {
						parent.timeline.layers.get(getLayerIndex(parent.historicChanges
								.get(ca + 1).y)).jbs.get(parent.historicChanges
								.get(ca + 1).x).isKey=false;
					}
					if (parent.ACTIONTYPE.get(ca + 1)
							.equals("Keyframe Removed")) {
						parent.timeline.layers.get(getLayerIndex(parent.historicChanges
								.get(ca + 1).y)).jbs.get(parent.historicChanges
								.get(ca + 1).x).isKey=true;

						parent.timeline.layers.get(cl_index).jbs.get(cf).setIcon(new ImageIcon(
								parent.timeline.emptyIcon));

					}

				}

			saveImageToDisk((parent.historicImages.get(ca).get()), cl, ck);
			// repaint();

			if (parent.ACTIONTYPE.get(ca).equals("Keyframe Added")) {
				parent.timeline.layers.get(cl_index).jbs.get(cf).isKey=true;
			}
			if (parent.ACTIONTYPE.get(ca).equals("Keyframe Removed")) {
				parent.timeline.layers.get(cl_index).jbs.get(cf).isKey=false;
				
				parent.timeline.layers.get(cl_index).jbs.get(cf).setIcon(new ImageIcon(
						parent.timeline.emptyIcon));

			}

			parent.timeline.shiffleTable(cf, cl, 0,false);
			parent.CURRENTLAYER = cl;
			parent.CURRENTFRAME = cf;
		} else {
			parent.currentActionIndex = 0;
		}

	}

	public void redo() {

		parent.currentActionIndex++;

		if (parent.currentActionIndex < parent.historicChanges.size()) {
			int ca = parent.currentActionIndex;
			int cl = parent.historicChanges.get(ca).y;
			int cl_index = getLayerIndex(cl);
			int cf = parent.historicChanges.get(ca).x;
			int ck = getKeyFrame(cf,cl_index);

			// TODO: Is this needed HERE
			currentFrameGraphic.beginDraw();
			currentFrameGraphic.clear();
			currentFrameGraphic.endDraw();

			saveImageToDisk((parent.historicImages.get(ca).get()), cl, ck);

			if (parent.ACTIONTYPE.get(ca).equals("Keyframe Added")) {
				parent.timeline.layers.get(cl_index).jbs.get(cf).isKey=true;
			}
			if (parent.ACTIONTYPE.get(ca).equals("Keyframe Removed")) {
				parent.timeline.layers.get(cl_index).jbs.get(cf).isKey=false;
			}

			parent.timeline.shiffleTable(cf, cl, 0,false);
			parent.CURRENTLAYER = cl;
			parent.CURRENTFRAME = cf;
		} else {
			parent.currentActionIndex = parent.historicChanges.size();
		}

	}

	public void mylineBasic(int x, int y, int x2, int y2, Color c, int alpha,
			int siz, boolean g) {


		if(parent.currentTool.equals("brush")){
		tempInkGraphic.tint(c.getRGB(), alpha);
		tint(c.getRGB(), alpha);
		}
		
		

		int w = x2 - x;
		int h = y2 - y;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if (w < 0)
			dx1 = -1;
		else if (w > 0)
			dx1 = 1;
		if (h < 0)
			dy1 = -1;
		else if (h > 0)
			dy1 = 1;
		if (w < 0)
			dx2 = -1;
		else if (w > 0)
			dx2 = 1;
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if (!(longest > shortest)) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if (h < 0)
				dy2 = -1;
			else if (h > 0)
				dy2 = 1;
			dx2 = 0;
		}

		int pp = -abs(siz / 2);
		int numerator = longest >> 1;
		
		for (int i = 0; i <= longest && brush.size() > 0; i += max(1,
				abs(siz / 14))) {


			
			if(parent.currentTool.equals("brush")){
				tempInkGraphic.image(brush.get(brushIndex), x + pp, y + pp, siz,
						siz);

				image(brush.get(brushIndex), x + pp, y + pp, siz, siz);
			
				}
			else if(parent.currentTool.equals("Eraser")){
				eraserMask.image(brush.get(brushIndex), x + pp, y + pp, siz, siz);
				
			
				}
			
			
			brushIndex++;
			if (brushIndex >= brush.size())
				brushIndex = 0;

			numerator += shortest;
			if (!(numerator < longest)) {
				numerator -= longest;
				x += dx1;
				y += dy1;
			} else {
				x += dx2;
				y += dy2;
			}
		}

	
		
		
		if(parent.currentTool.equals("brush")){
			tempInkGraphic.noTint();

			noTint();
		
			}

	}

	public void tidyClipBoard() {
		if (clipBoardWidth < 0) {
			clipBoardX = clipBoardX + clipBoardWidth;
			clipBoardWidth = -clipBoardWidth;
		}
		if (clipBoardHeight < 0) {
			clipBoardY = clipBoardY + clipBoardHeight;
			clipBoardHeight = -clipBoardHeight;
		}
	}

	public void layDownFrames(int hiddenLayer) {
		background(bgColor, 255);
		tint(255, transValue);

		// printFrameToImage(parent.CURRENTLAYER, parent.CURRENTFRAME);
		
		
		
		for (int i = parent.MAXLAYERS - 1; i > -1; i--) {
			setBlending(i);
			if (parent.timeline.layers.get(i).visible && !parent.timeline.layers.get(i).activeMask && parent.timeline.layers.get(i).layerID != parent.CURRENTLAYER && i!=hiddenLayer){
			
				PImage tmp =loadImageFromDisk(parent.timeline.layers.get(i).layerID, parent.CURRENTFRAME);
				if(tmp!=null){
					if(parent.timeline.layers.get(i).hasMask){
						if(parent.timeline.layers.get(parent.canvas.getLayerIndex(parent.timeline.layers.get(i).myMask)).activeMask){

							PImage maskImage =loadImageFromDisk(parent.timeline.layers.get(parent.canvas.getLayerIndex(parent.timeline.layers.get(i).myMask)).layerID, parent.CURRENTFRAME);
							//tmp.mask(maskImage);
							tmp=drawMaskedAdvanced(tmp,maskImage);
							
						}
					
					}
					
				image(tmp, 0, 0);
				}
			}
		}
		/*
		 * if(trans!=null){ tint(bgColor); image(trans,0,0,cw,ch); }
		 */

		tint(255, onionTransValue);
		ArrayList<Integer> shownKeys = new ArrayList<Integer>();
		for (int i = Math.max(0, parent.CURRENTFRAME - parent.onionLeft); i <= Math
				.min(parent.MAXFRAMES - 1, parent.CURRENTFRAME
						+ parent.onionRight); i++) {
			setBlending(parent.CURRENTLAYER);
			if(parent.timeline.layers.get(getLayerIndex(parent.CURRENTLAYER)).visible)
			if (getKeyFrame(i, getLayerIndex(parent.CURRENTLAYER)) != getKeyFrame(
					parent.CURRENTFRAME, getLayerIndex(parent.CURRENTLAYER)))
				if (!shownKeys.contains(getKeyFrame(i, getLayerIndex(parent.CURRENTLAYER)))  && parent.CURRENTLAYER!=hiddenLayer) {
					shownKeys.add((getKeyFrame(i, getLayerIndex(parent.CURRENTLAYER))));
					
					PImage tmp =loadImageFromDisk(parent.CURRENTLAYER, i);
					
					if(parent.timeline.layers.get(getLayerIndex(parent.CURRENTLAYER)).hasMask){
						PImage maskImage =loadImageFromDisk(parent.timeline.layers.get(getLayerIndex(parent.timeline.layers.get(getLayerIndex(parent.CURRENTLAYER)).myMask)).layerID, i);
						//tmp.mask(maskImage);
						tmp=drawMaskedAdvanced(tmp,maskImage);
						
					}
					image(tmp, 0, 0);
					
				}
		}
		noTint();
		PImage tmp=null;
		int maxTriesCount = 0;
		while(tmp==null && maxTriesCount <4){
		tmp =loadImageFromDisk(parent.CURRENTLAYER, parent.CURRENTFRAME);
		if(parent.timeline.layers.get(getLayerIndex(parent.CURRENTLAYER)).visible)
			setBlending(parent.CURRENTLAYER);
			if(tmp!=null){
		if( parent.CURRENTLAYER!=hiddenLayer && !parent.timeline.layers.get(getLayerIndex(parent.CURRENTLAYER)).activeMask)
			if(parent.timeline.layers.get(getLayerIndex(parent.CURRENTLAYER)).hasMask){
				PImage maskImage =loadImageFromDisk(parent.timeline.layers.get(parent.canvas.getLayerIndex(parent.timeline.layers.get(getLayerIndex(parent.CURRENTLAYER)).myMask)).layerID, parent.CURRENTFRAME);
				//tmp.mask(maskImage);
				tmp=drawMaskedAdvanced(tmp,maskImage);
				
			}
		if(tmp!=null){
		image(tmp, 0, 0);

		currentFrameGraphic.beginDraw();
		
		currentFrameGraphic.image(tmp, 0,0);
		currentFrameGraphic.endDraw();
		}
		}else{
		maxTriesCount++;
		System.out.println("NULL YA");
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		}
		getTempDispImage();
		blendMode(NORMAL);
		currentFrameGraphic.blendMode(NORMAL);
	}
	
	public void setBlending(int x){
		String b=parent.timeline.layers.get(x).BLENDING;
		if(b.equals("Normal")){
			blendMode(NORMAL);
			currentFrameGraphic.blendMode(NORMAL);
		}else
			if(b.equals("Normal")){
				blendMode(NORMAL);
				currentFrameGraphic.blendMode(NORMAL);
			}else
				if(b.equals("Add")){
					blendMode(ADD);
					currentFrameGraphic.blendMode(ADD);
				}else
					if(b.equals("Subtract")){
						blendMode(SUBTRACT);
						currentFrameGraphic.blendMode(SUBTRACT);
					}else
						if(b.equals("Darkest")){
							blendMode(DARKEST);
							currentFrameGraphic.blendMode(DARKEST);
						}else
							if(b.equals("Lightest")){
								blendMode(LIGHTEST);
								currentFrameGraphic.blendMode(LIGHTEST);
							}else
								if(b.equals("Difference")){
									blendMode(DIFFERENCE);
									currentFrameGraphic.blendMode(DIFFERENCE);
								}else
									if(b.equals("Exclusion")){
										blendMode(EXCLUSION);
										currentFrameGraphic.blendMode(EXCLUSION);
									}else
										if(b.equals("Multiply")){
											blendMode(MULTIPLY);
											currentFrameGraphic.blendMode(MULTIPLY);
										}else
											if(b.equals("Screen")){
												blendMode(SCREEN);
												currentFrameGraphic.blendMode(SCREEN);
											}else
												if(b.equals("Replace")){
													blendMode(REPLACE);
													currentFrameGraphic.blendMode(REPLACE);
												}
		
			
	}

PImage drawMaskedAdvanced(PImage img,PImage mask) {
	PImage tmp=new PImage();
	
   tmp = createImage(img.width,img.height,ARGB);
 // img.mask(mask);
  int t = color(0, 0);
  for (int i=0; i<img.pixels.length; i++) {
    if ((alpha(img.pixels[i])>0) && (alpha(mask.pixels[i])>0)) {
      tmp.pixels[i] = color(red(img.pixels[i]),green(img.pixels[i]),
    		  blue(img.pixels[i]),(alpha(mask.pixels[i])/255)*alpha(img.pixels[i]));
    } 
    else {
      tmp.pixels[i] = color(0,0);
    }
  }
  return tmp;
}

	public boolean isImage(int cl, int cf) {
cl = getLayerIndex(cl);
		int ck = getKeyFrame(cf, cl);
if(parent.timeline.layers.get(cl).jbs.get(ck).hasImage)
return true;
		
		return false;

		
	}

	public void copyImage(String location, int cl, int cf) {
		loadImageFromDisk(cl, cf).save(location);
	}

	public PImage loadImageFromDisk(int cl, int cf) {
	
		
		int ck = getKeyFrame(cf, getLayerIndex(cl));

		if (parent.cachedImagesNames.contains("image" + cl + "_" + ck)) {

			return loadImageFromCache(cl, ck);

		} else {
			return loadImage("src/data/images/" + parent.tmpName + "/" + cl
					+ "_" + ck + ".png");

		}

	}

	public PImage loadImageFromCache(int l, int k) {
		return parent.cachedImages.get(parent.cachedImagesNames.indexOf("image"
				+ l + "_" + k));
	}

	public void deleteImageFromDisk(int cl, int ck) {
		if (parent.cachedImagesNames.contains("" + cl + "_" + ck)) {
			parent.cachedImages.remove(parent.cachedImagesNames.indexOf("image"
					+ cl + "_" + ck));
		}
		saveImageToDisk(emptyImage, cl, ck);
	}

	public void addToCache(PImage img, final int cl, int ck) {

		if (!((parent.cachedImagesNames.contains("image" + cl + "_" + ck)))) {
			parent.cachedImagesNames.add("image" + cl + "_" + ck);
			parent.cachedImages.add(img);

			while (parent.cachedImagesNames.size() > parent.CACHEMAX) {
				parent.cachedImagesNames.remove(0);
				parent.cachedImages.remove(0);
			}
		} else {
			parent.cachedImages.set(
					parent.cachedImagesNames.indexOf("image" + cl + "_" + ck),
					img);

		}
	}

	public void saveImageToDisk(PImage img, final int cl, int cf) {
		final int clt=getLayerIndex(cl);
		final int ck = getKeyFrame(cf, clt);

		final PImage tmpImage = img.get();

		addToCache(img, cl, ck);

		parent.timeline.layers.get(clt).jbs.get(ck).hasImage= true;
		new Thread() {

			public void run() {

				// Thread.currentThread().setPriority(1);
				SAVING = true;

				String sp = savePath("src/data/images/" + parent.tmpName + "/"
						+ cl + "_" + ck + ".png");
				tmpImage.save(sp);

				SAVING = false;

			}
		}.start();

	}
	PImage tempImg;
	public void finaliseFrame(int layer, int frame) {

		if (parent.pasting) {
			parent.canvas.pasteClipBoardToTempGraphic(false, "Paste");
			keyEdited = true;
		}

		if (unsaved) {
			saveAction(layer, frame, "Paint");

		}

		int lk = getKeyFrame(frame, getLayerIndex(layer));

		if (keyEdited) {

			tempImg = currentFrameGraphic.get();
			saveImageToDisk(tempImg.get(), layer, lk);
			tempImg.resize(parent.timeline.boxWidth, parent.timeline.boxHeight);
			BufferedImage tmp = (BufferedImage) (tempImg.getNative());
			parent.timeline.layers.get(getLayerIndex(layer)).jbs.get(lk).setIcon(new ImageIcon(tmp));

		}

		/*
		 * if(parent.currentTool.equals("move")){
		 * 
		 * parent.currentTool="brush"; }
		 */

	}

	public void showNewFrame(int layer, int frame,int hideLayer) {
		
		parent.CURRENTLAYER = layer;
		parent.CURRENTFRAME = frame;

		currentFrameGraphic.beginDraw();
		currentFrameGraphic.clear();
		currentFrameGraphic.endDraw();
		unsaved = false;
		parent.pasting = false;

		keyEdited = false;
		layDownFrames(hideLayer);
	}

	public void initImages(boolean isLoading) {
		for (int i = 0; i < parent.MAXLAYERS; i++)
			for (int j = 0; j < parent.MAXFRAMES; j++) {
				if (j == 0 || parent.timeline.layers.get(i).jbs.get(j).isKey) {
					
					
					
					if(isLoading==false){
					saveImageToDisk(emptyImage, i, 0);
					parent.timeline.layers.get(i).jbs.get(j).hasImage=true;
					parent.timeline.layers.get(i).jbs.get(j).isKey=true;
					}
					else{
						if(parent.timeline.layers.get(i).jbs.get(j).isKey)
							parent.timeline.layers.get(i).jbs.get(j).hasImage=true;
					}

				} else {
					parent.timeline.layers.get(i).jbs.get(j).hasImage=false;
				}

			}

	}

	public void takeNap(int sl) {
		try {
			Thread.sleep(sl);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public PImage createPGImage(PGraphics pg) {

		PImage img = emptyImage;
		img.loadPixels();
		for (int i = 0; i < img.pixels.length; i++) {
			img.pixels[i] = pg.pixels[i];
		}
		img.updatePixels();
		return img;
	}

	public void pasteFromClipBoard(boolean clearClipBoard, String saveMessage) {

		clipBoardWidth = clipBoard.width;
		clipBoardHeight = clipBoard.height;
		if (parent.currentTool.equals("move"))
			pasteClipBoardToTempGraphic(clearClipBoard, saveMessage);

		parent.pasting = true;
		if (mouseX > 0 && mouseX < width - 1 && mouseY > 0
				&& mouseY < height - 1 && !clearClipBoard) {

			clipBoardX = mouseX - clipBoard.width / 2;
			clipBoardY = mouseY - clipBoard.height / 2;

		} else {
			clipBoardX = (width / 2 - clipBoard.width / 2);
			clipBoardY = (height / 2 - clipBoard.height / 2);
		}

		image(tempDispImage, 0, 0);
		image(clipBoard, clipBoardX, clipBoardY);
		parent.currentTool = "move";
		parent.setCursor("move");
		keyEdited = true;
		unsaved = true;
	}


boolean eraseInitialized=false;
PImage tempDispImage2;
public void prepareForEraser(){
tempDispImage2 = currentFrameGraphic.get();
	eraserMask.beginDraw();
	eraserMask.clear();

	eraserMask.endDraw();
	eraseInitialized=true;
	showNewFrame(parent.CURRENTLAYER,parent.CURRENTFRAME,parent.CURRENTLAYER);
}


		
		
	public void pasteClipBoardToTempGraphic(boolean clearClipBoard,
			String saveMessage) {

		parent.pasting = false;

		if (clipBoardWidth < 0 && clipBoardHeight < 0) {
			currentFrameGraphic.pushMatrix();
			currentFrameGraphic.scale(-1, -1);
			currentFrameGraphic.image(clipBoard, -clipBoardX, -clipBoardY,
					-clipBoardWidth, -clipBoardHeight);
			currentFrameGraphic.popMatrix();
		} else if (clipBoardWidth < 0) {
			currentFrameGraphic.pushMatrix();
			currentFrameGraphic.scale(-1, 1);
			currentFrameGraphic.image(clipBoard, -clipBoardX, clipBoardY,
					-clipBoardWidth, clipBoardHeight);
			currentFrameGraphic.popMatrix();

		} else if (clipBoardHeight < 0) {
			currentFrameGraphic.pushMatrix();
			currentFrameGraphic.scale(1, -1);
			currentFrameGraphic.image(clipBoard, clipBoardX, -clipBoardY,
					clipBoardWidth, -clipBoardHeight);
			currentFrameGraphic.popMatrix();

		} else {
			currentFrameGraphic.image(clipBoard, clipBoardX, clipBoardY,
					clipBoardWidth, clipBoardHeight);

		}

		saveAction(parent.CURRENTLAYER, parent.CURRENTFRAME, saveMessage);

		// parent.timeline.shiffleTable(parent.CURRENTFRAME,parent.CURRENTLAYER,0);

		if (clearClipBoard) {
			clipBoard = emptyImage;
		} else {
			clipBoardWidth = clipBoard.width;
			clipBoardHeight = clipBoard.height;
		}

		keyEdited = true;
		parent.topPanel.setBrushOptions();
		parent.currentTool = "brush";
		parent.canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

	public void copyToClipBoard() {

		if (parent.currentTool.equals("selectRect")) {
			if (selectBeginX != selectEndX) {

				int x = min(selectBeginX, selectEndX);
				int y = min(selectBeginY, selectEndY);
				int w = max(selectBeginX, selectEndX) - x;
				int h = max(selectBeginY, selectEndY) - y;

				clipBoard = currentFrameGraphic.get(x, y, w, h);
				clipBoardWidth = clipBoard.width;
				clipBoardHeight = clipBoard.height;
				addFeatherAndCorners(x, y, w, h);

			}
		}
	}

	public void addFeatherAndCorners(int x, int y, int w, int h) {

		if (parent.ROUNDCORNERSIZE > 0)
			clipBoard = roundSelectedCorners(clipBoard, parent.ROUNDCORNERSIZE);
		if (parent.FEATHERSIZE > 0)
			clipBoard = featherSelectedCorners(clipBoard, parent.FEATHERSIZE);

	}

	public PImage featherSelectedCorners(PImage img, int featherDepth) {
		int w = img.width;
		int h = img.height;

		if (featherDepth > w)
			featherDepth = w;
		if (featherDepth > h)
			featherDepth = h;

		img.loadPixels();

		for (int i = 0; i < featherDepth; i++)
			for (int j = 0; j < h; j++) {
				int loc = (j * w) + i;
				int c = img.pixels[loc];
				img.pixels[loc] = color(red(c), green(c), blue(c),
						(int) ((alpha(c) / featherDepth) * i));
			}
		for (int i = w - 1; i >= w - featherDepth; i--)
			for (int j = 0; j < h; j++) {
				int loc = (j * w) + i;
				int c = img.pixels[loc];
				img.pixels[loc] = color(red(c), green(c), blue(c),
						(int) ((alpha(c) / featherDepth) * (w - i)));
			}
		for (int i = 0; i < w; i++)
			for (int j = 0; j < featherDepth; j++) {
				int loc = (j * w) + i;
				int c = img.pixels[loc];
				img.pixels[loc] = color(red(c), green(c), blue(c),
						(int) ((alpha(c) / featherDepth) * j));
			}
		for (int i = 0; i < w; i++)
			for (int j = h - 1; j > h - featherDepth; j--) {
				int loc = (j * w) + i;
				int c = img.pixels[loc];
				img.pixels[loc] = color(red(c), green(c), blue(c),
						(int) ((alpha(c) / featherDepth) * (h - j)));
			}
		img.updatePixels();

		return img;
	}

	public PImage roundSelectedCorners(PImage img, int cornerSize) {
		int w = img.width;
		int h = img.height;
		PGraphics tempGraphic = new PGraphics();
		tempGraphic = createGraphics(w, h);
		tempGraphic.beginDraw();
		tempGraphic.background(0, 0);
		tempGraphic.fill(255);
		tempGraphic.rect(0, 0, w, h, cornerSize);
		tempGraphic.endDraw();
		
		img.loadPixels();
		for (int i = 0; i < cornerSize; i++) {
			for (int j = 0; j < cornerSize; j++) {
				int loc = (j * w) + i;
				if (alpha(tempGraphic.pixels[loc]) == 0) {
					img.pixels[loc] = color(0, 0);
				}
			}
		}

		for (int i = 0; i < cornerSize; i++) {
			for (int j = h - 1; j > h - cornerSize; j--) {
				int loc = (j * w) + i;
				if (alpha(tempGraphic.pixels[loc]) == 0) {
					img.pixels[loc] = color(0, 0);
				}
			}
		}

		for (int i = w - 1; i > w - cornerSize; i--) {
			for (int j = 0; j < cornerSize; j++) {
				int loc = (j * w) + i;
				if (alpha(tempGraphic.pixels[loc]) == 0) {
					img.pixels[loc] = color(0, 0);
				}
			}
		}

		for (int i = w - 1; i > w - cornerSize; i--) {
			for (int j = h - 1; j > h - cornerSize; j--) {
				int loc = (j * w) + i;
				if (alpha(tempGraphic.pixels[loc]) == 0) {
					img.pixels[loc] = color(0, 0);
				}
			}
		}
		img.updatePixels();

		tempGraphic = null;
		return img;

	}

	public void fillGraphic(int x, int y) {

		int c = currentFrameGraphic.pixels[x + (y * width)];
		int newCol = color(parent.PENCOLOR.getRed(),
				parent.PENCOLOR.getGreen(), parent.PENCOLOR.getBlue(),
				parent.PENCOLOR.getAlpha());
		if (c != newCol) {

			currentFrameGraphic.loadPixels();
			fillIterative(c, newCol, x, y);
			currentFrameGraphic.updatePixels();
		}
		saveAction(parent.CURRENTLAYER, parent.CURRENTFRAME, "Fill");
		parent.timeline.shiffleTable(parent.CURRENTFRAME, parent.CURRENTLAYER,
				0,false);

	}

	public void fillIterative(int oldCol, int newCol, int x, int y) {

		int[][] checked = new int[width][height];

		Queue<Point> queue = new LinkedList<Point>();
		queue.add(new Point(x, y));
		boolean ignoreAlpha = false;
		if (alpha(oldCol) == 0.0)
			ignoreAlpha = true;


		while (!queue.isEmpty()) {
			Point p = queue.remove();

			if ((p.x >= 0) && (p.x < width && (p.y >= 0) && (p.y < height))) {
				int tmp = (int) alpha(currentFrameGraphic.pixels[p.x
						+ (p.y * width)]);

				if ((tmp > 0 || ignoreAlpha)
						&& (checked[p.x][p.y] != 1)
						&& compareColors(currentFrameGraphic.pixels[p.x
								+ (p.y * width)], oldCol,
								parent.fillInaccuracy, ignoreAlpha)) {

					if (!ignoreAlpha) {

						currentFrameGraphic.pixels[p.x + (p.y * width)] = color(
								red(newCol), green(newCol), blue(newCol), tmp);

					} else
						currentFrameGraphic.pixels[p.x + (p.y * width)] = color(
								red(newCol), green(newCol), blue(newCol), 255);

					queue.add(new Point(p.x + 1, p.y));
					queue.add(new Point(p.x - 1, p.y));
					queue.add(new Point(p.x, p.y + 1));
					queue.add(new Point(p.x, p.y - 1));
				}
				checked[p.x][p.y] = 1;
			}

		}

	}

	public boolean compareColors(int c1, int c2, int inAccuracy,
			boolean dontIgnoreAlpha) {
		if (c2 == c1)
			return true;
		double dif;
		if (!dontIgnoreAlpha)
			dif = (abs(red(c1) - red(c2)) + abs(green(c1) - green(c2)) + abs(blue(c1)
					- blue(c2)));
		else
			dif = (abs(red(c1) - red(c2)) + abs(green(c1) - green(c2))
					+ abs(blue(c1) - blue(c2)) + abs(alpha(c1) - alpha(c2)));
		// System.out.println(dif +" = DIFFERENCE");
		return (dif <= inAccuracy);
	}

	public boolean isInBounds(int x, int y) {
		return (y < height && y >= 0 && x >= 0 && x < width);
	}

	int distance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	// ###############################################################//
	// ######## FILTERS #############################################//
	// #############################################################//
	public PImage previewThumb;
	public BufferedImage previewImageBuffered;

	public void refreshPreviewThumb() {
		if (currentFrameGraphic != null) {
			previewThumb = currentFrameGraphic.get();
			previewThumb.resize(190, 160);

			previewImageBuffered = (BufferedImage) (previewThumb.getNative());
		}
	}

	public void defaultFilters(float[] amounts, boolean apply,String filterName) {
		

		
		if (!apply) {
			//refreshPreviewThumb();
			previewThumb = currentFrameGraphic.get();
			
			previewThumb.resize(190, 160);
			if(filterName.equals("Blur"))
			previewThumb.filter(BLUR,(int)(amounts[0]/2));
			else if(filterName.equals("Motion Blur"))
			previewThumb = motionBlurFilter(previewThumb,(float)(amounts[0]/10),(float)(amounts[1]/10),(float)(amounts[2]/360));
			else if(filterName.equals("Lens Blur"))
			previewThumb = lensnBlurFilter(previewThumb,(float)(amounts[0]/10),(float)(amounts[1]/10),(float)(amounts[2]/360),(float)(amounts[3]));
			else if(filterName.equals("Sharpen"))
				previewThumb = UnsharpFilter(previewThumb,(amounts[0]));
			else if(filterName.equals("Glow"))
				previewThumb = GlowBlur(previewThumb,(int)(amounts[0]));
			else if(filterName.equals("Rays"))
				previewThumb = RaysFilter(previewThumb,amounts[0],amounts[1],amounts[2]);
				else if(filterName.equals("Shadow"))
			previewThumb = ShadowFilter(previewThumb,(int)(amounts[0]),(int)(amounts[1]),(int)(amounts[2]),(float)(amounts[3]));
			else if(filterName.equals("Threshold"))
			previewThumb.filter(THRESHOLD,(float)(amounts[0]/100));
			else if(filterName.equals("Posterize"))
			previewThumb.filter(POSTERIZE,Math.max(2,amounts[0]));
			else if(filterName.equals("Spherify"))
			previewThumb = spherifyFilter(previewThumb,(int)(amounts[0]/2),amounts[1]);
			else if(filterName.equals("Boxify"))
			previewThumb = boxifyFilter(previewThumb,(int)(amounts[0]/2),amounts[1],amounts[2]);
			
			previewImageBuffered = (BufferedImage) (previewThumb.getNative());
			
		}else
		if (apply) {
			keyEdited = true;
			unsaved = true;
			PImage tmp = currentFrameGraphic.get();
			currentFrameGraphic.beginDraw();
			currentFrameGraphic.clear();
			if(filterName.equals("Blur"))
			tmp.filter(BLUR, amounts[0]);
			else if(filterName.equals("Motion Blur"))
				tmp = motionBlurFilter(tmp,(float)(amounts[0]/10),(float)(amounts[1]/10),(float)(amounts[2]/360));
			else if(filterName.equals("Shadow"))
				tmp = ShadowFilter(tmp,(int)(amounts[0]),(int)(amounts[1]),(int)(amounts[2]),(float)(amounts[3]));
			else if(filterName.equals("Glow"))
				tmp = GlowBlur(tmp,(int)(amounts[0]));
			else if(filterName.equals("Sharpen"))
				tmp = UnsharpFilter(tmp,(amounts[0]));
			else if(filterName.equals("Rays"))
				tmp = RaysFilter(tmp,amounts[0],amounts[1],amounts[2]);
			else if(filterName.equals("Lens Blur"))
			tmp = lensnBlurFilter(tmp,(float)(amounts[0]/10),(float)(amounts[1]/10),(float)(amounts[2]/360),(float)(amounts[3]));
			else if(filterName.equals("Threshold"))
			tmp.filter(THRESHOLD, (float)(amounts[0]/100));
			else if(filterName.equals("Posterize"))
			tmp.filter(POSTERIZE,amounts[0]);
			else if(filterName.equals("Spherify"))
			tmp = spherifyFilter(tmp,(int)(amounts[0]),amounts[1]);
			else if(filterName.equals("Boxify"))
			tmp = boxifyFilter(tmp,(int)(amounts[0]),amounts[1],amounts[2]);
			currentFrameGraphic.image(tmp, 0, 0);
			currentFrameGraphic.endDraw();

			new Thread() {
				public void run() {
					setSave(currentlySaving, false, parent.CURRENTLAYER,
							parent.CURRENTFRAME, "Filter");

				}
			}.start();
		}

	}
	
	public PImage ShadowFilter(PImage img, int radius, int xOffset, int yOffset, float opacity){
		ShadowFilter sf = new ShadowFilter(radius,xOffset,yOffset,opacity/100);
		PImage tmp = new PImage(sf.filter((BufferedImage)img.getNative(),null));
	return tmp;
	}
	
	public PImage GlowBlur(PImage img,int amt){
		GlowFilter gf = new GlowFilter(amt);
		PImage tmp = new PImage(gf.filter((BufferedImage)img.getNative(),null));
	return tmp;
	}

	public PImage RaysFilter(PImage img,float opacity,float threshold,float strength){
		RaysFilter rf = new RaysFilter(opacity/100,threshold/100,strength/100);
		PImage tmp = new PImage(rf.filter((BufferedImage)img.getNative(),null));
	return tmp;
	}
	

	public PImage UnsharpFilter(PImage img,float amt){
		UnsharpFilter rf = new UnsharpFilter(amt/100,1);
		PImage tmp = new PImage(rf.filter((BufferedImage)img.getNative(),null));
	return tmp;
	}

	public PImage motionBlurFilter(PImage img, float f, float g, float angle){
		MotionBlur mb = new MotionBlur(g,angle,f,0);
		PImage tmp = new PImage(mb.filter((BufferedImage)img.getNative(),null));
	return tmp;
	}
	
	public PImage lensnBlurFilter(PImage img, float radius, float bloom, float angle,float sides){
		LensBlurFilter lb = new LensBlurFilter(radius,bloom,angle,(int)sides);
		PImage tmp = new PImage(lb.filter((BufferedImage)img.getNative(),null));
	return tmp;
	}
	
	public PImage spherifyFilter(PImage img, int amount,float amounts){
		if(amount<=1)
			amount=1;
		img.loadPixels();
		PGraphics tmp = new PGraphics();
		tmp = createGraphics(img.width,img.height);
tmp.noStroke();
		tmp.beginDraw();
		tmp.background(0,0);
		if(amounts>-1)
		tmp.strokeWeight(amounts);
		for(int x=0;x<img.width;x+=amount)
			for(int y=0;y<img.height;y+=amount){
			
				int loc = (y*img.width)+x;
				int c = img.pixels[loc];
				if(amounts>-1)
					tmp.stroke(c);
				tmp.fill(red(c),green(c),blue(c),alpha(c));
				tmp.ellipse(x+amount/2,y+amount/2,amount,amount);
			}
		tmp.endDraw();
		return tmp.get();
		
	}
	
	
	public PImage boxifyFilter(PImage img, float boxSize,float rotDegrees,float stWeight){
		println("BOXIFYING |||| BOXSIZE: "+boxSize+" :: ROTDEG: "+rotDegrees+" :: STROKEWEIGHT: "+stWeight);
		if(boxSize<=1)
			boxSize=1;
		img.loadPixels();
		PGraphics tmp = new PGraphics();
		tmp = createGraphics(img.width,img.height);
		rectMode(CENTER);
		tmp.beginDraw();
		tmp.background(0,0);
		tmp.noStroke();
		if(stWeight>-1)
		tmp.strokeWeight(stWeight);
		for(int x=0;x<img.width;x+=boxSize)
			for(int y=0;y<img.height;y+=boxSize){
			float rand = (float) ((Math.random() * (rotDegrees - 0)));
			
		tmp.pushMatrix();
				int loc = (y*img.width)+x;
				int c = img.pixels[loc];
				tmp.fill(red(c),green(c),blue(c),alpha(c));
				if(stWeight>-1)
					tmp.stroke(c);
				tmp.translate(x-(boxSize/2),y-(boxSize/2)); 
				tmp.rotate(radians(rand));
				tmp.rect(0,0,boxSize,boxSize);

				tmp.popMatrix();
			}
		tmp.endDraw();
		return tmp.get();
		
	}
	
	}

