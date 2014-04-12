/**
 * 
 */
package com.animation.shop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


/**
 * @author eddie
 *
 */
public class ColorButton extends JButton {  
	   
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Main parent;
public ColorButton(final Main parent){  
this.parent=parent;
setBackground(parent.PENCOLOR);
addActionListener(new ActionListener(){  
@Override  
public void actionPerformed(ActionEvent e) {  
	
	parent.PENCOLOR = parent.colorPick(parent.PENCOLOR);
	setBackground(parent.PENCOLOR);
	
}  
});  
}  
   
} 