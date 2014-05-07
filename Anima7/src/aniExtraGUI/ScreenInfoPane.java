package aniExtraGUI;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.animation.shop.Main;

public class ScreenInfoPane extends EPanel {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
Main parent;
int mousey;
int mousex;
ESlider zoomSlider;

	public ScreenInfoPane(Main parent){
		this.parent=parent;
		showMe();
	}
	
	public void showMe(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		EPanel ep = new EPanel();
		ep.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		zoomSlider = new ESlider(20, 450, 100);
		zoomSlider.addChangeListener(new MyChangeListener("zoom"));
		
		ELabel label = new ELabel("Zoom Level: ");
		
		ep.add(label);
		ep.add(zoomSlider);
		this.add(ep);
		
	}
	
	int actionChangeIndex = 0;
	public class MyChangeListener implements ChangeListener{
		String myAction;
		public MyChangeListener(String str){
			this.myAction = str;
		}
		@Override
		public void stateChanged(ChangeEvent e) {
			if(myAction.equals("zoom")){
				
				
				
				setZoom();
			}
			
		}
	}
	
	public void setZoom(){
		actionChangeIndex++;
		final int x = actionChangeIndex;
		new Thread() {
			public void run() {
				try {
			
			Thread.sleep(120);

			if(x == actionChangeIndex){
				parent.canvas.zoom(zoomSlider.getValue());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}}}.start();
	}
	
	
}
