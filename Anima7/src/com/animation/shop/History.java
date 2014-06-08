package com.animation.shop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
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
		this.setTitle(translate("History"));
		this.setLayout(new BorderLayout());
		this.setClosable(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(1);

	
		panelArea=new EPanel();
		panelArea.setLayout(new BoxLayout(panelArea,BoxLayout.Y_AXIS));
		
		sc.setViewportView(panelArea);
		this.add(sc);
		
		
	}
	 public String translate(String str){
		 return parent.translate(str);
	 }
	Image img;
	public void update(){
		
		panelArea.removeAll();
		int y=0;
		
		//top down loop
		if(parent.canvas.userActions!=null)
			for(int j = parent.canvas.userActions.size() - 1; j >= 0; j--){
				if(!parent.canvas.userActions.get(j).name.equals("KEYADDFIX") && !parent.canvas.userActions.get(j).name.equals("KEYREMOVEFIX")){
				EButton jb = new EButton();
				jb.setText(translate(parent.canvas.userActions.get(j).name));
				jb.setPreferredSize(new Dimension(this.getWidth(),28));
				jb.setMaximumSize(new Dimension(this.getWidth(),28));
				jb.setBounds(0,(y*30),this.getWidth(),28);
			jb.setBorder(null);
				try {
					//TODO final static images here
					img = ImageIO.read(getClass().getResource("/data/icons/actions/"+parent.canvas.userActions.get(j).name.replaceAll("\\s","")+".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				jb.setHorizontalAlignment(SwingConstants.LEFT);
				jb.setIcon(new ImageIcon(img));
				jb.setVisible(true);
				panelArea.add(jb);
				
				y++;
				
				jb.addActionListener(new MyActionListenerZ(j));
				}
		}
		
			panelArea.repaint();
			sc.repaint();
		this.repaint();
		
		
	}
	

    private class MyActionListenerZ implements ActionListener {
//TODO the problem here is that the currentActionIndex is dynamic and myActionIndex refers to a static number
        int myActionIndex;

        public MyActionListenerZ(int a) {
           this.myActionIndex = a;
        }
        

        public void actionPerformed(ActionEvent e) {
        if(myActionIndex>parent.currentActionIndex){
				while(parent.currentActionIndex<myActionIndex-1){
					parent.canvas.redo(false);
				
				}
				if(parent.currentActionIndex==myActionIndex-1)
				parent.canvas.redo(true);
				}
				else{
					
				while(parent.currentActionIndex>myActionIndex+1){
					
					parent.canvas.undo(false);
				}	

				if(parent.currentActionIndex==myActionIndex+1)
				parent.canvas.undo(true);
				}

        }
    }
}
