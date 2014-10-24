package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.content.Intent;import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;import android.widget.DatePicker;import android.widget.EditText;import android.widget.TimePicker;


public class AddQuestActivity extends Activity {

    private DatePicker dPicker;
    private TimePicker tPicker;
    private EditText eText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quest);

        dPicker = (DatePicker)findViewById(R.id.datePicker);
        tPicker = (TimePicker)findViewById(R.id.timePicker);
        eText = (EditText)findViewById(R.id.quest_name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_quest, menu);
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
        }

        if (id == R.id.action_submit){
            Intent data = new Intent();

            StringBuilder sb = new StringBuilder();
            sb.append(eText.getText().toString());
            sb.append(" - ");
            sb.append(dPicker.getMonth());
            sb.append("/");
            sb.append(dPicker.getDayOfMonth());
            sb.append("/");
            sb.append(dPicker.getYear());
            sb.append(" by ");
            sb.append(tPicker.getCurrentHour());
            sb.append(":");
            sb.append(tPicker.getCurrentMinute());

            data.putExtra("questDemo", sb.toString());

            this.setResult(RESULT_OK, data);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
