package com.rinkusaini.a2_myappteam;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class LeadsFragment extends Fragment {

    EditText leadsname;
    Button submitbtn;
    ListView listView;

    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_leads, container, false);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {

       leadsname = v.findViewById(R.id.leadsName);
       submitbtn = v.findViewById(R.id.submitLeads);
        listView = v.findViewById(R.id.ListLeads);

        //Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(getActivity());

        //Add Database Values to ArrayList
        arrayList = databaseHelper.getAllText();

        //Initialize ArrayAdapter
        arrayAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1,arrayList); // use getActivity in Fragment instead of this

        //Set ArrayAdapter to ListView
        listView.setAdapter(arrayAdapter);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get text from EditText
                String text = leadsname.getText().toString();
                if (!text.isEmpty()) {
                    if (databaseHelper.addText(text)) {
                       leadsname.setText(""); //ClearText

                        //Display Toast message
                        Context context = null;
                        Toast.makeText(context.getApplicationContext(), "Data Inserted..."
                                ,Toast.LENGTH_SHORT).show();

                        //Clear ArrayList
                        arrayList.clear();
                        arrayList.addAll(databaseHelper.getAllText());

                        //Refresh ListView Data
                        arrayAdapter.notifyDataSetChanged();
                        listView.invalidateViews();
                        listView.refreshDrawableState();
                    }
                }
            }
        });
    }

}