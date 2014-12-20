package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.hinodesoftworks.dailyrpg.game.Enemy;
import com.hinodesoftworks.dailyrpg.util.EnemyListAdapter;

import java.util.ArrayList;

public class DungeonFragment extends Fragment implements ListView.OnItemClickListener,
                    View.OnClickListener {

    private OnDungeonFragmentInteractionListener mListener;
    private ListView enemyList;
    private EnemyListAdapter eAdapter = null;
    private boolean signInVisible = false;

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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (eAdapter == null){
            eAdapter = new EnemyListAdapter(getActivity(), R.layout.enemy_row, new ArrayList<Enemy>());
        }


    }

    @Override
    public void onResume() {
        super.onResume();

        enemyList = (ListView)getActivity().findViewById(R.id.battle_enemy_list);
        enemyList.setAdapter(eAdapter);

        getActivity().findViewById(R.id.sign_in_button).setOnClickListener(this);
        getActivity().findViewById(R.id.sign_out_button).setOnClickListener(this);
        getActivity().findViewById(R.id.show_leader).setOnClickListener(this);

        enemyList.setOnItemClickListener(this);

        mListener.onDungeonScreenResumed();
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

    public interface OnDungeonFragmentInteractionListener {
        public void onEnemySelected(int position);
        public void onDungeonScreenResumed();
        public void onSignIn();
        public void onSignOut();
        public void onShowLeaderboard();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        mListener.onEnemySelected(pos);
    }

    public void updateEnemyList(ArrayList<Enemy> list){
        eAdapter.clear();

        for (Enemy e : list){
            eAdapter.add(e);
        }

        eAdapter.notifyDataSetChanged();
    }

    public void updateButtonUI(boolean signInClicked){
        try{
            if(signInClicked){
                getActivity().findViewById(R.id.sign_in_button).setVisibility(View.GONE);
                getActivity().findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
            }
            else{
                getActivity().findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.sign_out_button).setVisibility(View.GONE);
            }
        }
        catch (NullPointerException e){
            return;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
                mListener.onSignIn();
                break;
            case R.id.sign_out_button:
                mListener.onSignOut();
                break;
            case R.id.show_leader:
                mListener.onShowLeaderboard();
                break;
        }
    }
}
