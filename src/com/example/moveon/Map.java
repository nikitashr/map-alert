package com.example.moveon;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

import com.example.db.SqlHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends Activity{
	
	SqlHandler sqlHandler;
	
	private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGITUDE = "longitude";
    

	private static String latitude = "";
	private static String longitude = "";
    private static String altitude = "";
	
    public static double latMin = 0;
    public static double longMin = 0;
    public static String altMin = "";
    
    public static double latMax = 0;
    public static double longMax = 0;
    public static String altMax = "";
    
	//Google map
		private GoogleMap googleMap;
		TextView tvMax,tvMin;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.result);
			
			sqlHandler = new SqlHandler(this);
			
			tvMax = (TextView)findViewById(R.id.maxAlt);
			tvMin = (TextView)findViewById(R.id.minAlt);
			
			
			// getting intent data
//	        Intent in = getIntent();
//	        
	        // Get JSON values from previous intent
//	        final String latitude = in.getStringExtra(TAG_LATITUDE);
//	        final String longitude = in.getStringExtra(TAG_LONGITUDE);
	        
	        String query0 = "select * from moveon where altitude = (SELECT min(altitude) FROM moveon)order by altitude desc limit 1";
			String query1 = "select * from moveon where altitude = (SELECT max(altitude) FROM moveon)order by altitude desc limit 1";

			Cursor c = sqlHandler.selectQuery(query0);
			if (c != null && c.getCount() != 0){
				c.moveToFirst();
				for(int i=0; i<c.getCount(); i++){
					
					latitude = c.getString(c.getColumnIndex("latitude"));
					longitude = c.getString(c.getColumnIndex("longitude"));
					altitude = c.getString(c.getColumnIndex("altitude"));
					
					c.moveToNext();
				}
				latMin = Double.parseDouble(latitude);
		        longMin = Double.parseDouble(longitude);
		        altMin = altitude;
		        tvMin.setText("Minimum Height: "+altMin+" Meters");
			}
			
			Cursor c1 = sqlHandler.selectQuery(query1);
			if (c1 != null && c1.getCount() != 0){
				c1.moveToFirst();
				for(int i=0; i<c1.getCount(); i++){
					
					latitude = c1.getString(c1.getColumnIndex("latitude"));
					longitude = c1.getString(c1.getColumnIndex("longitude"));
					altitude = c1.getString(c1.getColumnIndex("altitude"));
					
					c1.moveToNext();
				}
				latMax = Double.parseDouble(latitude);
		        longMax = Double.parseDouble(longitude);
		        altMax = altitude;
		        tvMax.setText("Maximum Height: "+altMax+" Meters");
			}
			
	        

	        try{
	        	initializeMap();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	      
	    }

		private void initializeMap() {
//	    	Function to load map
	    	if(googleMap == null){
	    		googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
//	    		setting default location
	    		googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(latMin,longMin) , 12.2f) );
	    		googleMap.setMyLocationEnabled(true);	//show device location
	    		googleMap.addMarker(new MarkerOptions().position(new LatLng(latMin, longMin)).title("Highest Altitude"));
	    		googleMap.addMarker(new MarkerOptions().position(new LatLng(latMax, longMax)).title("Lowest Altitude"));
//	    		checking if the map is successfully created or not ????
	    		if(googleMap == null)
	    			Toast.makeText(getApplicationContext(), "Couldn't show the map", Toast.LENGTH_LONG).show();
	    	}
			
		}
			
}
