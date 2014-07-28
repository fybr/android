package systems.jarvis.fybr.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.providers.Auth;
import systems.jarvis.fybr.services.ContactService;
import systems.jarvis.fybr.services.SmsService;

public class Boot extends Receiver {

    @Override
    protected void execute(Context context, Intent intent) {
        StartServices(context);
    }

    public static void StartServices(final Context context) {

        {
            Intent i = new Intent(context, ContactService.class);
            context.startService(i);
        }

        {
            Intent i = new Intent(context, SmsService.class);
            context.startService(i);
        }

        {
            final Api api = new Auth(context).connect();
            if(api == null) return;
            new AsyncTask() {
                @Override
                protected String doInBackground(Object[] params) {
                    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
                    try {
                        String id = gcm.register("487325715729");
                        Log.i("Push", id);
                        api.post("users/devices", id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            }.execute(null, null, null);
        }

        {
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            long frequency= 24*60*60*1000;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), frequency, pending);
        }

    }
}