package com.cansoft.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cansoft.app.activity.MainActivity;
import com.cansoft.app.R;
import com.cansoft.app.model.About;
import com.cansoft.app.model.AboutD;
import com.cansoft.app.network.RestClient2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {
    private TextView aboutDetails;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.app_bar);
        toolbar.setTitle("");
        aboutDetails = (TextView) view.findViewById(R.id.about_details);
        RestClient2.getInstance().callRetrofit(view.getContext()).getAbout().enqueue(new Callback<AboutD>() {
            @Override
            public void onResponse(Call<AboutD> call, Response<AboutD> response) {
                List<About> about = response.body().getData();
                aboutDetails.setText(about.get(0).getDetails());
            }

            @Override
            public void onFailure(Call<AboutD> call, Throwable t) {

            }
        });
        showDrawerButton();
        return view;
    }
    public void showDrawerButton() {

            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

}
