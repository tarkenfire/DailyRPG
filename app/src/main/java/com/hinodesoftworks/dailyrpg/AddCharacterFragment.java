package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hinodesoftworks.dailyrpg.game.*;
import com.hinodesoftworks.dailyrpg.game.Character;

public class AddCharacterFragment extends Fragment implements View.OnClickListener,
        Spinner.OnItemSelectedListener{

    EditText nameInput;
    Spinner classSpinner;
    ImageView avatarView;
    TextView descView;

    private OnCharacterCreateListener mListener;

    public AddCharacterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    public void onResume() {
        super.onResume();

        avatarView = (ImageView) getActivity().findViewById(R.id.create_char_image);
        avatarView.setOnClickListener(this);

        nameInput = (EditText) getActivity().findViewById(R.id.create_char_name);
        classSpinner = (Spinner) getActivity().findViewById(R.id.create_class_spinner);
        classSpinner.setOnItemSelectedListener(this);
        descView = (TextView) getActivity().findViewById(R.id.create_char_desc);

        //TODO: Dynamic init of spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.class_names,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.create_character, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create_character) {
            String nameHolder = nameInput.getText().toString();

            mListener.onCharacterCreated(Character.createNewCharacter(
                    nameHolder.matches("") ? "Player" : nameHolder,
                    classSpinner.getSelectedItem().toString()));


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        mListener.onImageClick();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        Resources res = getActivity().getResources();

        switch (pos){
            case 0: //Warrior
                descView.setText(res.getText(R.string.desc_warrior_class));
                break;
            case 1: //Thief
                descView.setText(res.getText(R.string.desc_thief_class));
                break;
            case 2: //Paladin
                descView.setText(R.string.desc_paladin_class);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        descView.setText("");
    }

    public void updateUI(Bitmap image){
        avatarView.setImageBitmap(image);
    }


    public interface OnCharacterCreateListener {
        public void onCharacterCreated(com.hinodesoftworks.dailyrpg.game.Character character);
        public void onImageClick();
    }
}
