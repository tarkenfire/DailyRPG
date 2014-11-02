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


public class HomeActivity extends Activity implements HomeFragment.OnHomeInteractionListener,
        QuestFragment.OnQuestFragmentInteractionListener, ShopFragment.OnShopFragmentInteractionListener,
        DungeonFragment.OnDungeonFragmentInteractionListener, AddCharacterFragment.OnCharacterCreateListener,
        GameManager.GameListener
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
    protected void onCreate(Bundle savedInstanceState)
    {
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
        gameManager.init(new Character("Player", "Super Player",1, 100, 50, 40, null ), this);

        //action bar stuff
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //TODO: Draw character from database

        //go to "home" on app launch.
        selectItem(NAV_HOME);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
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

    }

    @Override
    public void onWarningClicked(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_view, addCharacterFragment)
                .commit();
        drawerList.clearChoices();
    }

    @Override
    public void onCharacterCreated(Uri uri)
    {

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
    public void onQuestSelected(int position)
    {

    }

    @Override
    public void onShopItemSelected(String id)
    {

    }

    //game manager methods

    @Override
    public void onBattleEnd() {

    }

    @Override
    public void onTurnEnd(com.hinodesoftworks.dailyrpg.game.Character character, Enemy enemy) {

    }

    @Override
    public void onBattleFled() {

    }
}
