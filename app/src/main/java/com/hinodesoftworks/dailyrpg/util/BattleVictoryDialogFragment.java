package com.hinodesoftworks.dailyrpg.util;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BattleVictoryDialogFragment extends DialogFragment {

    private OnInteractionListener mListener;

    public static BattleVictoryDialogFragment newInstance(){
return null;
        

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setListener(OnInteractionListener listener){
        mListener = listener;
    }

    public interface OnInteractionListener{
        public void onAccept();
    }
}
