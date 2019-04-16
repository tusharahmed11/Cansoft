package com.cansoft.app.util;

import android.app.Application;

import com.onesignal.OneSignal;

public class OneSignalPush extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // OneSignalPush Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
