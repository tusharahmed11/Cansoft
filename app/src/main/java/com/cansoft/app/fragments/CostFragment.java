package com.cansoft.app.fragments;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cansoft.app.activity.MainActivity;
import com.cansoft.app.R;
import com.cansoft.app.util.ViewAnimation;
import com.cansoft.app.widget.Fab;
import com.gordonwong.materialsheetfab.MaterialSheetFab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CostFragment extends Fragment {


    private CheckBox checkBoxSeo;
    private CheckBox checkBoxWeb;
    private Button costBtn;
    private int seoPrice;
    private int webPrice;
    private int finalresult = 0;
    private LinearLayout seoLayout;
    private Button costContactBtn;
    int finalcost;
    private static final int MAX_STEP = 20;
    private ProgressBar progressBar;
    private TextView status;

    private List<View> view_list = new ArrayList<>();
    private List<RelativeLayout> step_view_list = new ArrayList<>();
    private int success_step = 0;
    private int current_step =1;


    public CostFragment() {
        // Required empty public constructor
    }


    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cost, container, false);
        /*checkBoxSeo = (CheckBox) view.findViewById(R.id.check_seo);
        checkBoxWeb = (CheckBox) view.findViewById(R.id.check_web);
        costBtn = (Button) view.findViewById(R.id.cost_btn);
        seoLayout = (LinearLayout) view.findViewById(R.id.seo_cost_layout);
        costContactBtn = (Button) view.findViewById(R.id.cost_contact_btn);*/





        makeTransperantStatusBar(false);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.app_bar);
        toolbar.setVisibility(View.VISIBLE);


        /*checkBoxWeb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    webPrice= 500;
                    updateBtn(webPrice);

                    *//*seoPrice = checkBoxSeo.getText().toString();*//*
                }
                else{

                    webPrice = -500;
                    updateBtn(webPrice);
                }
            }
        });
        checkBoxSeo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    seoPrice = 200;
                    updateBtn(seoPrice);
                    *//*seoPrice = checkBoxSeo.getText().toString();*//*
                }
                else{
                    seoPrice = -200;
                    updateBtn(seoPrice);
                }
            }
        });
        costContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactFragment fragment = new ContactFragment();
                Bundle args = new Bundle();
                args.putInt("cost",finalresult);
                fragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
            }
        });*/
        showBackButtonStatus(false);
        initComponent(view);


        return view;
    }

    private void initComponent(View view) {
        status = (TextView) view.findViewById(R.id.status);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        progressBar.setMax(MAX_STEP);
        progressBar.setProgress(current_step);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        ((LinearLayout) view.findViewById(R.id.lyt_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backStep(current_step);
            }
        });

        ((LinearLayout) view.findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep(current_step);
            }
        });

        String str_progress = String.format(getString(R.string.step_of), current_step, MAX_STEP);
        status.setText(str_progress);
    }

    private void backStep(int progress) {
        if (progress > 1) {
            progress--;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        }
        String str_progress = String.format(getString(R.string.step_of), current_step, MAX_STEP);
        status.setText(str_progress);
        progressBar.setProgress(current_step);
    }
    private void nextStep(int progress) {
        if (progress < MAX_STEP) {
            progress++;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        }
        String str_progress = String.format(getString(R.string.step_of), current_step, MAX_STEP);
        status.setText(str_progress);
        progressBar.setProgress(current_step);
    }

    private void updateBtn(int score) {

        finalresult = finalresult + score;
        costBtn.setText(Integer.toString(finalresult) + " $");
        finalcost = finalresult;

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
