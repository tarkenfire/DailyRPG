package com.hinodesoftworks.dailyrpg.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hinodesoftworks.dailyrpg.R;
import com.hinodesoftworks.dailyrpg.game.Enemy;

import java.util.ArrayList;
import java.util.List;

public class EnemyListAdapter extends ArrayAdapter<Enemy> {

    private Context context;
    private ArrayList<Enemy> enemies;

    public EnemyListAdapter(Context context, int resource, ArrayList<Enemy> objects) {
        super(context, resource, objects);

        this.context = context;
        this.enemies = objects;
    }

    @Override
    public void add(Enemy object) {
        enemies.add(object);
    }

    @Override
    public void clear() {
        enemies.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.enemy_row, parent, false);
        }

        Enemy currEnemy = enemies.get(position);

        TextView nameView = (TextView) convertView.findViewById(R.id.enemy_row_name);

        //todo: hard coded string
        nameView.setText("Level " + currEnemy.getLevel() + " " +currEnemy.getName());

        return convertView;
    }
}
