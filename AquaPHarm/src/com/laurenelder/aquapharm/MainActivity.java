package com.laurenelder.aquapharm;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


public class MainActivity extends Activity implements TabListener {
	


	Context context;
	boolean switched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        
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
        
        actionBar.setSelectedNavigationItem(0);
        
//        TabHost tHost = (TabHost) getParent().findViewById(android.R.id.tabhost);
//        tHost.setCurrentTab(0);
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
 //       menu.findItem(R.id.action_about).s
        menu.findItem(R.id.action_item_one).setEnabled(true).setVisible(true);
        
        menu.findItem(R.id.action_item_one).setIcon(R.drawable.ic_action_new);
        menu.findItem(R.id.action_item_two).setIcon(R.drawable.ic_action_about);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_item_one) {
        	Intent addIntent = new Intent(context, AddContainerActivity.class);
        	startActivityForResult(addIntent, 1);
            return true;
        }
        if (id == R.id.action_item_two) {
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		int tabIndex = tab.getPosition();
		if (tabIndex == 0) {
			switched = true;
		}
		if (tabIndex == 1) {
//			switched = true;
			if (switched == true) {
				Intent fishIntent = new Intent(context, FishActivity.class);
				startActivity(fishIntent);
			}
		}
		if (tabIndex == 2) {
			if (switched == true) {
				Intent plantsIntent = new Intent(context, PlantsActivity.class);
				startActivity(plantsIntent);
			}
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
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				
			}
			if (requestCode == 2) {
				
			}
		}
	}
}
