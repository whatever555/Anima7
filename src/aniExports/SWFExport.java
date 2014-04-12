package aniExports;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import com.flagstone.transform.Movie;
import com.flagstone.transform.MovieHeader;
import com.flagstone.transform.Place2;
import com.flagstone.transform.ShowFrame;
import com.flagstone.transform.datatype.Bounds;
import com.flagstone.transform.datatype.WebPalette;
import com.flagstone.transform.image.ImageTag;
import com.flagstone.transform.linestyle.LineStyle1;
import com.flagstone.transform.shape.ShapeTag;
import com.flagstone.transform.util.image.ImageFactory;
import com.flagstone.transform.util.image.ImageShape;

public class SWFExport {
    Movie movie;  
    final MovieHeader header;
    final ArrayList<ShapeTag> shapes = new ArrayList<ShapeTag>();
    Color bgColor;
    int uid = 1;
int w,h;
    final ImageFactory factory = new ImageFactory();
    String location;
    int fw,fh;
	 public SWFExport(int w, int h, int FPS,Color bgColor, boolean showBG,String location){
		 this.bgColor=bgColor;
		 this.w=w;this.h=h;
		 this.fw=w*20;
		 this.fh=h*20;
		 movie = new Movie(); 
		 header = new MovieHeader();
		 header.setVersion(9);
		 header.setFrameRate(FPS);
		// header.setFrameSize(new Bounds(-w/2, -h/2, w/10, h/10));
		 header.setFrameSize(new Bounds(0,0,fw,fh));
		 movie.add(header);
		    this.location=location;
		// exportThis();
		    
		    
		    
	 }
	 
	 int xx=0;
	 public void addImage(String loc){
		try {
				factory.read(new File(loc));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DataFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    uid++;
		    
		    final ImageFactory factory = new ImageFactory();
		    try {
				factory.read(new File(loc));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DataFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    final ImageTag image = factory.defineImage(uid);
		    uid++;
		    movie.add(image);
		 
		    
		    shapes.add(new ImageShape().defineShape(uid, image,
		        0,0, new LineStyle1(0, WebPalette.LIGHT_BLUE.color())));
		  
		    movie.add(shapes.get(shapes.size()-1));
		    movie.add(Place2.show(shapes.get(shapes.size()-1).getIdentifier(), layer, 0, 0));
		    movie.add(ShowFrame.getInstance());
		    layer++;
	 }
	 int layer=1;
	 public void exportThis(){
		  
			 
			    
			    
			 
			  //  movie.add(new Background(WebPalette.LIGHT_BLUE.color()));
			 
			
			     exportToFile(location);
			   
			 
			    
			
	 }
	 
	 
	 public void exportToFile(String location){
		
		 File f=new File(location);
		    
				try {
					movie.encodeToFile(f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DataFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String html = "<object width='"+w+"' height='"+h+"'><param name='move' " +
				"value='swf.swf'><embed src='swf.swf' width='"+w+"' height='"+h+"'></embed>" +
						"</object>";
				
				 f=new File(location.replace(".swf",".html"));
				 FileWriter fw = null;
				try {
					fw = new FileWriter(f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				 try {
					fw.write(html);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			       
				 
	 }
}
