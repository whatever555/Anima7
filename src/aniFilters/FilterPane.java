package aniFilters;

import java.awt.Dimension;
import java.awt.FlowLayout;

import aniExtraGUI.EPanel;
import aniExtraGUI.ESpinnerPanel;

import com.animation.shop.Main;

public class FilterPane extends EPanel {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Main parent;
  ESpinnerPanel[] filterSpinnerPanels;
  public FilterFrame holder;
  public EPanel mainPanel;
  String[] varNames;
  public float[] vals;
  String name;
  int[] rangeLow, rangeHigh;
  public FilterPane(String name,Main parent, FilterFrame holder,String[] varNames,int[] rangeLow, int[] rangeHigh,float[] vals){
this.name=name;
		int varCount = varNames.length;
		mainPanel=new EPanel();

this.vals=vals;
	this.filterSpinnerPanels = new ESpinnerPanel[varCount];
	  this.holder=holder;
	  this.parent = parent;
	  this.varNames=varNames;
	  this.rangeLow=rangeLow;
	  this.rangeHigh=rangeHigh;
	  show();
	  
  }
  
  public void show(){
	  //mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
	  this.setLayout(new FlowLayout(FlowLayout.LEADING));
	  //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	  this.setPreferredSize(new Dimension(500, 70));
	  this.setBounds(0,0,500,70);
	
	  for(int i=0;i<varNames.length;i++){
		  filterSpinnerPanels[i] = new ESpinnerPanel(i,this,parent,rangeLow[i],rangeHigh[i],vals[i],1,varNames[i]);
		  
		
	  }
this.add(mainPanel);

  }
  
  public void applyPreviewFilter(){
	  parent.canvas.refreshPreviewThumb();
		
	  if(parent.canvas.previewImageBuffered!=null){
	 parent.canvas.defaultFilters(vals,false,name);

		holder.updatePreviewImage();
	  }
  }

    }
  

  
  
  

