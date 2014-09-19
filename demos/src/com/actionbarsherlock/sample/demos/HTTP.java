package com.actionbarsherlock.sample.demos;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class HTTP extends AsyncTask<String, Void, String>
	{
	    @Override
	    protected String doInBackground(String... params) {
		    String result = "";
		    try {
			    String urlstring = "http://emon.cairnsolutions.com/emoncms/feed/value.json?id=" + params[0] + "&apikey=e04fe1d32ac15480d068de04bc905e73";
			    Log.i("EmonLog", "HTTP Connecting: "+urlstring);
			    URL url = new URL(urlstring);
			    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			
			    try {
			        InputStream reader = new BufferedInputStream(urlConnection.getInputStream());
			        
			        String text = "";
			        int i = 0;
			        while((i=reader.read())!=-1)
			        {
			            text += (char)i;
			        }
			        Log.i("EmonLog", "HTTP Response: "+text);
			        result = text;
			        
			    } catch (Exception e) {
				    Log.i("EmonLog", "HTTP Exception: "+e);
			    }
			    finally {
				    Log.i("EmonLog", "HTTP Disconnecting");
		            urlConnection.disconnect();
			    }
			
		    } catch (Exception e) {
			    e.printStackTrace();
			    Log.i("EmonLog", "HTTP Exception: "+e);
		    }
		
		    return result;
	    }
}
