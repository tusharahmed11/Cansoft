package com.cansoft.cansoft.cansoft.mail;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import javax.mail.Session;

public class AssessmentMail extends AsyncTask<Void,Void,Void> {
    private Context context;
    private Session session;
    private String name,phone,emailAddress,pages,desgin,interest,websiteUrl,haveDomain,needHosting,domainUrl;
    private ProgressDialog progressDialog;

    public AssessmentMail(Context context, String name, String phone, String emailAddress, String pages, String desgin, String interest, String websiteUrl, String haveDomain, String needHosting,String domainUrl) {
        this.context = context;
        this.name = name;
        this.phone = phone;
        this.emailAddress = emailAddress;
        this.pages = pages;
        this.desgin = desgin;
        this.interest = interest;
        this.websiteUrl = websiteUrl;
        this.haveDomain = haveDomain;
        this.needHosting = needHosting;
        this.domainUrl = domainUrl;

    }

    @Override
    protected Void doInBackground(Void... params) {


        try {

            GMailSender sender = new GMailSender(

                    email_auth.EMAIL,

                    email_auth.PASSWORD);

            sender.sendMail("Email From android app", "Name: "+name  + "\n" + "Phone No:"+ phone+ "\n"+ "Email Address:"+ emailAddress+"\n" +"How many pages: "+pages+"\n"+
                            "Design: "+ desgin+ "\n"+"Interest in rank: "+ interest+ "\n" +"Website url: " +websiteUrl+ "\n"+"Have Domain"+haveDomain+"\n"+"Domain url: "+domainUrl+"\n"+"Need hosting: "+needHosting,

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
