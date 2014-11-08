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

public class FishFragment extends Fragment {
	
	Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View fishView = inflater.inflate(R.layout.activity_fish, container);
		
		context = getActivity();
		
		Spinner fishSpinner = (Spinner) fishView.findViewById(R.id.fishSpinner);
		List<String> fish = new ArrayList<String>();
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, fish);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		fishSpinner.setAdapter(spinnerAdapter);
		
		fish.add("Blue Gill");
		fish.add("Catfish");
		fish.add("Cichlids");
		fish.add("Gold Fish");
		fish.add("Koi");
		fish.add("Lake Trout");
		fish.add("Land Locked Salmon");
		fish.add("Large Mouth Bass");
		fish.add("Minnows");
		fish.add("Rainbow Trout");
		fish.add("Small Mouth Bass");
		fish.add("Tilapia");
		fish.add("White Perch");
		fish.add("Yellow Perch");

		spinnerAdapter.notifyDataSetChanged();
		
		return fishView;
	}

}
