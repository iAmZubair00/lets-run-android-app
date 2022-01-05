package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

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

    // Different types of input
    public static final String INPUT_TYPE = "input_type";
    public static final String MANUAL_ENTRY = "Manual Entry";
    public static final String AUTOMATIC = "Automatic";
    public static final String GPS = "GPS";

    public static final Map<String,Integer> INPUT_TO_ID_MAP;
    static{
        INPUT_TO_ID_MAP = new HashMap<>();
        INPUT_TO_ID_MAP.put(MANUAL_ENTRY,0);
        INPUT_TO_ID_MAP.put(AUTOMATIC,1);
        INPUT_TO_ID_MAP.put(GPS,2);
    }
    public static final String[] ID_TO_INPUT = {MANUAL_ENTRY, AUTOMATIC, GPS};


    // Different types of Activities
    public static final String ACTIVITY_TYPE = "activity_type";

    public static final Map<String,Integer> ACTIVITY_TO_ID_MAP;
    static{
        ACTIVITY_TO_ID_MAP = new HashMap<>();
        ACTIVITY_TO_ID_MAP.put("Running", 0);
        ACTIVITY_TO_ID_MAP.put("Walking", 1);
        ACTIVITY_TO_ID_MAP.put("Standing", 2);
        ACTIVITY_TO_ID_MAP.put("Cycling", 3);
        ACTIVITY_TO_ID_MAP.put("Hiking", 4);
        ACTIVITY_TO_ID_MAP.put("Downhill Skiing", 5);
    }

    public static final String[] ID_TO_ACTIVITY = {"Running", "Walking", "Standing",
            "Cycling", "Hiking", "Downhill Skiing"};

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
        View view= inflater.inflate(R.layout.start_frag, container, false);
        Spinner inputSpinner = (Spinner) view.findViewById(R.id.inputSpinner);
        Spinner activitySpinner=(Spinner) view.findViewById(R.id.activitySpinner);

        final Button startActivityBtn=(Button) view.findViewById(R.id.addActivityBtn);
        startActivityBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                String inputText = inputSpinner.getSelectedItem().toString();
                String activityText = activitySpinner.getSelectedItem().toString();


                if (inputText.equals("Manual Entry")) {
                    Intent mManualEntryIntent = new Intent(getContext(), manual_entry.class);
                    mManualEntryIntent.putExtra(INPUT_TYPE,INPUT_TO_ID_MAP.get(inputText));
                    mManualEntryIntent.putExtra(ACTIVITY_TYPE,ACTIVITY_TO_ID_MAP.get(activityText));
                    startActivity(mManualEntryIntent);
                } else {
                    Intent mGPSIntent = new Intent(getContext(), mapActivity.class);
                    mGPSIntent.putExtra(INPUT_TYPE,INPUT_TO_ID_MAP.get(inputText));
                    mGPSIntent.putExtra(ACTIVITY_TYPE,ACTIVITY_TO_ID_MAP.get(activityText));
                    startActivity(mGPSIntent);
                }

            }
        });

        return view;
    }
}