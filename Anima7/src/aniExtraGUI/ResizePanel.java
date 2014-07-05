package aniExtraGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import processing.core.PGraphics;
import processing.core.PImage;

import com.animation.shop.Main;

public class ResizePanel extends EInternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * user config indices
	 * 0 = workspace folder
	 * 1 = language
	 * 2 = cachesize
	 * 3 = theme
	 * 4 = layout
	 * 
	 */
	Main parent;
	
		public ResizePanel(Main parent){
			
			this.parent=parent;
			
			showMe();
		}
		
		
		ETabbedPane maintabbedPane;
		ESpinner widthSizeSlider;
		ESpinner heightSizeSlider;
		EPanel basicPanel;
	
		 
		public void showMe(){
			Font f = new Font("Verdana", 8,8);
			
			
			
			
			this.setClosable(true); 
			//tlFrame.setIconifiable(true); 
			this.setResizable(true);
			this.setDefaultCloseOperation(1);
			
			maintabbedPane = new ETabbedPane();
			basicPanel = new EPanel();
			basicPanel.setLayout(new BoxLayout(basicPanel,BoxLayout.Y_AXIS));
			EPanel ep = new EPanel();
			ep.setLayout(new FlowLayout(FlowLayout.LEADING));
			ep.setMaximumSize(new Dimension(400,32));

			 SpinnerNumberModel widthModel = new SpinnerNumberModel(parent.CANVASWIDTH, 1,1024 ,1);
			widthSizeSlider = new ESpinner(widthModel);
			widthSizeSlider.addChangeListener(new MyChangeListener("width"));

			widthSizeSlider.setFont(f);
			ELabel label = new ELabel("Width:");
			
			ep.add(label);
			ep.add(widthSizeSlider);
			basicPanel.add(ep);
			

			
			ep = new EPanel();
			ep.setLayout(new FlowLayout(FlowLayout.LEADING));
			ep.setMaximumSize(new Dimension(400,32));
			 SpinnerNumberModel heightModel = new SpinnerNumberModel(parent.CANVASHEIGHT, 1, 1024,1);
				heightSizeSlider = new ESpinner(heightModel);
			heightSizeSlider.addChangeListener(new MyChangeListener("height"));

			heightSizeSlider.setFont(f);
			label = new ELabel("Height:");
			
			ep.add(label);
			ep.add(heightSizeSlider);
			basicPanel.add(ep);

		
			
		
			maintabbedPane.addTab("General",basicPanel);
			this.add(maintabbedPane);
			this.setPreferredSize(new Dimension(400,200));
			
			updateMe();
			
		}
		
		
		
		int changeActionCount=0;
		
		public void updateMe(){
			widthSizeSlider.setValue(parent.CANVASWIDTH);
			heightSizeSlider.setValue(parent.CANVASHEIGHT);
			
		}
		 
		  
		public class  MyChangeListener implements ChangeListener{
			String actionName;
			public MyChangeListener(String str){
				this.actionName = str;
				
			}

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
			

				changeActionCount++;
			
				 new Thread()
					{
					    public void run() {
					    	try {
					    		final int x=changeActionCount;
								Thread.sleep(420);

								updateWidthAndHeight(x);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
					    }
					}.start();
				
				
			}
			
			
		}
		
		public void updateWidthAndHeight(int x){
		
				if(x==changeActionCount){
						if(parent.LOADED==true){
							parent.CANVASWIDTH = (int)widthSizeSlider.getValue();
							parent.CANVASHEIGHT =(int) heightSizeSlider.getValue();
							System.out.println("RESIZING GRAPHIC "+parent.CANVASWIDTH+"  H: "+parent.CANVASHEIGHT);
							
							parent.canvas.resizeFrameGraphic();
							
							parent.canvas.finaliseFrame(parent.CURRENTLAYER, parent.CURRENTFRAME);
							parent.canvas.showNewFrame(parent.CURRENTLAYER, parent.CURRENTFRAME,-1);
					parent.canvas.zoom(parent.canvas.zoomLevel);

					parent.canvas.showNewFrame(parent.CURRENTLAYER, parent.CURRENTLAYER,-1);
						}
				}
			
			
		}
}
