package com.laurenelder.aquapharm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
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
import android.widget.TabHost;


public class MainActivity extends Activity implements TabListener, MainFragment.mainInterface {

	String tag = "MAIN ACTIVITY";
	Context context;
	FileManager fileManager;
	FragmentManager fragMgr;
	Fragment mainFrag;
	boolean switched;
	Double cap;
	List <Containers> containerList = new ArrayList<Containers>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

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

		actionBar.setSelectedNavigationItem(0);

		// Setup FragmentManager to call methods in FormFragment
		fragMgr = getFragmentManager();
		mainFrag = (MainFragment)fragMgr.findFragmentById(R.id.mainFrag);
		if(mainFrag == null) {
			mainFrag = new AddContainerFragment();
		}

		checkForFile();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		//       menu.findItem(R.id.action_about).s
		menu.findItem(R.id.action_item_one).setEnabled(true).setVisible(true);

		menu.findItem(R.id.action_item_one).setIcon(R.drawable.ic_action_new);
		menu.findItem(R.id.action_item_two).setIcon(R.drawable.ic_action_about);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_item_one) {
			Intent addIntent = new Intent(context, AddContainerActivity.class);
			startActivityForResult(addIntent, 1);
			return true;
		}
		if (id == R.id.action_item_two) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		int tabIndex = tab.getPosition();
		if (tabIndex == 0) {
			switched = true;
		}
		if (tabIndex == 1) {
			//			switched = true;
			if (switched == true) {
				Intent fishIntent = new Intent(context, FishActivity.class);
				startActivity(fishIntent);
			}
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				checkForFile();
			}
			if (requestCode == 2) {
				String ctype = data.getExtras().getString("type").toString();
				String clength = data.getExtras().getString("length").toString();
				String cwidth = data.getExtras().getString("width").toString();
				String cheight = data.getExtras().getString("height").toString();
				String button = data.getExtras().getString("button").toString();
				int spot = 0;
				
				Log.i(tag, "requestCode 2 hit");
				Log.i(tag, containerList.toString());
				
				for (int r = 0; r < containerList.size(); r++) {
					if (ctype == containerList.get(r).type.toString() && 
							clength == containerList.get(r).length.toString() && 
							cwidth == containerList.get(r).width.toString() && 
							cheight == containerList.get(r).height.toString()) {
						spot = r;
//						Log.i(tag, "CallBack hit - " + spot.toString());
					}
				}
				
				if (button.matches("edit")) {
					Intent addIntent = new Intent(context, AddContainerActivity.class);
					addIntent.putExtra("type", ctype);
					addIntent.putExtra("length", clength);
					addIntent.putExtra("width", cwidth);
					addIntent.putExtra("height", cheight);
					addIntent.putExtra("spot", spot);
					startActivityForResult(addIntent, 1);
				}
				if (button.matches("delete")) {
//					Log.i(tag, "Delete Hit" + "-" + spot.toString());
					containerList.remove(spot);
					saveData();
				}
			}
		}
	}
	
	public void startContainerActivity(int pos) {
		Intent containerIntent = new Intent(context, ContainerActivity.class);
		containerIntent.putExtra("type", containerList.get(pos).type);
		containerIntent.putExtra("length", containerList.get(pos).length);
		containerIntent.putExtra("width", containerList.get(pos).width);
		containerIntent.putExtra("height", containerList.get(pos).height);
		startActivityForResult(containerIntent, 2);
	}
	
	public void checkForFile() {
		// Check for saved file and parse if file is available
		if (!containerList.isEmpty()) {
			containerList.removeAll(containerList);
			((MainFragment) mainFrag).clearList();
		}
		fileManager = FileManager.getInstance();
		File checkForFile = getBaseContext().getFileStreamPath(getResources()
				.getString(R.string.container_file_name));
		if (checkForFile.exists()) {
			String fileContent = fileManager.readFromFile(context, getResources()
					.getString(R.string.container_file_name), null);
			if (fileContent != null) {
				parseData(fileContent.toString());
				Log.i(tag, fileContent.toString());
			}
		}
	}
	
	/* parseData is passed in a raw string from saved file, parses, and calls
	 * the setData method
	 */
	public void parseData (String rawData) {

		JSONObject mainObject = null;
		JSONArray subObject = null;

		try {
			mainObject = new JSONObject(rawData);
			Log.i(tag, rawData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (mainObject != null) {
				subObject = mainObject.getJSONArray("containers");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < subObject.length(); i++) {

			JSONObject dataObj = null;
			try {
				if (subObject != null) {
					dataObj = subObject.getJSONObject(i);

					String containerType = dataObj.getString("type");
					String containerLength = dataObj.getString("length");
					String containerWidth = dataObj.getString("width");
					String containerHeight = dataObj.getString("height");

					Log.i(tag, containerType.toString());
					Log.i(tag, containerLength.toString());
					Log.i(tag, containerWidth.toString());
					Log.i(tag, containerHeight.toString());

					setData(containerType, containerLength, containerWidth, containerHeight);
					((MainFragment) mainFrag).addData(containerType, containerLength, containerWidth, containerHeight);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		((MainFragment) mainFrag).updateListView();
	}

	/* setData is called at the end of the parseData method and saves each
	 * JSON object to a custom object in the activity
	 */
	public boolean setData (String type, String length, String width, 
			String height) {

		Containers newContainer = new Containers(type, length, width,
				height);
		containerList.add(newContainer);
		return true;
	}
	
	public Double getTotalCapacity() {
		Double cap = 0.00;
		for (int q = 0; q < containerList.size(); q++) {
			Integer cuInches = Integer.parseInt(containerList.get(q).length) * 
					Integer.parseInt(containerList.get(q).width) * 
					Integer.parseInt(containerList.get(q).height);
			Log.i(tag, cuInches.toString());
			
			Double cuFoot = cuInches / 1728.00;
			
			Double gallons = 7.48 * cuFoot;
			
			cap = cap + gallons;
		}
		return cap;
	}
	
	public Double getCycleRate(Integer flow) {
		Double cycleRate = cap / flow;
		return cycleRate;
	}
	
	/* saveData method is called by the action bar button.
	 * This method builds a JSON file from the objects store within custom
	 * objects and calls writeToFile to save the JSON file to the device.
	 */
	public void saveData() {
		String preJson = "{'containers':[";
		String contentJson = null;
		String postJson = "]}";
		Log.i(tag, "saveData method hit");
		for (int i = 0; i < containerList.size(); i++) {
			if (i == 0) {
				contentJson = "{'type':" + "'" + containerList.get(i).type.toString() + "'" + "," +
						"'length':" + "'" + containerList.get(i).length.toString() + "'" + "," +
						"'width':" + "'" + containerList.get(i).width.toString() + "'" + "," +
						"'height':" + "'" + containerList.get(i).height.toString() + "'" +  "}";
			} else {
				contentJson = contentJson + "{'type':" + "'" + containerList.get(i).type.toString() + "'" + "," +
						"'length':" + "'" + containerList.get(i).length.toString() + "'" + "," +
						"'width':" + "'" + containerList.get(i).length.toString() + "'" + "," +
						"'height':" + "'" + containerList.get(i).height.toString() + "'" + "}";
			}

			if (i != containerList.size() - 1) {
				contentJson = contentJson + ",";
			}	
		}
		String fullJson = preJson + contentJson + postJson;
		Log.i(tag, fullJson);
		fileManager.writeToFile(context, getResources().getString(R.string.container_file_name), fullJson);
		checkForFile();
	}
}
