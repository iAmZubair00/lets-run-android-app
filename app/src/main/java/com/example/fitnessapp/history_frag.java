package com.example.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class history_frag extends Fragment {


    public history_frag() {
        // Required empty public constructor
    }

    public static exerciseDBHelper exDbHelper;

    //public static HistoryAdapter adapter;
    public static myAdapter adapter;
    RecyclerView rcv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exDbHelper=new exerciseDBHelper(getContext());

        if(getActivity() != null) {
            ArrayList<ExerciseEntry> list;
            list= exDbHelper != null ?
                    exDbHelper.fetchEntries() : null;
            //Log.d("list", list.toString());
            adapter = new myAdapter(list);
            Log.d("adapter created", "adapter created");

//            if(LoaderManager.getInstance(this).getLoader(0) == null) {
//                LoaderManager.getInstance(this).initLoader(0, null, this).forceLoad();
//            } else {
//                LoaderManager.getInstance(this).restartLoader(0, null, this).forceLoad();
//            }
            //setListAdapter(adapter);
        }

        if(savedInstanceState != null){
            adapter.notifyDataSetChanged();
            //setListAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.history_frag, container, false);

        rcv=(RecyclerView) view.findViewById(R.id.rclview);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(adapter);
        return view;
    }

//    @Override
//    public void onListItemClick(ListView parent, View v, int position, long id) {
//        super.onListItemClick(parent, v, position, id);
//
//        // Find the row id by accessing the value in the invisible row
//        TextView tv = (TextView) v.findViewById(R.id.rowid);
//        long rowid = Long.parseLong(tv.getText().toString());
//        ExerciseEntry entry = manual_entry.exDbHelper.fetchEntryByIndex(rowid);
//
////        Intent mIntent;
////        if(entry.getmInputType() == StartFragment.INPUT_TO_ID_MAP.get(StartFragment.MANUAL_ENTRY)){
////            mIntent = new Intent(getActivity(), DisplayEntryActivity.class);
////        }else{
////            mIntent = new Intent(getActivity(), MapDisplayActivity.class);
////        }
////
////        mIntent.putExtra(FROM_HISTORY,true);
////        mIntent.putExtra(ROW_INDEX, rowid);
////        getActivity().startActivity(mIntent);
//    }




    public class myAdapter extends RecyclerView.Adapter<myAdapter.holder>
    {
        private List<ExerciseEntry> entries;

        public myAdapter(List<ExerciseEntry> entries)
        {
            this.entries = entries;
        }

        @NonNull
        @Override
        public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater inflater=LayoutInflater.from(parent.getContext());
            View view=inflater.inflate(R.layout.list_history,parent,false);
            return new holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull holder holder, int position)
        {
            ExerciseEntry entry = entries.get(position);

            holder.firstLine.setText(getFirstLine(entry));
            holder.secondLine.setText(getSecondLine(entry));
            holder.thirdLine.setText(entry.getmId()+"");
        }

        @Override
        public int getItemCount() {
            return entries.size();
        }

        public void notifyDataSetChangedWrapper(){
            if(manual_entry.exDbHelper != null) {
                List<ExerciseEntry> list = manual_entry.exDbHelper.fetchEntries();
                adapter = new myAdapter(list);
                rcv.setAdapter(adapter);
            }
            notifyDataSetChanged();
        }


        class holder extends RecyclerView.ViewHolder
        {
            TextView firstLine;
            TextView secondLine;
            TextView thirdLine;
            public holder(@NonNull View itemView) {
                super(itemView);
                firstLine=(TextView)itemView.findViewById(R.id.history_list_first_line);
                secondLine=(TextView)itemView.findViewById(R.id.history_list_second_line);
                thirdLine=(TextView)itemView.findViewById(R.id.rowid);

            }
        }

        private String getFirstLine(ExerciseEntry entry) {

            String input = start_frag.ID_TO_INPUT[entry.getmInputType()];
            String activity = start_frag.ID_TO_ACTIVITY[entry.getmActivityType()];
            String dateTime = formatDateTime(entry.getmDateTime());
            return input + ": " + activity + ", " + dateTime;
        }

        /**
         * Helper function to get the second line in the history list
         */
        private String getSecondLine(ExerciseEntry entry) {

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String unitPref = pref.getString("unitPref", "Kms");
            String distance = formatDistance(entry.getmDistance(),unitPref);
            String duration = formatDuration(entry.getmDuration());
            return distance + ", " + duration;
        }

        public void setHistory(List<ExerciseEntry> data) {
            if (data != null) entries.addAll(data);
            notifyDataSetChangedWrapper();
        }

    }
    // Convert the date and time from milliseconds to the proper format
    public static String formatDateTime(long dateTime) {
        Date date = new Date(dateTime);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");

        return timeFormat.format(date) + " " + dateFormat.format(date);
    }

    // Convert the duration from seconds to the proper format
    public static String formatDuration(double duration) {
        int minutes = (int)(duration/60);
        int seconds = (int)(duration%60);
        if (minutes == 0 && seconds == 0) return "0secs";
        return String.valueOf(minutes) + "min " + String.valueOf(seconds) + "secs";
    }

    // Convert the distance from kilometers to the proper format
    public static String formatDistance(double distance, String unitPref) {

        if (unitPref.equals("Miles")) {
            distance /= manual_entry.MILES2KM; // converts from km to miles
        }
        return String.format("%.2f", distance)+" "+unitPref;
    }






