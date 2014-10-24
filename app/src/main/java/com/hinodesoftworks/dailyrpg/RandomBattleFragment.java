package com.hinodesoftworks.dailyrpg;



import android.app.Activity;import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;import android.widget.Button;import android.widget.TextView;import android.widget.Toast;

public class RandomBattleFragment extends Fragment implements View.OnClickListener {

    private OnRBInteractionListener mListener;

    GameManager gameManager;

    //UI handles
    TextView playerName, playerHp, playerAttack, playerDefense;
    TextView enemyName, enemyHp, enemyAttack, enemyDefense;
    Button attackButton, defendButton, healButton, fleeButton;

    public RandomBattleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random_battle, container, false);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            mListener = (OnRBInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        //get ui handles

        Activity parent = getActivity();

        playerName = (TextView)parent.findViewById(R.id.rb_player_title);
        playerHp = (TextView)parent.findViewById(R.id.rb_player_hp);
        playerAttack = (TextView)parent.findViewById(R.id.rb_player_attack);
        playerDefense = (TextView)parent.findViewById(R.id.rb_player_defense);

        enemyName = (TextView)parent.findViewById(R.id.rb_enemy_title);
        enemyHp = (TextView)parent.findViewById(R.id.rb_enemy_hp);
        enemyAttack = (TextView)parent.findViewById(R.id.rb_enemy_attack);
        enemyDefense = (TextView)parent.findViewById(R.id.rb_enemy_defense);

        attackButton = (Button)parent.findViewById(R.id.rb_attack_button);
        defendButton = (Button)parent.findViewById(R.id.rb_defend_button);
        healButton = (Button)parent.findViewById(R.id.rb_temp_item_button);
        fleeButton = (Button)parent.findViewById(R.id.rb_flee_button);

        attackButton.setOnClickListener(this);
        defendButton.setOnClickListener(this);
        healButton.setOnClickListener(this);
        fleeButton.setOnClickListener(this);

    }


    @Override
    public void onResume(){
        super.onResume();

        gameManager = GameManager.getInstance(getActivity());

        if (gameManager.doesCharacterExist()){
            gameManager.newBattle();
            updateUI();
        }
        else{
            Toast.makeText(getActivity(), "You need a character to engage in battles.", Toast.LENGTH_SHORT).show();
            mListener.onBattleClose();
        }
    }

    private void updateUI(){
        playerName.setText(gameManager.getCharacterName());
        playerHp.setText("HP: " + gameManager.getPlayerCurrentHP() + "/" + gameManager.getPlayerMaxHP());
        playerAttack.setText("Atk: " + gameManager.getPlayerAttack());
        playerDefense.setText("Def: " + gameManager.getPlayerDefense());

        enemyName.setText(gameManager.getEnemyName());
        enemyHp.setText("HP: " + gameManager.getEnemyCurrentHP() + "/" + gameManager.getEnemyMaxHP());
        enemyAttack.setText("Atk: " + gameManager.getEnemyAttack());
        enemyDefense.setText("Def: " + gameManager.getEnemyDefense());
    }


    public interface OnRBInteractionListener{
        public void onBattleClose();
        public void onBattleEnd();
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.rb_attack_button:
                gameManager.nextBattleTurn(0);
                break;
            case R.id.rb_defend_button:
                gameManager.nextBattleTurn(1);
                break;
            case R.id.rb_temp_item_button:
                gameManager.nextBattleTurn(2);
                break;
            case R.id.rb_flee_button:
                Toast.makeText(getActivity(), "Fled From Battle", Toast.LENGTH_SHORT).show();
                mListener.onBattleClose();
                break;
        }

        //check for battle end conditions
        if (gameManager.isPlayerDead()){
            Toast.makeText(getActivity(), "Battle Lost", Toast.LENGTH_SHORT).show();
            mListener.onBattleClose();
        }
        else if (gameManager.isEnemyDead()){
            Toast.makeText(getActivity(), "Battle Won", Toast.LENGTH_SHORT).show();
            mListener.onBattleClose();
        }

        updateUI();
    }
}
