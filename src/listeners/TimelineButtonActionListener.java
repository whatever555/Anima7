package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.animation.shop.Main;

public class TimelineButtonActionListener implements ActionListener {

	 Main parent;
      public TimelineButtonActionListener(Main parent) {
      	
      	  this.parent=parent;
      }
      

      public void actionPerformed(ActionEvent e) {
   	   
    	  String[] action = (e.getActionCommand()).split("-");
    	  
   	 	parent.timeline.shiffleTable(Short.parseShort(action[1]),Short.parseShort(action[0]),(byte)1,false);
      	
      }
  }
