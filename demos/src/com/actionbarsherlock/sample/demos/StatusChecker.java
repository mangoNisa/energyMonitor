package com.actionbarsherlock.sample.demos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class StatusChecker implements Runnable{
	 // Time in ms in which to fetch a feed value update
    private int updateInterval = 5000;
    // Handler for periodic updates
    private Handler periodicHandler = new Handler();
    // Handler for updating UI on data update
    private Handler uiUpdateHandler = new Handler();

    private String powervalue;
    private String feed;
    
    public StatusChecker() {
	}
    
    public StatusChecker(String feed){
    	this.feed = feed;
    	run();
    }

	@Override
	public void run() {
		 Log.i("EmonLog", "Periodic");

         try {
             String result = new HTTP().execute(getFeed()).get();
             result = result.replaceAll("\"","");

             powervalue = result;
            
             setPowerVal(powervalue);
            

         } catch (Exception e) {
        	 
         }
         periodicHandler.postDelayed(this, updateInterval);
	}
	
    private String getFeed() {
		return this.feed;
	}
   
	private void setPowerVal(String value) {
		powervalue = value;
		
	}

	void startRepeatingTask() {
        this.run(); 
    }

    void stopRepeatingTask() {
        periodicHandler.removeCallbacks(this);
    }

	public String getPowerVal() {
		return powervalue;
	}
}
	
