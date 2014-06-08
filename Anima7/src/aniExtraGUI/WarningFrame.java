package aniExtraGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;


import com.animation.shop.Main;
import com.animation.shop.TButton;

public class WarningFrame extends EInternalFrame {

	Main parent;
	ELabel warningTitle;
	public ELabel warningBody;
	public String linkValue="";
	public TButton linkBut;
	EPanel mainPanel;
	public WarningFrame(Main parent){
		this.parent=parent;
		showMe();
	}
	
	public void showMe(){
		 mainPanel=new EPanel();
		 mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		 this.parent=parent;
		 this.setAlignmentX( Component.LEFT_ALIGNMENT );
			this.setLayout(new BorderLayout());
			this.setClosable(true);
			this.setResizable(true);
			this.setDefaultCloseOperation(1);
			this.setMinimumSize(new Dimension(300,500));
			this.setPreferredSize(new Dimension(300,500));
		
		warningBody=new ELabel("");
		warningTitle=new ELabel("");
		linkBut=new TButton("");
		mainPanel.add(warningBody);
		mainPanel.add(linkBut);
		this.add(mainPanel);

		linkBut.addActionListener(new MyActionListener());
		
	}
	
	
	 private class MyActionListener implements ActionListener {


	        public MyActionListener() {
	        }
	        

	        public void actionPerformed(ActionEvent e) {
	        	URI link = null;
				try {
					link = new URI(linkValue);
				} catch (URISyntaxException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
	        		if (Desktop.isDesktopSupported()) {
	        		      try {
	        		        Desktop.getDesktop().browse(link);
	        		      } catch (IOException e1) { /* TODO: error handling */ }
	        		    } else { /* TODO: error handling */ }
	        	
	        	
	        	
				
	        }
	        }

}
