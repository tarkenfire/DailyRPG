package com.hinodesoftworks.dailyrpg.todo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hinodesoftworks.dailyrpg.R;


public class QuestStatusDialogFragment extends DialogFragment implements View.OnClickListener {

    private OnStatusInteractionListener mListener;

    //factory method
    public static QuestStatusDialogFragment newInstance(String name, String detail, String time,
                                                        String type, boolean isOverdue){
        QuestStatusDialogFragment holder = new QuestStatusDialogFragment();

        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("detail", detail);
        args.putString("time", time);
        args.putString("type", type);
        args.putBoolean("overdue", isOverdue);

        holder.setArguments(args);

        return holder;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_quest_status, container, false);

        Button completeButton, okButton;
        TextView nameDisplay, detailDisplay, timeDisplay, typeDisplay;

        completeButton = (Button) parent.findViewById(R.id.q_status_complete_button);
        okButton = (Button)parent.findViewById(R.id.q_status_ok_button);

        completeButton.setOnClickListener(this);
        okButton.setOnClickListener(this);

        nameDisplay = (TextView) parent.findViewById(R.id.q_status_name);
        detailDisplay = (TextView) parent.findViewById(R.id.q_status_details);
        timeDisplay = (TextView) parent.findViewById(R.id.q_time_due);
        typeDisplay = (TextView) parent.findViewById(R.id.q_status_type);

        Bundle args = getArguments();
        nameDisplay.setText(args.getString("name", "Quest Name"));
        detailDisplay.setText(args.getString("detail", "Quest Details"));
        timeDisplay.setText(args.getString("time", "Due Time"));
        typeDisplay.setText(args.getString("type", "Quest Type"));

        boolean checkFlag = args.getBoolean("overdue", false);

        if (checkFlag){
            completeButton.setEnabled(false);
        }

        //set dialog title for asthetic reasons
        getDialog().setTitle("Quest Details");

        return parent;
    }


    //interface
    public void setListener (OnStatusInteractionListener listener){
        this.mListener = listener;
    }

    public interface OnStatusInteractionListener{
        public void onCompleteClicked();
        public void onOkClicked();
    }

    //implemented listeners
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.q_status_complete_button:
                mListener.onCompleteClicked();
                break;
            case R.id.q_status_ok_button:
                mListener.onOkClicked();
                break;
        }

        this.dismissAllowingStateLoss();
    }
}
