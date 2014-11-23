package com.laurenelder.aquapharm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class AboutDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		final LayoutInflater dialogInflater = getActivity().getLayoutInflater();

		View aboutView = dialogInflater.inflate(R.layout.about_dialog, null);


		TextView aboutText = (TextView)aboutView.findViewById(R.id.dialogText);
		aboutText.setText("Build System Tab - \r\n" +
				"The Build System tab will displayed any containers saved to the device." +
				"To add a container to your system select the Add icon in the Action Bar and" +
				"enter the data for the container in each field on the following screen. The " +
				"Accept icon the Action Bar will save the container to the device. To view the " +
				"details of a container select the container from the Build System Tab in the list. " +
				"Containers can be edited or deleted from the Container Detail screen. As containers are" +
				"added to the device the total water capacity will be displayed at the bottom of the " +
				"Build System Tab. To view the cycle rate for the grow beds enter the flow rate of the " +
				"water pump being used just below the list of containers and deselect the field. The flow" +
				" rate will be displayed at the very bottom of the Build System tab. \r\n\r\n " +
				"Fish Tab - \r\n" +
				"By selecting a type of fish from the drop down menu an image of the selected fish" +
				" will be displayed at the top of the Fish tab and a series of colored icons will be " +
				"displayed at the bottom of the tab. The colors correspond to how well the selected fish " +
				"thrive in the users climate. If the color is green the fish will easily thrive. If the " +
				"color is yellow the climate will be hard on the fish at times but there is still a " +
				"possibility the fish will survive. If the color is red there is very little chance the " +
				"fish will survive without extra measures being taken to offset the extremes in the climate." +
				"The very bottom of the Fish tab displays whether or not the selected fish is edible. \r\n\r\n " +
				"Plant Tab - \r\n" +
				"The Plant tab is very similar to the Fish tab and contains the same functionality with the " +
				"only difference being it is in regards to plants.");

		alertDialogBuilder.setView(aboutView);
		alertDialogBuilder.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}

		});

		return alertDialogBuilder.create();
	}
	static Context context;
	// Establish Instance of AlertDialogFragmentClass
	static AboutDialog newInstance(Context appContext) {
		context = appContext;
		return new AboutDialog();
	}
}
