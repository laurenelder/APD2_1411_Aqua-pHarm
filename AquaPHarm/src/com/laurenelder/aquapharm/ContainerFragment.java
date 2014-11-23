package com.laurenelder.aquapharm;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContainerFragment extends Fragment {

	String tag = "CONTAINER FRAGMENT";
	LinearLayout linearLayout;
	Context context;
	TextView cType;
	TextView cLength;
	TextView cWidth;
	TextView cHeight;
	TextView cCapacity;

	private containerInterface containerActivity;

	// Set up interface to parent activity
	public interface containerInterface {

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		context = this.getActivity();

		if (activity instanceof containerInterface) {
			containerActivity = (containerInterface) activity;
		} else {
			throw new ClassCastException((activity.toString()) + 
					"Did not impliment containerInterface");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View containerView = inflater.inflate(R.layout.activity_container, container);

		// Get and set UI elements
		cType = (TextView) containerView.findViewById(R.id.containerTypeOutput);
		cLength = (TextView) containerView.findViewById(R.id.containerlengthOutput);
		cWidth = (TextView) containerView.findViewById(R.id.containerWidthOutput);
		cHeight = (TextView) containerView.findViewById(R.id.containerHeightOutput);
		cCapacity = (TextView) containerView.findViewById(R.id.containerCapacityOutput);
		linearLayout = (LinearLayout) containerView.findViewById(R.id.linearLayout);

		// Set background image transparency
		linearLayout.getBackground().setAlpha(85);

		return containerView;
	}

	// update text views with new data
	public void updateTextViews(String type, String length, String width, String height, String capacity) {
		cType.setText(type);
		cLength.setText(length);
		cWidth.setText(width);
		cHeight.setText(height);
		cCapacity.setText(capacity + " Gallons");
	}
}
