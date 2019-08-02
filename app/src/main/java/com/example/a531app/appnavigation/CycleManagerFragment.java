package com.example.a531app.appnavigation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a531app.R;
import com.example.a531app.cyclenavigation.WeeksDisplayFragment;
import com.example.a531app.cycles.CycleManager;
import com.example.a531app.cycles.ManagerAdapter;
import com.example.a531app.cycles.Weeks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CycleManagerFragment extends BaseFragment implements ManagerAdapter.ManagerAdapterClickListener {

    private RecyclerView recyclerView;
    private ManagerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    public CycleManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.managermenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
//            case R.id.action_edit:
//                editCycles();
//                return true;
//            case R.id.action_add:
//                addCycle();
//                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View providedView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cycle_manager, container, false);

        recyclerView = view.findViewById(R.id.rv_cycles);
        recyclerView.setHasFixedSize(true);

        adapter = new ManagerAdapter(new ArrayList<String>(), this, getActivity());
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    @Override
    public String actionTitle() {
        return "Manage Cycles";
    }

    @Override
    public boolean setBackButton() {
        return false;
    }

    public void addCycle(){
        Calendar rightNow = Calendar.getInstance();
        String month = rightNow.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day = rightNow.get(rightNow.DAY_OF_MONTH);
        adapter.addDate(month+" " + day);
    }

    private void editCycles(){

    }

    @Override
    public void onClick() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, new WeeksDisplayFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
