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
import java.awt.Point;
import java.awt.ScrollPane;
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
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
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
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import processing.core.PImage;
import aniExtraGUI.EInternalFrame;
import aniFilters.FilterFrame;

/**
 * @author eddie
 * 
 */
public class Main {

	//TODO LIST
	//Use disk files for Loaded File
	//CLEAN UP UNUSED FILES *NB
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
	// RESTORE/dELETE temp files
	// order algorithm for JFrames/layout
	//tidy copy paste etc
	boolean LOADED=false;
	int layerIndex = 0;
	int timelineButtonWidth;
	int timelineButtonHeight;
	int screenWidth;// = Toolkit.getDefaultToolkit().getScreenSize().width;
	int screenHeight;// = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	boolean pasting = false;
	boolean ISLOADED = false;

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
	
	

	
	int frameIsEmpty = 0, frameIsEmptySelected = 1, frameIsKey = 2,
			frameIsKeySelected = 3;

	final JColorChooser jcc = new JColorChooser();
	ColorSelectionModel jccModel = jcc.getSelectionModel();
	ChangeListener changeListener;

	
	public JLayeredPane mainPanel;
	
	PreviousColours prevColPanel;

	public FilterFrame filterFrame;
	public Canvas canvas;

	PenOptions penOps;
	History historyPanel;

	TimelineSwing timeline;
	static EInternalFrame tlFrame = new EInternalFrame("Timeline");
TimelineControls timelineControls;

	JPanel messagePanel = new JPanel();

	String fileConfigString="";
	
	JScrollPane scMain;

	   static String loadFile;
   JLabel messageTextArea;
	boolean CTRL = false;
	boolean SHIFT = false;
	static JFrame frame = new JFrame();
	
	
	public void initVars(){
		cachedImagesNames=new ArrayList<String>();
		cachedImages=new ArrayList<PImage>();
		CTRL=false;
		timelineButtonWidth=30;
		timelineButtonHeight=20;
		layerIndex=0;
		
	}
	
