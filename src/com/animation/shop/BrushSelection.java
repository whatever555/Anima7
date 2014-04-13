package com.animation.shop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.URL;

import javax.swing.BoxLayout;

import aniExtraGUI.EInternalFrame;
import aniExtraGUI.EPanel;
import aniExtraGUI.EScrollPane;

public class BrushSelection extends EInternalFrame {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Main parent;

		public BrushSelection(Main parent){
			this.parent=parent;
			showThis();
		}
		
		
		EPanel bordPanel = new EPanel();
		EPanel boxPanel;
		EScrollPane sc;
		int WIDTH  = 260;
		int HEIGHT = 240;
		
		public void showThis(){

			bordPanel.setLayout(new BorderLayout());
			this.setLayout(new BorderLayout());
			this.setVisible(false);
			this.setTitle("Brush Selection");
			this.setClosable(true); 
			this.setResizable(true);
			this.setDefaultCloseOperation(1);
			
			

			sc = new EScrollPane();
			sc.setBounds(0,0,WIDTH,HEIGHT);
			
			

			
			//this.setBorder(BorderFactory.createCompoundBorder(border1,border2));
			
			

		
			boxPanel = new EPanel();

			
			boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
		
			
			int i=1;
			int j=1;
			
			
			URL url = getClass().getResource("/data/multibrushes/"+j+"/1.png");
			

			
			while(true){
				EPanel contentPanel1=new EPanel();
				contentPanel1.setLayout(new FlowLayout(FlowLayout.LEADING));
				contentPanel1.setMaximumSize( new Dimension(WIDTH-10, 24));
				contentPanel1.setVisible(true);
				i=1;
				url = getClass().getResource("/data/brushes/"+j+"/1.png");
				if (url!=null) {
					while(true){
					  url = getClass().getResource("/data/brushes/"+j+"/"+i+".png");
						if (url!=null) {	
							BrushButton but = new BrushButton(parent,j,i-1,1);
							but.setPreferredSize(new Dimension(20, 20) );
							but.setMaximumSize(new Dimension(20, 20) );
							contentPanel1.add(but,3,0);
							i++;
						}else{
							break;
						}
						}

				j++;
				}else{
					
					break;
				}

				boxPanel.add(contentPanel1,3,0);
				}
			
			
			
			EPanel contentPanel2 = new EPanel();
			contentPanel2.setLayout(new FlowLayout(FlowLayout.LEADING));
			contentPanel2.setMaximumSize( new Dimension(WIDTH-10, 24));
			contentPanel2.setVisible(true);
			
			
			
			
			j=1;
			
			while(true){
		
				
			i=1;
			
			url = getClass().getResource("/data/multibrushes/"+j+"/1.png");
			
			if (url!=null) {
				while(true){
				 url = getClass().getResource("/data/multibrushes/"+j+"/"+i+".png");
						
					if (url!=null) {		
						i++;
					}else{
						BrushButton but = new BrushButton(parent,j,i-1,2);
						but.setPreferredSize(new Dimension(20, 20) );
						but.setMaximumSize(new Dimension(20, 20) );
						contentPanel2.add(but,3,0);
						
						break;
					}
					}

			j++;
			}else{
				
				break;
			}

			}

			
			boxPanel.add(contentPanel2,3,0);
			bordPanel.add(boxPanel);
			bordPanel.setVisible(true);
			
			this.add(sc);
			sc.setVisible(true);


			sc.setViewportView(bordPanel);

			
		}
}
