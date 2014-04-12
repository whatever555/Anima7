package com.animation.shop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;


public class ToolsBar extends JInternalFrame{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Main parent;
		public ToolsBar(Main parent){
			this.parent = parent;
			showMe();
		}
		 int WIDTH = 68;
		 int HEIGHT = 400;
		 int butWIDTH =24;
		 int butHEIGHT =24;
		 ColorButton colorButton ;
		 ArrayList<ToolButton> toolButtonsList = new ArrayList<ToolButton>();
		 
		 
		public void showMe(){
this.setClosable(true); 
			this.setResizable(true);
			this.setDefaultCloseOperation(1);
			
			JPanel toolsPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			toolsPanel.setBounds(0,0,WIDTH,HEIGHT);
			


			toolButtonsList.add(new ToolButton(this,"pointer",butWIDTH,butHEIGHT));
			toolButtonsList.add(new ToolButton(this,"move",butWIDTH,butHEIGHT));
			toolButtonsList.add(new ToolButton(this,"selectRect",butWIDTH,butHEIGHT));
			toolButtonsList.add(new ToolButton(this,"selectCirc",butWIDTH,butHEIGHT));
			toolButtonsList.add(new ToolButton(this,"brush",butWIDTH,butHEIGHT));
			toolButtonsList.add(new ToolButton(this,"Eraser",butWIDTH,butHEIGHT));
			toolButtonsList.add(new ToolButton(this,"rect",butWIDTH,butHEIGHT));
			toolButtonsList.add(new ToolButton(this,"circle",butWIDTH,butHEIGHT));
			toolButtonsList.add(new ToolButton(this,"bucket",butWIDTH,butHEIGHT));
			toolButtonsList.add(new ToolButton(this,"dropper",butWIDTH,butHEIGHT));
		    
			
			
			for(ToolButton tb: toolButtonsList){
				toolsPanel.add(tb);
			}
			colorButton = new ColorButton(parent);//parent.penOps.colorButton;
			colorButton.setPreferredSize(new Dimension(butWIDTH,butHEIGHT));
			colorButton.setMaximumSize(new Dimension(butWIDTH,butHEIGHT));
			toolsPanel.add(colorButton);
			colorButton.setVisible(true);
			
			this.add(toolsPanel);
			this.setVisible(true);
			

			this.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(238,238,238)));
			
			
		}
		
		public void refreshList(){
			for(ToolButton tb: toolButtonsList){
				tb.selected=false;
				tb.setBackground(null);
				
			}
		}
		
		

	    
}
