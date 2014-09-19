package com.actionbarsherlock.sample.demos;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TempView extends Activity{
    StatusChecker sc;

    private TextView bedval, livingval, utilval, bathval, kitchenval;
    private TabHost myTabHost;
    
    public TempView(){
    	
    }

    public void tempInfoBox(int val, int progressFeed, String feed){
    	TextView textView = (TextView) findViewById(val);
    	String value = new StatusChecker(feed).getPowerVal();
    	textView.setText(value + "°C");
    	SemiCircleProgressBarView progress = (SemiCircleProgressBarView) findViewById(progressFeed);
    	progress.setLayerType(View.LAYER_TYPE_SOFTWARE, null);	
        progress.setClipping(roomTemp(new StatusChecker(feed).getPowerVal()));
    }
    
    public void temproom(){
    	 
         tempInfoBox(R.id.bedroomval, R.id.bedroomtempprogress, "142");
         tempInfoBox(R.id.livingroomval, R.id.livingtempprogress, "145");
         tempInfoBox(R.id.utilroomval, R.id.utiltempprogress, "151");
        
    }
    
    public int roomTemp(String temp){
 	   double t = Double.parseDouble(temp);
 	   int percentage = (int) ((t/40)*100);
 	   return percentage;
    }
    
    public void waterInfoBox(int val, int progressFeed, String feed){
    	TextView textView = (TextView) findViewById(val);
    	String value = new StatusChecker(feed).getPowerVal();
    	textView.setText(value + "°C");
    	SemiCircleProgressBarView progress = (SemiCircleProgressBarView) findViewById(progressFeed);
    	progress.setLayerType(View.LAYER_TYPE_SOFTWARE, null);	
        progress.setClipping(waterTemp(new StatusChecker(feed).getPowerVal()));
    }
    
    public void tempwater(){ 	 
    	 waterInfoBox(R.id.kitchenval, R.id.kitchemwaterprogress, "140");
    	 waterInfoBox(R.id.bathroomval, R.id.bathroomwaterprogress, "141"); 
    }
    
    private float waterTemp(String temp) {
       double t = Double.parseDouble(temp);
  	   int percentage = (int) ((t/60)*100);
  	   return percentage;
	}

    public void humidityInfoBox(int val, int progressFeed, String feed){
    	TextView textView = (TextView) findViewById(val);
    	String value = new StatusChecker(feed).getPowerVal();
    	textView.setText(value + "%");
    	SemiCircleProgressBarView progress = (SemiCircleProgressBarView) findViewById(progressFeed);
    	progress.setLayerType(View.LAYER_TYPE_SOFTWARE, null);	
        progress.setClipping(humidityLevel(new StatusChecker("142").getPowerVal()));
    }
    
	public void humidity(){
		
		humidityInfoBox(R.id.livinghumval, R.id.livinghumprogress, "146");
		humidityInfoBox(R.id.bedroomhumval, R.id.bedroomhumprogress, "143");
    	
   }

	private float humidityLevel(String level) {
		double h = Double.parseDouble(level);
		int percentage = (int) h;
		return percentage;
	}
  
    
   
  
}
