package com.animation.shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import aniExtraGUI.EButton;
import aniExtraGUI.EInternalFrame;
import aniExtraGUI.EPanel;
import aniExtraGUI.EScrollPane;

public class History extends EInternalFrame {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Main parent;
EPanel panelArea;
EScrollPane sc = new EScrollPane();
	public History(Main parent){
		this.parent=parent;
		this.setVisible(false);
		
		showMe();
		
		
	}

	public void showMe(){
		this.setTitle("History");
		this.setLayout(new BorderLayout());
		this.setClosable(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(1);

	
		panelArea=new EPanel();
		panelArea.setLayout(new BoxLayout(panelArea,BoxLayout.Y_AXIS));
		
		sc.setViewportView(panelArea);
		this.add(sc);
		
		
	}
	Image img;
	public void update(){
		panelArea.removeAll();
		int y=0;
		
			for(int j = parent.ACTIONTYPE.size() - 1; j >= 0; j--){
				
				EButton jb = new EButton();
				jb.setText(parent.ACTIONTYPE.get(j));
				jb.setPreferredSize(new Dimension(this.getWidth(),28));
				jb.setMaximumSize(new Dimension(this.getWidth(),28));
				jb.setBounds(0,(y*30),this.getWidth(),28);
			jb.setBorder(null);
				try {
					img = ImageIO.read(getClass().getResource("/data/icons/actions/"+parent.ACTIONTYPE.get(j).replaceAll("\\s","")+".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				jb.setHorizontalAlignment(SwingConstants.LEFT);
				jb.setIcon(new ImageIcon(img));
				jb.setVisible(true);
				panelArea.add(jb);
				
				y++;
				
				jb.addActionListener(new MyActionListener(j));
			
		}
		
			panelArea.repaint();
			sc.repaint();
		this.repaint();
		
		
	}
	

    private class MyActionListener implements ActionListener {

        int myActionIndex;

        public MyActionListener(int a) {
           this.myActionIndex = a;
        }
        

        public void actionPerformed(ActionEvent e) {
    		if(myActionIndex>parent.currentActionIndex){
				while(parent.currentActionIndex!=myActionIndex){
					parent.canvas.redo();
					//System.out.println("REDOING: "+parent.currentActionIndex);
				}
				}
				else{
				while(parent.currentActionIndex!=myActionIndex){
					
					parent.canvas.undo();
					//System.out.println("UNDOING: "+parent.currentActionIndex);
				}	
				}
        }
    }
}
