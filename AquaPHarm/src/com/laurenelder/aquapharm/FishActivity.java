package com.laurenelder.aquapharm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class FishActivity extends Activity implements TabListener, FishFragment.OnSelected {

	String tag = "FISH ACTIVITY";
	Context context;
	FileManager fileManager;
	boolean switched;
	String fileName;
	Intent fishIntent;
	FragmentManager fragMgr;
	Fragment fishFrag;
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
	List<Fish> fishList = new ArrayList<Fish>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_fish);

		context = this;
		switched = false;

		fishIntent = this.getIntent();

		if (fishIntent.hasExtra("annualLowAverage")) {
			annualLowAverage = fishIntent.getDoubleExtra("annualLowAverage", annualLowAverage);
			annualHighAverage = fishIntent.getDoubleExtra("annualHighAverage", annualHighAverage);
			winterLowAverage = fishIntent.getDoubleExtra("winterLowAverage", winterLowAverage);
			winterHighAverage = fishIntent.getDoubleExtra("winterHighAverage", winterHighAverage);
			springLowAverage = fishIntent.getDoubleExtra("springLowAverage", springLowAverage);
			springHighAverage = fishIntent.getDoubleExtra("springHighAverage", springHighAverage);
			summerLowAverage = fishIntent.getDoubleExtra("summerLowAverage", summerLowAverage);
			summerHighAverage = fishIntent.getDoubleExtra("summerHighAverage", summerHighAverage);
			fallLowAverage = fishIntent.getDoubleExtra("fallLowAverage", fallLowAverage);
			fallHighAverage = fishIntent.getDoubleExtra("fallHighAverage", fallHighAverage);
		}

		// Setup FragmentManager to call methods in FormFragment
		fragMgr = getFragmentManager();
		fishFrag = (FishFragment)fragMgr.findFragmentById(R.id.fishFrag);
		if(fishFrag == null) {
			fishFrag = new AddContainerFragment();
		}

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

		actionBar.setSelectedNavigationItem(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);

		menu.findItem(R.id.action_item_one).setEnabled(false).setVisible(false);

		//        menu.findItem(R.id.action_item_one).setIcon(R.drawable.ic_action_new);
		menu.findItem(R.id.action_item_two).setIcon(R.drawable.ic_action_about);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		int tabIndex = tab.getPosition();
		if (tabIndex == 0) {
			if (switched == true) {
				Intent buildIntent = new Intent(this, MainActivity.class);
				startActivity(buildIntent);
			}
		}
		if (tabIndex == 1) {
			if (switched == false) {
				if (!fishList.isEmpty()) {
					fishList.removeAll(fishList);
				}
				fileManager = FileManager.getInstance();
				fileName = getResources().getString(R.string.fish_file_name);
				checkForFile();
//				Log.i(tag, "Fish tab hit!");
			}
			switched = true;
		}
		if (tabIndex == 2) {
			if (switched == true) {
				Intent plantsIntent = new Intent(context, PlantsActivity.class);
				plantsIntent.putExtra("annualLowAverage", annualLowAverage);
				plantsIntent.putExtra("annualHighAverage", annualHighAverage);
				plantsIntent.putExtra("winterLowAverage", winterLowAverage);
				plantsIntent.putExtra("winterHighAverage", winterHighAverage);
				plantsIntent.putExtra("springLowAverage", springLowAverage);
				plantsIntent.putExtra("springHighAverage", springHighAverage);
				plantsIntent.putExtra("summerLowAverage", summerLowAverage);
				plantsIntent.putExtra("summerHighAverage", summerHighAverage);
				plantsIntent.putExtra("fallLowAverage", fallLowAverage);
				plantsIntent.putExtra("fallHighAverage", fallHighAverage);
				startActivity(plantsIntent);
			}
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

	}

	public boolean checkForFile() {
		boolean fileAvailable = false;

//		Log.i(tag, "CheckForFile hit");
		String fileContent = fileManager.readFromFile(context, null, getResources().openRawResource(R.raw.fish));
//		Log.i(tag, "File Check is Running");
		if (!fileContent.isEmpty()) {
			parseData(fileContent.toString());
			fileAvailable = true;
		}
		return fileAvailable;
	}

	// Get and Parse JSON Function... (fishData = Raw JSON code)
	public Boolean parseData(String fishData) {

//		Log.i(tag, "parseData hit");
		Boolean completed = false;
		String jsonString = null;
		if (fishData != null) {
			jsonString = fishData;
		}

		// Parse JSON
		try {
			// Creating JSONObject from String
			JSONObject mainObject = new JSONObject(jsonString);
			JSONArray subObject = mainObject.getJSONArray("fish");

			for (int i = 0; i < subObject.length(); i ++) {
				JSONObject fishObject = subObject.getJSONObject(i);

				// Class Specific Data
				String fishName = fishObject.getString("name");
				String fishMinTemp = fishObject.getString("mintemp");
				String fishMaxTemp = fishObject.getString("maxtemp");
				String fishImage = fishObject.getString("image");
				String fishEdible = fishObject.getString("edible");
				/*				Log.i(tag, fishName);
				Log.i(tag, fishMinTemp);
				Log.i(tag, fishMaxTemp);
				Log.i(tag, fishImage);*/
				Integer minTempInt = Integer.parseInt(fishMinTemp);
				Integer maxTempInt = Integer.parseInt(fishMaxTemp);

				// Save Data Here
				setClass(fishName, minTempInt, maxTempInt, fishImage, fishEdible);
			}
			completed = true;
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(tag, e.getMessage().toString());
			Log.i(tag, "Something went wrong parsing fish");
			e.printStackTrace();
		}
		return completed;
	}

	// Save Parsed Data to Class
	public void setClass(String name, Integer minTemp, Integer maxTemp, String image,String edible) {
//		Log.i(tag, "setClass hit");
		Fish newFish = new Fish(name, minTemp, maxTemp, image, edible);
		fishList.add(newFish);
	}

	@Override
	public ArrayList getData(int pos) {
		// TODO Auto-generated method stub
		ArrayList<String> item = new ArrayList<String>();
		item.add(fishList.get(pos).name);
		item.add(fishList.get(pos).minTemp.toString());
		item.add(fishList.get(pos).maxTemp.toString());
		item.add(fishList.get(pos).image);
		item.add(fishList.get(pos).edible);
//		Log.i(tag, item.toString());
		
		return item;
	}
	
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
