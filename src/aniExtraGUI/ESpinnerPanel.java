package aniExtraGUI;

import java.awt.FlowLayout;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aniFilters.FilterPane;

import com.animation.shop.Main;

public class ESpinnerPanel extends EPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Main parent;
	ESpinner spinner;
	ELabel   label;
	public FilterPane fp;
	int myIndex = 0;
	public ESpinnerPanel(int index,FilterPane fp,Main parent,int min, int max, float def, int inc, String labelStr){
	this.fp=fp;
		this.myIndex=index;
		this.parent=parent;
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.label = new ELabel(translate(labelStr)+": ");
		 SpinnerNumberModel model = new SpinnerNumberModel(def, min, max,inc);
		 spinner=new ESpinner(model);
		 this.add(label);
		 this.add(spinner);
		 spinner.addChangeListener(new MyChangeListener());
		 fp.mainPanel.add(this);
	}
	
	 public String translate(String str){
		 return parent.translate(str);
	 }
	  int updateCount=0;
	  private class MyChangeListener implements ChangeListener {
	  
	  	public MyChangeListener() {
	  		
	       }
		 public void stateChanged(ChangeEvent e) {
Double x = (Double)spinner.getValue();
			 fp.vals[myIndex] = x.floatValue();
			fp.holder.doAction();
		    }
	  }
	
}
