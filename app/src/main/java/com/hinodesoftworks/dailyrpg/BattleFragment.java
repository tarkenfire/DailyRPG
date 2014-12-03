package com.hinodesoftworks.dailyrpg;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hinodesoftworks.dailyrpg.game.Character;
import com.hinodesoftworks.dailyrpg.game.Enemy;
import com.hinodesoftworks.dailyrpg.game.GameManager;


public class BattleFragment extends Fragment implements View.OnClickListener {

    private OnBattleInteractionListener mListener;


    private TextView playerName, playerHP, playerAtk, playerDef;
    private TextView enemyName, enemyHP, enemyAtk, enemyDef;


    public BattleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_battle, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Activity parent = getActivity();
        //handles for UI I need references to
        playerName = (TextView) parent.findViewById(R.id.rb_player_title);
        playerHP = (TextView) parent.findViewById(R.id.rb_player_hp);
        playerAtk = (TextView) parent.findViewById(R.id.rb_player_attack);
        playerDef = (TextView) parent.findViewById(R.id.rb_player_defense);

        enemyName = (TextView) parent.findViewById(R.id.rb_enemy_title);
        enemyHP = (TextView) parent.findViewById(R.id.rb_enemy_hp);
        enemyAtk = (TextView) parent.findViewById(R.id.rb_enemy_attack);
        enemyDef = (TextView) parent.findViewById(R.id.rb_enemy_defense);

        //handles for UI I don't need to keep reference to
        Button attackButton;
        Button defendButton;
        Button itemButton;
        Button fleeButton;

        attackButton = (Button) parent.findViewById(R.id.rb_attack_button);
        defendButton = (Button) parent.findViewById(R.id.rb_defend_button);
        itemButton = (Button) parent.findViewById(R.id.rb_temp_item_button);
        fleeButton = (Button) parent.findViewById(R.id.rb_flee_button);

        attackButton.setOnClickListener(this);
        defendButton.setOnClickListener(this);
        itemButton.setOnClickListener(this);
        fleeButton.setOnClickListener(this);
        mListener.onFragmentAttached();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnBattleInteractionListener) activity;

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_attack_button:
                mListener.onBattleChoice(GameManager.BATTLE_CHOICE_ATTACK);
                break;
            case R.id.rb_defend_button:
                mListener.onBattleChoice(GameManager.BATTLE_CHOICE_DEFEND);
                break;
            case R.id.rb_temp_item_button:
                mListener.onBattleChoice(GameManager.BATTLE_CHOICE_ITEM);
                break;
            case R.id.rb_flee_button:
                mListener.onBattleChoice(GameManager.BATTLE_CHOICE_FLEE);
                break;
        }
    }

    //activity-called methods
    public void updateUI(Character character, Enemy enemy) {

        if (character == null || enemy == null) return;

        playerName.setText(character.getName());
        playerHP.setText("HP: " + character.getActualCurrentHP() + "/" + character.getActualMaxHP());
        playerAtk.setText("Atk: " + character.getBaseAtk());
        playerDef.setText("Def: " + character.getBaseDef());

        enemyName.setText(enemy.getName());
        enemyHP.setText("HP: " + enemy.getActualCurrentHP() + "/" + enemy.getActualMaxHP());
        enemyAtk.setText("Atk: " + enemy.getBaseAtk());
        enemyDef.setText("Def: " + enemy.getBaseDef());
    }

    //listener interface
    public interface OnBattleInteractionListener {
        public void onBattleChoice(int choice);

        public void onFragmentAttached();
    }
}
