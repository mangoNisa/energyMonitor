package com.actionbarsherlock.sample.demos;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabNavigation extends Activity{
    StatusChecker sc;

    private TextView bedval, livingval, utilval, bathval, kitchenval;
    private TabHost myTabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(hasConnection() == true){
	        setContentView(R.layout.tab_navigation);
	        
	        SpinnerAdapter adapter = ArrayAdapter.createFromResource(this, R.array.actions, android.R.layout.simple_spinner_dropdown_item);
	        
	        OnNavigationListener callback = new OnNavigationListener(){
	        	String[] items = getResources().getStringArray(R.array.actions);
	        	
	        	@Override
	        	public boolean onNavigationItemSelected(int positions, long id){
	        		if(id == 1){
	        			Intent intent = new Intent();
	        			
	        		}
	        		return true; 
	        	}
	        };
	        
	        
	      // Action Bar
	        ActionBar actions = getActionBar();
	        actions.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	        actions.setDisplayShowTitleEnabled(false);
	        actions.setListNavigationCallbacks(adapter, callback);
	        //Creating tabs
	        myTabHost = (TabHost) findViewById(R.id.room_temp_host);
	        
	        myTabHost.setup();
	        // adding the tabs 
	       
	        TabSpec temp = myTabHost.newTabSpec("roomTemp");
	        temp.setIndicator("Room Temp", getResources().getDrawable(android.R.drawable.ic_menu_add));
	        temp.setContent(R.id.tabtemp);
	        myTabHost.addTab(temp);
	        temproom(); // Current view
	
	        TabSpec water = myTabHost.newTabSpec("waterTemp");
	        water.setIndicator("Water Temp", getResources().getDrawable(android.R.drawable.ic_menu_add));
	        water.setContent(R.id.tabwater);
	        myTabHost.addTab(water);
	        
	        TabSpec humidity = myTabHost.newTabSpec("humidity");
	        humidity.setIndicator("Humidity", getResources().getDrawable(android.R.drawable.ic_menu_add));
	        humidity.setContent(R.id.tabhumidity);
	        myTabHost.addTab(humidity);
	       
	    
	        myTabHost.setOnTabChangedListener(new OnTabChangeListener(){
	
				@Override
				public void onTabChanged(String tabId) {
					if(tabId.equals("roomTemp")) temproom();
					else if(tabId.equals("waterTemp")) tempwater();
					else if(tabId.equals("humidity"))humidity();
					}
	        	});
        }else{
        	new AlertDialog.Builder(this)
            .setTitle("Connection Error")
            .setMessage("No internet connection available")
            .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) { 
                    finish();
                }
             })
            .setIcon(android.R.drawable.ic_dialog_alert).show();  
        	
        }
    }
    
    public void tempInfoBox(int val, int progressFeed, String feed){
    	TextView textView = (TextView) findViewById(val);
    	String value = new StatusChecker(feed).getPowerVal();
    	textView.setText(value + "°C");
    	SemiCircleProgressBarView progress = (SemiCircleProgressBarView) findViewById(progressFeed);
    	progress.setLayerType(View.LAYER_TYPE_SOFTWARE, null);	
        progress.setClipping(roomTemp(new StatusChecker(feed).getPowerVal()));
    }
    
    private boolean hasConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
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
        progress.setClipping(humidityLevel(new StatusChecker(feed).getPowerVal()));
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
  
    
   
   /* @Override
    public void onTabReselected(Tab tab, FragmentTransaction transaction) {
    	if(String.valueOf(tab.getPosition()).equals("0")){
    		 bedval = (TextView) findViewById(R.id.bedroomVal);
             bedval.setText(new StatusChecker("142").getPowerVal());
             livingval = (TextView)findViewById(R.id.livingroomval);
             livingval.setText(new StatusChecker("145").getPowerVal());
    	}
    	else if(String.valueOf(tab.getPosition()).equals("1")) ;
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction transaction) {
    	
    	if(String.valueOf(tab.getPosition()).equals("0")){
            bedval = (TextView) findViewById(R.id.bedroomVal);
            bedval.setText(new StatusChecker("142").getPowerVal());
            livingval = (TextView)findViewById(R.id.livingroomval);
            livingval.setText(new StatusChecker("145").getPowerVal());
    	}
    	else if(String.valueOf(tab.getPosition()).equals("1")) ;
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction transaction) {
    	if(String.valueOf(tab.getPosition()).equals("0")){
    		 bedval = (TextView) findViewById(R.id.bedroomVal);
             bedval.setText(new StatusChecker("142").getPowerVal());
             livingval = (TextView)findViewById(R.id.livingroomval);
             livingval.setText(new StatusChecker("145").getPowerVal());
    	}
    	else if(String.valueOf(tab.getPosition()).equals("1")) ;
    }*/
}
