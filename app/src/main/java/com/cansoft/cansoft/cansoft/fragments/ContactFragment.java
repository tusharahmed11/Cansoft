package com.cansoft.cansoft.cansoft.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cansoft.cansoft.cansoft.R;
import com.cansoft.cansoft.cansoft.activity.MainActivity;
import com.cansoft.cansoft.cansoft.mail.AssessmentMail;
import com.cansoft.cansoft.cansoft.mail.ContactMail;
import com.cansoft.cansoft.cansoft.mail.GMailSender;
import com.cansoft.cansoft.cansoft.mail.SeoAuditMail;
import com.cansoft.cansoft.cansoft.mail.mail;
import com.cansoft.cansoft.cansoft.widget.Fab;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    Dialog seoDialog;
    Dialog assessmentDialog;
    Button seoDialogeBtn;
    Button assessmentBtn;
    AppCompatButton contactBtn;
    EditText contactName;
    EditText contactPhoneNumber;
    EditText contactEmailAddress;
    EditText contactMessage;

    TextInputLayout firstNameLy;
    private String TAG;
    int cost = 0;

    public ContactFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_contact, container, false);
        seoDialog = new Dialog(view.getContext());
        assessmentDialog = new Dialog(view.getContext());
        makeTransperantStatusBar(false);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.app_bar);
        toolbar.setVisibility(View.VISIBLE);

        showBackButtonStatus(false);

        /*@SuppressLint("InflateParams") View mainView = getLayoutInflater().inflate(R.layout.activity_main, null);
        LinearLayout contactfab = mainView.findViewById(R.id.fab_sheet_item_note);
        contactfab.setVisibility(View.GONE);*/

        /*seoDialogeBtn = (Button) view.findViewById(R.id.seo_audit_btn);
        assessmentBtn = (Button) view.findViewById(R.id.seo_assessment_btn);*/
        contactBtn = (AppCompatButton) view.findViewById(R.id.contact_btn);

      //  firstNameLy = (TextInputLayout) view.findViewById(R.id.contact_first_name_ly);


        contactName = (EditText) view.findViewById(R.id.contact_name);
        contactPhoneNumber = (EditText) view.findViewById(R.id.contact_phone_number);
        contactEmailAddress = (EditText) view.findViewById(R.id.contact_email_address);
        contactMessage = (EditText) view.findViewById(R.id.contact_message);


        Bundle bundle = getArguments();
        if (bundle != null){
            cost = bundle.getInt("cost");
        }

       /* seoDialogeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSeoDialog(view);
            }
        });

        assessmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAssessmentDialog(view);
            }
        });*/

        final int finalCost = cost;
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String firstName = contactName.getText().toString();
                 String phoneNumber = contactPhoneNumber.getText().toString();
                 String emailAddress = contactEmailAddress.getText().toString();
                 String message = contactMessage.getText().toString();

                if (firstName.isEmpty() ||  phoneNumber.isEmpty() || emailAddress.isEmpty() || message.isEmpty()){
                    Log.d(TAG, "onClick: " + firstName + phoneNumber +emailAddress+ message);
                    open(view);
                }else{
                    if (finalCost > 0) {
                        String message1 = "Cost: "+finalCost+"\n" +contactMessage.getText().toString();
                        ContactMail contactMail = new ContactMail(view.getContext(),firstName,phoneNumber,emailAddress,message1);
                        contactMail.execute();
                    }else {
                        ContactMail contactMail1 = new ContactMail(view.getContext(),firstName,phoneNumber,emailAddress,message);
                        contactMail1.execute();
                    }
                }

            }

        });

        return view;
    }


    public void open(final View view){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        View titleView = getLayoutInflater().inflate(R.layout.contact_alert_title,null);
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
/*    private void mailSend(final View view, final String firstName, final String phoneNumber, final String emailAddress, final String message){

        mail mail = new mail(view.getContext(),firstName,phoneNumber,message,emailAddress);
        mail.execute();
       *//* new Thread(new Runnable() {

            public void run() {

                try {

                    GMailSender sender = new GMailSender(

                            "ahmedorno11@gmail.com",

                            "");

                    sender.sendMail("Test mail", firstName+ "\n" + lastName + "\n" + phoneNumber +  "\n" +message,

                            emailAddress,

                            "ahmedorno11@gmail.com");


                } catch (Exception e) {

                    Toast.makeText(view.getContext(),"Error",Toast.LENGTH_LONG).show();

                }

            }

        }).start();

        Toast.makeText(view.getContext(),"Mail send successfully",Toast.LENGTH_LONG).show();
        contactFirstName.setText("");
        contactLastName .setText("");
        contactPhoneNumber.setText("");
        contactEmailAddress.setText("");
        contactMessage.setText("");
*//*

    }*/

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
