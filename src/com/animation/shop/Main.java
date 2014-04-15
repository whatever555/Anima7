/**
 * 
 */
package com.animation.shop;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import processing.core.PImage;
import aniExtraGUI.EInternalFrame;
import aniExtraGUI.EPanel;
import aniExtraGUI.EScrollPane;
import aniExtraGUI.LayerOptionsFrame;
import aniFilters.FilterFrame;
import listeners.TimelineButtonActionListener;
/**
 * @author eddie
 * 
 */
public class Main {
	
	//TODO LIST
	//MAKE SAVING AND LOADING MORE ELEGANT [NO MISSING FILES] CLEANING ETC
	//SIMPLE BRUSHES
	//Clean ICONS
	//DRAG / MOVE KEYS
	//fix paste
	// import images exceptions // stageholders
	//rotate transforms
	//add cut feature
	//dashed lines on select rect
	//ellipse + pologon select
	//dashed/resizing ink
	//layer properties (blending options)
	//TWEENS + expand drawing area larger than stage +ZOOM
	// CURSORS
	// AUDIO
	// ERASER (done as polygon select maybe?)
	// Styling and Skins
	// fix undos
	// Workspace memory
	// order algorithm for JFrames/layout
	//tidy copy paste etc
	public boolean LOADED=false;
	int layerIndex = 0;
	int timelineButtonWidth;
	int timelineButtonHeight;
	int screenWidth;// = Toolkit.getDefaultToolkit().getScreenSize().width;
	int screenHeight;// = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	boolean pasting = false;
	boolean ISLOADED = false;

	String language="EN";
	boolean SAVEDTODISK=true;
	int rotationOfTransformingBlock=0;
	
	ArrayList<String> layerNames = new ArrayList<String>();
	String currentTool = "brush";

	ArrayList<Color> PREVCOLS;

	boolean loopPreview=false;
	boolean playPreviewBool = false;
	int FPS = 10;
	int lastFrame = 0;
	int onionLeft=0;
	int onionRight=0;
	Font smallFont = new Font("Verdana", Font.ITALIC, 8);
	
	int MAXLAYERS = 5;
	int MAXFRAMES = 120;
	public int CURRENTFRAME = 0;
	public int CURRENTLAYER = 0;
	int CANVASWIDTH = 620;
	int CANVASHEIGHT = 400;

	Color PENCOLOR = new Color(0, 0, 0);
	int PENSIZE = 15;
	int PENALPHA = 100;
	int FEATHERSIZE = 0;
	int ROUNDCORNERSIZE = 0;

	int fillInaccuracy=12;
	int selectInaccuracy=12;
	// ## UNDO REDO VARS
	 int MAXUNDOS = 20;
	ArrayList<String> ACTIONTYPE = new ArrayList<String>();
	int CHANGECOUNT = 0;
	int LASTCHANGEINDEX = 0;
	int currentActionIndex = 0;
	ArrayList<PImage> historicImages = new ArrayList<PImage>();
	ArrayList<SimpleRow> historicChanges = new ArrayList<SimpleRow>();
	boolean UNDOKEYCHECK = false;
	// ##

	
	
	ArrayList<PImage> cachedImages = new ArrayList<PImage>();
	
	ArrayList<String> cachedImagesNames = new ArrayList<String>();
	int CACHEMAX = 50;
	

	//translations.put("Key Frame" , "");
	Map<String, String> translations = new HashMap<String, String>();
	
	
	int frameIsEmpty = 0, frameIsEmptySelected = 1, frameIsKey = 2,
			frameIsKeySelected = 3;

	final JColorChooser jcc = new JColorChooser();
	ColorSelectionModel jccModel = jcc.getSelectionModel();
	ChangeListener changeListener;

	
	public JLayeredPane mainPanel;
	
	PreviousColours prevColPanel;

	
	public Canvas canvas;
	EScrollPane canvasSc;
	PenOptions penOps;
	History historyPanel;
public TimelineButtonActionListener timelineButtonActionListener;
	public TimelineSwing timeline;
	static EInternalFrame tlFrame;
TimelineControls timelineControls;

	EPanel messagePanel = new EPanel();

	String fileConfigString="";
	
	EScrollPane scMain;

	String workspaceFolder = null;
	
	   static String loadFile;
   JLabel messageTextArea;
	boolean CTRL = false;
	boolean SHIFT = false;
	static JFrame frame = new JFrame();
	
	
	public void initVars(){
		timelineButtonActionListener = new TimelineButtonActionListener(this);
		initTranslations();
		cachedImagesNames=new ArrayList<String>();
		cachedImages=new ArrayList<PImage>();
		CTRL=false;
		timelineButtonWidth=10;
		timelineButtonHeight=20;
		layerIndex=0;
		
	}
	String[] EN_TRANS;
	String fontName = "Verdana";
	public void initTranslations(){
		if(language.equals("CHI") ){
			fontName="MS Song";
			
		}else
			if(language.equals("JP")){
				fontName="MS Mincho";
			}
		
		UIManager.put("OptionPane.font", new Font(fontName, Font.BOLD, 12));
		UIManager.put("Label.font", new Font(fontName, Font.BOLD, 12));
		UIManager.put("Panel.font", new Font(fontName, Font.BOLD, 12));
		UIManager.put("Button.font", new Font(fontName, Font.BOLD, 12));
		UIManager.put("Slider.font", new Font(fontName, Font.BOLD, 12));
		UIManager.put("Spinner.font", new Font(fontName, Font.BOLD, 12));
		UIManager.put("ComboBox.font", new Font(fontName, Font.BOLD, 10));
		UIManager.put("InternalFrame.font", new Font(fontName, Font.BOLD, 10));
		UIManager.put("TextField.font", new Font(fontName, Font.BOLD, 10));
		UIManager.put("InternalFrame.titleFont", new Font(fontName, Font.BOLD, 10));

		basicPapplet bp = new basicPapplet();
		
		EN_TRANS = bp.loadStrings("data/translations/EN.txt");
		
		String[] transTemp = EN_TRANS;
		transTemp=bp.loadStrings("data/translations/"+language+".txt");
			
		bp=null;
		
		for(int i=0;i<EN_TRANS.length;i++){
translations.put(EN_TRANS[i] , transTemp[i]);
		}
		
	}
	
