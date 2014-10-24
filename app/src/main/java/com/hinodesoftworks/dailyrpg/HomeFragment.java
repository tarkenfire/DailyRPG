package com.hinodesoftworks.dailyrpg;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment
{
    private GameManager manager;
    private TextView warningText;
    private RelativeLayout contentDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        manager = GameManager.getInstance(this.getActivity());
    }

    @Override
    public void onResume(){
        super.onResume();
        warningText = (TextView)getActivity().findViewById(R.id.main_no_char_warning);
        contentDisplay = (RelativeLayout)getActivity().findViewById(R.id.home_character_details);

        if (manager.doesCharacterExist()){
            warningText.setVisibility(View.GONE);
            contentDisplay.setVisibility(View.VISIBLE);
        }
        else{
            warningText.setVisibility(View.VISIBLE);
            contentDisplay.setVisibility(View.GONE);
        }



    }
}
