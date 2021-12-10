package com.example.fitnessapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import android.app.DialogFragment;

public class selectorDialogFragment extends DialogFragment {
    // Different dialog IDs
    public static final int DIALOG_ID_ERROR = -1;
    public static final int DIALOG_ID_DURATION = 2;
    public static final int DIALOG_ID_DISTANCE = 3;
    public static final int DIALOG_ID_CALORIES = 4;
    public static final int DIALOG_ID_HEART_RATE = 5;
    public static final int DIALOG_ID_COMMENT = 6;

    private static final String DIALOG_ID_KEY = "dialog_id";

    public static selectorDialogFragment newInstance(int dialog_id) {
        selectorDialogFragment frag = new selectorDialogFragment();
        Bundle args = new Bundle();
        args.putInt(DIALOG_ID_KEY, dialog_id);
        frag.setArguments(args);
        return frag;
    }

    /*
        This function is overriden
        Because we want to create our own Dialog
        This is achieved by AlertDialog customization
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        int dialog_id = getArguments().getInt(DIALOG_ID_KEY);

        final Activity parent = getActivity();

        final AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        // Setup dialog appearance and onClick Listeners
        switch (dialog_id) {

            case DIALOG_ID_DURATION:

                final EditText mDurEditText = new EditText(parent);
                mDurEditText.setInputType(InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                builder.setTitle(R.string.ui_manual_entry_duration_title)
                        .setView(mDurEditText)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                return builder.create();
            case DIALOG_ID_DISTANCE:
                final EditText mDisEditText = new EditText(parent);
                mDisEditText.setInputType(InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                builder.setTitle(R.string.ui_manual_entry_distance_title)
                        .setView(mDisEditText)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                return builder.create();
            case DIALOG_ID_CALORIES:
                final EditText mCalEditText = new EditText(parent);
                mCalEditText.setInputType(InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                builder.setTitle(R.string.ui_manual_entry_calories_title)
                        .setView(mCalEditText)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                return builder.create();
            case DIALOG_ID_HEART_RATE:
                final EditText mHearEditText = new EditText(parent);
                mHearEditText.setInputType(InputType.TYPE_CLASS_NUMBER |
                        InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                builder.setTitle(R.string.ui_manual_entry_heart_rate_title)
                        .setView(mHearEditText)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                return builder.create();
            case DIALOG_ID_COMMENT:
                final EditText mComEditText = new EditText(parent);
                mComEditText.setHint(R.string.hint);
                builder.setTitle(R.string.ui_manual_entry_comment_title)
                        .setView(mComEditText)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                return builder.create();

            default:
                return null;
        }

    }

}
