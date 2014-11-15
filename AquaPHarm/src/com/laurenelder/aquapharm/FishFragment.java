package com.laurenelder.aquapharm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class FishFragment extends Fragment {
	
	Context context;
	String tag = "FISH FRAGMENT";
	ImageView fishPic;
	
	// Interface to FishActivity methods
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
		View fishView = inflater.inflate(R.layout.activity_fish, container);
		
		context = getActivity();
		
		fishPic = (ImageView)fishView.findViewById(R.id.fishImage);
		
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
		
		fishSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				String imageName = parentActivity.getData(position).get(3).toString();
				imageName = imageName.substring(0, imageName.length() - 4);
				Log.i(tag, imageName);
				
				int resID = getResources().getIdentifier(imageName, "raw", "com.laurenelder.aquapharm");
				fishPic.setImageResource(resID);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		return fishView;
	}

	public static int getStringIdentifier(Context thisContext, String name) {
	    return thisContext.getResources().getIdentifier(name, "string", thisContext.getPackageName());
	}
	
}