	public static void main(String[] args) {
		
		if(args.length > 0) {
			loadFile = (args[0]);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					JFrame.setDefaultLookAndFeelDecorated(true);
					new Main();


					frame.setBackground(new Color(67, 67, 67));

				
					frame.addComponentListener(new ComponentListener() 
					{  
					        public void componentResized(ComponentEvent evt) {
					        	 tlFrame.repaint();
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
	            saveFile(file);
	        } else {
	        	System.out.println("Open command cancelled by user.");
	        }
		
	}
	
	public void saveInit(){
		if(fileName==null)
			saveNewFile();
		else
			saveFile(file);
	
	}
	
	public void saveFile(File file){
		saveData(file.getPath());
        fileConfigString+="BGCOL::"+canvas.bgColor+":::";
        fileConfigString+="FPS::"+FPS+":::";
        fileConfigString+="MAXLAYERS::"+MAXLAYERS+":::";
        fileConfigString+="MAXFRAMES::"+MAXFRAMES+":::";
        fileConfigString+="CURRENTLAYER::"+CURRENTLAYER+":::";
        fileConfigString+="CURRENTFRAME::"+CURRENTFRAME+":::";
        fileConfigString+="LASTFRAME::"+lastFrame+":::"; 
        
        fileConfigString+="LAYERS::";
        
        for(int i=0;i<timeline.layers.size();i++){
        	TimelineLayer tl = timeline.layers.get(i);
        	fileConfigString+="8q3b5R1ZMINDEX8q3b5R1ZM"+i+"8q3b5R1ZMz0ID8q3b5R1ZMz0"+tl.layerID+"8q3b5R1ZMz0MASK8q3b5R1ZMz0"+tl.isMask+"8q3b5R1ZMz0MASKOF8q3b5R1ZMz0"+tl.maskOf+"8q3b5R1ZMz0LABEL8q3b5R1ZMz0"+tl.layerName+"8q3b5R1ZMz0";
        }
        
        fileConfigString+=":::";
        fileConfigString+="FOLDER::"+file.getPath()+":::";
       
        
        saveText(fileConfigString,file.getPath()+extension);
	}
	public void saveData(String location){
		
		 File theDir = new File(location);
		 
		  // if the directory does not exist, create it
		 boolean result = true;
		  if (!theDir.exists()) {
		    result = theDir.mkdir();  
		  }
		     if(result) {    
		       for(int y=0;y<MAXLAYERS;y++)
		    	   for(int x=0;x<=lastFrame;x++)
		    		   if(timeline.layers.get(y).jbs.get(x).isKey)
		    			   canvas.copyImage(location+"/"+timeline.layers.get(y).layerID+"_"+x+".png", timeline.layers.get(y).layerID, x);
		  
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
		String[] strs = canvas.loadStrings(filePath);
		if(strs.length>0){
			timeline.mainLine.removeAll();
			timeline.mainLine.revalidate();
			timeline.mainLine.repaint();
			
			messageTextArea = new JLabel("Loading "+fileShortName);
			//setTitle(fileShortName);
			messagePanel.setVisible(true);
			 fileName = filePath;
	            fileShortName=file.getName();
			setConfig(strs[0],file.getPath().replaceAll(extension,""));
		}
	}
	

	
	public void loadNewFile(){
		LOADED=false;
		
		
		setLoadedFile(getFilePath());
		LOADED=true;
	}
	public void setConfig(String line,String folderName){
		
		
		initVars();
		timeline.layers = new ArrayList<TimelineLayer>();
		timeline.showMe();
		
		
		
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
				 		        		MAXFRAMES = Integer.parseInt(parts[1]);
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
	        		for(int z=0;z<layerData.length;z++){
	        			if(layerData[z].length()>3){
	        				String key  ="8q3b5R1ZMz0";
	        				String[] dt = layerData[z].split(key);
	        				
	        					int id = Integer.parseInt(dt[2]);
	        					boolean mask = (dt[4].equals("true"));
	        					int maskof = Integer.parseInt(dt[6]);
	        					String label = dt[8];
	        					
	        					timeline.addNewLayer(id,mask,maskof,label);
	        					
	        					
	        				
	        			}
	        		}
	        		
 		        }else
	        	  if(parts[0].equals("FOLDER")){
	        	
	        		  cleanLocalFolder(); 

	  	        	canvas.loadNewFile(parts[1],MAXLAYERS,MAXFRAMES);
	  	        }
		}
		}
		  
		  timeline.showMe();
		  timeline.repaint();
		  canvas.repaint();
//loadApplication();

			messagePanel.setVisible(false);
			timeline.shiffleTable(CURRENTFRAME,CURRENTLAYER,0,true);
		
	}
	
	public void cleanLocalFolder(){
		// Load the directory as a resource
		  URL dir_url = getClass().getClassLoader().getResource("/data/images");
		  // Turn the resource into a File object
		  File dir = null;
		  if(dir_url!=null){
		try {
			dir = new File(dir_url.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  // List the directory
		
		String files[] = dir.list();
		 
  	   for (String temp : files) {
  	      //construct the file structure
  	      File fileDelete = new File( temp);

  	      //recursive delete
  	      fileDelete.delete();
  	   }
  	   
		  dir.delete();
		  }
	}
	
	public void loadApplication(){
		
		screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width-50;
		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height-50;
		frame.setSize(new Dimension(screenWidth,screenHeight));
		
		mainPanel = new JLayeredPane();
		mainPanel.setBackground(new Color(67,67,67));
		mainPanel.setOpaque(true);
		frame.setVisible(true);
		mainPanel.setVisible(true);
		
		JPanel messageHolder = new JPanel();
		
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
		
		
		filterFrame.setPreferredSize(new Dimension(200,260));
		filterFrame.setMaximumSize(new Dimension(200,260));
		filterFrame.setBounds(200,200,200,300);
		filterFrame.setBackground(new Color(255,255,255));
		filterFrame.setVisible(false);

		canvasFrame.setVisible(true);
		timelineControls.setVisible(true);
		tlFrame.setVisible(true);
		toolBar.setVisible(true);
		prevColPanel.setVisible(true);
		penOps.setVisible(true);
		BrushSelectionOps.setVisible(true);
		historyPanel.setVisible(true);
		historyPanel.update();
		

filterFrame.showFilter("Blur");
mainPanel.add(filterFrame);
mainPanel.add(tlFrame);

		mainPanel.add(timelineControls);

		mainPanel.add(prevColPanel);
		mainPanel.add(canvasFrame);
		mainPanel.add(penOps);
		mainPanel.add(BrushSelectionOps);
		mainPanel.add(historyPanel);
		mainPanel.add(toolBar);
		JPanel bug_workaround = new JPanel();
		
mainPanel.add(bug_workaround);
bug_workaround.setVisible(false);
		
		
		messagePanel.setVisible(false);
		mainPanel.setPreferredSize(new Dimension(screenWidth,screenHeight));
		
		
		scMain = new JScrollPane();

		scMain.setPreferredSize(new Dimension(screenWidth,screenHeight));
		scMain.setBounds(0,0,screenWidth,screenHeight);
	
		scMain.setViewportView(mainPanel);
		frame.getContentPane().add(scMain);


		

		frame.setVisible(true);

tlFrame.toFront();
try{
	tlFrame.setSelected(true);
}catch(Exception ex)
{

}

	}
	public Main() {
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
	
	public void addFilterFrame(){
		filterFrame = new FilterFrame(this);
		
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
		historyPanel.setBorder(null);
		
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
		
		 timeline = new TimelineSwing(MAXLAYERS, MAXFRAMES, this);
		
		JScrollPane timelineScrollPane = new JScrollPane();
		//timelineScrollPane.add(timeline);

		timelineScrollPane.setViewportView(timeline);
		//timeline.setFocusable(false);

		tlFrame.setClosable(true);
		tlFrame.setResizable(true);

		tlFrame.setDefaultCloseOperation(1);

		tlFrame.setVisible(true);

		
		tlFrame.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(238,238,238)));
		
		tlFrame.add(timelineScrollPane);


		


	}

	public EInternalFrame canvasFrame = new EInternalFrame("Canvas");
	


	public void addCanvas() {
		canvas = new Canvas(CANVASWIDTH, CANVASHEIGHT, this);


	
		
		canvas.setVisible(true);
		canvas.init();

		JPanel canvasPanel = new JPanel();
		canvasPanel.setBounds(0, 0, CANVASWIDTH, CANVASHEIGHT);
		canvasPanel.setVisible(true);

		
		canvas.setBounds(0, 0, CANVASWIDTH, CANVASHEIGHT);

		canvasPanel.add(canvas);
		canvasFrame.setVisible(false);
canvasPanel.setBackground(new Color(67,67,67));
		//canvasFrame.setClosable(true);
		canvasFrame.setResizable(true);
		canvasFrame.setDefaultCloseOperation(1);
		canvasFrame.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(67,67,67)));
		


		
		ScrollPane canvasSc = new ScrollPane();

		canvasSc.setBounds(50, 10, CANVASWIDTH, CANVASHEIGHT);
		
		canvasSc.setVisible(true);
		canvasSc.add(canvasPanel);
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
		
		final JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		final JMenuItem mntmNew = new JMenuItem("New.. [Ctrl+N]");
		mnFile.add(mntmNew);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
		mntmNew.setAccelerator(tmpKey);
		
		final JMenuItem mntmOpen = new JMenuItem("Open.. [Ctrl+O]");
		mnFile.add(mntmOpen);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
		mntmOpen.setAccelerator(tmpKey);

		mntmSave = new JMenuItem("Save.. [Ctrl+S]");
		mnFile.add(mntmSave);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
		mntmSave.setAccelerator(tmpKey);
		
		mnSaveAs = new JMenuItem("Save As..");
		mnFile.add(mnSaveAs);
		
		mnFile.addSeparator();
		
		mnImportImage = new JMenuItem("Import Image To Stage.. [Ctrl+I]");
		mnFile.add(mnImportImage);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK);
		mnImportImage.setAccelerator(tmpKey);
		
		
		mnFile.addSeparator();
		
