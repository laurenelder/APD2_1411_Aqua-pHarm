package com.laurenelder.aquapharm;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PlantsFragment extends Fragment {
	
	Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View plantsView = inflater.inflate(R.layout.activity_plants, container);
		
		context = getActivity();
		
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
		
		return plantsView;
	}

}
