package com.cansoft.app.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cansoft.app.R;
import com.cansoft.app.activity.MainActivity;
import com.cansoft.app.activity.NotificationNewsActivity;
import com.cansoft.app.activity.OffersActivity;
import com.cansoft.app.activity.SplashActivity;
import com.cansoft.app.fragments.NewsDetailsFragment;
import com.cansoft.app.model.FcmNotif;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class NotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String id = remoteMessage.getData().get("post_id");
        Log.d(TAG, "onMessageReceived: " +id);
        if (id!=null){
                Map<String, String> data = remoteMessage.getData();
                FcmNotif fcmNotif = new FcmNotif();
                fcmNotif.setTitle(data.get("title"));
                fcmNotif.setContent(data.get("content"));
                fcmNotif.setPost_id(Integer.parseInt(data.get("post_id")));
                displayNotificationIntent(fcmNotif);

        }else {
            if (!remoteMessage.getNotification().getChannelId().isEmpty()){
                String chanelid = remoteMessage.getNotification().getChannelId();
                Log.d(TAG, "onMessageReceived: " +chanelid);
                if (chanelid.equals("111")){
                    showNotification2(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
                }else {
                    showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
                }
            }

            showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

        }

        Log.d(TAG, "onMessageReceived: " +remoteMessage);
    }

    public void showNotification(String title, String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"999")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_general)
                .setAutoCancel(true)
                .setContentText(message);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        manager.notify(999,builder.build());
    }
    public void showNotification2(String title, String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"111")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_general)
                .setAutoCancel(true)
                .setContentText(message);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        manager.notify(111,builder.build());
    }
    private void displayNotificationIntent(FcmNotif fcmNotif) {
        Intent intent = new Intent(getApplicationContext(),  MainActivity.class);
        if (fcmNotif.getPost_id() != -1) {
            intent = new Intent(getApplicationContext(), NotificationNewsActivity.class);

            String title = fcmNotif.getTitle();
            int id = fcmNotif.getPost_id();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            boolean from_notif = !MainActivity.active;
            intent.putExtra("title",title);
            intent.putExtra("id",id);
            intent.putExtra(NotificationNewsActivity.EXTRA_NOTIF, from_notif);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"111")
                    .setContentTitle(fcmNotif.getTitle())
                    .setSmallIcon(R.drawable.ic_news)
                    .setAutoCancel(true)
                    .setContentText(fcmNotif.getContent());
            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            builder.setContentIntent(pendingIntent);
            int unique_id = (int) System.currentTimeMillis();
            manager.notify(unique_id, builder.build());
        }else {
            intent = new Intent(getApplicationContext(), OffersActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            boolean from_notif1 = !MainActivity.active;
            intent.putExtra(OffersActivity.EXTRA_NOTIF1, from_notif1);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"222")
                    .setContentTitle(fcmNotif.getTitle())
                    .setSmallIcon(R.drawable.ic_offer)
                    .setAutoCancel(true)
                    .setContentText(fcmNotif.getContent());
            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            builder.setContentIntent(pendingIntent);
            int unique_id = (int) System.currentTimeMillis();
            manager.notify(unique_id, builder.build());
        }





        /*PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"News");
        builder.setContentTitle(fcmNotif.getTitle());
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(fcmNotif.getContent()));
        builder.setContentText(fcmNotif.getContent());
        builder.setSmallIcon(R.drawable.ic_alarm_black);
        builder.setDefaults(Notification.DEFAULT_LIGHTS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int unique_id = (int) System.currentTimeMillis();
        notificationManager.notify(unique_id, builder.build());*/
    }
}
