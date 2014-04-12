package com.animation.shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class BrushSelection extends JInternalFrame {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Main parent;

		public BrushSelection(Main parent){
			this.parent=parent;
			showThis();
		}
		
		
		Panel bordPanel = new Panel(new BorderLayout());
		JPanel boxPanel;
		ScrollPane sc;
		int WIDTH  = 260;
		int HEIGHT = 240;
		
		public void showThis(){
			this.setLayout(new BorderLayout());
			this.setVisible(false);
			this.setTitle("Brush Selection");
			this.setClosable(true); 
			this.setResizable(true);
			this.setDefaultCloseOperation(1);
			
			bordPanel.setBounds(0,0,WIDTH,HEIGHT);
			sc = new ScrollPane();
			
			this.add(sc);
			



			this.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(238,238,238)));
			
			//this.setBorder(BorderFactory.createCompoundBorder(border1,border2));
			
			sc.add(bordPanel);
			
			
			

			boxPanel = new JPanel();

			
			boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
		
			
			int i=1;
			int j=1;
			
			
			URL url = getClass().getResource("/data/multibrushes/"+j+"/1.png");
			

			
			while(true){
				JPanel contentPanel1=new JPanel(new FlowLayout(FlowLayout.LEADING));
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
			
			
			
			JPanel contentPanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
			
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
			sc.add(bordPanel);
			bordPanel.setVisible(true);
			
			
		}
}