//
//    @Override
//    public Loader<List<ExerciseEntry>> onCreateLoader(int id, Bundle args) {
//        Log.d("Create", "Create");
//
//        return new HistoryLoader(getActivity());
//    }
//
//    @Override
//    public void onLoadFinished(Loader<List<ExerciseEntry>> loader, List<ExerciseEntry> data) {
//        Log.d("finish", "finish");
//        adapter.setHistory(data);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<List<ExerciseEntry>> loader) {
//        adapter.setHistory(new ArrayList<ExerciseEntry>());
//    }
//
//
//
//
//
//    public static class HistoryLoader extends AsyncTaskLoader<List<ExerciseEntry>> {
//
//        public HistoryLoader(Context context){
//            super(context);
//        }
//
//        @Override
//        public List<ExerciseEntry> loadInBackground() {
//            Log.d("loading","loading");
//            return manual_entry.exDbHelper != null ?
//                    manual_entry.exDbHelper.fetchEntries() : null;
//        }
//    }
//
//public class HistoryAdapter extends BaseAdapter {
//    private LayoutInflater inflater;
//    private List<ExerciseEntry> entries;
//
//    public HistoryAdapter(Context context, List<ExerciseEntry> entries) {
//        this.entries = entries;
//        inflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//
//        if (view == null) {
//            view = inflater.inflate(R.layout.list_history, parent, false);
//        }
//
//        ExerciseEntry entry = entries.get(position);
//
//        // Set the first line
//        TextView firstLine = (TextView) view.findViewById(R.id.history_list_first_line);
//        firstLine.setText(getFirstLine(entry));
//
//        // Set the second line
//        TextView secondLine = (TextView) view.findViewById(R.id.history_list_second_line);
//        secondLine.setText(getSecondLine(entry));
//
//        //Set the id
//        TextView thirdLine = (TextView) view.findViewById(R.id.rowid);
//        thirdLine.setText(entry.getmId()+"");
//
//        return view;
//    }
//
//    @Override
//    public void notifyDataSetChanged(){
//        if(manual_entry.exDbHelper != null) {
//            List<ExerciseEntry> list = manual_entry.exDbHelper.fetchEntries();
//            //adapter = new HistoryAdapter(getActivity(), list);
//            //setListAdapter(adapter);
//        }
//        super.notifyDataSetChanged();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return entries.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getCount() {
//        return entries.size();
//    }
//
//    public void setHistory(List<ExerciseEntry> data) {
//        if (data != null) entries.addAll(data);
//        notifyDataSetChanged();
//    }
//
//
//    /////////////////////// Helper function ///////////////////////
//
//    /**
//     * Helper function to get the first line in the history list
//     */
//    private String getFirstLine(ExerciseEntry entry) {
//
//        String input = start_frag.ID_TO_INPUT[entry.getmInputType()];
//        String activity = start_frag.ID_TO_ACTIVITY[entry.getmActivityType()];
//        String dateTime = formatDateTime(entry.getmDateTime());
//        return input + ": " + activity + ", " + dateTime;
//    }
//
//    /**
//     * Helper function to get the second line in the history list
//     */
//    private String getSecondLine(ExerciseEntry entry) {
//
//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        String unitPref = pref.getString("unitPref", "Kms");
//        String distance = formatDistance(entry.getmDistance(),unitPref);
//        String duration = formatDuration(entry.getmDuration());
//        return distance + ", " + duration;
//    }
//}
//


}