package aniFilters;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.animation.shop.Main;

public class DefaultFilter extends JPanel {
  Main parent;
  JPanel mainPanel = new JPanel();
  JSpinner filterSpinber;
  FilterFrame holder;
  String myname;
  int rangeLow=0;int rangeHigh=10;int defaultValue=0;
  public DefaultFilter(Main parent, FilterFrame holder,String myname,int rangeLow, int rangeHigh,int defaultValue){
	  this.holder=holder;
	  this.parent = parent;
	  this.myname=myname;
	  this.rangeLow=rangeLow;
	  this.rangeHigh=rangeHigh;
	  this.defaultValue=defaultValue;
	  show();
	  
  }
  
  public void show(){
	  mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	  mainPanel.setPreferredSize(new Dimension(180, 26));
	  JPanel flowPanel1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
	  JLabel filterLabel = new JLabel(myname+": ");
	  SpinnerNumberModel filterModel = new SpinnerNumberModel(defaultValue, rangeLow, rangeHigh,
				1);
		filterSpinber = new JSpinner(filterModel);

		filterSpinber.addChangeListener(new MyChangeListener(myname));
		flowPanel1.setPreferredSize(new Dimension(180, 26));
	
		  
		flowPanel1.add(filterLabel);
		flowPanel1.add(filterSpinber);
		mainPanel.add(flowPanel1);
		this.add(mainPanel);
  }
  
  private class MyChangeListener implements ChangeListener {
  String myname;
  	public MyChangeListener(String myname) {
  		this.myname=myname;
       }
	 public void stateChanged(ChangeEvent e) {
		 parent.canvas.finaliseFrame(parent.CURRENTLAYER,parent.CURRENTFRAME);
		 
		holder.intVal1 = (Integer) filterSpinber.getValue();
		
		parent.canvas.defaultFilters(holder.intVal1/2, false,myname);
		holder.updatePreviewImage();
		
	    }
  }
    }
  

  
  
  

