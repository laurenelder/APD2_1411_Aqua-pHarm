package com.laurenelder.aquapharm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AddContainerActivity extends Activity {

	String tag = "ADD CONTAINER ACTIVITY";
	Context context;
	FileManager fileManager;
	FragmentManager fragMgr;
	Fragment AddContainerFrag;
	Intent thisIntent;
	Intent finishedIntent = new Intent();
	boolean save = false;
	boolean edit = false;
	List <Containers> containerList = new ArrayList<Containers>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_addcontainer);

		context = this;
		thisIntent = this.getIntent();

		// Setup FragmentManager to call methods in FormFragment
		fragMgr = getFragmentManager();
		AddContainerFrag = (AddContainerFragment)fragMgr.findFragmentById(R.id.addContainerFrag);
		if(AddContainerFrag == null) {
			AddContainerFrag = new AddContainerFragment();
		}

		// Check for saved file and parse if file is available
		if (!containerList.isEmpty()) {
			containerList.removeAll(containerList);
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
		
		if (thisIntent.hasExtra("spot")) {
			edit = true;
			((AddContainerFragment) AddContainerFrag).updateFields
				(thisIntent.getExtras().getString("type").toString(), 
				thisIntent.getExtras().getString("length").toString(), 
				thisIntent.getExtras().getString("width").toString(), 
				thisIntent.getExtras().getString("height").toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);

		//       menu.findItem(R.id.action_about).s
		menu.findItem(R.id.action_item_one).setEnabled(false).setVisible(false);

		//       menu.findItem(R.id.action_item_one).setIcon(R.drawable.ic_action_new);
		menu.findItem(R.id.action_item_two).setIcon(R.drawable.ic_action_accept);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.action_item_two) {
			ArrayList<String> fieldInfo = new ArrayList<String>();
			fieldInfo = ((AddContainerFragment) AddContainerFrag).getDataFields();
			if (fieldInfo != null) {
				if (setData(fieldInfo.get(0).toString(), 
						fieldInfo.get(1).toString(), 
						fieldInfo.get(2).toString(), 
						fieldInfo.get(3).toString())) {
					saveData();
				}
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		setResult(RESULT_CANCELED, finishedIntent);
		finish();

		super.onBackPressed();
	}

	/* parseData is passed in a raw string from saved file, parses, and calls
	 * the setData method
	 */
	public void parseData (String rawData) {

		JSONObject mainObject = null;
		JSONArray subObject = null;

		try {
			mainObject = new JSONObject(rawData);
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
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/* setData is called at the end of the parseData method and saves each
	 * JSON object to a custom object in the activity
	 */
	public boolean setData (String type, String length, String width, 
			String height) {
		
		if (edit == true) {
			containerList.remove(thisIntent.getExtras().getInt("spot"));
			Containers newContainer = new Containers(type, length, width,
					height);
			containerList.add(thisIntent.getExtras().getInt("spot"), newContainer);
		} else {
			Containers newContainer = new Containers(type, length, width,
					height);
			containerList.add(newContainer);
		}
		
		return true;
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
		setResult(RESULT_OK, finishedIntent);
		finish();
	}
}
