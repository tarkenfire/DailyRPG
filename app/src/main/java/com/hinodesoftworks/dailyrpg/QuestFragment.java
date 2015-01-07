package com.hinodesoftworks.dailyrpg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.hinodesoftworks.dailyrpg.dummy.DummyContent;
import com.hinodesoftworks.dailyrpg.todo.Quest;
import com.hinodesoftworks.dailyrpg.todo.QuestListAdapter;
import com.hinodesoftworks.dailyrpg.todo.QuestStatusDialogFragment;

import java.util.ArrayList;
import java.util.List;


public class QuestFragment extends Fragment implements AbsListView.OnItemClickListener,
        QuestStatusDialogFragment.OnStatusInteractionListener{

    private OnQuestFragmentInteractionListener mListener;
    private AbsListView mListView;
    private QuestListAdapter mAdapter = null;

    private int selected;

    public QuestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // TODO: Change Adapter to display your content

        if (mAdapter == null) {
            mAdapter = new QuestListAdapter(getActivity(), R.layout.quest_row, new ArrayList<Quest>());
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quest, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnQuestFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onQuestFragmentResumed();

        if (mAdapter.getCount() > 0){
            TextView emptyView = (TextView) getActivity().findViewById(android.R.id.empty);
            emptyView.setText("");
        }
        else{
            TextView emptyView = (TextView) getActivity().findViewById(android.R.id.empty);
            emptyView.setText(getResources().getText(R.string.warning_no_quests));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.selected = position;

        if (null != mListener) {
            mListener.onQuestSelected(position);
        }
    }

    public void updateList(ArrayList<Quest> updateList){
        mAdapter.clear();

        for (Quest q : updateList){
            mAdapter.add(q);
        }

        mAdapter.notifyDataSetChanged();




    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public interface OnQuestFragmentInteractionListener {
        public void onQuestSelected(int position);
        public void onAddQuestPressed();
        public void onQuestCompleted(int position);
        public void onQuestFragmentResumed();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.quest, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_quest) {
            mListener.onAddQuestPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    //methods called from parent activity
    public void showQuestDetails(String name, String detail, String time, String type, boolean isOverdue){
        QuestStatusDialogFragment dialog = QuestStatusDialogFragment.newInstance(name, detail, time
                , type, isOverdue);
        dialog.setListener(this);
        dialog.show(getActivity().getFragmentManager(), "detailDialog");
    }

    //Implemented methods
    @Override
    public void onCompleteClicked() {
        mListener.onQuestCompleted(selected);
    }

    @Override
    public void onOkClicked() {
        //nothing needs to be done, in this version.
    }
}
