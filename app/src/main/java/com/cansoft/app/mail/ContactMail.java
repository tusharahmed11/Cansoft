package com.cansoft.app.mail;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cansoft.app.R;

import javax.mail.Session;

public class ContactMail  extends AsyncTask<Void,Void,Void> {
    private Context context;
    private Session session;
    private String name, phone, emailAddress, message;
    private ProgressDialog progressDialog;

    public ContactMail(Context context, String name, String phone, String emailAddress, String message ) {
        this.context = context;
        this.name = name;
        this.phone = phone;
        this.emailAddress = emailAddress;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... params) {


        try {

            GMailSender sender = new GMailSender(

                    email_auth.EMAIL,

                    email_auth.PASSWORD);

            sender.sendMail("Email From android app", "Name: " + name + "\n" + "Phone No:" + phone + "\n" + "Email Address:" + emailAddress + "\n" + "Message: " + message,

                    emailAddress,

                    email_auth.EMAIL);


        } catch (Exception e) {
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();

            /*LayoutInflater inflater = LayoutInflater.from(context);
            View toastView = inflater.inflate(R.layout.toast_custom_error_view, null);
            TextView toastTitle = toastView.findViewById(R.id.customToastText);
            toastTitle.setText("Something wrong! please try again");
            // Initiate the Toast instance.
            Toast toast = new Toast(context);
            // Set custom view in toast.
            toast.setView(toastView);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0,0);
            toast.show();
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();*/

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        LayoutInflater inflater = LayoutInflater.from(context);
        View toastView = inflater.inflate(R.layout.toast_custom_view, null);
        TextView toastTitle = toastView.findViewById(R.id.customToastText);
        toastTitle.setText("Email Sent Successfully");
        // Initiate the Toast instance.
        Toast toast = new Toast(context);
        // Set custom view in toast.
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.show();
       /* Toast.makeText(context, "Email Sent", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = progressDialog.show(context, "Sending Email", "Please wait.....", false, false);
    }
}