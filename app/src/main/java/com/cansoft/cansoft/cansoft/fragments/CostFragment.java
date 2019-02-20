package com.cansoft.cansoft.cansoft.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.cansoft.cansoft.cansoft.R;
import com.cansoft.cansoft.cansoft.activity.MainActivity;

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


    public CostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cost, container, false);
        checkBoxSeo = (CheckBox) view.findViewById(R.id.check_seo);
        checkBoxWeb = (CheckBox) view.findViewById(R.id.check_web);
        costBtn = (Button) view.findViewById(R.id.cost_btn);
        seoLayout = (LinearLayout) view.findViewById(R.id.seo_cost_layout);
        costContactBtn = (Button) view.findViewById(R.id.cost_contact_btn);


        makeTransperantStatusBar(false);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.app_bar);
        toolbar.setVisibility(View.VISIBLE);


        checkBoxWeb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    webPrice= 500;
                    updateBtn(webPrice);

                    /*seoPrice = checkBoxSeo.getText().toString();*/
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
                    /*seoPrice = checkBoxSeo.getText().toString();*/
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
        });
        showBackButtonStatus(false);

        return view;
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
