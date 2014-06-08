package MyTracking;

import java.io.File;
import java.io.IOException;

import processing.core.PApplet;

public class Tracker extends PApplet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean DONOTTRACK=false;
	public String MYAPPID=null;
	public String VERSION = "1.1.0";
	
	public void setup(){
		
	}
	
	
	public boolean isUpToDate(){
	
			String[] data  = loadStrings("http://imaga.me/prod/track/version.txt");
			if(data!=null){
				if(!data[0].equals(VERSION))
					return false;
			}
		
		
		return true;
	}
	
	public void getAppId(){
		println("GETTINAPP ID");
		String[]  data  = loadStrings(".myAppId");
		if(data!=null){
			MYAPPID = data[0];
			
		}else{
			data = loadStrings("http://imaga.me/prod/track/getAppId.php");
			if(data!=null){

				if(data[0].length()>70){
				MYAPPID = data[0];
				saveStrings(".myAppId",data);
				}else{
					DONOTTRACK=true;
				}
			}else{
				DONOTTRACK=true;
			}
		}
	}
	
	public void track(String analyticsData,boolean uploadLocal){
		if(DONOTTRACK==false){
		if(uploadLocal)
		pushLocalTrackVars();
		String getvars = "?analyticsData=[["+analyticsData+"]][[APPID:"+MYAPPID+"]]";
		
		String[] x =loadStrings("http://imaga.me/prod/track/analyticsData.php"+getvars);
		if(x==null){
		  saveLocalTracks(getvars);
		} 

		}
	}
	
	public void saveLocalTracks(String getvars){
		String[] ad;
	
			ad =loadStrings(".analyticsData");
			if(ad!=null){
			String[] newVars = new String[ad.length+1];
			for(int i=0;i<ad.length;i++){
				newVars[i]=ad[i];
			}
			newVars[newVars.length-1]=getvars;
			}else{
			ad = new String[1];
			ad[0]=getvars;
		  saveStrings(".analyticsData",ad);
			}
		} 
	
		
	
	
	public void pushLocalTrackVars(){
		println("PUSHLOCAL TRACKVARS");
		String[] ad;
		
			ad =loadStrings(".analyticsData");
			if(ad!=null){
			for(int i=0;i<ad.length;i++){
				track(ad[i],false);
				
			}
			String[] x = null;
			saveStrings(".analyticsData",ad);
		}else
		{
			//do nothing
		} 
		
	}
	
}
