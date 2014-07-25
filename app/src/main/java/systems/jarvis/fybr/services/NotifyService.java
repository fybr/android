package systems.jarvis.fybr.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Base64;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import systems.jarvis.fybr.providers.Auth;
import systems.jarvis.fybr.providers.Dismiss;
import systems.jarvis.fybr.providers.Notification;

public class NotifyService extends NotificationListenerService {

    @Override
    public void onCreate() {
        Log.i("Service", "Notification Service started");
    }



    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Notification model = new Notification();
        model.id = sbn.getId() + "";
        Bundle extras = sbn.getNotification().extras;
        model.extras = new HashMap<String, Object>();
        model.tag = sbn.getTag();
        model.name = sbn.getPackageName();
        for(String key : extras.keySet()) {
            model.extras.put(key.replaceAll("android.", ""), extras.get(key));
        }

        try {
            PackageManager pm = this.getPackageManager();
            model.app = pm.getApplicationLabel(pm.getApplicationInfo(sbn.getPackageName(), 0)) + "";

            Context packageContext = this.createPackageContext(sbn.getPackageName(), 0);
            Bitmap bmp = BitmapFactory.decodeResource(packageContext.getResources(), extras.getInt(android.app.Notification.EXTRA_SMALL_ICON));
            if(bmp != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                model.icon = "data:image/jpeg;base64," + Base64.encodeToString(b, Base64.DEFAULT);
            }



        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new Auth(this).connect().event(model);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Dismiss model = new Dismiss();
        model.id = sbn.getId() + "";
        new Auth(this).connect().event(model);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String command = intent.getStringExtra("command");
        if(command.equals("dismiss")) {
            String tag = intent.getStringExtra("tag");
            String name = intent.getStringExtra("name");
            int id = Integer.parseInt(intent.getStringExtra("id"));
            this.cancelNotification(name, tag, id);
        }

        return 0;
    }
}
