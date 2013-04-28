package com.example.muc13_01_bachnigsch;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

public class AccelerometerActivity extends Activity implements
		SensorEventListener {
	
	// colors for different axes in chart
	private final int[] seriesColors = { Color.BLUE, Color.RED, Color.YELLOW };

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private TextView text;
	private float[] acceleration = new float[3];
	
	// initial timestamp
	private long initTimestamp = -1;

	// The main dataset that includes all the series that go into a chart
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	// The main renderer that includes all the renderers customizing a chart
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	// The Chart view that displays the data
	private GraphicalView mChartView;

	// XYSeries for x-,y- and z-axis
	private XYSeries[] mSeries = new XYSeries[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerometer);
		// Show the Up button in the action bar.
		setupActionBar();

		// get TextViews
		text = (TextView) findViewById(R.id.acc_text);

		// get sensormanager
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// get accelerometers
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
			mAccelerometer = mSensorManager
					.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

			text.setText("Found Sensor");
		} else {
			text.setText("No Accelerometer found!");
		}

		// set some properties on the main renderer
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.BLACK);
		mRenderer.setAxisTitleTextSize(16);
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(false);
		mRenderer.setPointSize(1);
		mRenderer.setRange(new double[] {0.0, 1000000.0, -3.0, 3.0});

		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
		mChartView = ChartFactory.getLineChartView(this, mDataset, mRenderer);

		// Create series
		mSeries[0] = new XYSeries("X-Axis");
		mSeries[1] = new XYSeries("Y-Axis");
		mSeries[2] = new XYSeries("Z-Axis");
		mDataset.addSeries(mSeries[0]);
		mDataset.addSeries(mSeries[1]);
		mDataset.addSeries(mSeries[2]);

		// Create renderer
		for (int i = 0; i < 3; ++i) {
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			mRenderer.addSeriesRenderer(renderer);
			// set some renderer properties
			renderer.setPointStyle(PointStyle.CIRCLE);
			renderer.setFillPoints(true);
			renderer.setDisplayChartValues(false);
			renderer.setColor(seriesColors[i]);
		}

		layout.addView(mChartView);
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
		getMenuInflater().inflate(R.menu.accelerometer, menu);
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
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		acceleration[0] = event.values[0];
		acceleration[1] = event.values[1];
		acceleration[2] = event.values[2];

		// first time: init timestamp
		if(initTimestamp == -1) initTimestamp = event.timestamp;
		
		// get timestamp in seconds
		float ts = (float) ((event.timestamp - initTimestamp) / 1000000000.);

		// Draw Values
		mSeries[0].add(ts, acceleration[0]);
		mSeries[1].add(ts, acceleration[1]);
		mSeries[2].add(ts, acceleration[2]);
		
		// delete old series
		for(int i = 0; i < 3; ++i) {
			if(mSeries[i].getItemCount() > 100)
				mSeries[i].remove(0);
		}
		
		// adjust visible range
		mRenderer.setRange(new double[] {mSeries[0].getX(0), ts, -10.0, 10.0});

		// repaint chart
		mChartView.repaint();

	}

}
