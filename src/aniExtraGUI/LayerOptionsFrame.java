package aniExtraGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.animation.shop.Main;
import com.animation.shop.TimelineLayer;

public class LayerOptionsFrame extends EInternalFrame{
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Main parent;
 TimelineLayer currentLayer;
 EComboBox blendList;
 EPanel mainPanel;
 JTextField layerNameField;
 ESpinner transparencySpinner;
 
 
 public LayerOptionsFrame(Main parent){
	 mainPanel=new EPanel();
	 mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
	 this.parent=parent;
	 this.setAlignmentX( Component.LEFT_ALIGNMENT );
		this.setTitle(translate("Layer Options"));
		this.setLayout(new BorderLayout());
		this.setClosable(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(1);
		this.setMinimumSize(new Dimension(300,500));
		this.setPreferredSize(new Dimension(300,500));
		
		
		EPanel renameLayerPanel = new EPanel();
		renameLayerPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		ELabel layerNameLabel = new ELabel(translate("Layer Name"));
		layerNameField = new JTextField(8);
		layerNameField.addActionListener(new MyActionListener("Layer Name"));
		
		layerNameField.setDocument
        (new ETextFieldLimit(12));
		EButton layerNameUpdate = new EButton();
		layerNameUpdate.setPreferredSize(new Dimension(60,20));
		layerNameUpdate.setText(translate("Update"));
		layerNameUpdate.setBorder(null);
		layerNameUpdate.setPreferredSize(new Dimension(60,20));
		layerNameUpdate.addActionListener(new MyActionListener("Layer Name"));
		
		
		renameLayerPanel.add(layerNameLabel);
		renameLayerPanel.add(layerNameField);
		renameLayerPanel.add(layerNameUpdate);
		
		EPanel blendingOptionsLayer = new EPanel();
		blendingOptionsLayer.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		ELabel blendingLabel = new ELabel(translate("Blending Mode"));
		String[] opStrings = { "Normal", "Blend", "Add", "Subtract", "Darkest","Lightest","Difference","Exclusion","Multiply","Screen","Replace" };

		 blendList = new EComboBox(opStrings);
		 blendList.setSelectedIndex(0);
		 blendList.addActionListener(new MyActionListener("BLEND"));
		 
		 blendingOptionsLayer.add(blendingLabel);
		 blendingOptionsLayer.add(blendList);

		 EPanel transparencyPanel = new EPanel();
		 transparencyPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		 
		 ELabel transparencyLabel;
		 transparencyLabel = new ELabel(translate("Alpha")+": ");
		 SpinnerNumberModel transparencyModel = new SpinnerNumberModel(255, 0, 255,1);
		 transparencySpinner=new ESpinner(transparencyModel);
		 transparencySpinner.addChangeListener(new MyChangeListener("alpha"));

		 transparencyPanel.add(transparencyLabel);
		 transparencyPanel.add(transparencySpinner);
		 
		 renameLayerPanel.setMaximumSize(new Dimension(300,40));
		 blendingOptionsLayer.setMaximumSize(new Dimension(300,40));
		 mainPanel.add(renameLayerPanel);
		 mainPanel.add(blendingOptionsLayer);
		 mainPanel.add(transparencyPanel);
		 this.add(mainPanel);
		 this.setVisible(false);
	 
 }
 public String translate(String str){
	 return parent.translate(str);
 }
 public void setLayer(TimelineLayer layer){
	 this.currentLayer = layer;
	 blendList.setSelectedItem(currentLayer.BLENDING);
	 layerNameField.setText(currentLayer.layerNameLabel.getText());
		
 }
 
private class MyChangeListener implements ChangeListener{
	String myName;
	public MyChangeListener(String myname){
		this.myName=myname;
	}
	public void stateChanged(ChangeEvent e){
		if(myName.equals("alpha")){
			currentLayer.alphaLevel = (short) transparencySpinner.getValue();
			 refreshCanvasStepOne();
		}
	}
}
 private class MyActionListener implements ActionListener {

     String myActionName;

     public MyActionListener(String actName) {
        this.myActionName = actName;
     }
     
 
 public void actionPerformed(ActionEvent e) {

		if(myActionName.equals("Layer Name") && parent.LOADED){
			currentLayer.layerNameLabel.setText(layerNameField.getText());
			currentLayer.layerName=(layerNameField.getText());
			
			currentLayer.layerNameLabel.revalidate();
		}
		else
		if(myActionName.equals("BLEND") && parent.LOADED){
		 currentLayer.BLENDING = (String) blendList.getSelectedItem();
		 refreshCanvasStepOne();
	 }

		}
}
 
 int myCount = 0;
 public void refreshCanvasStepOne(){
myCount++;
final int x = myCount;
		new Thread() {
			public void run() {
				try {
					Thread.sleep(120);

					 refreshCanvasStepTwo(x);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

			}
		}.start();
 }
 public void refreshCanvasStepTwo(int x){
 if(x == myCount)
		parent.canvas.showNewFrame(parent.CURRENTLAYER,parent.CURRENTFRAME,(short)-1);
 }
}

