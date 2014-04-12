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
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;

import processing.core.PImage;

import com.animation.shop.Main;

public class FilterFrame extends JInternalFrame {
	int intVal1 = 0, intVal2 =0, intVal3 = 0;
	
	Main parent;
	JPanel previewImageHolder;
	int currentFilterIndex=0;
	String currentFilter = "";
	ArrayList<String> filters;
	ArrayList<JPanel> filterPanes;
	JPanel previewPanel;
	DefaultFilter bf;
	DefaultFilter tf;
	DefaultFilter pf;
	DefaultFilter sf;
	DefaultFilter boxf;
	public FilterFrame(final Main parent){
		
		
		  
		   
		   
		filters = new ArrayList<String>();
		filterPanes = new ArrayList<JPanel>();
		this.setAlignmentX( Component.LEFT_ALIGNMENT );
		this.setTitle("Filter");
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setClosable(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(1);
		
		this.parent=parent;
	
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.LEADING));
		filters.add("Blur");
		bf = new DefaultFilter(parent,this,"Blur",0,100,0);
		jp.add(bf);
		filterPanes.add(jp);
		

		jp = new JPanel(new FlowLayout(FlowLayout.LEADING));
		filters.add("Posterize");
		pf = new DefaultFilter(parent,this,"Posterize",0,255,0);
		jp.add(pf);
		filterPanes.add(jp);

		jp = new JPanel(new FlowLayout(FlowLayout.LEADING));
		filters.add("Threshold");
		tf = new DefaultFilter(parent,this,"Threshold",0,100,50);
		jp.add(tf);
		filterPanes.add(jp);
		

		jp = new JPanel(new FlowLayout(FlowLayout.LEADING));
		filters.add("Spherify");
		sf = new DefaultFilter(parent,this,"Spherify",0,100,20);
		jp.add(sf);
		filterPanes.add(jp);

		jp = new JPanel(new FlowLayout(FlowLayout.LEADING));
		filters.add("Boxify");
		boxf = new DefaultFilter(parent,this,"Boxify",0,100,20);
		jp.add(boxf);
		filterPanes.add(jp);
		
		for(int i=0;i<filters.size();i++){
			
			this.add(filterPanes.get(i));
			filterPanes.get(i).setVisible(true);
		}
		
		previewImageHolder = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		 previewImage = new JLabel();
		previewImage.setPreferredSize(new Dimension(200,160));
		previewImage.setAlignmentX( Component.LEFT_ALIGNMENT );
		previewImageHolder.add(previewImage);
		previewImageHolder.setBackground(new Color(parent.canvas.bgColor));
		this.add(previewImageHolder);
		
		JButton applyBut = new JButton("Apply");
	   applyBut.setPreferredSize(new Dimension(200,26));
	   this.add(applyBut);
	   

		applyBut.addActionListener(new MyActionListener("apply"));
		
		
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
		        

				         
				         System.out.println("IT the sevS IN HERE NOW");
		         
		        }
		     
		      });
		 
	}
	
	public void showFilter(String name){
		parent.canvas.refreshPreviewThumb();
		if(parent.canvas.previewThumb!=null)
		parent.canvas.previewImageBuffered = (BufferedImage) (parent.canvas.previewThumb.getNative());

		currentFilter=name;
		for(int i=0;i<filters.size();i++){
			filterPanes.get(i).setVisible(false);
			if(filters.get(i).equals(name)){
				filterPanes.get(i).setVisible(true);
				this.setTitle(name+" Filter");
			}
			
		}
		updatePreviewImage();
	//	revalidate();
	//	repaint();
	}
	
	

	  JLabel previewImage;
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
	  
	    private class MyActionListener implements ActionListener {

	        String myActionName;

	        public MyActionListener(String actName) {
	           this.myActionName = actName;
	        }
	        

	        public void actionPerformed(ActionEvent e) {
	       parent.canvas.defaultFilters(intVal1,true,currentFilter); 

       	bf.filterSpinber.setValue(0);
    	tf.filterSpinber.setValue(50);
    	pf.filterSpinber.setValue(0);

    	sf.filterSpinber.setValue(20);
    	boxf.filterSpinber.setValue(20);
	        
	        parent.canvas.finaliseFrame(parent.CURRENTLAYER,parent.CURRENTFRAME);
	        parent.canvas.showNewFrame(parent.CURRENTLAYER,parent.CURRENTFRAME,-1);
	       
	     
	   	
	    			}
	        
	        }
	 
}
