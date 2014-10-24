package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hinodesoftworks.dailyrpg.database.DBManager;


public class HomeActivity extends Activity implements QuestFragment.OnQuestFragmentInteractionListener,
ShopItemFragment.OnShopFragmentInteractionListener, DungeonFragment.OnDungeonFragmentInteractionListener,
RandomBattleFragment.OnRBInteractionListener{

    private String[] pageNames;
    private DrawerLayout layout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private GameManager gameManager;

    public static final int REQUEST_CHAR_CREATE = 1;

    //nav drawer items are in a fixed position, so create convience constants to refelct this
    public static final int NAV_HOME = 0;
    public static final int NAV_QUESTS = 1;
    public static final int NAV_STORE = 2;
    public static final int NAV_DUNGEON = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pageNames = getResources().getStringArray(R.array.nav_items);
        layout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, layout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close){

            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView){
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

        //action bar stuff
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //go to "home" on app launch.
        selectItem(NAV_HOME);

        gameManager = GameManager.getInstance(this);
    }

    //button/view click handling
    public void onClick(View sender){
        Log.e("Click", "Click");
        switch (sender.getId())
        {
            case R.id.main_no_char_warning:
                Intent intent = new Intent(this, CreateCharacterActivity.class);
                startActivity(intent);
                break;
            case R.id.battleButton:
                Fragment battleFragment = new RandomBattleFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_view, battleFragment)
                        .commit();
                break;
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //check for nav drawer open on icon click.
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }


        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        //TODO: hide action items, if needed.

        return super.onPrepareOptionsMenu(menu);
    }

    private void selectItem(int position){
        //TODO: Filter by text index
        Fragment navFragment = null;

        switch (position){
            case NAV_HOME:
                navFragment = new HomeFragment();
                break;
            case NAV_QUESTS:
                navFragment = new QuestFragment();
                break;
            case NAV_STORE:
                navFragment = new ShopItemFragment();
                break;
            case NAV_DUNGEON:
                navFragment = new DungeonFragment();
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
    private class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id){
            selectItem(pos);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 200){

            if (resultCode == RESULT_OK){
                String holder = data.getStringExtra("questDemo");
                gameManager.hackyQList.add(holder);
                selectItem(NAV_QUESTS);
            }

        }
    }

    //fragment interfaces
    @Override
    public void onQuestSelected(int position)
    {

    }

    @Override
    public void onShopItemSelected(String id)
    {
        Toast.makeText(this, id + " added to inventory", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }

    @Override
    public void onBattleClose()
    {
        Fragment homeFrag = new HomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_view, homeFrag)
                .commit();
    }

    @Override
    public void onBattleEnd()
    {

    }}
