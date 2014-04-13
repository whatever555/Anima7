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
	ETabbedPane tabbedPane;
	EPanel testp;
	public FilterFrame(final Main parent){
		
		 tabbedPane = new ETabbedPane();
		  
		 EPanel mainPanel = new EPanel();
		 mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		 
		filters = new ArrayList<String>();
		filterPanes = new ArrayList<DefaultFilter>();
		this.setAlignmentX( Component.LEFT_ALIGNMENT );
		this.setTitle("Filter");
		this.setLayout(new BorderLayout());
		this.setClosable(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(1);
		this.setMinimumSize(new Dimension(400,200));
		this.setPreferredSize(new Dimension(400,200));
		
		this.parent=parent;
	
		filters.add("Blur");
	
		filterPanes.add(new DefaultFilter(parent,this,"Blur",0,100,0));
		

		filters.add("Posterize");
		
		filterPanes.add(new DefaultFilter(parent,this,"Posterize",2,255,24));

		filters.add("Threshold");
		
		filterPanes.add(new DefaultFilter(parent,this,"Threshold",0,100,50));
		

		filters.add("Spherify");
		filterPanes.add(new DefaultFilter(parent,this,"Spherify",1,100,20));

		filters.add("Boxify");
		
		filterPanes.add(new DefaultFilter(parent,this,"Boxify",1,100,20));
		//tabbedPane.addTab("FUKC",new TButton("TEST"));
		for(int i=0;i<filters.size();i++){
			JPanel jp = new JPanel();
			jp.add(filterPanes.get(i));
			filterPanes.get(i).setAlignmentX( Component.LEFT_ALIGNMENT );
			tabbedPane.addTab(filters.get(i),jp);
			//filterPanes.get(i).setVisible(true);
		}
		mainPanel.add(tabbedPane);
		tabbedPane.setMinimumSize(new Dimension(400,60));
		
		previewImageHolder = new EPanel();
		previewImageHolder.setLayout(new FlowLayout(FlowLayout.LEADING));
		 previewImage = new ELabel();
		previewImage.setPreferredSize(new Dimension(200,160));
		previewImage.setAlignmentX( Component.LEFT_ALIGNMENT );
		previewImageHolder.add(previewImage);
		previewImageHolder.setBackground(new Color(parent.canvas.bgColor));
		mainPanel.add(previewImageHolder);
		
		applyBut.setText("Apply");
	   applyBut.setPreferredSize(new Dimension(200,26));
	   mainPanel.add(applyBut);
	   
	
this.add(mainPanel);
		
		
		
		
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
		
	 tabbedPane.addChangeListener(new MyChangeListener("tabbed"));
	 
	 applyBut.addActionListener(new MyActionListener("apply"));
	
		
		currentFilter=name;
		for(int i=0;i<filters.size();i++){
		//	filterPanes.get(i).setVisible(false);
			if(filters.get(i).equals(name)){
				filterPanes.get(i).setVisible(true);
				this.setTitle(name+" Filter");
				if(i>0)
					tabbedPane.setSelectedIndex(i-1);
				else
					tabbedPane.setSelectedIndex(i+1);
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
			
			previewImage.repaint();
		//	previewImage.revalidate();
		//	revalidate();
		//	repaint();
	  }
	  
	  int changeIntCount = 0;
	  public void actionChangeEvent(int x){
		  if(changeIntCount == x){
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
	        	 
	        		if(myActionName.equals("apply")){
	        		 changeIntCount++;
		 new Thread()
			{
			    public void run() {
			    	try {
			    		final int x=changeIntCount;
						Thread.sleep(300);

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
	 
	    
	    private class MyChangeListener implements ChangeListener {

	        String myActionName;

	        public MyChangeListener(String actName) {
	           this.myActionName = actName;
	        }
	        

	        public void stateChanged(ChangeEvent e) {
	        	 
	        		 int d=(int) filterPanes.get(tabbedPane.getSelectedIndex()).defaultValue;
	        		 int x=(int) filterPanes.get(tabbedPane.getSelectedIndex()).filterSpinber.getValue();
		        		if(x==d){d+=1;}
		        				
		        		
	        		 filterPanes.get(tabbedPane.getSelectedIndex()).filterSpinber.setValue(d);
	        		 filterPanes.get(tabbedPane.getSelectedIndex()).filterSpinber.setValue(x);
	        	//actionChangeEvent();
	        	 
	    			}
	        
	        }
	 
}
