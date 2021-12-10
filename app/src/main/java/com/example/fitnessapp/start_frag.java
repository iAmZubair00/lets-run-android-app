package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link start_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class start_frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public start_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment start_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static start_frag newInstance(String param1, String param2) {
        start_frag fragment = new start_frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_start_frag, container, false);
        Spinner inputSpinner = (Spinner) view.findViewById(R.id.inputSpinner);
        Spinner activitySpinner=(Spinner) view.findViewById(R.id.activitySpinner);

        final Button startActivityBtn=(Button) view.findViewById(R.id.addActivityBtn);
        startActivityBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                String selectedSpinnerItem = inputSpinner.getSelectedItem().toString();
                String selectedActivityItem = activitySpinner.getSelectedItem().toString();


                if (selectedSpinnerItem.equals("Manual Entry")) {
                    Intent mManualEntryIntent = new Intent(getContext(), manual_entry.class);
                    mManualEntryIntent.putExtra("activity_type",selectedActivityItem);
                    startActivity(mManualEntryIntent);
                } else {
                    Intent mGPSIntent = new Intent(getContext(), mapActivity.class);
                    mGPSIntent.putExtra("activity_type",selectedActivityItem);
                    startActivity(mGPSIntent);
                }

            }
        });

        return view;
    }
}