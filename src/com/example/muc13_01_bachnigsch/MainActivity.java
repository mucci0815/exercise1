package com.example.muc13_01_bachnigsch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    /** Called when the user clicks the Light button */
    public void callLight(View view) {
    	Intent intent = new Intent(this, LightActivity.class);
    	startActivity(intent);
    }
    
    
    /** Called when the user clicks the Accelometer button */
    public void callAccelometer(View view) {
    	Intent intent = new Intent(this, AccelerometerActivity.class);
    	startActivity(intent);
    }
    
    /** Called when the user clicks the Orientation button */
    public void callOrientation(View view) {
    	Intent intent = new Intent(this, OrientationActivity.class);
    	startActivity(intent);
    }
    
    /** Called when the user clicks the GPS button */
    public void callGPS(View view) {
    	Intent intent = new Intent(this, GPSActivity.class);
    	startActivity(intent);
    }
    
}
