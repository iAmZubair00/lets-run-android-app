package com.example.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settings_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settings_frag extends PreferenceFragmentCompat {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public settings_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settings_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static settings_frag newInstance(String param1, String param2) {
        settings_frag fragment = new settings_frag();
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

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        SharedPreferences sh_prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
//        SharedPreferences.Editor pref_editor = sh_prefs.edit();
//
//        View view= inflater.inflate(R.layout.fragment_settings_frag, container, false);
//
//        final TextView signOutBtn=(TextView) view.findViewById(R.id.signoutBtn);
//        signOutBtn.setOnClickListener(new View.OnClickListener()
//        {
//
//            @Override
//            public void onClick(View v)
//            {
//                pref_editor.clear();
//                pref_editor.apply();
//
//                Intent intent = new Intent(getContext(), Login.class);
//                startActivity(intent);
//            }
//        });
//
//        return view;
//    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preference_settings);

        SharedPreferences sh_prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor pref_editor = sh_prefs.edit();

        Preference signoutBtn = getPreferenceManager().findPreference("signoutBtn");
        if (signoutBtn != null) {
            signoutBtn.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference arg0) {
                    pref_editor.clear();
                    pref_editor.apply();

                    Intent intent = new Intent(getContext(), Login.class);
                    startActivity(intent);
                    return true;
                }
            });
        }

    }

}