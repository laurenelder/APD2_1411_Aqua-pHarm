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
	TextView plantEdible;
	
	// Interface to PlantsActivity methods
			public interface OnSelected {
				public ArrayList getData(int pos);
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
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		return plantsView;
	}

}
