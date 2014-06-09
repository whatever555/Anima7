package aniExtraGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.animation.shop.Main;
import com.animation.shop.TButton;
import com.animation.shop.basicPapplet;

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
		ELabel workspaceFolderLabel;
		EComboBox themeList;
		 EComboBox layoutList;
		 
		 public String[] getThemeList(){
				String name = "data/themes/list";

				basicPapplet bp = new basicPapplet();

				return bp.loadStrings(name);
		 }
		 
		public void showMe(){
			Font f = new Font("Verdana", 8,8);
			
			
			
			
			this.setClosable(true); 
			//tlFrame.setIconifiable(true); 
			this.setResizable(true);
			this.setDefaultCloseOperation(1);
			
			maintabbedPane = new ETabbedPane();
			basicPanel = new EPanel();
			basicPanel.setLayout(new BoxLayout(basicPanel,BoxLayout.Y_AXIS));
			EPanel ep = new EPanel();
			ep.setLayout(new FlowLayout(FlowLayout.LEADING));
			ep.setMaximumSize(new Dimension(400,32));
			cacheSizeSlider = new ESlider(0, 1024,parent.CACHEMAX);
			cacheSizeSlider.addChangeListener(new MyChangeListener("cache"));

			cacheSizeSlider.setMajorTickSpacing(250);
			cacheSizeSlider.setMinorTickSpacing(25);
			cacheSizeSlider.setPaintLabels(true);
			cacheSizeSlider.setFont(f);
			ELabel label = new ELabel("Cache Size:");
			
			ep.add(label);
			ep.add(cacheSizeSlider);
			basicPanel.add(ep);
			

			ep = new EPanel();
			ep.setLayout(new FlowLayout(FlowLayout.LEADING));
			ep.setMaximumSize(new Dimension(400,32));
			
			label= new ELabel("Warning: Setting cache too high can cause crashes");
			ep.add(label);
			label.setFont(f);

			basicPanel.add(ep);
			
			ep = new EPanel();
			ep.setLayout(new FlowLayout(FlowLayout.LEADING));
			ep.setMaximumSize(new Dimension(400,32));
			
			label= new ELabel("Workspace Folder");
			ep.add(label);

			basicPanel.add(ep);
			
			ep = new EPanel();
			ep.setLayout(new FlowLayout(FlowLayout.LEADING));
			ep.setMaximumSize(new Dimension(400,32));
			workspaceFolderLabel = new ELabel(parent.workspaceFolder);
			ep.add(workspaceFolderLabel);
			workspaceFolderLabel.setFont(f);
			TButton button = new TButton("Change..");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String tmp = parent.getFolderPath(parent.workspaceFolder);
					parent.pout("tmpd string: "+tmp);
					if (tmp != null) {
						if (tmp.length() > 1) {
							parent.workspaceFolder = tmp;

						}
					}
					changeActionCount++;
					final int x = changeActionCount;
					
					saveConfigFile(x);
				}
			});
			button.setFont(f);
			ep.add(button);
			basicPanel.add(ep);
			
			
			ep = new EPanel();
			ep.setLayout(new FlowLayout(FlowLayout.LEADING));
			ep.setMaximumSize(new Dimension(400,32));
			
			label= new ELabel("THEME [requires restart] ");
			ELabel l2 = new ELabel(" ("+parent.THEME+")");
			l2.setFont(parent.smallFont);
			
			ep.add(label);
			ep.add(l2);

			basicPanel.add(ep);
			
			ep = new EPanel();
			ep.setLayout(new FlowLayout(FlowLayout.LEADING));
			ep.setMaximumSize(new Dimension(400,32));
			
		
			String[] opStrings = getThemeList();
			themeList = new EComboBox(opStrings);
			themeList.setFont(parent.smallFont);
			themeList.addActionListener(new MyActionListener("theme"));
			
			ep.add(themeList);
			basicPanel.add(ep);
			
			String[] opStrings2 = {"Classic","Artist","Basic"};
			layoutList = new EComboBox(opStrings2);
			layoutList.setFont(f);
			
			maintabbedPane.addTab("General",basicPanel);
			this.add(maintabbedPane);
			this.setPreferredSize(new Dimension(420,400));
			updateMe();
			
			
		}
		
		public void updateMe(){
			workspaceFolderLabel.setText(parent.workspaceFolder);
		}
		
		int changeActionCount=0;
		
		
		  private class MyActionListener implements ActionListener {

		        String myActionName;

		        public MyActionListener(String actName) {
		           this.myActionName = actName;
		        }
		        

		        public void actionPerformed(ActionEvent e) {
		        	if(myActionName.equals("theme")){
		        		parent.THEME = (String) themeList.getSelectedItem();
		        	
		        		parent.setUITheme();
		        		  try
		                  {
		                     //UIManager.setLookAndFeel(parent.frame);
		                     SwingUtilities.updateComponentTreeUI(parent.frame);
		                  }
		                  catch (Exception e1)
		                  {
		                     e1.printStackTrace();
		                  }
		        			parent.setUIOnExtras();
		        		parent.frame.revalidate();
		        		parent.frame.repaint();

		        		parent.timeline.colorMePink();
		        	}
		        	
		        	changeActionCount++;
					final int x = changeActionCount;
				
					saveConfigFile(x);
					
		        }
		        }
		  
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

				changeActionCount++;
				final int x = changeActionCount;
			
				saveConfigFile(x);
				
			}
			
			
		}
		
		public void saveConfigFile(int x){
		
			try {
				Thread.sleep(60);
				if(x==changeActionCount){
					parent.userConfig[0] = parent.workspaceFolder;
					parent.userConfig[1] = parent.language;
					parent.userConfig[2] = ""+parent.CACHEMAX;
					parent.userConfig[3] = ""+parent.THEME;
					parent.userConfig[4] = ""+parent.LAYOUT;
					
					
					parent.saveUserConfig();
					updateMe();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
