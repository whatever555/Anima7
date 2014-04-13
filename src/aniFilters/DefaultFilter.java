package aniFilters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

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
  ESpinner filterSpinber;
  FilterFrame holder;
  EPanel mainPanel;
  String myname;
  int rangeLow=0;int rangeHigh=10;int defaultValue=0;
  public DefaultFilter(Main parent, FilterFrame holder,String myname,int rangeLow, int rangeHigh,int defaultValue){
	mainPanel=new EPanel();
	  this.holder=holder;
	  this.parent = parent;
	  this.myname=myname;
	  this.rangeLow=rangeLow;
	  this.rangeHigh=rangeHigh;
	  this.defaultValue=defaultValue;
	  show();
	  
  }
  
  public void show(){
	  //mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
	  this.setLayout(new FlowLayout(FlowLayout.LEADING));
	  //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	  this.setPreferredSize(new Dimension(400, 70));
this.setBounds(0,0,400,70);
	  ELabel filterLabel = new ELabel(myname+": ");
	  SpinnerNumberModel filterModel = new SpinnerNumberModel(defaultValue, rangeLow, rangeHigh,
				1);
		filterSpinber = new ESpinner(filterModel);

		filterSpinber.addChangeListener(new MyChangeListener(myname));
		
		mainPanel.add(filterLabel);		
		mainPanel.add(filterSpinber);
this.add(mainPanel);

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
			    		final int x=updateCount;
						Thread.sleep(300);

						doAction(x);
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
		if(myname.equals("POSTERIZE"))
		parent.canvas.defaultFilters(holder.intVal1, false,myname);
		else
		parent.canvas.defaultFilters((int)(holder.intVal1/2), false,myname);
		holder.updatePreviewImage();
	  }
  }
    }
  

  
  
  

