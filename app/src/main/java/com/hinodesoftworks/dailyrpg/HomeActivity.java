package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.hinodesoftworks.dailyrpg.game.*;
import com.hinodesoftworks.dailyrpg.game.Character;
import com.hinodesoftworks.dailyrpg.todo.Quest;
import com.hinodesoftworks.dailyrpg.todo.QuestManager;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


//TODO: Get a class variable to hold fragment manager so as to not call .getFragmentManager so much

public class HomeActivity extends Activity implements HomeFragment.OnHomeInteractionListener,
        QuestFragment.OnQuestFragmentInteractionListener,
        DungeonFragment.OnDungeonFragmentInteractionListener, AddCharacterFragment.OnCharacterCreateListener,
        GameManager.GameListener, BattleFragment.OnBattleInteractionListener, AddQuestFragment.OnAddQuestInteractionListener {

    //nav drawers variables
    private String[] pageNames;
    private DrawerLayout layout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    //nav drawer items are in a fixed position, so create convience constants to reflect this
    public static final int NAV_HOME = 0;
    public static final int NAV_QUESTS = 1;
    public static final int NAV_DUNGEON = 2;

    //request codes
    private static final int REQUEST_PICK_IMAGE = 10001;

    //refs to all fragments
    private HomeFragment homeFragment;
    private QuestFragment questFragment;
    private DungeonFragment dungeonFragment;
    private AddCharacterFragment addCharacterFragment;
    private BattleFragment battleFragment;
    private AddQuestFragment addQuestFragment;

    //managers
    private GameManager gameManager;
    private QuestManager questManager;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pageNames = getResources().getStringArray(R.array.nav_items);
        layout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, layout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
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
        dungeonFragment = new DungeonFragment();
        addCharacterFragment = new AddCharacterFragment();
        battleFragment = new BattleFragment();
        addQuestFragment = new AddQuestFragment();


        //init managers
        gameManager = GameManager.getInstance();
        gameManager.init(null, this);
        questManager = QuestManager.getInstance(this);
        dataManager = DataManager.getInstance(this);

        //check for saved data
        if (dataManager.isCharacterSaved()){
            gameManager.updateCharacter(dataManager.getStoredCharacter());
        }

        if (dataManager.areQuestsSaved()){
            questManager.setQuests(dataManager.getStoredQuests());
        }




        //action bar stuff
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //TODO: Draw character from database

        //go to "home" on app launch.
        selectItem(NAV_HOME);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataManager.persistData(gameManager.getPlayerCharacter(), questManager.getQuests());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
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
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    try{
                    gameManager.setsUserImage(decodeUri(data.getData()));
                    }
                    catch (FileNotFoundException e) {
                        //TODO: handle error
                    }
                    addCharacterFragment.updateUI(gameManager.getsUserImage());
                }
                break;
        }
    }

    private void selectItem(int position) {
        //check for a created character, if none exists, restrict navigation until one is created
        Fragment navFragment = null;

        switch (position) {
            case NAV_HOME:
                navFragment = homeFragment;
                break;
            case NAV_QUESTS:
                if (gameManager.getPlayerCharacter() == null){
                    Toast.makeText(this, "Character must be created first.", Toast.LENGTH_LONG).show();
                    selectItem(NAV_HOME);
                    return;
                }
                navFragment = questFragment;
                break;
            case NAV_DUNGEON:
                if (gameManager.getPlayerCharacter() == null){
                    Toast.makeText(this, "Character must be created first.", Toast.LENGTH_LONG).show();
                    selectItem(NAV_HOME);
                    return;
                }
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

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException{
        //final max image size
        final int MAX_SIZE = 200;

        //get original image size
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null,  opts);

        //get scale size
        int tWidth = opts.outWidth; int tHeight = opts.outHeight;
        int scale = 1;

        while(true){
            if (tWidth / 2 < MAX_SIZE || tHeight / 2 < MAX_SIZE){
                break;
            }
            tWidth /= 2;
            tHeight /= 2;
            scale *= 2;
        }

        //decode image at correct scale
        BitmapFactory.Options opts2 = new BitmapFactory.Options();
        opts2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null,
                opts2);
    }

    //listener
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
            selectItem(pos);
        }
    }

    //fragment interfaces
    @Override
    public void onHomeResumed() {
        if (gameManager.getPlayerCharacter() == null) {
            homeFragment.setWarningVisibility(true);
        } else {
            homeFragment.setWarningVisibility(false);
            homeFragment.updatePlayerUI(gameManager.getPlayerCharacter(), gameManager.getsUserImage());

        }
    }

    @Override
    public void onWarningClicked() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_view, addCharacterFragment)
                .commit();
        drawerList.clearChoices();
    }

    @Override
    public void onLevelUpClicked(){
        Character player = gameManager.getPlayerCharacter();

        //todo: should scale exp
        if (player.getExperience() >= 1000){
            //okay to level up
            player.setExperience(player.getExperience() - 1000);
            player.levelUp();
            homeFragment.updatePlayerUI(player, gameManager.getsUserImage());
            Toast.makeText(this, player.getName() + " leveled up to level " + player.getLevel(),
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Not enough experience to level up.", Toast.LENGTH_SHORT).show();
        }

        //cleanup for battle manager; null enemy list to force new list generation.
        gameManager.nullEnemyList();

    }

    //char create fragment
    @Override
    public void onCharacterCreated(Character character) {
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

    public void onImageClick(){
        Intent pick = new Intent(Intent.ACTION_PICK);
        pick.setType("image/*");
        startActivityForResult(pick, REQUEST_PICK_IMAGE);
    }

    //from dungeon fragment
    @Override
    public void onEnemySelected(int position) {
        gameManager.setCurrentEnemy(position);

        //change to battle fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_view, battleFragment)
                .commit();
        drawerList.clearChoices();

    }

    @Override
    public void onDungeonScreenResumed(){
        if (gameManager.getEnemyList() == null){
            gameManager.generateEnemies(gameManager.getPlayerCharacter().getLevel());
        }
        dungeonFragment.updateEnemyList(gameManager.getEnemyList());
    }

    //quest fragment
    @Override
    public void onQuestSelected(int position) {
        Quest holder = questManager.getQuest(position);
        String timeString, typeString = "";

        questManager.checkForOverdue();

        DateFormat df = SimpleDateFormat.getDateTimeInstance();
        Calendar formatCalendar = Calendar.getInstance();
        formatCalendar.setTimeInMillis(holder.getDueTimeInMillis());
        timeString = df.format(formatCalendar.getTime());


        switch (holder.getCurrentType()){
            case QUEST_SINGLE:
                typeString = "Single";
                break;
            case QUEST_DAILY:
                typeString = "Daily";
                break;
            case QUEST_MONTHLY:
                typeString = "Monthly";
                break;
        }

        questFragment.showQuestDetails("Name: " + holder.getQuestName(), "Details: " + holder.getQuestDetails(),
                "Due By: " + timeString, "Type: " + typeString);
    }

    public void onAddQuestPressed() {
        //changed to add fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_view, addQuestFragment)
                .commit();
        drawerList.clearChoices();
    }

    @Override
    public void onQuestCompleted(int position) {
        Quest completed = questManager.getQuest(position);
        Character player = gameManager.getPlayerCharacter();

        player.modifyExp(completed.getExpValue());

        Toast.makeText(this, "Quest: " + completed.getQuestName() + " completed! " +
                        completed.getExpValue() + " experience earned.", Toast.LENGTH_LONG)
                        .show();

        questManager.completeQuest(position);
        questFragment.updateList(questManager.getQuests());
    }

    //battle fragment
    @Override
    public void onBattleChoice(int choice) {
        switch (choice) {
            case GameManager.BATTLE_CHOICE_ATTACK:
                gameManager.attack();
                break;
            case GameManager.BATTLE_CHOICE_DEFEND:
                gameManager.defend();
                break;
            case GameManager.BATTLE_CHOICE_FLEE:
                gameManager.flee();
                break;
        }
    }

    @Override
    public void onFragmentAttached() {

        battleFragment.updateUI(gameManager.getPlayerCharacter(),
                gameManager.getCurrentEnemy() != null ? gameManager.getCurrentEnemy() :
                        new Enemy("Enemy", 100, 50, 20, 10));

    }

    //add quest fragment
    @Override
    public void onQuestCreated(Quest quest) {
        questManager.addQuest(quest);

        Toast.makeText(this, "Quest Created", Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_view, questFragment)
                .commit();
        drawerList.clearChoices();

        questFragment.updateList(questManager.getQuests());
        questManager.checkForOverdue();
    }

    //game manager methods

    @Override
    public void onBattleEnd() {
        Toast.makeText(this, "Battle Ended. Current High Score is: " + gameManager.getScore(),
                        Toast.LENGTH_SHORT).show();

        selectItem(NAV_HOME);
    }



    @Override
    public void onTurnEnd(com.hinodesoftworks.dailyrpg.game.Character character, Enemy enemy) {
        battleFragment.updateUI(gameManager.getPlayerCharacter(),
                gameManager.getCurrentEnemy() != null ? gameManager.getCurrentEnemy() :
                        new Enemy("Enemy", 100, 50, 20, 10));
    }

    @Override
    public void onBattleFled() {
        Toast.makeText(this, "Battle Fled", Toast.LENGTH_SHORT).show();

        selectItem(NAV_HOME);
    }

    //utility methods


}
