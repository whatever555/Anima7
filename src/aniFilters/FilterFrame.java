package aniFilters;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameEvent;

import processing.core.PImage;
import aniExtraGUI.EButton;
import aniExtraGUI.EInternalFrame;
import aniExtraGUI.ELabel;
import aniExtraGUI.EPanel;
import aniExtraGUI.ETabbedPane;

import com.animation.shop.Main;

public class FilterFrame extends EInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int intVal1 = 0, intVal2 =0, intVal3 = 0;
	
	Main parent;
	EPanel previewImageHolder;
	int currentFilterIndex=0;
	String currentFilter = "";
	ArrayList<String> filters;
	ArrayList<DefaultFilter> filterPanes;
	EPanel previewPanel;

	EButton applyBut = new EButton();
	ETabbedPane tabbedPane = new ETabbedPane();
	boolean inactive = true;
	
	public FilterFrame(final Main parent){
		
		
		  
		   
		   
		filters = new ArrayList<String>();
		filterPanes = new ArrayList<DefaultFilter>();
		this.setAlignmentX( Component.LEFT_ALIGNMENT );
		this.setTitle("Filter");
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setClosable(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(1);
		
		this.parent=parent;
	
		filters.add("Blur");
	
		filterPanes.add(new DefaultFilter(parent,this,"Blur",0,100,0));
		

		filters.add("Posterize");
		
		filterPanes.add(new DefaultFilter(parent,this,"Posterize",0,255,0));

		filters.add("Threshold");
		
		filterPanes.add(new DefaultFilter(parent,this,"Threshold",0,100,50));
		

		filters.add("Spherify");
		filterPanes.add(new DefaultFilter(parent,this,"Spherify",0,100,20));

		filters.add("Boxify");
		
		filterPanes.add(new DefaultFilter(parent,this,"Boxify",0,100,20));
		
		tabbedPane.setBackground(new Color(90,0,244));
		for(int i=0;i<filters.size();i++){
			
			tabbedPane.add(filterPanes.get(i));
			filterPanes.get(i).setVisible(true);
		}
		this.add(tabbedPane);
		previewImageHolder = new EPanel();
		previewImageHolder.setLayout(new FlowLayout(FlowLayout.LEADING));
		 previewImage = new ELabel();
		previewImage.setPreferredSize(new Dimension(200,160));
		previewImage.setAlignmentX( Component.LEFT_ALIGNMENT );
		previewImageHolder.add(previewImage);
		previewImageHolder.setBackground(new Color(parent.canvas.bgColor));
		this.add(previewImageHolder);
		
		applyBut.setText("Apply");
	   applyBut.setPreferredSize(new Dimension(200,26));
	   this.add(applyBut);
	   
	

		
		
		
		
		 this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
		        public void internalFrameClosing(InternalFrameEvent e) {
		        	  parent.filterFrame.setVisible(false);
			         parent.canvasFrame.requestFocus();
			         parent.canvasFrame.grabFocus();
			     	parent.canvasFrame.revalidate();
			         parent.canvasFrame.repaint();
			         parent.canvas.validate();
			         parent.canvas.repaint();
			          
				         System.out.println("ITS IN HERE NOW");
		         
		        }
		        
		        public void internalFrameClosed(InternalFrameEvent e) {
		        	  
		    		parent.canvasFrame.revalidate();
			         parent.canvasFrame.repaint();
			         parent.canvas.validate();
			         parent.canvas.repaint();
			    	 tabbedPane.removeChangeListener(new MyChangeListener("tabbed"));
					 
					 applyBut.removeActionListener(new MyActionListener("apply"));
					
			         
				         
		         
		        }
		     
		      });
		
	}
	

	
	public void showFilter(String name){
		
	inactive=false;
	 tabbedPane.addChangeListener(new MyChangeListener("tabbed"));
	 
	 applyBut.addActionListener(new MyActionListener("apply"));
	
		
		currentFilter=name;
		for(int i=0;i<filters.size();i++){
		//	filterPanes.get(i).setVisible(false);
			if(filters.get(i).equals(name)){
				filterPanes.get(i).setVisible(true);
				this.setTitle(name+" Filter");
				tabbedPane.setSelectedIndex(i);
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

				previewImageHolder.setBackground(new Color(parent.canvas.bgColor));
		     previewImage.setIcon(new ImageIcon(parent.canvas.previewImageBuffered));
		   
		     
		  }
			
		//	previewImage.repaint();
		//	previewImage.revalidate();
		//	revalidate();
		//	repaint();
	  }
	  public void actionChangeEvent(){
		  if(!inactive){
		  if(parent.canvas.previewImageBuffered!=null){
		  parent.canvas.defaultFilters(intVal1,true,filters.get(tabbedPane.getSelectedIndex())); 

		 // intVal1 = (Integer) filterPanes.get(tabbedPane.getSelectedIndex()).filterSpinber.getValue();
	        
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
	        	 if(!inactive){
	      actionChangeEvent();
	        	 }
	   	
	    			}
	        
	        }
	 
	    
	    private class MyChangeListener implements ChangeListener {

	        String myActionName;

	        public MyChangeListener(String actName) {
	           this.myActionName = actName;
	        }
	        

	        public void stateChanged(ChangeEvent e) {
	        	System.out.println("thats now "+filters.get(tabbedPane.getSelectedIndex()));
	        	 if(!inactive){
	        		 filterPanes.get(tabbedPane.getSelectedIndex()).filterSpinber.setValue(1);
	        	//actionChangeEvent();
	        	 }
	    			}
	        
	        }
	 
}
