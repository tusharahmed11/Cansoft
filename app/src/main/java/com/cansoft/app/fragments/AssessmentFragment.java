package com.cansoft.app.fragments;


import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cansoft.app.activity.MainActivity;
import com.cansoft.app.R;
import com.cansoft.app.mail.AssessmentMail;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssessmentFragment extends Fragment {

    EditText name,email,phone,pageCount,existingWebsite,domainUrl;
    Spinner designMind,haveDomain,googleSearch,needHosting;
    LinearLayout needDomainLayout;
    AppCompatButton submitBtn;


    public AssessmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_assessment, container, false);
        name = (EditText) view.findViewById(R.id.seo_assessment_name);
        email = (EditText) view.findViewById(R.id.seo_assessment_email);
        phone = (EditText) view.findViewById(R.id.seo_assessment_phone);
        pageCount = (EditText) view.findViewById(R.id.seo_assessment_pages);
        existingWebsite = (EditText) view.findViewById(R.id.seo_assessment_existing_url);
        domainUrl = (EditText) view.findViewById(R.id.seo_assessment_domain_url);

        designMind = (Spinner) view.findViewById(R.id.design_mind_spinner);
        haveDomain = (Spinner) view.findViewById(R.id.hav_domain_spinner);
        googleSearch = (Spinner) view.findViewById(R.id.google_search_spinner);
        needHosting = (Spinner) view.findViewById(R.id.hosting_spinner);

        needDomainLayout = (LinearLayout) view.findViewById(R.id.needDomainLayout);
        submitBtn = (AppCompatButton) view.findViewById(R.id.seo_assessment_btn);

        final String[] design = new String[1];
        final String[] interestRank = new String[1];
        final String[] domain = new String[1];
        final String[] hosting = new String[1];

        needDomainLayout.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> designAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.design_array, R.layout.spinner_item);
        designAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        designMind.setAdapter(designAdapter);
        designMind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                design[0] =  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> googleSearchAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.rank_array, R.layout.spinner_item);
        googleSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        googleSearch.setAdapter(googleSearchAdapter);
        googleSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                interestRank[0] =  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> needHostingAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.hosting_array, R.layout.spinner_item);
        needHostingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        needHosting.setAdapter(needHostingAdapter);
        needHosting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hosting[0] =  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.domain_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        haveDomain.setAdapter(adapter);
        haveDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String p =  parent.getItemAtPosition(position).toString();
                domain[0] = parent.getItemAtPosition(position).toString();
                if (p.equals("Yes")){

                    needDomainLayout.setVisibility(View.VISIBLE);
                }
                if (p.equals("No")){
                    needDomainLayout.setVisibility(View.GONE);
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
                String pages = pageCount.getText().toString();
                String url = existingWebsite.getText().toString();
                String domainUrl1 = domainUrl.getText().toString();
                if (name1.isEmpty() || phone1.isEmpty() || email1.isEmpty()|| pages.isEmpty() || url.isEmpty()){
                    if (name1.isEmpty()){
                        open(view, "Name");
                    }else if (phone1.isEmpty()){
                        open(view,"Phone");
                    }else if (email1.isEmpty()){
                        open(view,"Email");
                    }else if (pages.isEmpty()){
                        open(view,"Page Count");
                    }else if (url.isEmpty()){
                        open(view,"Domain Url");
                    }

                }else {
                    AssessmentMail assessmentMail = new AssessmentMail(view.getContext(),name1,phone1,email1,pages,design[0],interestRank[0],url,domain[0],hosting[0],domainUrl1);
                    assessmentMail.execute();
                }


            }
        });
        showBackButtonStatus(false);

        return view;
    }

    public void open(final View view, String field){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        View titleView = getLayoutInflater().inflate(R.layout.contact_alert_title,null);
        TextView textView = titleView.findViewById(R.id.popTitle);
        String titletext = field+ " is empty!";
        textView.setText(titletext);

        View messageView = getLayoutInflater().inflate(R.layout.contact_alert_message,null);

        alertDialogBuilder.setCustomTitle(titleView);
        alertDialogBuilder.setView(messageView);
        /* alertDialogBuilder.setMessage("\nPlease fill all the field!");*/
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void showBackButtonStatus(Boolean status){
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(status);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(status);
    }

}
