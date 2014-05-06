package aniExtraGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.animation.shop.Main;

public class SettingsPanel extends EInternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * user config indices
	 * 0 = workspace folder
	 * 1 = language
	 * 2 = cachesize
	 * 3 = theme
	 * 4 = layout
	 * 
	 */
	Main parent;
	
		public SettingsPanel(Main parent){
			
			this.parent=parent;
			
			showMe();
		}
		
		
		ETabbedPane maintabbedPane;
		ESlider cacheSizeSlider;
		EPanel basicPanel;
		
		public void showMe(){
			maintabbedPane = new ETabbedPane();
			basicPanel = new EPanel();
			
			EPanel ep = new EPanel();
			ep.setLayout(new FlowLayout(FlowLayout.LEADING));
			
			cacheSizeSlider = new ESlider(0, 1024,parent.CACHEMAX);
			cacheSizeSlider.addChangeListener(new MyChangeListener("cache"));
			
			ELabel label = new ELabel("Cache Size:");
			
			ep.add(label);
			ep.add(cacheSizeSlider);
			basicPanel.add(ep);
			

			ep = new EPanel();
			ep.setLayout(new FlowLayout(FlowLayout.LEADING));
			
			
			maintabbedPane.addTab("General",basicPanel);
			this.add(maintabbedPane);
			this.setPreferredSize(new Dimension(320,400));
			
			
			
		}
		
		int changeActionCount=0;
		
		public class  MyChangeListener implements ChangeListener{
			String actionName;
			public MyChangeListener(String str){
				this.actionName = str;
				
			}

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if(actionName.equals("cache")){
					parent.CACHEMAX = cacheSizeSlider.getValue();
				}

				final int x = changeActionCount;
				saveConfigFile(x);
				
			}
			
			
		}
		
		public void saveConfigFile(int x){
		
			try {
				Thread.sleep(120);
				if(x==changeActionCount){
					parent.userConfig[0] = parent.workspaceFolder;
					parent.userConfig[1] = parent.language;
					parent.userConfig[2] = ""+parent.CACHEMAX;
					parent.userConfig[3] = ""+parent.THEME;
					parent.userConfig[4] = ""+parent.LAYOUT;
					
					
					parent.saveUserConfig();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
