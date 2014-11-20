package com.laurenelder.aquapharm;

import java.util.ArrayList;
import java.util.List;

import com.laurenelder.aquapharm.FishFragment.OnSelected;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class PlantsFragment extends Fragment {
	
	Context context;
	String tag = "PLANTS FRAGMENT";
	ImageView plantPic;
	ImageView annualTemp;
	ImageView winterTemp;
	ImageView springTemp;
	ImageView summerTemp;
	ImageView fallTemp;
	TextView plantEdible;
	int spinnerViewPosition = 0;
	
	// Interface to PlantsActivity methods
			public interface OnSelected {
				public ArrayList getData(int pos);
				public ArrayList<Double> getTemps();
			}
			
			private OnSelected parentActivity;

			@Override
			public void onAttach(Activity activity) {
				// TODO Auto-generated method stub
				super.onAttach(activity);
				context = getActivity();
				
				if(activity instanceof OnSelected) {
					parentActivity = (OnSelected) activity;

				} else {
					throw new ClassCastException((activity.toString()) + "Did not impliment onSelected interface");
				}
			}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View plantsView = inflater.inflate(R.layout.activity_plants, container);
		
		context = getActivity();
		
		plantPic = (ImageView)plantsView.findViewById(R.id.plantImage);
		annualTemp = (ImageView)plantsView.findViewById(R.id.plantOverallColor);
		winterTemp = (ImageView)plantsView.findViewById(R.id.plantWinterColor);
		springTemp = (ImageView)plantsView.findViewById(R.id.plantSpringColor);
		summerTemp = (ImageView)plantsView.findViewById(R.id.plantSummerColor);
		fallTemp = (ImageView)plantsView.findViewById(R.id.plantFallColor);
		plantEdible = (TextView)plantsView.findViewById(R.id.plantEatable);
		
		Spinner plantsSpinner = (Spinner) plantsView.findViewById(R.id.plantSpinner);
		List<String> plants = new ArrayList<String>();
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, plants);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		plantsSpinner.setAdapter(spinnerAdapter);
		
		plants.add("Angel Face");
		plants.add("Azaleas");
		plants.add("Bell Peppers");
		plants.add("Bird of Paradise");
		plants.add("Blackberries");
		plants.add("Blueberries");
		plants.add("Broccoli");
		plants.add("Brussel Spouts");
		plants.add("Cabbage");
		plants.add("Carnations");
		plants.add("Celery");
		plants.add("Cherries");
		plants.add("Chives");
		plants.add("Collard Greens");
		plants.add("Cucumbers");
		plants.add("Daffodils");
		plants.add("Daisies");
		plants.add("Eggplant");
		plants.add("Garlic");
		plants.add("Geranium");
		plants.add("Grapes");
		plants.add("Green Beans");
		plants.add("Hibiscus");
		plants.add("Iris");
		plants.add("Jalapeno Peppers");
		plants.add("Kiwi Fruit");
		plants.add("Lettuce");
		plants.add("Lima Beans");
		plants.add("Onions");
		plants.add("Peas");
		plants.add("Pineapple");
		plants.add("Pumpkin");
		plants.add("Raspberries");
		plants.add("Roses");
		plants.add("Spinach");
		plants.add("Squash");
		plants.add("Strawberries");
		plants.add("Tomatoes");
		plants.add("Watermelon");

		spinnerAdapter.notifyDataSetChanged();
		
		plantsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				spinnerViewPosition = position;
				
				String imageName = parentActivity.getData(position).get(3).toString();
				imageName = imageName.substring(0, imageName.length() - 4);
				Log.i(tag, imageName);
				
				int resID = getResources().getIdentifier(imageName, "raw", "com.laurenelder.aquapharm");
				
				plantPic.setImageResource(resID);
				
				if (parentActivity.getData(position).get(4).toString().matches("yes")) {
					plantEdible.setText("Edible");
				} else {
					plantEdible.setText("Uneatable");
				}
				
				updateColors();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		return plantsView;
	}

	public void updateColors() {
		ArrayList<Integer> temps = new ArrayList<Integer>();
		for (int z = 0; z < 10; z++) {
			Integer value = parentActivity.getTemps().get(z).intValue();
			temps.add(value);
			//		Log.i(tag, String.valueOf(parentActivity.getTemps().get(z)));
			//			Log.i(tag, value.toString());
		}
		Log.i(tag, parentActivity.getData(spinnerViewPosition).get(1).toString());
		// Annual Field
		if (temps.get(0) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 5 && 
				temps.get(1) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 5) {
			annualTemp.setBackgroundResource(R.color.green);	
			Log.i(tag, "Color should be green");
		}
		if (temps.get(0) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 10 || 
				temps.get(1) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 10) {
			if (temps.get(0) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 5 || 
					temps.get(1) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 5) {
				annualTemp.setBackgroundResource(R.color.yellow);	
				Log.i(tag, "Color should be yellow");
			}
		}
		if (temps.get(0) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 10 || 
				temps.get(1) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 10) {
			annualTemp.setBackgroundResource(R.color.red);	
			Log.i(tag, "Color should be red");
		}
		// Winter Field
		if (temps.get(2) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 5 && 
				temps.get(3) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 5) {
			winterTemp.setBackgroundResource(R.color.green);	
			Log.i(tag, "Color should be green");
		}
		if (temps.get(2) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 10 || 
				temps.get(3) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 10) {
			if (temps.get(2) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 5 || 
					temps.get(3) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 5) {
				winterTemp.setBackgroundResource(R.color.yellow);	
				Log.i(tag, "Color should be yellow");
			}
		}
		if (temps.get(2) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 10 || 
				temps.get(3) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 10) {
			winterTemp.setBackgroundResource(R.color.red);	
			Log.i(tag, "Color should be red");
		}
		// Spring Field
		if (temps.get(4) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 5 && 
				temps.get(5) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 5) {
			springTemp.setBackgroundResource(R.color.green);	
			Log.i(tag, "Color should be green");
		}
		if (temps.get(4) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 10 || 
				temps.get(5) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 10) {
			if (temps.get(4) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 5 || 
					temps.get(5) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 5) {
				springTemp.setBackgroundResource(R.color.yellow);	
				Log.i(tag, "Color should be yellow");
			}
		}
		if (temps.get(4) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 10 || 
				temps.get(5) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 10) {
			springTemp.setBackgroundResource(R.color.red);	
			Log.i(tag, "Color should be red");
		}
		// Summer Field
		if (temps.get(6) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 5 && 
				temps.get(7) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 5) {
			summerTemp.setBackgroundResource(R.color.green);	
			Log.i(tag, "Color should be green");
		}
		if (temps.get(6) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 10 || 
				temps.get(7) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 10) {
			if (temps.get(6) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 5 || 
					temps.get(7) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 5) {
				summerTemp.setBackgroundResource(R.color.yellow);	
				Log.i(tag, "Color should be yellow");
			}
		}
		if (temps.get(6) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 10 || 
				temps.get(7) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 10) {
			summerTemp.setBackgroundResource(R.color.red);	
			Log.i(tag, "Color should be red");
		}
		// Fall Field
		if (temps.get(8) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 5 && 
				temps.get(9) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 5) {
			fallTemp.setBackgroundResource(R.color.green);	
			Log.i(tag, "Color should be green");
		}
		if (temps.get(8) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 10 || 
				temps.get(9) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 10) {
			if (temps.get(8) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 5 || 
					temps.get(9) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 5) {
				fallTemp.setBackgroundResource(R.color.yellow);	
				Log.i(tag, "Color should be yellow");
			}
		}
		if (temps.get(8) < Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(1).toString()) - 10 || 
				temps.get(9) > Integer.parseInt(parentActivity.getData(spinnerViewPosition).get(2).toString()) + 10) {
			fallTemp.setBackgroundResource(R.color.red);	
			Log.i(tag, "Color should be red");
		}
	}
}
