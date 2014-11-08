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

public class AddContainerFragment extends Fragment {
	
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View addContainerView = inflater.inflate(R.layout.activity_addcontainer, container);
		
		context = getActivity();
		
		Spinner addContainerSpinner = (Spinner) addContainerView.findViewById(R.id.containerSpinner);
		List<String> containerTypes = new ArrayList<String>();
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, containerTypes);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		addContainerSpinner.setAdapter(spinnerAdapter);
		
		containerTypes.add("Fish Tank");
		containerTypes.add("Sump Tank");
		containerTypes.add("Grow Bed");

		spinnerAdapter.notifyDataSetChanged();
		
		return addContainerView;
	}
	
}
