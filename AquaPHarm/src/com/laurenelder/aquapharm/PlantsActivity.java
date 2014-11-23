package com.laurenelder.aquapharm;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class PlantsActivity extends Activity implements TabListener, PlantsFragment.OnSelected {

	Context context;
	String tag = "PlantsActivity";
	FileManager fileManager;
	boolean switched;
	String fileName;
	Intent plantIntent;
	FragmentManager fragMgr;
	Fragment plantFrag;
	Double annualLowAverage = 0.0;
	Double annualHighAverage = 0.0;
	Double winterLowAverage = 0.0;
	Double winterHighAverage = 0.0;
	Double springLowAverage = 0.0;
	Double springHighAverage = 0.0;
	Double summerLowAverage = 0.0;
	Double summerHighAverage = 0.0;
	Double fallLowAverage = 0.0;
	Double fallHighAverage = 0.0;
	List<Plants> plantsList = new ArrayList<Plants>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_plants);

		context = this;
		switched = false;

		plantIntent = this.getIntent();

		// Get weather data that has passed through the Intent
		if (plantIntent.hasExtra("annualLowAverage")) {
			annualLowAverage = plantIntent.getDoubleExtra("annualLowAverage", annualLowAverage);
			annualHighAverage = plantIntent.getDoubleExtra("annualHighAverage", annualHighAverage);
			winterLowAverage = plantIntent.getDoubleExtra("winterLowAverage", winterLowAverage);
			winterHighAverage = plantIntent.getDoubleExtra("winterHighAverage", winterHighAverage);
			springLowAverage = plantIntent.getDoubleExtra("springLowAverage", springLowAverage);
			springHighAverage = plantIntent.getDoubleExtra("springHighAverage", springHighAverage);
			summerLowAverage = plantIntent.getDoubleExtra("summerLowAverage", summerLowAverage);
			summerHighAverage = plantIntent.getDoubleExtra("summerHighAverage", summerHighAverage);
			fallLowAverage = plantIntent.getDoubleExtra("fallLowAverage", fallLowAverage);
			fallHighAverage = plantIntent.getDoubleExtra("fallHighAverage", fallHighAverage);
		}

		// Setup FragmentManager to call methods in FormFragment
		fragMgr = getFragmentManager();
		plantFrag = (PlantsFragment)fragMgr.findFragmentById(R.id.plantsFrag);
		if(plantFrag == null) {
			plantFrag = new AddContainerFragment();
		}

		// Add Action Bar tabs
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab actionBarTab = actionBar.newTab();
		actionBarTab.setText(R.string.actionbar_buildsystem).setTabListener(this);
		actionBar.addTab(actionBarTab);
		actionBarTab = actionBar.newTab();
		actionBarTab.setText(R.string.actionbar_fish).setTabListener(this);
		actionBar.addTab(actionBarTab);
		actionBarTab = actionBar.newTab();
		actionBarTab.setText(R.string.actionbar_plants).setTabListener(this);
		actionBar.addTab(actionBarTab);

		actionBar.setSelectedNavigationItem(2);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);

		menu.findItem(R.id.action_item_one).setEnabled(false).setVisible(false);

		menu.findItem(R.id.action_item_two).setIcon(R.drawable.ic_action_about);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.action_item_two) {
			new AboutDialog();
			AboutDialog dialogFrag = AboutDialog.newInstance(context);
			dialogFrag.show(getFragmentManager(), "about_dialog");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Pass weather data through Intents for the tab navigation
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		int tabIndex = tab.getPosition();
		if (tabIndex == 0) {
			if (switched == true) {
				Intent buildIntent = new Intent(context, MainActivity.class);
				buildIntent.putExtra("annualLowAverage", annualLowAverage);
				buildIntent.putExtra("annualHighAverage", annualHighAverage);
				buildIntent.putExtra("winterLowAverage", winterLowAverage);
				buildIntent.putExtra("winterHighAverage", winterHighAverage);
				buildIntent.putExtra("springLowAverage", springLowAverage);
				buildIntent.putExtra("springHighAverage", springHighAverage);
				buildIntent.putExtra("summerLowAverage", summerLowAverage);
				buildIntent.putExtra("summerHighAverage", summerHighAverage);
				buildIntent.putExtra("fallLowAverage", fallLowAverage);
				buildIntent.putExtra("fallHighAverage", fallHighAverage);
				startActivity(buildIntent);
			}
		}
		if (tabIndex == 1) {
			//			switched = true;
			if (switched == true) {
				Intent fishIntent = new Intent(context, FishActivity.class);
				fishIntent.putExtra("annualLowAverage", annualLowAverage);
				fishIntent.putExtra("annualHighAverage", annualHighAverage);
				fishIntent.putExtra("winterLowAverage", winterLowAverage);
				fishIntent.putExtra("winterHighAverage", winterHighAverage);
				fishIntent.putExtra("springLowAverage", springLowAverage);
				fishIntent.putExtra("springHighAverage", springHighAverage);
				fishIntent.putExtra("summerLowAverage", summerLowAverage);
				fishIntent.putExtra("summerHighAverage", summerHighAverage);
				fishIntent.putExtra("fallLowAverage", fallLowAverage);
				fishIntent.putExtra("fallHighAverage", fallHighAverage);
				startActivity(fishIntent);
			}
		}
		if (tabIndex == 2) {
			if (switched == false) {
				if (!plantsList.isEmpty()) {
					plantsList.removeAll(plantsList);
				}
				fileManager = FileManager.getInstance();
				fileName = getResources().getString(R.string.plants_file_name);
				checkForFile();
				Log.i(tag, "Plant tab hit!");
			}
			switched = true;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	// Check for Fish JSON file and parse if available
	public boolean checkForFile() {
		boolean fileAvailable = false;

		Log.i(tag, "CheckForFile hit");
		String fileContent = fileManager.readFromFile(context, null, getResources().openRawResource(R.raw.plants));
		Log.i(tag, "File Check is Running");
		if (!fileContent.isEmpty()) {
			parseData(fileContent.toString());
			fileAvailable = true;
		}
		return fileAvailable;
	}

	// Get and Parse JSON Function... (plantData = Raw JSON code)
	public Boolean parseData(String plantData) {

		Log.i(tag, "parseData hit");
		Boolean completed = false;
		String jsonString = null;
		if (plantData != null) {
			jsonString = plantData;
		}

		// Parse JSON
		try {
			// Creating JSONObject from String
			JSONObject mainObject = new JSONObject(jsonString);
			JSONArray subObject = mainObject.getJSONArray("plants");

			for (int i = 0; i < subObject.length(); i ++) {
				JSONObject plantObject = subObject.getJSONObject(i);

				// Class Specific Data
				String plantName = plantObject.getString("name");
				String plantMinTemp = plantObject.getString("mintemp");
				String plantMaxTemp = plantObject.getString("maxtemp");
				String plantImage = plantObject.getString("image");
				String plantEdible = plantObject.getString("edible");
				/*				Log.i(tag, plantName);
				Log.i(tag, plantMinTemp);
				Log.i(tag, plantMaxTemp);
				Log.i(tag, plantImage);*/
				Integer minTempInt = Integer.parseInt(plantMinTemp);
				Integer maxTempInt = Integer.parseInt(plantMaxTemp);

				// Save Data Here
				setClass(plantName, minTempInt, maxTempInt, plantImage, plantEdible);
			}
			completed = true;
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(tag, e.getMessage().toString());
			Log.i(tag, "Something went wrong parsing plants");
			e.printStackTrace();
		}
		return completed;
	}

	// Save Parsed Data to Class
	public void setClass(String name, Integer minTemp, Integer maxTemp, String image, String edible) {
		Log.i(tag, "setClass hit");
		Plants newPlant = new Plants(name, minTemp, maxTemp, image, edible);
		plantsList.add(newPlant);
	}

	@Override
	public ArrayList<String> getData(int pos) {
		// TODO Auto-generated method stub
		ArrayList<String> item = new ArrayList<String>();
		item.add(plantsList.get(pos).name);
		item.add(plantsList.get(pos).minTemp.toString());
		item.add(plantsList.get(pos).maxTemp.toString());
		item.add(plantsList.get(pos).image);
		item.add(plantsList.get(pos).edible);

		Log.i(tag, item.toString());
		return item;
	}

	// getTemps passes weather data to FishFragment
	public ArrayList<Double> getTemps() {
		ArrayList<Double> temps = new ArrayList<Double>();
		temps.add(annualLowAverage);
		temps.add(annualHighAverage);
		temps.add(winterLowAverage);
		temps.add(winterHighAverage);
		temps.add(springLowAverage);
		temps.add(springHighAverage);
		temps.add(summerLowAverage);
		temps.add(summerHighAverage);
		temps.add(fallLowAverage);
		temps.add(fallHighAverage);
		return temps;
	}
}
