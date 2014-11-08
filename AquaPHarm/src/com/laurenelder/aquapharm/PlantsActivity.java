package com.laurenelder.aquapharm;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PlantsActivity extends Activity implements TabListener {
	
	Context context;
	boolean switched;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_plants);
		
		context = this;
		switched = false;
		
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        Tab actionBarTab = actionBar.newTab();
        actionBarTab.setText(R.string.actionbar_buildsystem).setTabListener(this);
        actionBar.addTab(actionBarTab);
        actionBarTab = actionBar.newTab();
        actionBarTab.setText(R.string.actionbar_fish).setTabListener(this);
        actionBar.addTab(actionBarTab);
        actionBarTab = actionBar.newTab();
        actionBarTab.setText(R.string.actionbar_plants).setTabListener(this);
        actionBar.addTab(actionBarTab);
        
        actionBar.setSelectedNavigationItem(2);
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.main, menu);
        
 //       menu.findItem(R.id.action_about).s
        menu.findItem(R.id.action_item_one).setEnabled(false).setVisible(false);
        
//        menu.findItem(R.id.action_item_one).setIcon(R.drawable.ic_action_new);
        menu.findItem(R.id.action_item_two).setIcon(R.drawable.ic_action_about);
        
        return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		int tabIndex = tab.getPosition();
		if (tabIndex == 0) {
			if (switched == true) {
				Intent buildIntent = new Intent(context, MainActivity.class);
				startActivity(buildIntent);
			}
		}
		if (tabIndex == 1) {
//			switched = true;
			if (switched == true) {
				Intent fishIntent = new Intent(context, FishActivity.class);
				startActivity(fishIntent);
			}
		}
		if (tabIndex == 2) {
			switched = true;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}