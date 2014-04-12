package com.example.moveon;


import com.example.db.SqlHandler;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class Result extends Activity {
	
	SqlHandler sqlHandler;
	
	TextView tvLat,tvLong,tvAlt;
	TableLayout resulttable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testme);
		
		tvLat=(TextView)findViewById(R.id.tvLat);
		tvLong = (TextView)findViewById(R.id.tvLong);
		tvAlt = (TextView)findViewById(R.id.tvAlt);
		resulttable = (TableLayout) findViewById(R.id.resulttable);
		
		sqlHandler = new SqlHandler(this);
		
		try{
			showData();
			
		}catch(Exception ex){
			String error = ex.toString();
			Log.e("Error=================",error);
		}
		
	}

	private void showData() {
		// TODO Auto-generated method stub
		
		String query0 = "select * from moveon where altitude = (SELECT min(altitude) FROM moveon)order by altitude desc limit 1";
		

		String query1 = "select * from moveon where altitude = (SELECT max(altitude) FROM moveon)order by altitude desc limit 1";
		
		Cursor c = sqlHandler.selectQuery(query0);
		
		resulttable.removeAllViews();
		
		if (c != null && c.getCount() != 0){
			c.moveToFirst();
			for(int i=0; i<c.getCount(); i++){
				
				String latitude = c.getString(c.getColumnIndex("latitude"));
				String longitude = c.getString(c.getColumnIndex("longitude"));
				String altitude = c.getString(c.getColumnIndex("altitude"));
				
				TableRow tr = new TableRow(getApplicationContext());
				LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				tr.setLayoutParams(params);
				
				TextView tvLat = new TextView(getApplicationContext());
				tvLat.setText(latitude);
				Log.d("Latitude stored ::::::::::::::::::::::::::::::::::::",latitude);
				TextView tvLong = new TextView(getApplicationContext());
				tvLong.setText(longitude);
				Log.d("Longitude stored ::::::::::::::::::::::::::::::::::::",longitude);
				TextView tvAlt = new TextView(getApplicationContext());
				tvAlt.setText(altitude);
				Log.d("Altitude stored ::::::::::::::::::::::::::::::::::::",altitude);

				tr.addView(tvLat);
				tr.addView(tvLong);
				tr.addView(tvAlt);
				resulttable.addView(tr);
				
				c.moveToNext();
			}
		}
		
	}
		
}
