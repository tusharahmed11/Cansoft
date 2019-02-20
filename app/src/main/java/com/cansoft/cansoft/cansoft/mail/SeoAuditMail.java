package com.cansoft.cansoft.cansoft.mail;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import javax.mail.Session;

public class SeoAuditMail extends AsyncTask<Void,Void,Void> {
    private Context context;
    private Session session;
    private String name,phone,emailAddress,website,rankCity,currentCity,searchTerm;
    private ProgressDialog progressDialog;

    public SeoAuditMail(Context context, String name, String phone, String emailAddress, String website, String rankCity, String currentCity, String searchTerm) {
        this.context = context;
        this.name = name;
        this.phone = phone;

        this.emailAddress = emailAddress;
        this.website = website;
        this.rankCity = rankCity;
        this.currentCity = currentCity;
        this.searchTerm = searchTerm;
    }


    @Override
    protected Void doInBackground(Void... params) {


        try {

            GMailSender sender = new GMailSender(

                    email_auth.EMAIL,

                    email_auth.PASSWORD);

            sender.sendMail("Email From android app", "Name: "+name  + "\n" + "Phone No:"+ phone+ "\n"+ "Email Address:"+ emailAddress+"\n" +"Website Address: "+website+"\n"+
                            "City: "+ rankCity+ "\n"+"Current City: "+ currentCity+ "\n" +"Search terms: " +searchTerm,

                    emailAddress,

                    email_auth.EMAIL);


        } catch (Exception e) {

            Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        Toast.makeText(context, "Email Sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = progressDialog.show(context,"Sending Email","Please wait.....",false,false);
    }
}
