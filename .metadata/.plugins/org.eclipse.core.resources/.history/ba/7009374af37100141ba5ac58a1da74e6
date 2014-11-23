package com.laurenelder.aquapharm;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ContainerActivity extends Activity implements ContainerFragment.containerInterface {

	String tag = "CONTAINER ACTIVITY";
	Context context;
	FragmentManager fragMgr;
	Fragment containerFrag;
	String ctype;
	String clength;
	String cwidth;
	String cheight;
	int cposition;
	Intent thisIntent;
	Intent finishedIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_container);

		// Setup FragmentManager to call methods in FormFragment
		fragMgr = getFragmentManager();
		containerFrag = (ContainerFragment)fragMgr.findFragmentById(R.id.containerFrag);
		if(containerFrag == null) {
			containerFrag = new ContainerFragment();
		}

		thisIntent = this.getIntent();
		ctype = thisIntent.getExtras().getString("type").toString();
		clength = thisIntent.getExtras().getString("length").toString();
		cwidth = thisIntent.getExtras().getString("width").toString();
		cheight = thisIntent.getExtras().getString("height").toString();
		cposition = thisIntent.getExtras().getInt("position");

		Integer cuInches = Integer.parseInt(clength) * Integer.parseInt(cwidth) *
				Integer.parseInt(cheight);
		Log.i(tag, cuInches.toString());

		Double cuFoot = cuInches / 1728.00;
		//		Log.i(tag, cuFoot.toString());

		Double gallons = 7.48 * cuFoot;

		String gallonsStr =  String.format("%.1f", gallons);

		((ContainerFragment) containerFrag).updateTextViews(ctype, clength, 
				cwidth, cheight, gallonsStr);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);

		menu.findItem(R.id.action_item_one).setEnabled(true).setVisible(true);

		menu.findItem(R.id.action_item_one).setIcon(R.drawable.ic_action_edit);
		menu.findItem(R.id.action_item_two).setIcon(R.drawable.ic_action_discard);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();


		if (id == R.id.action_item_one) {
			finishedIntent = new Intent();

			finishedIntent.putExtra("type", ctype);
			finishedIntent.putExtra("length", clength);
			finishedIntent.putExtra("width", cwidth);
			finishedIntent.putExtra("height", cheight);
			finishedIntent.putExtra("button", "edit");
			finishedIntent.putExtra("position", cposition);
			setResult(RESULT_OK, finishedIntent);
			finish();
			return true;
		}
		if (id == R.id.action_item_two) {
			finishedIntent = new Intent();

			finishedIntent.putExtra("type", ctype);
			finishedIntent.putExtra("length", clength);
			finishedIntent.putExtra("width", cwidth);
			finishedIntent.putExtra("height", cheight);
			finishedIntent.putExtra("button", "delete");
			finishedIntent.putExtra("position", cposition);
			setResult(RESULT_OK, finishedIntent);
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
