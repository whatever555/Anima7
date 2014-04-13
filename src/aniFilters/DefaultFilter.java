package aniFilters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aniExtraGUI.ELabel;
import aniExtraGUI.EPanel;
import aniExtraGUI.ESpinner;

import com.animation.shop.Main;

public class DefaultFilter extends EPanel {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Main parent;
  EPanel mainPanel = new EPanel();
  ESpinner filterSpinber;
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
	  this.setBackground(new Color(0,0,0));
	  this.setLayout(new BorderLayout());
	  //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	  this.setPreferredSize(new Dimension(400, 26));

	  ELabel filterLabel = new ELabel(myname+": ");
	  SpinnerNumberModel filterModel = new SpinnerNumberModel(defaultValue, rangeLow, rangeHigh,
				1);
		filterSpinber = new ESpinner(filterModel);

		filterSpinber.addChangeListener(new MyChangeListener(myname));
		
		  
		this.add(filterLabel);		
		this.add(filterSpinber);

  }
  int updateCount=0;
  private class MyChangeListener implements ChangeListener {
  String myname;
  	public MyChangeListener(String myname) {
  		this.myname=myname;
       }
	 public void stateChanged(ChangeEvent e) {

			updateCount++;
		 new Thread()
			{
			    public void run() {
			    	try {
						Thread.sleep(50);
				    	doAction(updateCount);
				    	
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			    }
			}.start();
		
		
		
	    }
  }
  public void doAction(int i){
	  if(i==updateCount){
	  parent.canvas.finaliseFrame(parent.CURRENTLAYER,parent.CURRENTFRAME);
		 
		holder.intVal1 = (Integer) filterSpinber.getValue();
		
		parent.canvas.defaultFilters(holder.intVal1/2, false,myname);
		holder.updatePreviewImage();
	  }
  }
    }
  

  
  
  

