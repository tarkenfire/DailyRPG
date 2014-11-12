package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hinodesoftworks.dailyrpg.game.*;
import com.hinodesoftworks.dailyrpg.game.Character;


//TODO: Get a class variable to hold fragment manager so as to not call .getFragmentManager so much

public class HomeActivity extends Activity implements HomeFragment.OnHomeInteractionListener,
        QuestFragment.OnQuestFragmentInteractionListener, ShopFragment.OnShopFragmentInteractionListener,
        DungeonFragment.OnDungeonFragmentInteractionListener, AddCharacterFragment.OnCharacterCreateListener,
        GameManager.GameListener, BattleFragment.OnBattleInteractionListener
{

    //nav drawers variables
    private String[] pageNames;
    private DrawerLayout layout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    //nav drawer items are in a fixed position, so create convience constants to reflect this
    public static final int NAV_HOME = 0;
    public static final int NAV_QUESTS = 1;
    public static final int NAV_STORE = 2;
    public static final int NAV_DUNGEON = 3;

    //refs to all fragments
    private HomeFragment homeFragment;
    private QuestFragment questFragment;
    private ShopFragment shopFragment;
    private DungeonFragment dungeonFragment;
    private AddCharacterFragment addCharacterFragment;
    private BattleFragment battleFragment;

    //managers
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pageNames = getResources().getStringArray(R.array.nav_items);
        layout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, layout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close){

            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }

        };

        drawerList = (ListView) findViewById(R.id.left_drawer);

        //TODO: custom list adapter
        //adapter for nav drawer.
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,
                pageNames));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        layout.setDrawerListener(drawerToggle);

        //create all fragments at start
        homeFragment = new HomeFragment();
        questFragment = new QuestFragment();
        shopFragment = new ShopFragment();
        dungeonFragment = new DungeonFragment();
        addCharacterFragment = new AddCharacterFragment();
        battleFragment = new BattleFragment();

        //init managers
        gameManager = GameManager.getInstance();
        gameManager.init(null, this);

        //action bar stuff
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //TODO: Draw character from database

        //go to "home" on app launch.
        selectItem(NAV_HOME);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        //check for nav drawer open on icon click.
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }


        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        //TODO: hide action items, if needed.

        return super.onPrepareOptionsMenu(menu);
    }

    private void selectItem(int position)
    {
        Fragment navFragment = null;

        switch (position)
        {
            case NAV_HOME:
                navFragment = homeFragment;
                break;
            case NAV_QUESTS:
                navFragment = questFragment;
                break;
            case NAV_STORE:
                navFragment = shopFragment;
                break;
            case NAV_DUNGEON:
                navFragment = dungeonFragment;
                break;
            default:
                //in case of error, default to "home" menu
                selectItem(NAV_HOME);
                return;
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_view, navFragment)
                .commit();

        drawerList.setItemChecked(position, true);
        layout.closeDrawer(drawerList);
    }

    //listener
    private class DrawerItemClickListener implements ListView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id)
        {
            selectItem(pos);
        }
    }

    //fragment interfaces
    @Override
    public void onHomeResumed(){
        if (gameManager.getPlayerCharacter() == null){
            homeFragment.setWarningVisibility(true);
        }
        else{
            homeFragment.setWarningVisibility(false);
            homeFragment.updatePlayerUI(gameManager.getPlayerCharacter());

        }
    }

    @Override
    public void onWarningClicked(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_view, addCharacterFragment)
                .commit();
        drawerList.clearChoices();
    }

    //char create fragment
    @Override
    public void onCharacterCreated(Character character){
        gameManager.updateCharacter(character);

        //TODO: Hard-coded string
        Toast.makeText(this, "Character Created", Toast.LENGTH_SHORT).show();

        //change back to home fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_view, homeFragment)
                .commit();

        drawerList.setItemChecked(NAV_HOME, true);
    }

    //from dungeon fragment
    @Override
    public void onButtonPressed(int id){
        switch (id){
            case R.id.randomBattleButton:
                gameManager.setBattleMode(GameManager.BattleMode.MODE_RANDOM);
                break;
            case R.id.dungeonBattleButton:
                gameManager.setBattleMode(GameManager.BattleMode.MODE_DUNGEON);
                break;
            case R.id.bossRushButton:
                gameManager.setBattleMode(GameManager.BattleMode.MODE_BOSS);
                break;
        }

        //change to battle fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_view, battleFragment)
                .commit();
        drawerList.clearChoices();

    }

    @Override
    public void onQuestSelected(int position){

    }

    @Override
    public void onShopItemSelected(String id){

    }

    //battle fragment
    @Override
    public void onBattleChoice(int choice){
        switch (choice){
            case GameManager.BATTLE_CHOICE_ATTACK:
                gameManager.attack();
                break;
            case GameManager.BATTLE_CHOICE_DEFEND:
                gameManager.defend();
                break;
            case GameManager.BATTLE_CHOICE_ITEM:
                //TODO: Doesn't really make sense as it is. Either change how "items" work or
                //figure out better way to do this.
                gameManager.useItem(new Consumable());
                break;
            case GameManager.BATTLE_CHOICE_FLEE:
                gameManager.flee();
                break;
        }
    }

    @Override
    public void onFragmentAttached(){
        gameManager.createNewBattle();
        battleFragment.updateUI(gameManager.getPlayerCharacter(),
                gameManager.getCurrentEnemy() != null ? gameManager.getCurrentEnemy() :
                        new Enemy("Enemy",100, 50, 20,10));

    }

    //game manager methods

    @Override
    public void onBattleEnd() {

    }

    @Override
    public void onTurnEnd(com.hinodesoftworks.dailyrpg.game.Character character, Enemy enemy) {
        battleFragment.updateUI(gameManager.getPlayerCharacter(),
                gameManager.getCurrentEnemy() != null ? gameManager.getCurrentEnemy() :
                        new Enemy("Enemy",100, 50, 20,10));
    }

    @Override
    public void onBattleFled() {

    }


}
