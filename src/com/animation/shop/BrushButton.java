/**
 * 
 */
package com.animation.shop;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;


/**
 * @author eddie
 *
 */
public class BrushButton extends JButton {  
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int fi=1;int bi=1;
Main parent;
String[] locs;
Image img;
public BrushButton(final Main parent, int folderIndex, int brushIndex,int BType){  
this.parent=parent;
this.bi=brushIndex;
this.fi=folderIndex;
locs = new String[1];

if(BType==2){
	locs = new String[bi];
	for(int i=0;i<bi;i++){
		locs[i]="multibrushes/"+fi+"/"+(i+1)+".png";
	}
}else{
bi++;
locs[0]="brushes/"+fi+"/"+bi+".png";
}

try {
	
	if(BType == 2){
	//	System.out.println("data/multibrushes/"+fi+"/1.png");
	img = ImageIO.read(getClass().getResource("/data/multibrushes/"+fi+"/1.png"));	
	}
	else{
	//	System.out.println("data/brushes/"+fi+"/"+bi+".png");
	img = ImageIO.read(getClass().getResource("/data/brushes/"+fi+"/"+bi+".png"));
	}

	
	setBackground(Color.black);
setIcon(new ImageIcon(img));
} catch (IOException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}

addActionListener(new ActionListener(){  
@Override  
public void actionPerformed(ActionEvent e) {  
	parent.currentTool="brush";
	parent.topPanel.setBrushOptions();
	parent.canvas.setBrush(locs);

	
}  
});  
}  
   
} 