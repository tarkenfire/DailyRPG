package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hinodesoftworks.dailyrpg.game.*;

public class HomeFragment extends Fragment implements View.OnClickListener
{
    private OnHomeInteractionListener mListener;

    private TextView warningText;
    private RelativeLayout contentDisplay;

    private TextView nameDisplay;
    private TextView classDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHomeInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHomeInteractionListener");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        warningText = (TextView)getActivity().findViewById(R.id.main_no_char_warning);
        contentDisplay = (RelativeLayout)getActivity().findViewById(R.id.home_character_details);

        nameDisplay = (TextView)getActivity().findViewById(R.id.display_char_name);
        classDisplay = (TextView)getActivity().findViewById(R.id.display_char_class);

        warningText.setOnClickListener(this);
        mListener.onHomeResumed();
    }

    //methods called from home activity
    public void setWarningVisibility(boolean isVisible){
        if (isVisible){
            warningText.setVisibility(View.VISIBLE);
            contentDisplay.setVisibility(View.GONE);
        }
        else{
            warningText.setVisibility(View.GONE);
            contentDisplay.setVisibility(View.VISIBLE);
        }
    }

    public void updatePlayerUI(com.hinodesoftworks.dailyrpg.game.Character player){
        nameDisplay.setText(player.getName());
        classDisplay.setText("Level " + player.getLevel() + " " +player.getClassName());
    }

    //callback interface
    public interface OnHomeInteractionListener{
        public void onHomeResumed();
        public void onWarningClicked();
    }

    @Override
    public void onClick(View view){
        mListener.onWarningClicked();
    }
}
