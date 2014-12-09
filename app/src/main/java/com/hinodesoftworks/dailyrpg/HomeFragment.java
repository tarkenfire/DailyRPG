package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hinodesoftworks.dailyrpg.game.*;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private OnHomeInteractionListener mListener;

    private TextView warningText;
    private RelativeLayout contentDisplay;

    private TextView nameDisplay;
    private TextView classDisplay;
    private TextView expDisplay;
    private ImageView charDisplay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    public void onResume() {
        super.onResume();
        warningText = (TextView) getActivity().findViewById(R.id.main_no_char_warning);
        contentDisplay = (RelativeLayout) getActivity().findViewById(R.id.home_character_details);

        nameDisplay = (TextView) getActivity().findViewById(R.id.display_char_name);
        classDisplay = (TextView) getActivity().findViewById(R.id.display_char_class);
        expDisplay = (TextView) getActivity().findViewById(R.id.display_exp_pool);
        charDisplay = (ImageView) getActivity().findViewById(R.id.home_char_image);

        Button levelButton = (Button) getActivity().findViewById(R.id.display_level_up_button);
        levelButton.setOnClickListener(this);

        warningText.setOnClickListener(this);
        mListener.onHomeResumed();
    }

    //methods called from home activity
    public void setWarningVisibility(boolean isVisible) {
        if (isVisible) {
            warningText.setVisibility(View.VISIBLE);
            contentDisplay.setVisibility(View.GONE);
        } else {
            warningText.setVisibility(View.GONE);
            contentDisplay.setVisibility(View.VISIBLE);
        }
    }

    public void updatePlayerUI(com.hinodesoftworks.dailyrpg.game.Character player, Bitmap image) {
        nameDisplay.setText(player.getName());
        classDisplay.setText("Level " + player.getLevel() + " " + player.getClassName());
        expDisplay.setText("Exp: " + player.getExperience());

        if (image != null){
            charDisplay.setImageBitmap(image);
        }
    }

    //callback interface
    public interface OnHomeInteractionListener {
        public void onHomeResumed();
        public void onWarningClicked();
        public void onLevelUpClicked();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_no_char_warning:
                mListener.onWarningClicked();
                break;
            case R.id.display_level_up_button:
                mListener.onLevelUpClicked();
                break;
        }
    }
}
