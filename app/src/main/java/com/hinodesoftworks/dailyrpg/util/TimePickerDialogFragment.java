package com.hinodesoftworks.dailyrpg.util;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener {

    private OnTimePickedListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        mListener.onTimePicked(hour, minute);
    }

    //listener/interface methods
    public void setListener(OnTimePickedListener listener) {
        mListener = listener;
    }

    public interface OnTimePickedListener {
        public void onTimePicked(int hour, int minute);
    }
}
