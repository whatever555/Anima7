package com.animation.shop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PreviousColours extends JPanel {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Main parent;

	public PreviousColours(Main parent){
		this.parent=parent;
		this.setLayout(null);
		this.setVisible(false);
		this.setBackground(new Color(67,67,67));
	}
	
	public void update(){
		this.removeAll();
		int y=0;
		
			for(int j = parent.PREVCOLS.size() - 1; j >= 0; j--){
				Color col = parent.PREVCOLS.get(j);
				JButton jb = new JButton();
				jb.setPreferredSize(new Dimension(20,40));
				jb.setMaximumSize(new Dimension(20,40));
				jb.setBounds(0,(y*20),20,20);
				jb.setBackground(col);
				jb.setVisible(true);
				this.add(jb);
				y++;
				
				jb.addActionListener(new MyActionListener(col));
			
		}
		
		this.repaint();
		
		
		
	}
	

    private class MyActionListener implements ActionListener {

        Color c;

        public MyActionListener(Color c) {
           this.c = c;
        }
        

        public void actionPerformed(ActionEvent e) {
        	parent.PENCOLOR  = c;
        	parent.penOps.colorButton.setBackground(c);
        	parent.toolBar.colorButton.setBackground(c);
        }
    }
}