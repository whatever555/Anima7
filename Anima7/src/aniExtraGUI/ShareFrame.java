package aniExtraGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;

import aniExports.UploadFile;

import com.animation.shop.Main;
import com.animation.shop.TButton;

public class ShareFrame extends EInternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Main parent;
	ELabel message;
	
	public ShareFrame(Main parent){
		this.parent=parent;
		showMe();
		update();
	}
	
	ETabbedPane maintabbedPane;
	EPanel basicPanel;
	public void showMe(){

		this.setClosable(true); 
		//tlFrame.setIconifiable(true); 
		this.setResizable(true);
		this.setDefaultCloseOperation(1);
		

		
		maintabbedPane = new ETabbedPane();
		basicPanel = new EPanel();
		basicPanel.setLayout(new BoxLayout(basicPanel,BoxLayout.Y_AXIS));
	
		
		
		
		
		maintabbedPane.addTab("Options",basicPanel);
		this.add(maintabbedPane);
		this.setPreferredSize(new Dimension(420,400));
		
	}
	
	public void update(){
		if(basicPanel!=null)
		basicPanel.removeAll();
		
		boolean showWarning=true;
		for(int i=0;i<parent.canvas.shareFilePaths.size();i++){
			File f = new File(parent.canvas.shareFilePaths.get(i)+".gif");
			if(f.exists()){
				showWarning=false;
				EPanel ep = new EPanel();
				ep.setLayout(new FlowLayout(FlowLayout.LEADING));
				ep.setMaximumSize(new Dimension(400,32));
				ELabel label = new ELabel(f.getName()+": ");
				ep.add(label);
				TButton fileButton = new TButton("Upload To Share");
				fileButton.addActionListener(new MyActionListener("share",parent.canvas.shareFilePaths.get(i)+".gif"));
				ep.add(fileButton);
				basicPanel.add(ep);
				
			}
			
		}
		if(showWarning == true){
			EPanel ep = new EPanel();
			ep.setLayout(new FlowLayout(FlowLayout.LEADING));
			ep.setMaximumSize(new Dimension(400,32));
			ELabel warningLabel = new ELabel("No Files to share. You must first export an animated gif");
			ep.add(warningLabel);
			basicPanel.add(ep);
		}
		

		message = new ELabel("");
		basicPanel.add(message);
		repaint();
		revalidate();
	}
	
	
	  private class MyActionListener implements ActionListener {

	        String myActionName;String filePath;

	        public MyActionListener(String actName,String filePath) {
	           this.myActionName = actName;
	           this.filePath=filePath;
	        }
	        

	        public void actionPerformed(ActionEvent e) {
	        	if(myActionName.equals("share")){
	        		UploadFile uploadFile = new UploadFile(filePath);
	        		String code = uploadFile.uploadFileAction();
	        		message.setText(code);
	        		
	        	}
			
				
	        }
	        }
}
