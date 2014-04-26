package aniExtraGUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class EScrollPane extends JScrollPane{
public EScrollPane(){
	super();
	styleMe();
}

public void styleMe(){
	setBorder(null);
	this.viewport.setBorder(null);
	this.setOpaque(false);
	this.getVerticalScrollBar().setUI(new BasicScrollBarUI()
    {   
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override    
        protected JButton createIncreaseButton(int orientation) {
        	  return createZeroButton();
        }
        @Override 
        protected void configureScrollBarColors(){
        	this.thumbColor = new Color(87,87,87);
        	
        }
      
    });
	
	this.getHorizontalScrollBar().setUI(new BasicScrollBarUI()
    {   
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override    
        protected JButton createIncreaseButton(int orientation) {
        	  return createZeroButton();
        }

        @Override 
        protected void configureScrollBarColors(){
        	this.thumbColor = new Color(87,87,87);
        	
        }
    });
}
private JButton createZeroButton() {
    JButton jbutton = new JButton();
    jbutton.setPreferredSize(new Dimension(0, 0));
    jbutton.setMinimumSize(new Dimension(0, 0));
    jbutton.setMaximumSize(new Dimension(0, 0));
    return jbutton;
}
}
