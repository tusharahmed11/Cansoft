package com.cansoft.cansoft.cansoft.mail;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import android.widget.Toast;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

public class mail extends AsyncTask<Void,Void,Void> {
    private Context context;
    private Session session;
    private String firstName,lastName,phoneNumber,message,emailAddress;
    private ProgressDialog progressDialog;

    public mail(Context context, String firstName, String phoneNumber, String message, String emailAddress) {
        this.context = context;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.emailAddress = emailAddress;

    }

    @Override
    protected Void doInBackground(Void... params) {


                try {

                    GMailSender sender = new GMailSender(

                            email_auth.EMAIL,

                            email_auth.PASSWORD);

                    sender.sendMail("Email From android app", "Name: "+firstName + lastName + "\n" + "Phone No:"+ phoneNumber+ "\n"+ "Email Address:"+ emailAddress
                                    +  "\n" +"Message: " +message,

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