	public String translate(String str){
		if(translations.get(str)!=null)
			return translations.get(str);
		return str;
	}
	public static void main(String[] args) {
		
		
		
		UIManager.put("Button.font", new Font("Verdana", Font.BOLD, 10));
		UIManager.put("Label.font", new Font("Verdana", Font.BOLD, 10));
		UIManager.put("Panel.font", new Font("Verdana", Font.BOLD, 10));
		UIManager.put("ComboBox.font", new Font("Verdana", Font.BOLD, 10));
		UIManager.put("InternalFrame.font", new Font("Verdana", Font.BOLD, 10));
		UIManager.put("Spinner.font", new Font("Verdana", Font.BOLD, 10));
		UIManager.put("TextField.font", new Font("Verdana", Font.BOLD, 10));
		
		
		UIManager.put("OptionPane.font", new Font("Verdana", Font.BOLD, 12));
		UIManager.put("OptionPane.foreground", new Color(202,202,202));
		UIManager.put("OptionPane.background", new Color(67,67,67));
		UIManager.put("OptionPane.border", null);
		
		UIManager.put("TabbedPane.tabAreaBackground", new Color(67,67,67));
		UIManager.put("TabbedPane.contentAreaColor ",new Color(67,67,67));
		UIManager.put("TabbedPane.selected", (new Color(37,37,37)));
		UIManager.put("TabbedPane.background", (new Color(57,57,57)));
		UIManager.put("TabbedPane.border", (null));
		UIManager.put("TabbedPane.contentBorderInsets", new Insets(0,0,0,0));
		UIManager.put("TabbedPane.tabsOverlapBorder", true);
		UIManager.put("TabbedPane.borderColor", new Color(67,67,67));
		UIManager.put("TabbedPane.darkShadow", new Color(67,67,67));
		UIManager.put("TabbedPane.light", new Color(67,67,67));
		UIManager.put("TabbedPane.highlight", new Color(67,67,67));
		UIManager.put("TabbedPane.focus", new Color(67,67,67));
		UIManager.put("TabbedPane.unselectedBackground", new Color(67,67,67));
		UIManager.put("TabbedPane.selectHighlight", new Color(67,67,67));
		UIManager.put("TabbedPane.tabAreaBackground", new Color(67,67,67));
		UIManager.put("TabbedPane.borderHightlightColor", new Color(67,67,67));
		
		
		UIManager.put("ScrollBar.trackHighlightForeground", (new Color(157,157,157))); 
		UIManager.put("scrollbar", (new Color(57,57,157))); 
		UIManager.put("ScrollBar.thumb", new ColorUIResource(new Color(157,157,157))); 
		UIManager.put("ScrollBar.thumbHeight", 2); 
		UIManager.put("ScrollBar.background", (new Color(57,57,57)));

		UIManager.put("InternalFrame.activeTitleBackground", new ColorUIResource(new Color(57,57,57)));
		UIManager.put("InternalFrame.inactiveTitleBackground", new ColorUIResource(new Color(57,57,57)));
		UIManager.put("InternalFrame.activeTitleForeground", new ColorUIResource(new Color(202,202,202)));
		UIManager.put("InternalFrame.inactiveTitleForeground", new ColorUIResource(new Color(202,202,202)));
		UIManager.put("InternalFrame.titleFont", new FontUIResource(new Font("Verdana",Font.BOLD,10)));
		
		//	//	UIManager.put("InternalFrame.paletteCloseIcon", new IconUIResource(new Font("Verdana",Font.BOLD,10)));
		
		
		if(args.length > 0) {
			loadFile = (args[0]);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			try {
					//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					//JFrame.setDefaultLookAndFeelDecorated(true);
					new Main();
				

					
				
					frame.addComponentListener(new ComponentListener() 
					{  
					        public void componentResized(ComponentEvent evt) {
					        	// tlFrame.repaint();
					        }

							@Override
							public void componentHidden(ComponentEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void componentMoved(ComponentEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void componentShown(ComponentEvent arg0) {
								// TODO Auto-generated method stub
								
							}
					});
					frame.addWindowListener(new WindowAdapter() {
						
						public void windowClosed(WindowEvent e) {
							System.exit(0);
						}
						public void windowClosing(WindowEvent we) {
							System.exit(0);
						}
						public void windowOpened(WindowEvent arg0) {

						}
						
					
					});

				

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	
	public void setCursor(String cursorName){
		Image image ;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Point hotSpot; 

		hotSpot = new Point(23,23); 
		
		if(cursorName.equals("dropper"))
			hotSpot = new Point(23,0);
		if(cursorName.equals("bucket"))
			hotSpot = new Point(23,12);
		
		try {
			image = ImageIO.read(getClass().getResource("/data/icons/tools/"+cursorName+".png"));
			Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "Pencil");  
			canvas.setCursor(cursor);  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	String tmpName = ""+(System.currentTimeMillis() / 1000L);
	String fileName=null;
	String fileShortName="";
	String filePath;
	boolean changesSinceLastSave = false;
	String timeOfLastSave;
	String extension=".anima";
	
	
	 File file;
	 
	 
	 public void importImage(String imagePath){
		 if(!imagePath.equals("")){
			 canvas.clipBoard = canvas.loadImage(imagePath).get();
			 canvas.pasteFromClipBoard(false,"Paste");
		 }
		 
	 }
	 public String getFilePath(){
		 final JFileChooser fc = new JFileChooser();
			
			//In response to a button click:
			int returnVal = fc.showOpenDialog(mainPanel);
			
			  if (returnVal == JFileChooser.APPROVE_OPTION) {
		            file = fc.getSelectedFile();
		            return file.getPath();
		            
		        } else {
		        	System.out.println("Open command cancelled by user.");
		        }
			return "";
	 }
	 
	public void saveNewFile(){
		
		//TODO: Delete non-conflicting records in folder if not new
		
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
		
		//In response to a button click:
		int returnVal = fc.showOpenDialog(mainPanel);
		
		  if (returnVal == JFileChooser.APPROVE_OPTION) {
	            file = fc.getSelectedFile();
	            fileName = file.getPath();
	            fileShortName=file.getName();
	            saveFile(file,false);
	        } else {
	        	System.out.println("Open command cancelled by user.");
	        }
		
	}
	
	public void saveInit(){
		if(fileName==null)
			saveNewFile();
		else
			saveFile(file,true);
	
	}
	
	public void saveFile(File file,boolean overwrite){
		saveData(file.getPath(),overwrite);
        fileConfigString+="BGCOL::"+canvas.bgColor+":::";
        fileConfigString+="FPS::"+FPS+":::";
        fileConfigString+="LASTFRAME::"+lastFrame+":::"; 
        
        fileConfigString+="MAXLAYERS::"+MAXLAYERS+":::";
        fileConfigString+="MAXFRAMES::"+MAXFRAMES+":::";
        fileConfigString+="CURRENTLAYER::"+CURRENTLAYER+":::";
        fileConfigString+="CURRENTFRAME::"+CURRENTFRAME+":::";
        
        fileConfigString+="LAYERS::";
        
        for(int i=0;i<timeline.layers.size();i++){
        	TimelineLayer tl = timeline.layers.get(i);
        	fileConfigString+="8q3b5R1ZMINDEX8q3b5R1ZM"+i+"8q3b5R1ZMz0ID8q3b5R1ZMz0"+tl.layerID+"8q3b5R1ZMz0MASK8q3b5R1ZMz0"+tl.isMask+"8q3b5R1ZMz0MASKOF8q3b5R1ZMz0"+tl.maskOf+"8q3b5R1ZMz0LABEL8q3b5R1ZMz0"+tl.layerName+"8q3b5R1ZMz0";
        }
        
        fileConfigString+=":::";
        
        fileConfigString+="KEYFRAMES::";
        for(int x=0;x<=lastFrame;x++)
        	for(int y=0;y<MAXLAYERS;y++){
        		if(timeline.layers.get(y).jbs.get(x).isKey){
        			fileConfigString+=y+"_"+x+"-";	
        		}
        	}
        		
        fileConfigString+=":::";
        
        fileConfigString+="FOLDER::"+file.getPath()+":::";
       
        
        saveText(fileConfigString,file.getPath()+extension);
	}
	
	public void forceRename(File source, File target) throws IOException
	{
	   if (target.exists()) target.delete();
	   source.renameTo(target);
	}
	
	public void saveData(String location,boolean overwrite){
		
		 File theDir = new File(location);
		 
		  // if the directory does not exist, create it
		 boolean result = true;
		  if (!theDir.exists()) {
		
		    result = theDir.mkdir();  
		  }else{
			  int selectedOption=1;
			  if(overwrite == false){
			   selectedOption = JOptionPane.showConfirmDialog(null, 
	                    translate("File Already Exists. Overwrite?"), 
	                    translate("Overwrite?"), 
	                    JOptionPane.YES_NO_OPTION); 
			  }
	if (selectedOption == JOptionPane.YES_OPTION||overwrite) {
	deleteDirectory(theDir);
	 result = theDir.mkdir(); 
	}
		  }
		     if(result) {    
		       for(int y=0;y<MAXLAYERS;y++)
		    	   for(int x=0;x<=lastFrame;x++)
		    		   if(timeline.layers.get(y).jbs.get(x).isKey)
		    			   canvas.copyImage(location+"/"+timeline.layers.get(y).layerID+"_"+x+".png", timeline.layers.get(y).layerID, x);
		       SAVEDTODISK=true;
		     }else{
		    	 JOptionPane.showMessageDialog(mainPanel, "Could Not Save");
		     }
		  
		  
		  
	}
	
public void saveText(String str,String txtFile){
    try {
        
        File newTextFile = new File(txtFile);

        FileWriter fw = new FileWriter(newTextFile);
        fw.write(str);
        fw.close();

    } catch (IOException iox) {
        //do stuff with exception
        iox.printStackTrace();
    }
}
	
	/*private void copyDataToDestination(String location,String fileName)
	{
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		InputStream stream = getClass().getResourceAsStream("/data/images/"+tmpName+fileName);
	    if (stream == null) {
	       System.out.println("Fuck"+"/data/images/"+tmpName+"/"+fileName);
	    }
	    OutputStream resStreamOut;
	    int readBytes;
	    byte[] buffer = new byte[4096];
	    resStreamOut=null;
	    try {
	        resStreamOut = new FileOutputStream(new File(location+"/"+fileName));
	        while ((readBytes = stream.read(buffer)) > 0) {
	            resStreamOut.write(buffer, 0, readBytes);
	        }
	    } catch (IOException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    } finally {
	        try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
				try {
					resStreamOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	    }
	}
*/


	public void setLoadedFile(String filePath){

		if(!SAVEDTODISK){
			int selectedOption = JOptionPane.showConfirmDialog(null, 
                    translate("Do you want to save changes to current file before closing?"), 
                    translate("Changes will be Discarded."), 
                    JOptionPane.YES_NO_OPTION); 
if (selectedOption == JOptionPane.YES_OPTION) {
	saveInit();
}
		}
		
		String[] strs = canvas.loadStrings(filePath);
		if(strs.length>0){
		
		
			// cleanLocalFolder(); 
			timeline.cleanOutTimeline();
			messageTextArea = new JLabel(translate("Loading")+" "+fileShortName);
			messagePanel.setVisible(true);
			 fileName = filePath;

			fileShortName=file.getName();

	    	setConfig(strs[0],file.getPath().replaceAll(extension,""));
		}
	}
	

	
	public void loadNewFile(){
		LOADED=false;
		
		setLoadedFile(getFilePath());

		System.out.println("THE END");
		LOADED=true;
	}
	public void setConfig(String line,String folderName){


		/* 
		for(int i=0;i<timeline.layers.size();i++){
		timeline.layers.get(i).setVisible(false);
		}
		*/
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		initVars();
	//	timeline.layers = new ArrayList<TimelineLayer>();
	


		
		 tmpName = ""+(System.currentTimeMillis() / 1000L)+"_loaded";
		 String[] configs = line.split(":::");
		/*  fileConfigString+="BGCOL::"+canvas.bgColor+":::";
	        fileConfigString+="FPS::"+FPS+":::";
	        fileConfigString+="FOLDER::"+file.getPath()+":::";
	        */

		  for(int i=0;i<configs.length;i++){
		if(line!=null){
	        String[] parts = configs[i].split("::");
	        if(parts.length>1)
	         	 if(parts[0].equals("FPS")){
		 	 	        	FPS = Integer.parseInt(parts[1]);
		 	 	        	timelineControls.fpsSpinber.setValue(FPS);
		 	 	        }else
	 	        	 if(parts[0].equals("MAXLAYERS")){
		 		        	MAXLAYERS = Integer.parseInt(parts[1]);
		 		        }else
		 		        	if(parts[0].equals("LASTFRAME")){
			 		        	lastFrame = Integer.parseInt(parts[1]);
			 		        }else
			 		        	 if(parts[0].equals("MAXFRAMES")){
			 		        		 if(lastFrame>MAXFRAMES || Integer.parseInt(parts[1])<MAXFRAMES){
			 		        			 MAXFRAMES	 = Integer.parseInt(parts[1]);
			 		        		timeline.setFramesLength();
			 		        		 }
			 		        	 }else
					 		        	 if(parts[0].equals("BGCOL")){
						 		        		canvas.bgColor = Integer.parseInt(parts[1]);
						 	 		        }else
							 		        	 if(parts[0].equals("CURRENTLAYER")){
								 		        		CURRENTLAYER = Integer.parseInt(parts[1]);
								 	 		        }else
									 		        	 if(parts[0].equals("CURRENTFRAME")){
										 		        		CURRENTFRAME = Integer.parseInt(parts[1]);
										 	 		        }else
        	 if(parts[0].equals("LAYERS")){
	        		String[] layerData = (parts[1].split("8q3b5R1ZMINDEX8q3b5R1ZM"));
	        		int zz=0;
	        		for(int z=0;z<layerData.length;z++){
	        			zz=z;
	        			if(layerData[z].length()>3){
	        				String key  ="8q3b5R1ZMz0";
	        				String[] dt = layerData[z].split(key);
	        				
	        					int id = Integer.parseInt(dt[2]);
	        					boolean mask = (dt[4].equals("true"));
	        					int maskof = Integer.parseInt(dt[6]);
	        					String label = dt[8];
	        					System.out.println("ADDING L "+dt[8]);
	        					if(z<timeline.layers.size()){
	        						timeline.layers.get(z).layerID=id;
	        						timeline.layers.get(z).layerName=label;
	        						timeline.layers.get(z).layerNameLabel.setText(label);
	        						timeline.layers.get(z).isMask = mask;
	        						if(mask){
	        							timeline.layers.get(z).maskOf = maskof;
	        						}
	        					
	        					}else{
	        					timeline.addNewLayer(id,mask,maskof,label);
	        					}
	        					
	        					
	        				
	        			}
	        		}
	        		for(int iz=zz+1;iz<timeline.layers.size();iz++){
	        			timeline.layers.get(iz).setVisible(false);
	        			timeline.layers.get(iz).deleteMe();
	        		}
 		        }else
 		        	 if(parts[0].equals("KEYFRAMES")){
 		 	        	String[] keys = (parts[1]).split("-");
 		 	        	for(int z=0;z<keys.length;z++){
 		 	        		if(keys[z].length()>1){
 		 	        			String[] vvs = keys[z].split("_");
 		 	        			if(vvs[0].length()>0){
 		 	        			int tempY = Integer.parseInt(vvs[0]);
 		 	        			int tempX = Integer.parseInt(vvs[1]);
 		 	        			timeline.layers.get(tempY).jbs.get(tempX).isKey=true;
 		 	        			timeline.layers.get(tempY).jbs.get(tempX).setBackground(timeline.active);
 		 	        			}
 		 	        		}
 		 	        	}
 		 	        	timelineControls.fpsSpinber.setValue(FPS);
 		 	        }else
	        	  if(parts[0].equals("FOLDER")){
	        	

	        		
	        		canvas.loadNewFile(parts[1],MAXLAYERS,MAXFRAMES);
	  	        }
		}
		}
		  
		
//loadApplication();

			messagePanel.setVisible(false);
			timeline.shiffleTable(CURRENTFRAME,CURRENTLAYER,0,true);
		
	}
	
	public static boolean deleteDirectory(File directory) {
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i<files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i]);
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    return(directory.delete());
	}
	
	public void cleanLocalFolder(){
		// Load the directory as a resource
		 
		  // Turn the resource into a File object
		  File file = null;
		  
		  for(int x=0;x<=lastFrame;x++){
				for(int y=0;y<MAXLAYERS;y++){
					if(timeline.layers.get(y).jbs.get(x).isKey){
					
						
						file=new File(workspaceFolder+"/images/"+tmpName+"/"+y+"_"+x+".png");
						file.delete();
					
					}
					
						
				}
				
			}
		  file = new File(workspaceFolder+"/images/"+tmpName);
		  file.delete();
	}
	
	public void loadApplication(){
		
		screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width-50;
		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height-50;
		frame.setSize(new Dimension(screenWidth,screenHeight));
		
		mainPanel = new JLayeredPane();
		mainPanel.setBackground(new Color(27,27,27));
		mainPanel.setOpaque(true);
		frame.setVisible(true);
		mainPanel.setVisible(true);
		
		EPanel messageHolder = new EPanel();
		
		messagePanel.setPreferredSize(new Dimension(screenWidth,screenHeight));
		messagePanel.setBackground(new Color(47,47,47));
		messagePanel.setBounds(0,0,screenWidth,screenHeight);
		messagePanel.setLayout(null);
		messageTextArea = new JLabel("Loading..");
		
		
	int fontSizeToUse=40;

		// Set the label's font size to the newly determined size.
		messageTextArea.setFont(new Font("Verdana", Font.PLAIN, fontSizeToUse));
		messageTextArea.setForeground(Color.white);
		messageHolder.setBackground(new Color(47,47,47));
		messageHolder.setBounds((screenWidth/2)-(400/2),200,400,200);
		messageHolder.add(messageTextArea);
		messagePanel.add(messageHolder);
		
		frame.getContentPane().add(messagePanel);
		// scMain.add(mainPanel);
		
		mainPanel.setSize(new Dimension(screenWidth,screenHeight));
		mainPanel.setPreferredSize(new Dimension(screenWidth,screenHeight));
		
		//frame.setLayout(null);
		//this.setVisible(true);
		PREVCOLS = new ArrayList<Color>();

	
		addHistoryPanel();
		
	
		addTimeline();
		canvasFrame.setVisible(true);
		
		mainPanel.setVisible(true);
		frame.setVisible(true);
		addCanvas();
		timeline.loadEmptyLayers();
		
		addTimelineControls();
		
		addPenOptions();
		addBrushOptions();
		// addColorPicker();
		// snooze("after color picker options");
		addPreviousColourList();

		addToolsBar();


		ISLOADED = true;
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		JMenuBar menuBar = new JMenuBar();
		//menuBar.setBackground(new Color(67,67,67));
		//menuBar.setForeground(new Color(227,227,227));
	
		frame.setJMenuBar(menuBar);
		addMenu(menuBar);
		
		
		
		 timeline.showMe();
		canvas.initImages(false);

		addFilterFrame();
		addLayerOptionsFrame();
		addActions();
		//this.pack();
		
		
		timeline.layers.get(canvas.getLayerIndex(CURRENTLAYER)).jbs.get(CURRENTFRAME).selected=true;
		
		
		mainPanel.setVisible(true);

		//frame.add(messagePanel,30,0);
		
		new Thread()
		{
		    public void run() {
		    	try {
					Thread.sleep(300);
			    	initShow();
			    	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    }
		}.start();
	}
	

	TopPanel topPanel;
	public void initShow(){
		
		
		
		
		screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width-50;
		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height-50;
		int extraX = 40;
		int extraY = 32;
		
		

		topPanel = new TopPanel(this);
		topPanel.setPreferredSize(new Dimension(screenWidth,30));
		topPanel.setMaximumSize(new Dimension(screenWidth,30));
		topPanel.setBounds(20,0,screenWidth,30);
		topPanel.setBrushOptions();

mainPanel.add(topPanel);


		
		prevColPanel.setBounds(0,0,20,screenHeight-20);
		
		canvasFrame.setBounds(20, extraY, CANVASWIDTH+extraX, CANVASHEIGHT+extraX);

		int tlHeight = screenHeight-(CANVASHEIGHT+extraY+45)-40;
		tlHeight=Math.max(tlHeight,120);
		tlFrame.setBounds(20,CANVASHEIGHT+extraY+45, (screenWidth-40), tlHeight);

		extraX+=25;
		
		int defaultPanelWidth = (screenWidth-(CANVASWIDTH+extraX)-40)/2;
		int defaultPanelHeight = 220;
		
		
		
		
			
		penOps.setBounds(CANVASWIDTH+extraX,extraY,defaultPanelWidth,defaultPanelHeight);
		
		BrushSelectionOps.setBounds(CANVASWIDTH+extraX,defaultPanelHeight+5+extraY,defaultPanelWidth,200);
		

		extraX+=5;
		timelineControls.setBounds((int) (CANVASWIDTH+extraX + (defaultPanelWidth)),defaultPanelHeight+5+extraY,
				(int) (defaultPanelWidth*.8), defaultPanelHeight);
		
		historyPanel.setBounds((int) (CANVASWIDTH+extraX + (defaultPanelWidth)),extraY,
				(int) (defaultPanelWidth*.8), defaultPanelHeight);

		extraX+=5;
		toolBar.setBounds((int) (CANVASWIDTH+extraX+(defaultPanelWidth*1.8)),extraY,(int) (defaultPanelWidth*.2),defaultPanelHeight*2);
		
		for(int i=0;i<filterFrames.size();i++){
		filterFrames.get(i).setPreferredSize(new Dimension(400,260));
		filterFrames.get(i).setBounds(200+i,200+i,400,300);
		filterFrames.get(i).setBackground(new Color(255,255,255));
		filterFrames.get(i).setVisible(false);
		}
		

		LOF.setBounds(100,100,300,500);

		LOF.setVisible(false);
		canvasFrame.setVisible(true);
		timelineControls.setVisible(true);
		tlFrame.setVisible(true);
		toolBar.setVisible(true);
		prevColPanel.setVisible(true);
		penOps.setVisible(true);
		BrushSelectionOps.setVisible(true);
		historyPanel.setVisible(true);
		historyPanel.update();
		
for(int i=0;i<filterFrames.size();i++)
mainPanel.add(filterFrames.get(i));
mainPanel.add(tlFrame);

		mainPanel.add(timelineControls);

		mainPanel.add(prevColPanel);
		mainPanel.add(canvasFrame);
		mainPanel.add(penOps);
		mainPanel.add(BrushSelectionOps);
		mainPanel.add(historyPanel);
		mainPanel.add(toolBar);
		EPanel bug_workaround = new EPanel();
		
mainPanel.add(bug_workaround);
bug_workaround.setVisible(false);
		
		
		messagePanel.setVisible(false);
		mainPanel.setPreferredSize(new Dimension(screenWidth,screenHeight));
		
		
		scMain = new EScrollPane();

		scMain.setPreferredSize(new Dimension(screenWidth,screenHeight));
		scMain.setBounds(0,0,screenWidth,screenHeight);
	
		scMain.setViewportView(mainPanel);
		frame.getContentPane().add(scMain);


		

		frame.setVisible(true);



	}
	String[] userConfig;
	public void loadUserConfig(){
		basicPapplet bp = new basicPapplet();
		String[] tmp = bp.loadStrings(".animaUserConfig.conf");
userConfig=new String[200];
if(tmp!=null)
		for(int i=0;i<tmp.length;i++){
			userConfig[i] = tmp[i];
		}
		tmp=null;
		bp=null;
	}
	
	
	public void saveUserConfig(){
		basicPapplet bp = new basicPapplet();
		bp.saveStrings(".animaUserConfig.conf",userConfig);

	}
	
	public String getWorkSpaceFolder(){
		return userConfig[0];
	}
	public void getLanguage(){
		if(userConfig[1]!=null)
			language=userConfig[1];
	}
	
	public String getFolderPath(){
		JFileChooser chooser;  
		chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle(translate("Select workspace folder"));
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		   
		    chooser.setAcceptAllFileFilterUsed(false);
		    //    
		    if (chooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) { 
		    
		     return chooser.getSelectedFile().getPath();
		      }
		    else {
		   return "";
		      }
		     }
	
	
	
	public Main() {
		loadUserConfig();
		getLanguage();
		workspaceFolder = getWorkSpaceFolder();
		if(workspaceFolder==null){
		
			workspaceFolder =System.getProperty("user.home");
			
			int selectedOption = JOptionPane.showConfirmDialog(null, 
                   translate("Your Workspace Folder is set to")+" "+workspaceFolder+". "+translate("Do you want to select a different folder?"), 
                    "Workspace", 
                    JOptionPane.YES_NO_OPTION); 
if (selectedOption == JOptionPane.YES_OPTION) {
String tmp = getFolderPath();
if(tmp!=null){
	if(tmp.length()>1){
		workspaceFolder=tmp;

	}
}}

userConfig[0]=workspaceFolder;
saveUserConfig();
			
		}
		
		canvasFrame = new EInternalFrame(translate("Canvas"));
		LOADED=false;
		initVars();
		loadApplication();
	
			if(loadFile!=null){
				setLoadedFile(loadFile);
			}
			LOADED=true;
			
	}

	public void addActions() {
		//frame.setFocusable(true);

		
		
		Action ctrlOffAction = new AbstractAction("CTRLOFF") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				CTRL = false;
			}
		};
		KeyStroke keyStroke = KeyStroke.getKeyStroke("released CONTROL");
		frame.getRootPane().getActionMap().put("CTRLOFF", ctrlOffAction);
		frame.getRootPane()
				.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(keyStroke, "CTRLOFF");
		
		Action ctrlOnAction = new AbstractAction("CTRLON") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				CTRL = true;
			}
		};
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL,InputEvent.CTRL_DOWN_MASK);
		frame.getRootPane().getActionMap().put("CTRLON", ctrlOnAction);
		frame.getRootPane()
				.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(keyStroke, "CTRLON");

		Action shiftOnAction = new AbstractAction("SHIFTON") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				SHIFT = true;
			}
		};
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT,InputEvent.SHIFT_DOWN_MASK);
		frame.getRootPane().getActionMap().put("SHIFTON", shiftOnAction);
		frame.getRootPane()
				.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(keyStroke, "SHIFTON");

		
		
		Action shiftOffAction = new AbstractAction("SHIFTOFF") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				SHIFT = false;
			}
		};
		keyStroke = KeyStroke.getKeyStroke("release SHIFT");
		frame.getRootPane().getActionMap().put("SHIFTOFF", shiftOffAction);
		frame.getRootPane()
				.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(keyStroke, "SHIFTOFF");
		

	
		
		
	}
	

	


	public void snooze(String message) {

		// System.out.println("sleeping "+message);
		try {
			Thread.sleep(10);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public void snooze(String message, int i) {

		// System.out.println("sleeping "+message);
		try {
			Thread.sleep(i);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public void layDownFrames(int hiddenLayer) {
		canvas.layDownFrames(hiddenLayer);
	}

	public void saveAction(int x, int y, String label) {
		canvas.saveAction(x, y, label);
	}

	ToolsBar toolBar;

	public void addToolsBar() {
		toolBar = new ToolsBar(this);
		toolBar.setVisible(false);
 
	}
	LayerOptionsFrame LOF;
	public void addLayerOptionsFrame(){
		LOF = new LayerOptionsFrame(this);
		mainPanel.add(LOF);
	}
	public ArrayList<FilterFrame> filterFrames = new ArrayList<FilterFrame>();
	public ArrayList<String> filterFrameNames=new ArrayList<String>();
	public void addFilterFrame(){


		filterFrames.add(new FilterFrame(this,"EFFECTS",0));
		filterFrameNames.add("EFFECTS");
		filterFrames.add(new FilterFrame(this,"BLUR",1));
		filterFrameNames.add("BLUR");
		filterFrames.add(new FilterFrame(this,"ARTISTIC",2));
		filterFrameNames.add("ARTISTIC");
		filterFrames.add(new FilterFrame(this,"SHADING",3));
		filterFrameNames.add("SHADING");
		
	}
	public int getFilterFrameIndex(String str){
		for(int i=0;i<filterFrames.size();i++){
			if(filterFrameNames.get(i).equals(str)){
				return i;
			}
		}
		return 0;
	}

	public void addPreviousColourList() {
	
		
		prevColPanel = new PreviousColours(this);
		prevColPanel.setBorder(null);
		
	}

	public void addPenOptions() {

		penOps = new PenOptions(this);

	}

	BrushSelection BrushSelectionOps;

	public void addBrushOptions() {

		BrushSelectionOps = new BrushSelection(this);

	}

	public void addHistoryPanel() {
	
		
		historyPanel = new History(this);
		
		//mainPanel.add(historyPanel,2);
	}

	public void addColorPicker() {
		changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				// sketch.displayCanvas(); todo
			}
		};
		jccModel.addChangeListener(changeListener);
	}

	public void addTimelineControls(){
		timelineControls = new TimelineControls(this);
	}
	public void addTimeline() {
		tlFrame = new EInternalFrame(translate("Timeline"));
		 timeline = new TimelineSwing(MAXLAYERS, MAXFRAMES, this);
		
		EScrollPane timelineScrollPane = new EScrollPane();
		//timelineScrollPane.add(timeline);

		timelineScrollPane.setViewportView(timeline);
		//timeline.setFocusable(false);

		tlFrame.setClosable(true);
		tlFrame.setResizable(true);

		tlFrame.setDefaultCloseOperation(1);

		tlFrame.setVisible(true);

		
		tlFrame.add(timelineScrollPane);


		


	}

	public EInternalFrame canvasFrame ;
	


	public void addCanvas() {
		canvas = new Canvas(CANVASWIDTH, CANVASHEIGHT, this);


	
		
		canvas.setVisible(true);
		canvas.init();

		EPanel canvasPanel = new EPanel();
		canvasPanel.setBounds(0, 0, CANVASWIDTH, CANVASHEIGHT);
		canvasPanel.setVisible(true);

		
		canvas.setBounds(0, 0, CANVASWIDTH, CANVASHEIGHT);

		canvasPanel.add(canvas);
		canvasFrame.setVisible(false);
canvasPanel.setBackground(new Color(67,67,67));
		//canvasFrame.setClosable(true);
		canvasFrame.setResizable(true);
		canvasFrame.setDefaultCloseOperation(1);
		

		
		canvasSc = new EScrollPane();

		canvasSc.setBounds(50, 10, CANVASWIDTH, CANVASHEIGHT);
		
		canvasSc.setVisible(true);
		canvasSc.setViewportView(canvasPanel);
		canvasFrame.add(canvasSc);

		
		
	
		
		
		//mainPanel.add(canvasFrame,1);
		canvas.setFocusable(false);
		canvas.repaint();

	}

	JMenuItem mntmSave;
	JMenuItem mnImportImage;
	JMenuItem mnSaveAs;
	public void addMenu(JMenuBar menuBar) {
		KeyStroke tmpKey;
		
		final JMenu mnFile = new JMenu(translate("File"));
		menuBar.add(mnFile);

		final JMenuItem mntmNew = new JMenuItem(translate("New")+"..");
		mnFile.add(mntmNew);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
		mntmNew.setAccelerator(tmpKey);
		
		final JMenuItem mntmOpen = new JMenuItem(translate("Open")+"..");
		mnFile.add(mntmOpen);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
		mntmOpen.setAccelerator(tmpKey);

		mntmSave = new JMenuItem(translate("Save.."));
		mnFile.add(mntmSave);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
		mntmSave.setAccelerator(tmpKey);
		
		mnSaveAs = new JMenuItem(translate("Save As.."));
		mnFile.add(mnSaveAs);
		
		mnFile.addSeparator();
		
		mnImportImage = new JMenuItem(translate("Import Image To Stage.."));
		mnFile.add(mnImportImage);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK);
		mnImportImage.setAccelerator(tmpKey);
		
		
		mnFile.addSeparator();
		
		JMenu mntmExport = new JMenu(translate("Export to .."));
		mnFile.add(mntmExport);
		

		JMenuItem mnExportSWF = new JMenuItem(translate("SWF"));
		mntmExport.add(mnExportSWF);

		JMenuItem mnExportAnimatedGIF = new JMenuItem(translate("Animated GIF"));
		mntmExport.add(mnExportAnimatedGIF);

		JMenuItem mnExportPNG = new JMenuItem("PNG "+translate("Images"));
		mntmExport.add(mnExportPNG);

		JMenuItem mnExportGIF = new JMenuItem("GIF "+translate("Images"));
		mntmExport.add(mnExportGIF);

		JMenuItem mnExportJPG = new JMenuItem("JPG "+translate("Images"));
		mntmExport.add(mnExportJPG);

		JMenuItem mnExportTFF = new JMenuItem("TIFF "+translate("Images"));
		mntmExport.add(mnExportTFF);
		
		mnFile.addSeparator();
		
		JMenuItem mnExit = new JMenuItem(translate("Exit"));
		mnFile.add(mnExit);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK);
		mnExit.setAccelerator(tmpKey);

		JMenu mnLayers = new JMenu(translate("Layers"));
		menuBar.add(mnLayers);

		JMenuItem mntmAddLayer = new JMenuItem(translate("Add Layer"));
		mnLayers.add(mntmAddLayer);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK);
		mntmAddLayer.setAccelerator(tmpKey);
		
		JMenuItem mntmAddMask = new JMenuItem(translate("Add Mask To Current Layer"));
		mnLayers.add(mntmAddMask);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK);
		mntmAddMask.setAccelerator(tmpKey);

		JMenuItem mntmDeleteLayer = new JMenuItem(translate("Delete Current Layer"));
		mnLayers.add(mntmDeleteLayer);
		

		JMenuItem mntmToggleKey = new JMenuItem(translate("Toggle Keyframe"));
		mnLayers.add(mntmToggleKey);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_DOWN_MASK);
		mntmToggleKey.setAccelerator(tmpKey);
		
		JMenuItem mntmMoveRightOne = new JMenuItem(translate("Go Forward 1 Frame"));
		mnLayers.add(mntmMoveRightOne);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,InputEvent.CTRL_DOWN_MASK);
		mntmMoveRightOne.setAccelerator(tmpKey);
		
		JMenuItem mntmMoveLeftOne = new JMenuItem(translate("Go Back 1 Frame"));
		mnLayers.add(mntmMoveLeftOne);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,InputEvent.CTRL_DOWN_MASK);
		mntmMoveLeftOne.setAccelerator(tmpKey);

		JMenuItem mntmMoveDownOne = new JMenuItem(translate("Move Down 1 Layer"));
		mnLayers.add(mntmMoveDownOne);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,InputEvent.CTRL_DOWN_MASK);
		mntmMoveDownOne.setAccelerator(tmpKey);
		
		JMenuItem mntmMoveUpOne = new JMenuItem(translate("Move Up 1 Layer"));
		mnLayers.add(mntmMoveUpOne);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_UP,InputEvent.CTRL_DOWN_MASK);
		mntmMoveUpOne.setAccelerator(tmpKey);

		JMenuItem mntmAppendFrames = new JMenuItem(translate("Append 40 Frames"));
		mnLayers.add(mntmAppendFrames);
		
		JMenuItem mntmRemoveFrames = new JMenuItem(translate("Remove 40 Frames"));
		mnLayers.add(mntmRemoveFrames);
		
		JMenu mnEdit = new JMenu(translate("Edit"));
		menuBar.add(mnEdit);

		JMenuItem mntmUndo = new JMenuItem(translate("Undo"));
		mnEdit.add(mntmUndo);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK);
		mntmUndo.setAccelerator(tmpKey);
		
		JMenuItem mntmRedo = new JMenuItem(translate("Redo"));
		mnEdit.add(mntmRedo);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK);
		mntmRedo.setAccelerator(tmpKey);
		
		JMenuItem mntmCut = new  JMenuItem(translate("Cut"));
		mnEdit.add(mntmCut);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK);
		mntmCut.setAccelerator(tmpKey);

		JMenuItem mntmCopy = new  JMenuItem(translate("Copy"));
		mnEdit.add(mntmCopy);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK);
		mntmCopy.setAccelerator(tmpKey);

		JMenuItem mntmPaste = new JMenuItem(translate("Paste"));
		mnEdit.add(mntmPaste);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK);
		mntmPaste.setAccelerator(tmpKey);
		
		JMenuItem mntmClear = new JMenuItem(translate("Clear"));
		mnEdit.add(mntmClear);

		final JMenu mnView = new JMenu(translate("View"));
		menuBar.add(mnView);

		final JMenuItem mntmToggleTimeline = new JMenuItem(translate("Hide Timeline"));
		mnView.add(mntmToggleTimeline);

		final JMenuItem mntmToggleTools = new JMenuItem(translate("Hide Tools"));
		mnView.add(mntmToggleTools);

		JMenuItem mntmToggleBrush = new JMenuItem(translate("Show Brush Options"));
		mnView.add(mntmToggleBrush);

		JMenuItem mntmToggleOnion = new JMenuItem(translate("Toggle")+" Onion Skin");
		mnView.add(mntmToggleOnion);

		JMenuItem mntmChangeBGColor = new JMenuItem(translate("Change Background Color.."));
		mnView.add(mntmChangeBGColor);

	
		
		
		final JMenu mnFilters = new JMenu(translate("Filters"));
		menuBar.add(mnFilters);

	//mnFile.addSeparator();
		
		JMenu mntmBlur = new JMenu(translate("Blur .."));
		mnFilters.add(mntmBlur);
		
		
		 JMenuItem mnBasicBlur = new JMenuItem(translate("Blur.."));
		mntmBlur.add(mnBasicBlur);
		
		 JMenuItem mnMotionBlur = new JMenuItem(translate("Motion Blur.."));
		mntmBlur.add(mnMotionBlur);


		 JMenuItem mnLensBlur = new JMenuItem(translate("Lens Blur.."));
		mntmBlur.add(mnLensBlur);

		 JMenuItem mnGlowBlur = new JMenuItem(translate("Glow Blur.."));
			mntmBlur.add(mnGlowBlur);

			 JMenuItem mnUnsharpen = new JMenuItem(translate("Sharpen.."));
				mntmBlur.add(mnUnsharpen);

		

		
		
		
		JMenu mntmEffects = new JMenu(translate("Effects .."));
		mnFilters.add(mntmEffects);
		
		final JMenuItem mnPosterize = new JMenuItem(translate("Posterize.."));
		mntmEffects.add(mnPosterize);
		

		final JMenuItem mnThreshold = new JMenuItem(translate("Threshold.."));
		mntmEffects.add(mnThreshold);

		JMenu mntmArtistic = new JMenu(translate("Artistic.."));
		mnFilters.add(mntmArtistic);
		
		final JMenuItem mnSpherify = new JMenuItem(translate("Spherify.."));
		mntmArtistic.add(mnSpherify);
		

		final JMenuItem mnBoxify = new JMenuItem(translate("Boxify.."));
		mntmArtistic.add(mnBoxify);
		
		JMenu mntmShading = new JMenu(translate("Shading.."));
		mnFilters.add(mntmShading);

		JMenuItem mnShadow= new JMenuItem(translate("Shadow.."));
		mntmShading.add(mnShadow);
		

		JMenuItem mnRays=  new JMenuItem(translate("Light Rays.."));
		mntmShading.add(mnRays);

		final JMenu mnBrush = new JMenu(translate("Brush"));
		menuBar.add(mnBrush);

		JMenuItem mntmBrushColor = new JMenuItem(translate("Brush Colour.."));
		mnBrush.add(mntmBrushColor);
	
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK);
		mntmBrushColor.setAccelerator(tmpKey);

		
		JMenu mntmHelp = new JMenu(translate("Help"));
		menuBar.add(mntmHelp);
		
		JMenu mntmLanguage = new JMenu(translate("Set Language.."));
		mntmHelp.add(mntmLanguage);
		
		

		JMenuItem mntmLanguageAfrikaans= new JMenuItem(translate("Afrikaans"));
		mntmLanguage.add(mntmLanguageAfrikaans);
		
		
		JMenuItem mntmLanguageChinese= new JMenuItem(translate("Chinese"));
		mntmLanguage.add(mntmLanguageChinese);
		
		JMenuItem mntmLanguageEnglish= new JMenuItem(translate("English"));
		mntmLanguage.add(mntmLanguageEnglish);
	
		JMenuItem mntmLanguageEsperanto= new JMenuItem(translate("Esperanto"));
		mntmLanguage.add(mntmLanguageEsperanto);
		
		
		JMenuItem mntmLanguageFrench= new JMenuItem(translate("French"));
		mntmLanguage.add(mntmLanguageFrench);
	
		JMenuItem mntmLanguageGerman= new JMenuItem(translate("German"));
		mntmLanguage.add(mntmLanguageGerman);
	
		JMenuItem mntmLanguageIrish= new JMenuItem(translate("Irish"));
		mntmLanguage.add(mntmLanguageIrish);

		JMenuItem mntmLanguageItalian= new JMenuItem(translate("Italian"));
		mntmLanguage.add(mntmLanguageItalian);
		
		JMenuItem mntmLanguageJapanese= new JMenuItem(translate("Japanese"));
		mntmLanguage.add(mntmLanguageJapanese);
		
		
		JMenuItem mntmLanguagePolish= new JMenuItem(translate("Polish"));
		mntmLanguage.add(mntmLanguagePolish);

		JMenuItem mntmLanguageSpanish= new JMenuItem(translate("Spanish"));
		mntmLanguage.add(mntmLanguageSpanish);
	
		
		mnFile.addSeparator();
		JMenuItem mntmLanguageRestart = new JMenuItem(translate("Requires Restart"));
		mntmLanguage.add(mntmLanguageRestart);
	    mntmLanguageRestart.setEnabled(false);
	    
		// //#####ACTIONS#####////
		
	    mntmLanguageJapanese.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDefaultLanguage("JP");
			}
		});
	    
	    mntmLanguageEsperanto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDefaultLanguage("Esperanto");
			}
		});
	    
	    mntmLanguageItalian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDefaultLanguage("Italian");
			}
		});
	    
	    mntmLanguageAfrikaans.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDefaultLanguage("Afrikaans");
			}
		});
	    
	    
	    mntmLanguageEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDefaultLanguage("EN");
			}
		});
	    
	    mntmLanguagePolish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDefaultLanguage("PL");
			}
		});
	    
	    mntmLanguageChinese.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDefaultLanguage("CHI");
			}
		});
	    
	    mntmLanguageSpanish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDefaultLanguage("ES");
			}
		});
	    
	    mntmLanguageFrench.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDefaultLanguage("FR");
			}
		});
	    
	    mntmLanguageGerman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDefaultLanguage("DE");
			}
		});
		
	    mntmLanguageIrish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDefaultLanguage("IE");
			}
		});
		
	    mntmRemoveFrames.addActionListener(new ActionListener() {
	 			public void actionPerformed(ActionEvent arg0) {

					canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
					MAXFRAMES-=40;
					//canvas.saveAction(CURRENTLAYER,MAXFRAMES-1, "Delete Frames");
	 				
					CURRENTFRAME = MAXFRAMES;
					
					canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);


	 				timeline.setFramesLength();
	 			}
	 		});
		
	    mntmAppendFrames.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent arg0) {
 				
 				MAXFRAMES+=40;
 				timeline.setFramesLength();
 			}
 		});
	

		

		mntmMoveUpOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				int index =canvas.getLayerIndex(CURRENTLAYER);
				if(timeline.layers.get(index).isMask){
					index=(canvas.getLayerIndex(timeline.layers.get(index).maskOf));
				}
				while(index>0){
					index--;
					if(!timeline.layers.get(index).isMask){
					int newLayer = timeline.layers.get(index).layerID;
				
					timeline.shiffleTable(CURRENTFRAME,newLayer,0,false);
					break;
					}
				}
			}
		});
		
		
		mntmMoveDownOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				int index =canvas.getLayerIndex(CURRENTLAYER);
				if(timeline.layers.get(index).isMask){
					index=(canvas.getLayerIndex(timeline.layers.get(index).maskOf));
				}
				
				while(index<timeline.layers.size()-1){
				index++;
					
						if(!timeline.layers.get(index).isMask){
						int newLayer = timeline.layers.get(index).layerID;
					
						timeline.shiffleTable(CURRENTFRAME,newLayer,0,false);
						break;
						}
			}
			}
		});
		
		

		mntmMoveLeftOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				
				if(CURRENTFRAME>0){
				
				timeline.shiffleTable(CURRENTFRAME-1,CURRENTLAYER,0,false);
				}
			}
		});
		
		mntmMoveRightOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				
				if(CURRENTFRAME<MAXFRAMES-1){
				
				timeline.shiffleTable(CURRENTFRAME+1,CURRENTLAYER,0,false);
				}
			}
		});
		
		
		mntmToggleKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				timeline.shiffleTable(CURRENTFRAME,CURRENTLAYER,2,false);
			}
		});
		
		
		mnExportSWF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				String location = getFilePath();
				
				canvas.exportImages(false,true,"png",location);
			}
		});
		

		mnExportAnimatedGIF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				String location = getFilePath();
				
				canvas.exportImages(true,false,"gif",location);
			}
		});
		
		mnExportGIF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				String location = getFilePath();
				
				canvas.exportImages(false,false,"gif",location);
			}
		});
		
		mnExportPNG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				String location = getFilePath();
				
				canvas.exportImages(false,false,"png",location);
			}
		});
		
		
		mnExportTFF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				String location = getFilePath();
				
				canvas.exportImages(false,false,"tiff",location);
			}
		});
		
		mnExportJPG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				String location = getFilePath();
				
				canvas.exportImages(false,false,"jpg",location);
			}
		});
		
		
		mntmDeleteLayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				timeline.layers.get(canvas.getLayerIndex(CURRENTLAYER)).deleteMe();
				timeline.revalidate();
				timeline.repaint();
			}
		});
		
		
		
		
		mntmAddMask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!timeline.layers.get(canvas.getLayerIndex(CURRENTLAYER)).hasMask 
						&& !timeline.layers.get(canvas.getLayerIndex(CURRENTLAYER)).isMask){

					canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
					canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
					
					MAXLAYERS++;
				
				timeline.addNewLayer(layerIndex,true);
				
				canvas.saveImageToDisk(canvas.emptyImage,layerIndex-1, 0);
				
				
			}
			}
		});
		
		
		
		mntmAddLayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);
				canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
		   		 
				MAXLAYERS++;
				
				timeline.addNewLayer( layerIndex,false);
				canvas.saveImageToDisk(canvas.emptyImage, layerIndex-1, 0);
				
			}
		});
		
		mntmChangeBGColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				canvas.bgColor = colorPick2(new Color(canvas.bgColor));
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				
			}
		});
		

		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
				loadNewFile();
				
			}
		});

		mnBasicBlur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		filterFrames.get(getFilterFrameIndex("BLUR")).showFilter("Blur");
		   		filterFrames.get(getFilterFrameIndex("BLUR")).setVisible(true);

		   	//	filterFrame.setBounds(201,201,200,300);
		   	//showFilterFrameOnTop();
			}
		});
		
		mnMotionBlur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		filterFrames.get(getFilterFrameIndex("BLUR")).showFilter("Motion Blur");
		   		filterFrames.get(getFilterFrameIndex("BLUR")).setVisible(true);

		   	//	filterFrame.setBounds(201,201,200,300);
		   	//showFilterFrameOnTop();
			}
		});
		
		mnGlowBlur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		filterFrames.get(getFilterFrameIndex("BLUR")).showFilter("Glow Blur");
		   		filterFrames.get(getFilterFrameIndex("BLUR")).setVisible(true);

		   	//	filterFrame.setBounds(201,201,200,300);
		   	//showFilterFrameOnTop();
			}
		});
		
		mnLensBlur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		filterFrames.get(getFilterFrameIndex("BLUR")).showFilter("Lens Blur");
		   		filterFrames.get(getFilterFrameIndex("BLUR")).setVisible(true);

		   	//	filterFrame.setBounds(201,201,200,300);
		   	//showFilterFrameOnTop();
			}
		});
		

		mnSpherify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		filterFrames.get(getFilterFrameIndex("ARTISTIC")).showFilter("Spherify");
		   		filterFrames.get(getFilterFrameIndex("ARTISTIC")).setVisible(true);

		   		//showFilterFrameOnTop();
			}
		});
		
		mnBoxify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		filterFrames.get(getFilterFrameIndex("ARTISTIC")).showFilter("Boxify");
		   		filterFrames.get(getFilterFrameIndex("ARTISTIC")).setVisible(true);

		   		//showFilterFrameOnTop();
			}
		});
		
		mnShadow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		filterFrames.get(getFilterFrameIndex("SHADING")).showFilter("Shadow");
		   		filterFrames.get(getFilterFrameIndex("SHADING")).setVisible(true);

		   		//showFilterFrameOnTop();
			}
		});
		mnRays.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		filterFrames.get(getFilterFrameIndex("SHADING")).showFilter("Rays");
		   		filterFrames.get(getFilterFrameIndex("SHADING")).setVisible(true);

		   		//showFilterFrameOnTop();
			}
		});
		mnUnsharpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		filterFrames.get(getFilterFrameIndex("BLUR")).showFilter("Sharpen");
		   		filterFrames.get(getFilterFrameIndex("BLUR")).setVisible(true);

		   		//showFilterFrameOnTop();
			}
		});
		mnThreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		filterFrames.get(getFilterFrameIndex("EFFECTS")).showFilter("Threshold");
		   		filterFrames.get(getFilterFrameIndex("EFFECTS")).setVisible(true);

		   	//	showFilterFrameOnTop();
			}
		});
		
		mnPosterize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		filterFrames.get(getFilterFrameIndex("EFFECTS")).showFilter("Posterize");
		   		filterFrames.get(getFilterFrameIndex("EFFECTS")).setVisible(true);

		   	//	showFilterFrameOnTop();
			}
		});
		
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			saveInit();
			}
		});
		
		mnSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			saveNewFile();
			}
		});

		
		mnImportImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			importImage(getFilePath());
			}
		});
		
		mntmCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentToolType(currentTool).equals("select")) {
					// canvas.clipBoard =
					// canvas.pg.get(Math.min(canvas.selectBeginX,canvas.selectEndX),Math.min(canvas.selectBeginY,canvas.selectEndY),Math.max(canvas.selectBeginX,canvas.selectEndX)-Math.min(canvas.selectBeginX,canvas.selectEndX),Math.max(canvas.selectBeginY,canvas.selectEndY)-Math.min(canvas.selectBeginY,canvas.selectEndY));
					canvas.copyToClipBoard();

				}
			}
		});

		mntmPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				canvas.pasteFromClipBoard(false,"Paste");
			}
		});

		mntmUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.undo();
				// System.out.println("UNDO"+currentActionIndex);
			}
		});

		mntmRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.redo();
			}
		});

		

		mntmToggleTools.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// sketch.cleanPGS();
				// System.out.println("OUTOUT: "+selectedX);
			}
		});

		mntmBrushColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PENCOLOR = colorPick(PENCOLOR);

			}
		});

		/*
		 * timelineFrame.addInternalFrameListener(new
		 * javax.swing.event.InternalFrameAdapter() { public void
		 * internalFrameClosing(InternalFrameEvent e) {
		 * 
		 * mntmToggleTimeline.setLabel("Show Timeline"); } });
		 * 
		 * 
		 * mntmToggleTimeline.addActionListener(new ActionListener() { public
		 * void actionPerformed(ActionEvent arg0) {
		 * if(timelineFrame.isVisible()){
		 * 
		 * timelineFrame.hide(); mntmToggleTimeline.setLabel("Show Timeline");
		 * }else{ timelineFrame.show();
		 * mntmToggleTimeline.setLabel("Hide Timeline");
		 * 
		 * } mainPanel.repaint(); } });
		 */

	}

	public void getLastFrame(){
		lastFrame=0;
		for(int y=0;y<MAXLAYERS;y++){
			for(int x=0;x<MAXFRAMES;x++){
				if(timeline.layers.get(y).jbs.get(x).isKey)
					lastFrame = x;
			}
		}
		
	}
	public void playPreview(){
		
		if(playPreviewBool && CURRENTFRAME <MAXFRAMES){

			snooze("Preview Playing",1000/FPS);
			
			if(CURRENTFRAME>=lastFrame){
				if(loopPreview)
				timeline.shiffleTable(0,-1,0,false);
				else
					playPreviewBool=false;
				
			}
			else
				timeline.shiffleTable(CURRENTFRAME+1,-1,0,false);
			
				
			 playPreview();
		}
			
	}
	public String currentToolType(String str) {
		if (str.equals("selectRect")) {
			return "select";
		} else if (str.equals("selectCirc")) {
			return "select";
		}
		if (str.equals("selectPen")) {
			return "select";
		}
		return str;
	}

	public int colorPick2(Color c) {
		return JColorChooser.showDialog(frame, "", c).getRGB();

	}

	public Color colorPick(Color c) {
		Color c2 = JColorChooser.showDialog(frame, "", c);
		if (c2 != null) {
			PREVCOLS.add(c);
			updateColorBoxes(c2);

			prevColPanel.update();
			return c2;
		} else {
			updateColorBoxes(c);
			return c;
		}

	}
	
	public void setDefaultLanguage(String lang){

		language = lang;
		userConfig[1] = lang;
		saveUserConfig();
	}
	
	public void updateColorBoxes(Color c){

		penOps.colorButton.setBackground(c);
		toolBar.colorButton.setBackground(c);
		topPanel.colorButton.setBackground(c);
	}

}
