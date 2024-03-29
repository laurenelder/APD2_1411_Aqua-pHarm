package com.laurenelder.aquapharm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity implements TabListener, MainFragment.mainInterface, 
LocationListener {

	String tag = "MAIN ACTIVITY";
	Context context;
	Intent buildIntent;
	FileManager fileManager;
	FragmentManager fragMgr;
	Fragment mainFrag;
	LocationManager locMgr;
	Location loc;
	boolean switched;
	Double cap;
	URL url = null;
	Integer apiNum = 0;
	String modifiedDate;
	String latit;
	String longit;
	ActionBar actionBar;
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
	List <Containers> containerList = new ArrayList<Containers>();
	List <Weather> weatherInfo = new ArrayList<Weather>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		context = this;
		switched = false;

		buildIntent = this.getIntent();

		if (buildIntent.hasExtra("annualLowAverage")) {
			annualLowAverage = buildIntent.getDoubleExtra("annualLowAverage", annualLowAverage);
			annualHighAverage = buildIntent.getDoubleExtra("annualHighAverage", annualHighAverage);
			winterLowAverage = buildIntent.getDoubleExtra("winterLowAverage", winterLowAverage);
			winterHighAverage = buildIntent.getDoubleExtra("winterHighAverage", winterHighAverage);
			springLowAverage = buildIntent.getDoubleExtra("springLowAverage", springLowAverage);
			springHighAverage = buildIntent.getDoubleExtra("springHighAverage", springHighAverage);
			summerLowAverage = buildIntent.getDoubleExtra("summerLowAverage", summerLowAverage);
			summerHighAverage = buildIntent.getDoubleExtra("summerHighAverage", summerHighAverage);
			fallLowAverage = buildIntent.getDoubleExtra("fallLowAverage", fallLowAverage);
			fallHighAverage = buildIntent.getDoubleExtra("fallHighAverage", fallHighAverage);
		}

		// Setup FragmentManager to call methods in FormFragment
		fragMgr = getFragmentManager();
		mainFrag = (MainFragment)fragMgr.findFragmentById(R.id.mainFrag);
		if(mainFrag == null) {
			mainFrag = new AddContainerFragment();
		}

//		If temperature is already available add tabs right away. If not add tabs after API data is
//		returned.
		if (annualLowAverage == 0.0) {
			// Setup LocationManager for GPS updates
			locMgr = (LocationManager)getSystemService(LOCATION_SERVICE);
			locMgr.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
			loc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		} else {
			actionBar = getActionBar();
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
		}


		checkForFile();

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

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
			new AboutDialog();
			AboutDialog dialogFrag = AboutDialog.newInstance(context);
			dialogFrag.show(getFragmentManager(), "about_dialog");
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
				calculateTemps();
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
/*				Log.i(tag, "Annual Average Low Temp: " + String.valueOf(annualLowAverage));
				Log.i(tag, "Annual Average High Temp: " + String.valueOf(annualHighAverage));
				Log.i(tag, "Winter Average Low Temp: " + String.valueOf(winterLowAverage));
				Log.i(tag, "Winter Average High Temp: " + String.valueOf(winterHighAverage));
				Log.i(tag, "Spring Average Low Temp: " + String.valueOf(springLowAverage));
				Log.i(tag, "Spring Average High Temp: " + String.valueOf(springHighAverage));
				Log.i(tag, "Summer Average Low Temp: " + String.valueOf(summerLowAverage));
				Log.i(tag, "Summer Average High Temp: " + String.valueOf(summerHighAverage));
				Log.i(tag, "Fall Average Low Temp: " + String.valueOf(fallLowAverage));
				Log.i(tag, "Fall Average High Temp: " + String.valueOf(fallHighAverage));*/
				startActivity(fishIntent);
			}
		}
		
		if (tabIndex == 2) {
			if (switched == true) {
				calculateTemps();
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

	// Modify data depending on what result is returned from activity.
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (data.hasExtra("annualLowAverage")) {
			annualLowAverage = data.getDoubleExtra("annualLowAverage", annualLowAverage);
			annualHighAverage = data.getDoubleExtra("annualHighAverage", annualHighAverage);
			winterLowAverage = data.getDoubleExtra("winterLowAverage", winterLowAverage);
			winterHighAverage = data.getDoubleExtra("winterHighAverage", winterHighAverage);
			springLowAverage = data.getDoubleExtra("springLowAverage", springLowAverage);
			springHighAverage = data.getDoubleExtra("springHighAverage", springHighAverage);
			summerLowAverage = data.getDoubleExtra("summerLowAverage", summerLowAverage);
			summerHighAverage = data.getDoubleExtra("summerHighAverage", summerHighAverage);
			fallLowAverage = data.getDoubleExtra("fallLowAverage", fallLowAverage);
			fallHighAverage = data.getDoubleExtra("fallHighAverage", fallHighAverage);
		}

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
				int position = data.getExtras().getInt("position");

//				Log.i(tag, "requestCode 2 hit");
//				Log.i(tag, containerList.toString());

				if (button.matches("edit")) {
					Intent addIntent = new Intent(context, AddContainerActivity.class);
					addIntent.putExtra("type", ctype);
					addIntent.putExtra("length", clength);
					addIntent.putExtra("width", cwidth);
					addIntent.putExtra("height", cheight);
					addIntent.putExtra("spot", position);
					addIntent.putExtra("annualLowAverage", annualLowAverage);
					addIntent.putExtra("annualHighAverage", annualHighAverage);
					addIntent.putExtra("winterLowAverage", winterLowAverage);
					addIntent.putExtra("winterHighAverage", winterHighAverage);
					addIntent.putExtra("springLowAverage", springLowAverage);
					addIntent.putExtra("springHighAverage", springHighAverage);
					addIntent.putExtra("summerLowAverage", summerLowAverage);
					addIntent.putExtra("summerHighAverage", summerHighAverage);
					addIntent.putExtra("fallLowAverage", fallLowAverage);
					addIntent.putExtra("fallHighAverage", fallHighAverage);
					startActivityForResult(addIntent, 1);
				}
				if (button.matches("delete")) {
//					Log.i(tag, "Delete Hit" + "-" + spot.toString());

					if (containerList.size() > 1) {
						containerList.remove(position);
						saveData();
					} else {
						containerList.removeAll(containerList);
						File checkForFileExist = getBaseContext().getFileStreamPath(getResources()
								.getString(R.string.container_file_name));
						if (checkForFileExist.exists()) {
							checkForFileExist.delete();
						}
						((MainFragment) mainFrag).clearList();
						((MainFragment) mainFrag).updateListView();
					}
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
		containerIntent.putExtra("position", pos);
		containerIntent.putExtra("annualLowAverage", annualLowAverage);
		containerIntent.putExtra("annualHighAverage", annualHighAverage);
		containerIntent.putExtra("winterLowAverage", winterLowAverage);
		containerIntent.putExtra("winterHighAverage", winterHighAverage);
		containerIntent.putExtra("springLowAverage", springLowAverage);
		containerIntent.putExtra("springHighAverage", springHighAverage);
		containerIntent.putExtra("summerLowAverage", summerLowAverage);
		containerIntent.putExtra("summerHighAverage", summerHighAverage);
		containerIntent.putExtra("fallLowAverage", fallLowAverage);
		containerIntent.putExtra("fallHighAverage", fallHighAverage);
		startActivityForResult(containerIntent, 2);
	}

	/* Check to see if the JSON file that stores the saved container list is
	 * available. If it is read and parse file.
	 */
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
				parseData(fileContent.toString(), "containers");
//				Log.i(tag, fileContent.toString());
			}
		}
	}

	/* parseData is passed in a raw string from saved file, parses, and calls
	 * the setData method
	 */
	public void parseData (String rawData, String parseType) {

		JSONObject mainObject = null;

		try {
			mainObject = new JSONObject(rawData);
			//			Log.i(tag, rawData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (parseType.matches("containers")) {
			JSONArray subObject = null;
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

/*						Log.i(tag, containerType.toString());
						Log.i(tag, containerLength.toString());
						Log.i(tag, containerWidth.toString());
						Log.i(tag, containerHeight.toString());*/

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
		if (parseType.matches("weather")) {
			JSONObject locationSubObject = null;
			JSONObject historySubObject = null;
			JSONObject historyDateSubObject = null;
			JSONArray observationsSubObject = null;
			JSONObject lowTempSubObject = null;
			JSONObject highTempSubObject = null;
			try {
				if (mainObject != null) {

					locationSubObject = mainObject.getJSONObject("location");
					historySubObject = mainObject.getJSONObject("history");
					historyDateSubObject = historySubObject.getJSONObject("date");
					observationsSubObject = historySubObject.getJSONArray("observations");
					lowTempSubObject = observationsSubObject.getJSONObject(0);
					highTempSubObject = observationsSubObject.getJSONObject(27);

					String location = locationSubObject.getString("city") + ", " + 
							locationSubObject.getString("state");
					String day = historyDateSubObject.getString("mday");
					String month = historyDateSubObject.getString("mon");
					String year = historyDateSubObject.getString("year");
					String minTemp = lowTempSubObject.getString("tempi");
					String maxTemp = highTempSubObject.getString("tempi");
					setDataWeatherData(location, day, month, year, minTemp, maxTemp);
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

		Containers newContainer = new Containers(type, length, width,
				height);
		containerList.add(newContainer);
		return true;
	}

	/* setData is called at the end of the parseData method and saves each
	 * JSON object to a custom object in the activity
	 */
	public boolean setDataWeatherData (String thisLocation, String thisDay, String thisMonth, String thisYear, 
			String thisLowTemp, String thisHighTemp) {
		apiNum++;
		Weather weatherData = new Weather(thisLocation, thisDay, thisMonth, thisYear, 
				thisLowTemp, thisHighTemp);
		Log.i(tag, "Low Temp: " + thisLowTemp);
		Log.i(tag, "High Temp: " + thisHighTemp);
		weatherInfo.add(weatherData);
		if (apiNum < 8) {
			handleAPI(modifiedDate);
		}
		Log.i(tag, weatherInfo.toString());
		addTabs();
		return true;
	}

	/* getTotalCapacity method adds the capacity in gallons for each container and returns the 
	 * total of all of the containers together.
	 */
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

	/* getContainerCapacity gets the capacity in gallons for each container in the list and
	 * returns only the capacity of an individual container.
	 */
	public Double getContainerCapacity(int pos) {
		Integer cuInches = Integer.parseInt(containerList.get(pos).length) * 
				Integer.parseInt(containerList.get(pos).width) * 
				Integer.parseInt(containerList.get(pos).height);
		Log.i(tag, cuInches.toString());

		Double cuFoot = cuInches / 1728.00;

		Double gallons = 7.48 * cuFoot;

		return gallons;
	}

	/* getTotalBedCapacity gets the capacity in gallons for each grow bed in the list and
	 * returns the capacity of all grow beds together.
	 */
	public Double getTotalBedCapacity() {
		Double bedCapacity = 0.00;
		for (int x = 0; x < containerList.size(); x++) {
			if (containerList.get(x).type.matches("Grow Bed")) {
				Integer cuInches = Integer.parseInt(containerList.get(x).length) * 
						Integer.parseInt(containerList.get(x).width) * 
						Integer.parseInt(containerList.get(x).height);
				Log.i(tag, cuInches.toString());

				Double cuFoot = cuInches / 1728.00;

				Double gallons = 7.48 * cuFoot;

				bedCapacity = bedCapacity + gallons;
			}
		}
		return bedCapacity;
	}

	/* saveData method is called by the action bar button.
	 * This method builds a JSON file from the objects store within custom
	 * objects and calls writeToFile to save the JSON file to the device.
	 */
	public void saveData() {
		String preJson = "{'containers':[";
		String contentJson = null;
		String postJson = "]}";
		//		Log.i(tag, "saveData method hit");
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
		//		Log.i(tag, fullJson);
		fileManager.writeToFile(context, getResources().getString(R.string.container_file_name), fullJson);
		checkForFile();
	}

	// Calls API with correct date and location
	public void handleAPI(String date) {
		if (loc != null) {
			// Check Network Connection and Show Error Notification if false
			if (checkConnection(context) == true) {

				getAPIdata data = new getAPIdata();
				String formattedURL = null;

				if (apiNum == 0) {
					modifiedDate = date;
					String subDayStr = modifiedDate.substring(6, 8);
					Integer subDayNum = Integer.parseInt(subDayStr);
					Log.i(tag, "Day: " + subDayStr);
					String subMonStr = modifiedDate.substring(4, 6);
					Integer subMonNum = Integer.parseInt(subMonStr);
					Log.i(tag, "Month: " + subMonStr);
					String subYearStr = modifiedDate.substring(0, 4);
					Integer subYearNum = Integer.parseInt(subYearStr);

					if (subDayNum == 1) {
						if (subMonNum == 1) {
							subDayNum = 30;
							subMonNum = 12;
							subYearNum = subYearNum - 1;
						} else {
							subDayNum = 28;
							subMonNum = subMonNum - 1;
						}
					} else {
						subDayNum = subDayNum - 1;
					}
					if (subDayNum < 10) {
						subDayStr = "0" + subDayNum.toString();
					} else {
						subDayStr = subDayNum.toString();
					}
					if (subMonNum < 10) {
						subMonStr = "0" + subMonNum.toString();
					} else {
						subMonStr = subMonNum.toString();
					}
					subYearStr = subYearNum.toString();

					formattedURL = context.getString(R.string.api_prehistory) + subYearStr + subMonStr
							+ subDayStr + context.getString(R.string.api_precoord) + latit + "," + 
							longit + context.getString(R.string.api_end);
					//					Log.i(tag, formattedURL);
					modifiedDate = date;
				} else {
					String subDayStr = modifiedDate.substring(6, 8);
					Integer subDayNum = Integer.parseInt(subDayStr);
					Log.i(tag, "Day: " + subDayStr);
					String subMonStr = modifiedDate.substring(4, 6);
					Integer subMonNum = Integer.parseInt(subMonStr);
					Log.i(tag, "Month: " + subMonStr);
					String subYearStr = modifiedDate.substring(0, 4);
					Integer subyearNum = Integer.parseInt(subYearStr);
					Log.i(tag, "Year: " + subYearStr);

					if (apiNum == 1 || apiNum == 3 || apiNum == 5 || 
							apiNum == 7) {
						if (subMonNum <= 2) {
							if (subMonNum == 1) {
								subMonNum = 11;
							}
							if (subMonNum == 2) {
								subMonNum = 12;
							}
							subyearNum = subyearNum - 1;
						} else {
							subMonNum = subMonNum - 2;
						}
						subDayNum = 15;

					} else {
						if (subMonNum == 1) {
							subMonNum = 12;
							subyearNum = subyearNum - 1;
						} else {
							subMonNum = subMonNum - 2;
						}
						subDayNum = 01;
					}

					if (subDayNum < 10) {
						subDayStr = "0" + subDayNum.toString();
					} else {
						subDayStr = subDayNum.toString();
					}
					if (subMonNum < 10) {
						subMonStr = "0" + subMonNum.toString();
					} else {
						subMonStr = subMonNum.toString();
					}

					modifiedDate = subyearNum.toString() + subMonStr + subDayStr;
					Log.i(tag, modifiedDate);

					formattedURL = context.getString(R.string.api_prehistory) + subyearNum.toString() + 
							subMonStr + subDayStr + context.getString(R.string.api_precoord) + latit + 
							"," + longit + context.getString(R.string.api_end);
				}

				try {
					url = new URL(formattedURL);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				Log.i(tag, formattedURL);
				data.execute(formattedURL);

			} else {
				Toast.makeText(context, "No Internet Connection Available", Toast.LENGTH_SHORT).show();
				Log.i(tag, "No Internet Connection");
			}
		}
	}

	// Check Network Connection Method
	public Boolean checkConnection (Context context) {
		Boolean connected = false;
		ConnectivityManager connManag = (ConnectivityManager) context
				.getSystemService
				(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfomation = connManag.getActiveNetworkInfo();
		if (networkInfomation != null) {
			if (networkInfomation.isConnected()) {
				Log.i(tag, "Connection Type: " + networkInfomation.getTypeName()
						.toString());
				connected = true;
			}
		}
		return connected;
	}

	// API Method
	public String getAPIresponse(URL url) {
		String apiResponse = "";
		try {
			URLConnection apiConnection = url.openConnection();
			BufferedInputStream bufferedInput = new BufferedInputStream(apiConnection
					.getInputStream());
			byte[] contextByte = new byte[1024];
			int bytesRead = 0;
			StringBuffer responseBuffer = new StringBuffer();
			while ((bytesRead = bufferedInput.read(contextByte)) != -1) {
				apiResponse = new String(contextByte, 0, bytesRead);
				responseBuffer.append(apiResponse);
			}
			apiResponse = responseBuffer.toString();
			//			Log.i(tag, apiResponse);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(tag, "getAPIresponse - no data returned");
			e.printStackTrace();
		}
//		Log.i(tag, apiResponse.toString());
		return apiResponse;
	}

	// API Class
	class getAPIdata extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String APIresponseStr = "";

			if (url != null) {
				APIresponseStr = getAPIresponse(url);
				return APIresponseStr;
			} else {
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			//			Log.i(tag, result);
			super.onPostExecute(result);

//			Log.i(tag, result);
			// Parse Methods Called based on which data is being entered after API has returned data
			parseData(result, "weather");
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.i(tag, "Latitude: " + location.toString() + ", " + "Longitude: " + location.toString());

		// Get current date
		Date date = new Date();
		CharSequence currentDate  = DateFormat.format("yyyyMMdd", date.getTime());
		Log.i(tag, currentDate.toString());

		latit = String.valueOf(location.getLatitude());
		longit = String.valueOf(location.getLongitude());

		handleAPI(currentDate.toString());
	}

// addTabs adds tabs after the API has returned data if weather data isn't already present.
	public void addTabs() {
		if (apiNum == 8) {
			actionBar = getActionBar();
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
		}
	}

	// calculateTemps calculates the average temp for all seasons as well as annually.
	public void calculateTemps() {
		ArrayList<Double> annualLowTemps = new ArrayList<Double>();
		ArrayList<Double> annualHighTemps = new ArrayList<Double>();
		ArrayList<Double> winterLowTemps = new ArrayList<Double>();
		ArrayList<Double> winterHighTemps = new ArrayList<Double>();
		ArrayList<Double> springLowTemps = new ArrayList<Double>();
		ArrayList<Double> springHighTemps = new ArrayList<Double>();
		ArrayList<Double> summerLowTemps = new ArrayList<Double>();
		ArrayList<Double> summerHighTemps = new ArrayList<Double>();
		ArrayList<Double> fallLowTemps = new ArrayList<Double>();
		ArrayList<Double> fallHighTemps = new ArrayList<Double>();

		if (!weatherInfo.isEmpty()) {
			for (int u = 0; u < weatherInfo.size(); u++) {
				Double lowTemp = 0.0;
				Double highTemp = 0.0;
				String lowTempStr = weatherInfo.get(u).lowTemp.toString();
				String highTempStr = weatherInfo.get(u).highTemp.toString();
				try {
					lowTemp = Double.parseDouble(lowTempStr);
					highTemp = Double.parseDouble(highTempStr);
				} catch(NumberFormatException nfe) {
					System.out.println("Could not parse " + nfe);
				} 
				annualLowTemps.add(lowTemp);
				annualHighTemps.add(highTemp);
				if (weatherInfo.get(u).month.toString().matches("12") || 
						weatherInfo.get(u).month.toString().matches("01") || 
						weatherInfo.get(u).month.toString().matches("02")) {
					winterLowTemps.add(lowTemp);
					winterHighTemps.add(highTemp);
				}
				if (weatherInfo.get(u).month.toString().matches("03") || 
						weatherInfo.get(u).month.toString().matches("04") || 
						weatherInfo.get(u).month.toString().matches("05")) {
					springLowTemps.add(lowTemp);
					springHighTemps.add(highTemp);
				}
				if (weatherInfo.get(u).month.toString().matches("06") || 
						weatherInfo.get(u).month.toString().matches("07") || 
						weatherInfo.get(u).month.toString().matches("08")) {
					summerLowTemps.add(lowTemp);
					summerHighTemps.add(highTemp);
				}
				if (weatherInfo.get(u).month.toString().matches("09") || 
						weatherInfo.get(u).month.toString().matches("10") || 
						weatherInfo.get(u).month.toString().matches("11")) {
					fallLowTemps.add(lowTemp);
					fallHighTemps.add(highTemp);
				}
			}
			for (int t = 0; t < annualLowTemps.size(); t++) {
				annualLowAverage = annualLowAverage + annualLowTemps.get(t);
				annualHighAverage = annualHighAverage + annualHighTemps.get(t);
			}

			for (int q = 0; q < winterLowTemps.size(); q++) {
				winterLowAverage = winterLowAverage + winterLowTemps.get(q);
				winterHighAverage = winterHighAverage + winterHighTemps.get(q);
			}

			for (int w = 0; w < springLowTemps.size(); w++) {
				springLowAverage = springLowAverage + springLowTemps.get(w);
				springHighAverage = springHighAverage + springHighTemps.get(w);
			}

			for (int e = 0; e < summerLowTemps.size(); e++) {
				summerLowAverage = summerLowAverage + summerLowTemps.get(e);
				summerHighAverage = summerHighAverage + summerHighTemps.get(e);
			}

			for (int r = 0; r < fallLowTemps.size(); r++) {
				fallLowAverage = fallLowAverage + fallLowTemps.get(r);
				fallHighAverage = fallHighAverage + fallHighTemps.get(r);
			}
			Log.i(tag, annualLowTemps.toString());
			Log.i(tag, annualHighTemps.toString());
			Log.i(tag, winterLowTemps.toString());
			Log.i(tag, winterHighTemps.toString());
			Log.i(tag, springLowTemps.toString());
			Log.i(tag, springHighTemps.toString());
			Log.i(tag, summerLowTemps.toString());
			Log.i(tag, summerHighTemps.toString());
			Log.i(tag, fallLowTemps.toString());
			Log.i(tag, fallHighTemps.toString());
			annualLowAverage = annualLowAverage / annualLowTemps.size();
			annualHighAverage = annualHighAverage / annualHighTemps.size();
			winterLowAverage = winterLowAverage / winterLowTemps.size();
			winterHighAverage = winterHighAverage / winterHighTemps.size();
			springLowAverage = springLowAverage / springLowTemps.size();
			springHighAverage = springHighAverage / springHighTemps.size();
			summerLowAverage = summerLowAverage / summerLowTemps.size();
			summerHighAverage = summerHighAverage / summerHighTemps.size();
			fallLowAverage = fallLowAverage / fallLowTemps.size();
			fallHighAverage = fallHighAverage / fallHighTemps.size();
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}
}
