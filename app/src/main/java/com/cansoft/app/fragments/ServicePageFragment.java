package com.cansoft.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cansoft.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicePageFragment extends Fragment {

    private TextView header;


    public ServicePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_page, container, false);
        header = (TextView) view.findViewById(R.id.service_page_header);
        Bundle bundle = getArguments();
        String headerText = bundle.getString("header");
        header.setText(headerText);

        return view;

    }

}
