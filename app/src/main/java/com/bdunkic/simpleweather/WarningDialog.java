package com.bdunkic.simpleweather;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

/**
 * Created by Bojan on 14.10.2016..
 */
public class WarningDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.warning_gps);
        builder.setMessage(R.string.gps_not_enabled);

        builder.setPositiveButton(getString(R.string.gps_enable), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                dialog.dismiss();
                //get gps
            }
        });
        builder.setNegativeButton(getString(R.string.cancel),new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();



                // TODO Auto-generated method stub

            }
        });

        AlertDialog dialog =builder.create();
        return dialog;
    }



}

