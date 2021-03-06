package aniExtraGUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class EScrollPane extends JScrollPane{
	ColorUIResource thumbColor;
public EScrollPane(ColorUIResource c){
	super();
	this.thumbColor=c;
	styleMe(c);
}

public void styleMe(ColorUIResource c){
	final Color cc = (Color)c;
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
        	this.thumbColor = (Color)cc;
        	
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
        	this.thumbColor = cc;
        	
        }
    });
}
private JButton createZeroButton() {
	
    JButton jbutton = new JButton();
    jbutton.setPreferredSize(new Dimension(0, 0));
    jbutton.setMinimumSize(new Dimension(0, 0));
    jbutton.setMaximumSize(new Dimension(0, 0));
    jbutton.setBackground(thumbColor);
    return jbutton;
}
}
