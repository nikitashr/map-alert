package com.example.mapalert;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

	private RadioGroup radioOptionGroup;
	private RadioButton radioOptionButton;
	
	// GPSTracker class
		GPSTracker gps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnAlert = (Button)findViewById(R.id.btnAlert);
		radioOptionGroup = (RadioGroup) findViewById(R.id.radioOption);
		
		btnAlert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 // get selected radio button from radioGroup
				int selectedId = radioOptionGroup.getCheckedRadioButtonId();
				
				// find the radiobutton by returned id
		        radioOptionButton = (RadioButton) findViewById(selectedId);
		        
		        Toast.makeText(MainActivity.this,radioOptionButton.getText(), Toast.LENGTH_SHORT).show();
		        
		     // create class object
		        gps = new GPSTracker(MainActivity.this);
		        
		     // check if GPS enabled		
		        if(gps.canGetLocation()){
		        	
		        	double latitude = gps.getLatitude();
		        	double longitude = gps.getLongitude();
		        	
		        	
		        	Intent i = new Intent(Intent.ACTION_SEND);
		        	i.setType("text/plain");
		        	i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"vparajuli819@gmail.com"});
		        	i.putExtra(Intent.EXTRA_SUBJECT, radioOptionButton.getText());
		        	i.putExtra(Intent.EXTRA_TEXT   , "Please Help, at:\nLatitude: "+latitude+
			        		"\nLongitude: "+longitude+"\n View place: https://maps.google.com.np/maps?client=firefox-a&channel=sb&ie=UTF-8&q="+latitude+","+longitude);
		        	try {
		        	    startActivity(Intent.createChooser(i, "Send mail..."));
		        	} catch (android.content.ActivityNotFoundException ex) {
		        	    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		        	}
		        	
		        	
//		        	Intent i = new Intent(Intent.ACTION_SENDTO);
//			        i.setType("text/html");
//			        i.putExtra(Intent.EXTRA_SUBJECT, radioOptionButton.getText());
//			        i.putExtra(Intent.EXTRA_TEXT, "Please Help, at:\nLatitude: "+latitude+
//			        		"\nLongitude: "+longitude);
//			        i.setData(Uri.parse("vparajuli819@gmail.com"));
//			        startActivity(i);
		        	
//		        	Toast.makeText(getApplicationContext(), "Your request has been send" + longitude, Toast.LENGTH_LONG).show();	
		        }else{
		        	// can't get location
		        	// GPS or Network is not enabled
		        	// Ask user to enable GPS/network in settings
		        	gps.showSettingsAlert();
		        }
		        
			}
		});

		
	}

}
