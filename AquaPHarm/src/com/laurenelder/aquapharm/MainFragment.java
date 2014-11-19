package com.laurenelder.aquapharm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainFragment extends Fragment {

	String tag = "MAIN FRAGMENT";
	Context context;
	LinearLayout linearLayout;
	ListView containerListView;
	EditText flowInput;
	TextView totalCapacity;
	TextView cycleRate;
	Integer fishTankNum;
	Integer sumpTankNum;
	Integer growBedNum;
	Double cap;
	ArrayAdapter<Containers> listAdapter;
	List<Containers> containerList = new ArrayList<Containers>();

	private mainInterface mainActivity;

	// Set up interface to parent activity
	public interface mainInterface {
		public void startContainerActivity(int pos);
		public Double getTotalCapacity();
		public Double getContainerCapacity(int pos);
		public Double getCycleRate(Integer flow);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		context = this.getActivity();

		if (activity instanceof mainInterface) {
			mainActivity = (mainInterface) activity;
		} else {
			throw new ClassCastException((activity.toString()) + 
					"Did not impliment mainInterface");
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View buildSystemView = inflater.inflate(R.layout.activity_main, container);

		fishTankNum = 0;
		sumpTankNum = 0;
		growBedNum = 0;

		containerListView = (ListView) buildSystemView.findViewById(R.id.list);
		flowInput = (EditText) buildSystemView.findViewById(R.id.waterInput);
		totalCapacity = (TextView) buildSystemView.findViewById(R.id.waterOutput);
		cycleRate = (TextView) buildSystemView.findViewById(R.id.bedOutput);
		linearLayout = (LinearLayout) buildSystemView.findViewById(R.id.linearLayout);

		linearLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (flowInput.isFocused()) {
					flowInput.clearFocus();
				}
				return false;
			}

		});

		flowInput.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {
					Integer flow = Integer.parseInt(flowInput.getText().toString());
					Double rate = cap / flow;
					String rateStr = String.format("%.1f", rate);
					cycleRate.setText(rateStr + " Cycles");
				}
			}

		});

		// Setup ListView
		listAdapter = new customListAdapter();
		containerListView.setAdapter(listAdapter);

		containerListView.setOnItemClickListener(new OnItemClickListener() {

			// On ListView click data is sent through an intent to the detail view
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mainActivity.startContainerActivity(position);
			}

		});


		return buildSystemView;
	}

	public void addData(String type, String length, String width, String height) {
		String adjustedType = null;
		if (type.matches("Fish Tank")) {
			fishTankNum++;
			adjustedType = type + " " + fishTankNum.toString();
		}
		if (type.matches("Sump Tank")) {
			sumpTankNum++;
			adjustedType = type + " " + sumpTankNum.toString();
		}
		if (type.matches("Grow Bed")) {
			growBedNum++;
			adjustedType = type + " " + growBedNum.toString();
		}
		Containers newContainer = new Containers(adjustedType, length, width,
				height);
		containerList.add(newContainer);
		Log.i(tag, newContainer.toString());
	}

	public void updateListView() {
		listAdapter.notifyDataSetChanged();
		cap = mainActivity.getTotalCapacity();
		String capStr = String.format("%.1f", cap);
		totalCapacity.setText(capStr + " Gallons");
	}

	public void clearList() {
		if (!containerList.isEmpty()) {
			fishTankNum = 0;
			sumpTankNum = 0;
			growBedNum = 0;
			containerList.removeAll(containerList);
		}
	}
	
	// Custom ListAdapter Class
    public class customListAdapter extends ArrayAdapter <Containers> {
    	public customListAdapter() {
    		super(context, R.layout.list_item, containerList);
    	}

    	// Set List Item Information
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View customItemView = convertView;
			
			if (customItemView == null) {
				LayoutInflater viewInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				customItemView = viewInflater.inflate(R.layout.list_item, parent, false);
			}
			
			TextView containerTitleLabel = (TextView)customItemView.findViewById(R.id.list_item_title);
			TextView containerCapacityLabel = (TextView)customItemView.findViewById(R.id.list_item_capacity);
			
			Double itemCapacity = mainActivity.getContainerCapacity(position);
			String itemCapacityStr = String.format("%.1f", itemCapacity) + " Gallons";
			
			containerTitleLabel.setText(containerList.get(position).type.toString());
			containerCapacityLabel.setText(itemCapacityStr);
			
			return customItemView;
		}
    }
}
