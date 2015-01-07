package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private TextView atkDisplay;
    private TextView defDisplay;
    private TextView hpDisplay;
    private ImageView charDisplay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
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
        atkDisplay = (TextView) getActivity().findViewById(R.id.display_attack);
        defDisplay = (TextView) getActivity().findViewById(R.id.display_defense);
        hpDisplay = (TextView) getActivity().findViewById(R.id.display_hp);

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
        atkDisplay.setText("Atk: " + player.getBaseAtk());
        defDisplay.setText("Def: " + player.getBaseDef());
        hpDisplay.setText("Max HP: " + player.getActualMaxHP());

        if (image != null){
            charDisplay.setImageBitmap(image);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.home_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_title){
            mListener.onDeleteClicked();
        }

        return super.onOptionsItemSelected(item);
    }


    //callback interface
    public interface OnHomeInteractionListener {
        public void onHomeResumed();
        public void onWarningClicked();
        public void onLevelUpClicked();
        public void onDeleteClicked();
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
