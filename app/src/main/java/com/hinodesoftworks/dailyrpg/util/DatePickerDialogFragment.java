package com.hinodesoftworks.dailyrpg.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    private OnDatePickedListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        mListener.onDatePicked(year, month, day);
    }

    //interface methods
    public void setListener(OnDatePickedListener listener){
        mListener = listener;
    }
    public interface OnDatePickedListener {
        public void onDatePicked(int year, int month, int day);
    }
}
