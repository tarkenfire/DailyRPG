package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DungeonFragment extends Fragment implements View.OnClickListener {

    private OnDungeonFragmentInteractionListener mListener;

    //UI
    Button randomBattleButton;
    Button dungeonButton;
    Button bRushButton;

    public DungeonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dungeon, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        randomBattleButton = (Button) getActivity().findViewById(R.id.randomBattleButton);
        dungeonButton = (Button) getActivity().findViewById(R.id.dungeonBattleButton);
        bRushButton = (Button) getActivity().findViewById(R.id.bossRushButton);

        randomBattleButton.setOnClickListener(this);
        dungeonButton.setOnClickListener(this);
        bRushButton.setOnClickListener(this);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDungeonFragmentInteractionListener) activity;
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

    //button listener
    @Override
    public void onClick(View view) {
        mListener.onButtonPressed(view.getId());
    }

    public interface OnDungeonFragmentInteractionListener {
        public void onButtonPressed(int id);
    }

}
