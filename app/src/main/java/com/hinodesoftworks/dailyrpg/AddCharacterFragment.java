package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.hinodesoftworks.dailyrpg.game.*;
import com.hinodesoftworks.dailyrpg.game.Character;

public class AddCharacterFragment extends Fragment {

    EditText nameInput;
    Spinner classSpinner;

    private OnCharacterCreateListener mListener;

    public AddCharacterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_character, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnCharacterCreateListener) activity;
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
    public void onResume(){
        super.onResume();

        nameInput = (EditText)getActivity().findViewById(R.id.create_char_name);
        classSpinner = (Spinner)getActivity().findViewById(R.id.create_class_spinner);

        //TODO: Dynamic init of spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.class_names,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.create_character, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_create_character){
            String nameHolder = nameInput.getText().toString();

            mListener.onCharacterCreated(Character.createNewCharacter(
                    nameHolder.matches("") ? "Player" : nameHolder,
                    classSpinner.getSelectedItem().toString()));
        }

        return super.onOptionsItemSelected(item);
    }

    public interface OnCharacterCreateListener {
        // TODO: Update argument type and name
        public void onCharacterCreated(com.hinodesoftworks.dailyrpg.game.Character character);
    }

}