		JMenu mntmExport = new JMenu("Export to ..");
		mnFile.add(mntmExport);
		

		JMenuItem mnExportSWF = new JMenuItem("SWF");
		mntmExport.add(mnExportSWF);

		JMenuItem mnExportAnimatedGIF = new JMenuItem("Animated GIF");
		mntmExport.add(mnExportAnimatedGIF);

		JMenuItem mnExportPNG = new JMenuItem("PNG Images");
		mntmExport.add(mnExportPNG);

		JMenuItem mnExportGIF = new JMenuItem("GIF Images");
		mntmExport.add(mnExportGIF);

		JMenuItem mnExportJPG = new JMenuItem("JPG Images");
		mntmExport.add(mnExportJPG);

		JMenuItem mnExportTFF = new JMenuItem("TIFF Images");
		mntmExport.add(mnExportTFF);
		
		mnFile.addSeparator();
		
		JMenuItem mnExit = new JMenuItem("Exit");
		mnFile.add(mnExit);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK);
		mnExit.setAccelerator(tmpKey);

		JMenu mnLayers = new JMenu("Layers");
		menuBar.add(mnLayers);

		JMenuItem mntmAddLayer = new JMenuItem("Add Layer");
		mnLayers.add(mntmAddLayer);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK);
		mntmAddLayer.setAccelerator(tmpKey);
		
		JMenuItem mntmAddMask = new JMenuItem("Add Mask To Current Layer");
		mnLayers.add(mntmAddMask);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK);
		mntmAddMask.setAccelerator(tmpKey);

		JMenuItem mntmDeleteLayer = new JMenuItem("Delete Current Layer");
		mnLayers.add(mntmDeleteLayer);
		

		JMenuItem mntmToggleKey = new JMenuItem("Toggle Keyframe [Ctrl+K]");
		mnLayers.add(mntmToggleKey);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_DOWN_MASK);
		mntmToggleKey.setAccelerator(tmpKey);
		
		JMenuItem mntmMoveRightOne = new JMenuItem("Go Forward 1 Frame [Ctrl+RIGHT]");
		mnLayers.add(mntmMoveRightOne);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,InputEvent.CTRL_DOWN_MASK);
		mntmMoveRightOne.setAccelerator(tmpKey);
		
		JMenuItem mntmMoveLeftOne = new JMenuItem("Go Back 1 Frame [Ctrl+LEFT]");
		mnLayers.add(mntmMoveLeftOne);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,InputEvent.CTRL_DOWN_MASK);
		mntmMoveLeftOne.setAccelerator(tmpKey);

		JMenuItem mntmMoveDownOne = new JMenuItem("Move Down 1 Layer [Ctrl+DOWN]");
		mnLayers.add(mntmMoveDownOne);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,InputEvent.CTRL_DOWN_MASK);
		mntmMoveDownOne.setAccelerator(tmpKey);
		
		JMenuItem mntmMoveUpOne = new JMenuItem("Move Up 1 Layer [Ctrl+UP]");
		mnLayers.add(mntmMoveUpOne);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_UP,InputEvent.CTRL_DOWN_MASK);
		mntmMoveUpOne.setAccelerator(tmpKey);

		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmUndo = new JMenuItem("Undo    [Ctrl+Z]");
		mnEdit.add(mntmUndo);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK);
		mntmUndo.setAccelerator(tmpKey);
		
		JMenuItem mntmRedo = new JMenuItem("Redo    [Ctrl+Y]");
		mnEdit.add(mntmRedo);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK);
		mntmRedo.setAccelerator(tmpKey);
		
		JMenuItem mntmCut = new  JMenuItem("Cut     [Ctrl+X]");
		mnEdit.add(mntmCut);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK);
		mntmCut.setAccelerator(tmpKey);

		JMenuItem mntmCopy = new  JMenuItem("Copy    [Ctrl+C]");
		mnEdit.add(mntmCopy);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK);
		mntmCopy.setAccelerator(tmpKey);

		JMenuItem mntmPaste = new JMenuItem("Paste   [Ctrl+P]");
		mnEdit.add(mntmPaste);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK);
		mntmPaste.setAccelerator(tmpKey);
		
		JMenuItem mntmClear = new JMenuItem("Clear");
		mnEdit.add(mntmClear);

		final JMenu mnView = new JMenu("View");
		menuBar.add(mnView);

		final JMenuItem mntmToggleTimeline = new JMenuItem("Hide Timeline");
		mnView.add(mntmToggleTimeline);

		final JMenuItem mntmToggleTools = new JMenuItem("Hide Tools");
		mnView.add(mntmToggleTools);

		JMenuItem mntmToggleBrush = new JMenuItem("Show Brush Options");
		mnView.add(mntmToggleBrush);

		JMenuItem mntmToggleOnion = new JMenuItem("Toggle Onion Skin");
		mnView.add(mntmToggleOnion);

		JMenuItem mntmChangeBGColor = new JMenuItem("Change Background Color..");
		mnView.add(mntmChangeBGColor);

		final JMenu mnFilters = new JMenu("Filters");
		menuBar.add(mnFilters);

		final JMenuItem mntmBlur = new JMenuItem("Blur..");
		mnFilters.add(mntmBlur);

		final JMenuItem mntmPosterize = new JMenuItem("Posterize..");
		mnFilters.add(mntmPosterize);
		

		final JMenuItem mntmThreshold = new JMenuItem("Threshold..");
		mnFilters.add(mntmThreshold);

		final JMenuItem mntmSpherify = new JMenuItem("Spherify..");
		mnFilters.add(mntmSpherify);
		

		final JMenuItem mntmBoxify = new JMenuItem("Boxify..");
		mnFilters.add(mntmBoxify);

		final JMenu mnBrush = new JMenu("Brush");
		menuBar.add(mnBrush);

		final JMenuItem mntmBrushColor = new JMenuItem("Brush Color..   [Ctrl+U]");
		mnBrush.add(mntmBrushColor);
		tmpKey = KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK);
		mntmBrushColor.setAccelerator(tmpKey);

		// //#####ACTIONS#####////
		
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

		mntmBlur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		 filterFrame.showFilter("Blur");
		   		 filterFrame.setVisible(true);

		   	//	filterFrame.setBounds(201,201,200,300);
		   	showFilterFrameOnTop();
			}
		});
		

		mntmSpherify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		 filterFrame.showFilter("Spherify");
		   		 filterFrame.setVisible(true);

		   		showFilterFrameOnTop();
			}
		});
		
		mntmBoxify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		 filterFrame.showFilter("Boxify");
		   		 filterFrame.setVisible(true);

		   		showFilterFrameOnTop();
			}
		});
		
		mntmThreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		 filterFrame.showFilter("Threshold");
		   		 filterFrame.setVisible(true);

		   		showFilterFrameOnTop();
			}
		});
		
		mntmPosterize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.finaliseFrame(CURRENTLAYER,CURRENTFRAME);

		   		 canvas.showNewFrame(CURRENTLAYER,CURRENTFRAME,-1);
			
		   		 filterFrame.showFilter("Posterize");
		   		 filterFrame.setVisible(true);

		   		showFilterFrameOnTop();
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

	
	public void showFilterFrameOnTop(){
		

 		canvasFrame.revalidate();
 		canvasFrame.repaint();
		mainPanel.revalidate();
 		filterFrame.revalidate();
   		canvas.invalidate();
   		canvas.validate();
 		mainPanel.repaint();
   		canvas.repaint();
 		filterFrame.repaint();
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
	
	public void updateColorBoxes(Color c){

		penOps.colorButton.setBackground(c);
		toolBar.colorButton.setBackground(c);
		topPanel.colorButton.setBackground(c);
	}

}