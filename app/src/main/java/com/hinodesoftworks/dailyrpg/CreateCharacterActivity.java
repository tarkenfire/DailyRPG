package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.hinodesoftworks.dailyrpg.database.ClassesTable;
import com.hinodesoftworks.dailyrpg.database.DBManager;


public class CreateCharacterActivity extends Activity {

    private DBManager dbManager;
    private GameManager gameManager;
    private EditText charName;
    private Spinner classSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);

        gameManager = GameManager.getInstance(this);

        //get spinner options from database
        dbManager = DBManager.getInstance(this);
        dbManager.openDatabase();

        String[] projection = {ClassesTable.COLUMN_ID, ClassesTable.COLUMN_NAME};
        Cursor classOptions = dbManager.query(ClassesTable.TABLE_NAME, projection, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,classOptions,
                new String[]{ClassesTable.COLUMN_NAME}, new int[] {android.R.id.text1});

        dbManager.closeDatabase();

        //DUMMY DATA
        classSpinner = (Spinner)findViewById(R.id.classSpinner);
        charName = (EditText)findViewById(R.id.charEditText);

        String[] dummyClasses =  {"Fighter", "Rogue", "Thief", "Cleric"};
        ArrayAdapter<String> dummyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1, dummyClasses);
        dummyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(dummyAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_character, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.create_character){
            createCharacter();
        }

        return super.onOptionsItemSelected(item);
    }

    private void createCharacter(){
        String tName;
        String tClass;
        //check edit text.
        tName = charName.getText().toString();

        if (tName.matches("")){
            Toast.makeText(this, "Name Cannot be Blank", Toast.LENGTH_LONG).show();
            return;
        }

        //get class name
        tClass = classSpinner.getSelectedItem().toString();

        //make character
        gameManager.addCharacter(gameManager.createDummyCharacter(tName, tClass));

        this.finish();
    }
}
