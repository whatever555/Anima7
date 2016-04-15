package aniFilters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameEvent;

import processing.core.PImage;
import aniExtraGUI.EButton;
import aniExtraGUI.EInternalFrame;
import aniExtraGUI.ELabel;
import aniExtraGUI.EPanel;
import aniExtraGUI.ETabbedPane;
import aniExtraGUI.WrapLayout;

import com.animation.shop.Main;

public class FilterFrame extends EInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	Main parent;
	EPanel previewImageHolder;
	int currentFilterIndex=0;
	String currentFilter = "";
	ArrayList<String> filters;
	ArrayList<FilterPane> filterPanes;
	EPanel previewPanel;

	EButton applyBut = new EButton();
	ETabbedPane tabbedPane;
	EPanel previewImageHolderBox;
	int myIndex=0;
	public FilterFrame(final Main parent,String options,int index){
		this.myIndex=index;
		 tabbedPane = new ETabbedPane();
		  
		 EPanel mainPanel = new EPanel();
		 mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		 
		filters = new ArrayList<String>();
		filterPanes = new ArrayList<FilterPane>();
		this.setAlignmentX( Component.LEFT_ALIGNMENT );
		this.setTitle("Filter");
		this.setLayout(new BorderLayout());
		this.setClosable(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(1);
		this.setMinimumSize(new Dimension(500,200));
		this.setPreferredSize(new Dimension(500,200));
		
		this.parent=parent;

		if(options.equals("PAINTING")){
			filters.add("Painting Style 1");
			filterPanes.add(new FilterPane("Painting Style 1",parent,this,new String[]{"Lossy","Density","Lossyness","Intensity"},new int[]{1,1,1,1},new int[]{100,100,100,100},new float[]{1,12,12,12}));
			
			filters.add("Painting Style 2");
			filterPanes.add(new FilterPane("Painting Style 2",parent,this,new String[]{"Min brush size","Max brush size","Roughness","Brush"},new int[]{1,1,1,1},new int[]{500,500,499,7},new float[]{1,12,5,1}));
			
			
		}else if(options.equals("AMNONP5")){
			filters.add("Polar Filter");
			filterPanes.add(new FilterPane("Polar Filter",parent,this,new String[]{"Factor","Density"},new int[]{0,1},new int[]{100,100},new float[]{0,1,0,0}));
			
			filters.add("Arcy Filter");
			filterPanes.add(new FilterPane("Arcy Filter",parent,this,new String[]{"Density","Rings","Number"},new int[]{0,1,0},new int[]{100,100,100},new float[]{0,1,0,0}));
			
		}else
			
				
		if(options.equals("EFFECTS")){
			filters.add("Posterize");
			filterPanes.add(new FilterPane("Posterize",parent,this,new String[]{"Level"},new int[]{2},new int[]{255},new float[]{25,0,0,0}));
			filters.add("Threshold");
			filterPanes.add(new FilterPane("Threshold",parent,this,new String[]{"Level"},new int[]{0},new int[]{100},new float[]{50,0,0,0}));
			}else
				
		if(options.equals("BLUR")){
			filters.add("Blur");
			filterPanes.add(new FilterPane("Blur",parent,this,new String[]{"Level"},new int[]{0},new int[]{100},new float[]{0,0,0,0}));
			filters.add("Glow");
			filterPanes.add(new FilterPane("Glow",parent,this,new String[]{"Level"},new int[]{0},new int[]{100},new float[]{0,0,0,0}));
			filters.add("Motion Blur");
			filterPanes.add(new FilterPane("Motion Blur",parent,this,new String[]{"Rotation","Distance","Angle"},new int[]{0,0,0},new int[]{360,360,360},new float[]{0,0,0,0}));
			filters.add("Lens Blur");
			filterPanes.add(new FilterPane("Lens Blur",parent,this,new String[]{"Radius","Bloom","Angle","Sides"},new int[]{0,0,0,0},new int[]{360,360,360,12},new float[]{0,0,0,0}));
			filters.add("Sharpen");
			filterPanes.add(new FilterPane("Sharpen",parent,this,new String[]{"Level"},new int[]{0},new int[]{100},new float[]{0,0,0,0}));
			
		}else
			if(options.equals("SHADING")){
				filters.add("Shadow");
				filterPanes.add(new FilterPane("Shadow",parent,this,new String[]{"Radius","Offset X","Offset Y", "Opacity"},new int[]{0,-100,-100,0},new int[]{100,100,100,100},new float[]{0,0,0,50}));
				filters.add("Rays");
				filterPanes.add(new FilterPane("Rays",parent,this,new String[]{"Opacity","Threshold","Strength"},new int[]{0,0,0},new int[]{100,100,100},new float[]{100,0,50,0}));
				
			}else
				
	
		if(options.equals("ARTISTIC")){
		filters.add("Spherify");
		filterPanes.add(new FilterPane("Spherify",parent,this,new String[]{"Ellipse Size","Stroke"},new int[]{1,-1},new int[]{100,100},new float[]{20,0,1,0}));
		filters.add("Boxify");
		filterPanes.add(new FilterPane("Boxify",parent,this,new String[]{"Box Size","Distortion","Stroke"},new int[]{1,0,-1},new int[]{100,360,100},new float[]{20,0,1,0}));
		}
		
		
		
		for(int i=0;i<filters.size();i++){
			JPanel jp = new JPanel();

			jp.setLayout(new WrapLayout());
			jp.add(filterPanes.get(i));
			filterPanes.get(i).setAlignmentX( Component.LEFT_ALIGNMENT );
			tabbedPane.addTab(filters.get(i),jp);
			jp.setBackground(filterPanes.get(i).getBackground());
			jp.setBorder(null);
		}
		mainPanel.add(tabbedPane);
		tabbedPane.setMinimumSize(new Dimension(400,100));
		
		previewImageHolder = new EPanel();
		previewImageHolder.setLayout(new FlowLayout(FlowLayout.LEADING));
		previewImageHolderBox = new EPanel();
		previewImageHolderBox = new EPanel();
		previewImageHolder.add(previewImageHolderBox);

		previewImageHolderBox.setPreferredSize(new Dimension(200,160));
		previewImageHolderBox.setMaximumSize(new Dimension(200,160));
		previewImageHolderBox.setBounds(0,0,200,160);
		 previewImage = new ELabel();
			previewImage.setPreferredSize(new Dimension(200,160));
			previewImage.setMaximumSize(new Dimension(200,160));
		previewImage.setAlignmentX( Component.LEFT_ALIGNMENT );
		previewImageHolderBox.add(previewImage);
		previewImageHolderBox.setBackground(new Color(parent.canvas.bgColor));
		mainPanel.add(previewImageHolder);
		
		applyBut.setText(translate("Apply"));
	   applyBut.setPreferredSize(new Dimension(200,26));
	   mainPanel.add(applyBut);
	   
	
this.add(mainPanel);
		
		
		
		
		 this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
		        public void internalFrameClosing(InternalFrameEvent e) {
		        	  parent.filterFrames.get(myIndex).setVisible(false);
			    /*     parent.canvasFrame.requestFocus();
			         parent.canvasFrame.grabFocus();
			     	parent.canvasFrame.revalidate();
			         parent.canvasFrame.repaint();
			         parent.canvas.validate();
			         parent.canvas.repaint();
			         */
			          
		         
		        }
		        
		        public void internalFrameClosed(InternalFrameEvent e) {
		        /*	  
		    		parent.canvasFrame.revalidate();
			         parent.canvasFrame.repaint();
			         parent.canvas.validate();
			         parent.canvas.repaint();
			         */
			    	 tabbedPane.removeChangeListener(new MyChangeListener("tabbed"));
					 
					 applyBut.removeActionListener(new MyActionListener("apply"));
					
			         
				         
		         
		        }
		     
		      });
		
	}
	

	 public String translate(String str){
		 return parent.translate(str);
	 }
	public void showFilter(String name){
		
	 tabbedPane.addChangeListener(new MyChangeListener("tabbed"));
	 
	 applyBut.addActionListener(new MyActionListener("apply"));
	
		
		currentFilter=name;
		for(int i=0;i<filters.size();i++){
		//	filterPanes.get(i).setVisible(false);
			if(filters.get(i).equals(name)){
				//filterPanes.get(i).setVisible(true);
				this.setTitle(name+" Filter");
				
				tabbedPane.setSelectedIndex(i);
				updatePreviewImage();
			}
			
		}
	//	actionChangeEvent();
	//	revalidate();
	//	repaint();
	}
	
	

	  ELabel previewImage;
	  PImage previewThumb;
	  
	  public void updatePreviewImage(){
		  if(parent.canvas.previewImageBuffered!=null){
			 
			  previewImageHolderBox.setBackground(new Color(parent.canvas.bgColor));
			  previewImage.setIcon(new ImageIcon(parent.canvas.previewImageBuffered));
		   
		  }
			
			//previewImage.repaint();
		
	  }
	  
	  int changeIntCount = 0;
	  
	  public void actionChangeEvent(int x){
		  if(changeIntCount == x){
		  if(parent.canvas.previewImageBuffered!=null){
			  String name = filterPanes.get(tabbedPane.getSelectedIndex()).name;
			
		  parent.canvas.defaultFilters(filterPanes.get(tabbedPane.getSelectedIndex()).vals,true,name); 


	        parent.canvas.finaliseFrame(parent.CURRENTLAYER,parent.CURRENTFRAME);
	        parent.canvas.showNewFrame(parent.CURRENTLAYER,parent.CURRENTFRAME,-1);
		  }
		  }
	  }
	    private class MyActionListener implements ActionListener {

	        String myActionName;

	        public MyActionListener(String actName) {
	           this.myActionName = actName;
	        }
	        

	        public void actionPerformed(ActionEvent e) {
	        	 
	        		if(myActionName.equals("apply")){
	        		 changeIntCount++;
		 new Thread()
			{
			    public void run() {
			    	try {
			    		final int x=changeIntCount;
						Thread.sleep(120);

					      actionChangeEvent(x);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			    }
			}.start();
	        	 }
	   	
	    			}
	        
	        }
	 
	    int actionCount;
	    private class MyChangeListener implements ChangeListener {

	        String myActionName;

	        public MyChangeListener(String actName) {
	           this.myActionName = actName;
	        }
	        

	        public void stateChanged(ChangeEvent e) {
	        	 
	        	doAction();
	        	 
	    			}
	        
	        }
	    
	    public void doAction(){
	    	actionCount++;
	    	new Thread() {
				public void run() {
					try {
				
        		 int x=actionCount;
    				Thread.sleep(120);
    			applyPrev(x);
    		} catch (InterruptedException es) {
    			// TODO Auto-generated catch block
    			es.printStackTrace();
    		}
	    
	    }
				}.start();
	    }
	    public void applyPrev(int x){
	    	if(x==actionCount)
	    	filterPanes.get(tabbedPane.getSelectedIndex()).applyPreviewFilter();
        	
	    }
	 
}
