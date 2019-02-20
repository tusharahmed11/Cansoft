package com.cansoft.cansoft.cansoft.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.cansoft.cansoft.cansoft.R;
import com.cansoft.cansoft.cansoft.activity.MainActivity;
import com.cansoft.cansoft.cansoft.adapter.AdapterListExpand;
import com.cansoft.cansoft.cansoft.model.Social;
import com.cansoft.cansoft.cansoft.util.DataGenerator;
import com.cansoft.cansoft.cansoft.widget.LineItemDecoration;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {
    private View parent_view;

    private RecyclerView recyclerView;
    private AdapterListExpand mAdapter;

    public TeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team, container, false);
        parent_view = view.findViewById(android.R.id.content);

        makeTransperantStatusBar(false);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.app_bar);
        toolbar.setVisibility(View.VISIBLE);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerView.setHasFixedSize(true);

        List<Social> items = DataGenerator.getSocialData(view.getContext());

        //set data and list adapter
        mAdapter = new AdapterListExpand(view.getContext(), items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
    /*    mAdapter.setOnItemClickListener(new AdapterListExpand.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Social obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
*/
        showBackButtonStatus(false);

        return view;
    }

    protected void makeTransperantStatusBar(boolean isTransperant) {
        if (isTransperant) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void showBackButtonStatus(Boolean status){
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(status);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(status);
    }



}