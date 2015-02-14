package com.example.faizan.touristguide;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ebad on 2/13/2015.
 */
public class SpinFrag extends Fragment
{

    public static String [] queryinDomain = null;
    public static String [] queryinValue = {"Hotels","Restaurants","Attrations","Rentals"};
    SearchView sv;

    public SpinFrag()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_dialogue, container, false);

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(0);
            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add(queryinValue[0]);
        categories.add(queryinValue[1]);
        categories.add(queryinValue[2]);
        categories.add(queryinValue[3]);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        sv = ((SearchView)rootView.findViewById(R.id.searchQuery));


        ((Button)rootView.findViewById(R.id.search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = spinner.getSelectedItem().toString();
                String query = sv.getQuery().toString().toLowerCase();
                queryinDomain = new String[]{query, val};

                getFragmentManager().popBackStackImmediate();

            }
        });



        return rootView;
    }

}
