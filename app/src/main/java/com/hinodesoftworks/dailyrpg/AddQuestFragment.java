package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.hinodesoftworks.dailyrpg.todo.Quest;
import com.hinodesoftworks.dailyrpg.util.TimePickerDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddQuestFragment extends Fragment implements
        TimePickerDialogFragment.OnTimePickedListener, View.OnClickListener {

    private OnAddQuestInteractionListener mListener;

    //ui handles
    private EditText nameField, detailsField;
    private Spinner typeSpinner;
    private Button dateButton, timeButton;

    //time related constants
    private long timeMillis;


    public AddQuestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set time to current time on create
        Calendar c = Calendar.getInstance();
        timeMillis = c.getTimeInMillis();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_quest, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Activity context = getActivity();

        nameField = (EditText) context.findViewById(R.id.quest_name_field);
        detailsField = (EditText) context.findViewById(R.id.quest_details_field);
        typeSpinner = (Spinner) context.findViewById(R.id.quest_type_spinner);
        dateButton = (Button) context.findViewById(R.id.quest_date_button);
        timeButton = (Button) context.findViewById(R.id.quest_time_button);

        //set handlers
        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);

        //spinner init
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.quest_types,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        //update dynamic ui elements like date and time buttons
        updateUI();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnAddQuestInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //utility methods
    public void updateUI() {
        //TODO: hard coded strings
        DateFormat df = SimpleDateFormat.getDateInstance();
        DateFormat hf = SimpleDateFormat.getTimeInstance();

        dateButton.setText("Due Date: " + df.format(new Date(timeMillis)));
        timeButton.setText("Time Due: " + hf.format(new Date(timeMillis)));
    }

    private void showDateDialog() {

    }

    private void showTimeDialog() {
        TimePickerDialogFragment timePicker = new TimePickerDialogFragment();
        timePicker.setListener(this);
        timePicker.show(getActivity().getFragmentManager(), "timePicker");
    }


    //interfaces
    public interface OnAddQuestInteractionListener {
        public void onQuestCreated(Quest quest);
    }

    //implemented methods
    @Override
    public void onTimePicked(long timeInMillis) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quest_date_button:

                break;
            case R.id.quest_time_button:
                showTimeDialog();
                break;
        }
    }
}
