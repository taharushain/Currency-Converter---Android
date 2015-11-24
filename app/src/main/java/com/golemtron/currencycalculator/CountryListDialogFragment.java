package com.golemtron.currencycalculator;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Taha Rushain on 11/23/15.
 */

public class CountryListDialogFragment extends DialogFragment {

    private int type = 0;

    public interface CountryListDialogListener {
        public void onDialogClick(DialogFragment dialog, int which, int type);

    }

    // Use this instance of the interface to deliver action events
    CountryListDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        type = getArguments().getInt("type");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String temp[] = getResources().getStringArray(R.array.country_array);
        ArrayList<String> currency_list_al = new ArrayList<String>();
        for(int i=0; i < temp.length ;i++) {
            currency_list_al.add(temp[i]);
        }
        Collections.sort(currency_list_al);

        for(int i=0;i<temp.length;i++)
            temp[i] = currency_list_al.get(i).trim().split(":")[0];

        builder.setTitle("Select Currency")
                .setItems(temp, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        mListener.onDialogClick(CountryListDialogFragment.this, which,type);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the CountryListDialogListener so we can send events to the host
            mListener = (CountryListDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement CountryListDialogListener");
        }
    }


}