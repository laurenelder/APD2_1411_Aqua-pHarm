package com.laurenelder.aquapharm;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class AddContainerFragment extends Fragment {
	
	Context context;
	LinearLayout linearLayout;
	Spinner addContainerSpinner;
	String containerType = null;
	EditText length;
	EditText width;
	EditText height;
	List<String> containerTypes = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View addContainerView = inflater.inflate(R.layout.activity_addcontainer, container);
		
		context = getActivity();
		
		// Get and set UI elements
		addContainerSpinner = (Spinner) addContainerView.findViewById(R.id.containerSpinner);
		length = (EditText) addContainerView.findViewById(R.id.containerLength);
		width = (EditText) addContainerView.findViewById(R.id.containerWidth);
		height = (EditText) addContainerView.findViewById(R.id.containerHeight);
		linearLayout = (LinearLayout) addContainerView.findViewById(R.id.linearLayout);
		
		// Set background image transparency
		linearLayout.getBackground().setAlpha(85);
		
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, containerTypes);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		addContainerSpinner.setAdapter(spinnerAdapter);
		
		containerTypes.add("Fish Tank");
		containerTypes.add("Sump Tank");
		containerTypes.add("Grow Bed");

		spinnerAdapter.notifyDataSetChanged();
		
		addContainerSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				containerType = containerTypes.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		return addContainerView;
	}
	
	// getDataFields retrieves data from input fields and returns the data to parent activity.
	public ArrayList<String> getDataFields() {
		if (containerType != null && 
				!length.getText().toString().matches("") &&
				!width.getText().toString().matches("") && 
				!height.getText().toString().matches("")) {
			
			ArrayList<String> dataFields = new ArrayList<String>();
			dataFields.add(containerType);
			dataFields.add(length.getText().toString());
			dataFields.add(width.getText().toString());
			dataFields.add(height.getText().toString());
			return dataFields;
			
		} else {
			
			Toast.makeText(context, "Please enter data in all fields before saving", Toast.LENGTH_SHORT).show();
			return null;
		}
	}
	
	// updateFields is called when a container is being editted. 
	//	This method inputs stored data into the input fields.
	public void updateFields(String defaultType, String defaultLength, 
			String defaultWidth, String defaultHeight) {
		
		if (defaultType.matches("Fish Tank")) {
			addContainerSpinner.setSelection(0);
		}
		if (defaultType.matches("Sump Tank")) {
			addContainerSpinner.setSelection(1);
		}
		if (defaultType.matches("Grow Bed")) {
			addContainerSpinner.setSelection(2);
		}
		length.setText(defaultLength);
		width.setText(defaultWidth);
		height.setText(defaultHeight);
	}
}
