package com.laurenelder.aquapharm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
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
	List<Fish> fishList = new ArrayList<Fish>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_fish);
		
		context = this;
		switched = false;
		
		
		
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
				Log.i(tag, "Fish tab hit!");
			}
			switched = true;
		}
		if (tabIndex == 2) {
			if (switched == true) {
				Intent plantsIntent = new Intent(context, PlantsActivity.class);
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
    	
			Log.i(tag, "CheckForFile hit");
        	String fileContent = fileManager.readFromFile(context, null, getResources().openRawResource(R.raw.fish));
        	Log.i(tag, "File Check is Running");
        	if (!fileContent.isEmpty()) {
        		parseData(fileContent.toString());
        		fileAvailable = true;
        	}
        return fileAvailable;
	}

	// Get and Parse JSON Function... (fishData = Raw JSON code)
	public Boolean parseData(String fishData) {

		Log.i(tag, "parseData hit");
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
				Log.i(tag, fishName);
				Log.i(tag, fishMinTemp);
				Log.i(tag, fishMaxTemp);
				Log.i(tag, fishImage);
				Integer minTempInt = Integer.parseInt(fishMinTemp);
				Integer maxTempInt = Integer.parseInt(fishMaxTemp);

				// Save Data Here
				setClass(fishName, minTempInt, maxTempInt, fishImage);
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
	public void setClass(String name, Integer minTemp, Integer maxTemp, String image) {
		Log.i(tag, "setClass hit");
			Fish newFish = new Fish(name, minTemp, maxTemp, image);
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
		Log.i(tag, item.toString());
		return item;
	}
}
