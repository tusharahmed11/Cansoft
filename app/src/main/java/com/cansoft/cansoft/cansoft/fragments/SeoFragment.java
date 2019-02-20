package com.cansoft.cansoft.cansoft.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.cansoft.cansoft.cansoft.R;
import com.cansoft.cansoft.cansoft.activity.MainActivity;
import com.cansoft.cansoft.cansoft.mail.SeoAuditMail;
import com.gordonwong.materialsheetfab.MaterialSheetFab;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeoFragment extends Fragment {

    private MaterialSheetFab materialSheetFab;
    private EditText name,phone,email,website,city,where,searchTerm;
    private Spinner haveWebsite;
    private LinearLayout websiteLayout,cityLayout,whereLayout,searchLayout;
    private Button submitBtn;

    public SeoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_seo, container, false);
        name = (EditText) view.findViewById(R.id.seo_audit_name);
        phone = (EditText) view.findViewById(R.id.seo_audit_phone);
        email = (EditText) view.findViewById(R.id.seo_audit_email);
        website = (EditText) view.findViewById(R.id.seo_audit_website);
        city = (EditText) view.findViewById(R.id.seo_audit_city);
        where = (EditText) view.findViewById(R.id.seo_audit_current_city);
        searchTerm = (EditText) view.findViewById(R.id.seo_audit_search_term);

        submitBtn = (Button) view.findViewById(R.id.seo_audit_submit_btn);

        haveWebsite = (Spinner) view.findViewById(R.id.planets_spinner);
        websiteLayout = (LinearLayout) view.findViewById(R.id.seo_website_layout);
        cityLayout = (LinearLayout) view.findViewById(R.id.city_layout);
        whereLayout = (LinearLayout) view.findViewById(R.id.rank_layout);
        searchLayout = (LinearLayout) view.findViewById(R.id.term_layout);

        websiteLayout.setVisibility(View.GONE);
        cityLayout.setVisibility(View.GONE);
        whereLayout.setVisibility(View.GONE);
        searchLayout.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.planets_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        haveWebsite.setAdapter(adapter);
        haveWebsite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String p =  parent.getItemAtPosition(position).toString();
                if (p.equals("Yes")){
                    websiteLayout.setVisibility(View.VISIBLE);
                    cityLayout.setVisibility(View.VISIBLE);
                    whereLayout.setVisibility(View.VISIBLE);
                    searchLayout.setVisibility(View.VISIBLE);
                }
                if (p.equals("No")){
                    websiteLayout.setVisibility(View.GONE);
                    cityLayout.setVisibility(View.GONE);
                    whereLayout.setVisibility(View.GONE);
                    searchLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString();
                String phone1 = phone.getText().toString();
                String email1 = email.getText().toString();
                String website1 = website.getText().toString();
                String rankcity = city.getText().toString();
                String currentcity = where.getText().toString();
                String searchterm = searchTerm.getText().toString();

                SeoAuditMail seoAuditMail = new SeoAuditMail(view.getContext(),name1,phone1,email1,website1,rankcity,currentcity,searchterm);
                seoAuditMail.execute();
            }
        });
        showBackButtonStatus(false);

        return view;
    }
    private void showBackButtonStatus(Boolean status){
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(status);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(status);
    }
}
