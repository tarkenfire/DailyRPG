package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.hinodesoftworks.dailyrpg.todo.Quest;
import com.hinodesoftworks.dailyrpg.util.DatePickerDialogFragment;
import com.hinodesoftworks.dailyrpg.util.TimePickerDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddQuestFragment extends Fragment implements
        TimePickerDialogFragment.OnTimePickedListener, View.OnClickListener,
        DatePickerDialogFragment.OnDatePickedListener {

    private OnAddQuestInteractionListener mListener;

    //ui handles
    private EditText nameField, detailsField;
    private Spinner typeSpinner;
    private Button dateButton, timeButton;

    //time related vars
    private int month, day, year;
    private int hour, minute;


    public AddQuestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set time to current time on create
        Calendar c = Calendar.getInstance();

        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        year = c.get(Calendar.YEAR);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);


        //notify of adding button to action bar
        setHasOptionsMenu(true);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.add_quest, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_quest_submit) {
            submitQuest();
        }

        return super.onOptionsItemSelected(item);
    }

    //utility methods
    public void updateUI(){
        DateFormat df = SimpleDateFormat.getDateInstance();
        DateFormat hf = SimpleDateFormat.getTimeInstance();

        Calendar formatCalendar = Calendar.getInstance();
        formatCalendar.set(year, month, day, hour, minute);

        //TODO: Hard coded strings
        dateButton.setText("Due Date: " + df.format(formatCalendar.getTime()));
        timeButton.setText("Time Due: " + hf.format(formatCalendar.getTime()));

    }

    private void showDateDialog() {
        DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
        datePicker.setListener(this);
        datePicker.show(getActivity().getFragmentManager(), "datePicker");
    }

    private void showTimeDialog() {
        TimePickerDialogFragment timePicker = new TimePickerDialogFragment();
        timePicker.setListener(this);
        timePicker.show(getActivity().getFragmentManager(), "timePicker");
    }

    private void submitQuest(){
        String qName = nameField.getText().toString();
        String qDetails = nameField.getText().toString();
        Quest.QuestType qType = Quest.QuestType.QUEST_SINGLE;
        long timeMillis;

        //TODO: Hard coded numbers
        switch(typeSpinner.getSelectedItemPosition()){
            case 0:
                qType = Quest.QuestType.QUEST_SINGLE;
                break;
            case 1:
                qType = Quest.QuestType.QUEST_DAILY;
                break;
            case 2:
                qType = Quest.QuestType.QUEST_MONTHLY;
                break;
        }

        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute);

        timeMillis = c.getTimeInMillis();


        mListener.onQuestCreated(new Quest(
                qType,
                qName.matches("") ? "Untiled Quest" : qName,
                qDetails.matches("") ? "No Details" : qDetails,
                timeMillis
        ));
    }


    //interfaces
    public interface OnAddQuestInteractionListener {
        public void onQuestCreated(Quest quest);
    }

    //implemented methods
    @Override
    public void onTimePicked(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        updateUI();
    }

    @Override
    public void onDatePicked(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        updateUI();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quest_date_button:
                showDateDialog();
                break;
            case R.id.quest_time_button:
                showTimeDialog();
                break;
        }
    }
}
