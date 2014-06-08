package aniExports;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.animation.shop.basicPapplet;

public class UploadFile {
	String filePath;
	

	public UploadFile(String filePath){
		this.filePath=filePath;
	}
	String saveToWeb(String filename, byte[] data) {
		return  postData(filename, "application/octet-stream", data);
	}
	
	String postData(String filename, String ctype, byte[] bytes) {
	String	retStr="Your file could not be uploaded ";
		  try {
		    URL u = new URL("http://localhost/www/anima.me/io/uploadFile.php");
		    URLConnection c = u.openConnection();
		    // post multipart data
		 
		    c.setDoOutput(true);
		    c.setDoInput(true);
		    c.setUseCaches(false);
		 
		    // set request headers
		    c.setRequestProperty("Content-Type", "multipart/form-data; boundary=AXi93A");
		 
		    // open a stream which can write to the url
		    DataOutputStream dstream = new DataOutputStream(c.getOutputStream());
		 
		    // write content to the server, begin with the tag that says a content element is coming
		    dstream.writeBytes("--AXi93A\r\n");
		 
		    // describe the content
		    dstream.writeBytes("Content-Disposition: form-data; name=p5uploader; filename=" + filename + 
		      " \r\nContent-Type: " + ctype + 
		      "\r\nContent-Transfer-Encoding: binary\r\n\r\n");
		    dstream.write(bytes, 0, bytes.length);
		 
		    // close the multipart form request
		    dstream.writeBytes("\r\n--AXi93A--\r\n\r\n");
		    dstream.flush();
		    dstream.close();
		 
		    // print the response
		    try {
		      BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		      String responseLine = in.readLine();
		      retStr="";
		      while (responseLine != null) {
		        retStr+=(responseLine);
		        responseLine = in.readLine();
		      }
		    }
		    catch(Exception e) {
		      e.printStackTrace();
		    }
		  }
		  catch(Exception e) { 
		    e.printStackTrace();
		  }
		  return retStr;
		}
		boolean isJPG(String filename) {
		  return filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg");
		}
		boolean isPNG(String filename) {
			  return filename.toLowerCase().endsWith(".png");
			}
		boolean isGIF(String filename) {
			  return filename.toLowerCase().endsWith(".gif");
			}
		
	public String uploadFileAction(){
		File f = new File(filePath);
		basicPapplet bp = new basicPapplet();
		return saveToWeb("upload.gif",bp.loadBytes(f));
		
	}
	
	public int uploadGif(){
		String urlToConnect = "http://localhost/www/anima.me/io/uploadFile.php";
		String paramToSend = "filename";
		File fileToUpload = new File(filePath);
		String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.

		URLConnection connection = null;
		try {
			connection = new URL(urlToConnect).openConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		connection.setDoOutput(true); // This sets request method to POST.
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		PrintWriter writer = null;
		try {
		    writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));

		    writer.println("--" + boundary);
		    writer.println("Content-Disposition: form-data; name=\"file\"");
		    writer.println("Content-Type: text/plain; charset=UTF-8");
		    writer.println();
		    writer.println(paramToSend);

		    writer.println("--" + boundary);
		    writer.println("Content-Disposition: form-data; name=\"fileToUpload\"; filename=\"file.txt\"");
		    writer.println("Content-Type: text/plain; charset=UTF-8");
		    writer.println();
		    BufferedReader reader = null;
		    try {
		        reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileToUpload), "UTF-8"));
		        for (String line; (line = reader.readLine()) != null;) {
		            writer.println(line);
		        }
		    } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
		        if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
		    }

		    writer.println("--" + boundary + "--");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
		    if (writer != null) writer.close();
		}

		// Connection is lazily executed whenever you request any status.
		int responseCode = 0;
		try {
			responseCode = ((HttpURLConnection) connection).getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return responseCode;
	}
	
}
