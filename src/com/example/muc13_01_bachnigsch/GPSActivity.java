package com.example.muc13_01_bachnigsch;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class GPSActivity extends Activity implements LocationListener {

	private LocationManager mLocationManager;
	private TextView text, altitudeText, longitudeText, latitudeText, accuracyText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// get TextViews
		text = (TextView) findViewById(R.id.gps_text);
		altitudeText = (TextView) findViewById(R.id.gps_altitude_text);
		longitudeText = (TextView) findViewById(R.id.gps_longitude_text);
		latitudeText = (TextView) findViewById(R.id.gps_latitude_text);
		accuracyText = (TextView) findViewById(R.id.gps_accuracy_text);
		
				
		// get LocationManager
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		// set initial texts
		text.setText("Searching for Location...");
		altitudeText.setText("");
		longitudeText.setText("");
		latitudeText.setText("");
		accuracyText.setText("");
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.g, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		// unregister LocationManager
		mLocationManager.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		// register LocationListener
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		text.setText("Location found!");
		
		// accuracy in meters => standard deviation
		float accuracy = location.getAccuracy();
		// altitude in meters above sea level
		double altitude = location.getAltitude();
		// latitude in degrees
		double latitude = location.getLatitude();
		// longitude in degrees
		double longitude = location.getLongitude();
		
		
		// display values
		altitudeText.setText("Current altitude: "+altitude+" meters above sea level");
		latitudeText.setText("Current latitude: "+Location.convert(latitude, Location.FORMAT_DEGREES)+" degrees");
		longitudeText.setText("Current longitude: "+Location.convert(longitude, Location.FORMAT_DEGREES)+" degrees");
		accuracyText.setText("Current standard deviation: "+accuracy+" meters");
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
		text.setText(provider + " enabled!");
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
