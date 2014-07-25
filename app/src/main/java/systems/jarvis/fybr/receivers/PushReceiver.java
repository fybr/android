package systems.jarvis.fybr.receivers;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import systems.jarvis.fybr.services.NotifyService;

public class PushReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Push", "Push received");
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        String type = intent.getStringExtra("type");
        try {
            if (type.equals("sms")) {
                String number = intent.getStringExtra("number");
                String message = intent.getStringExtra("message");
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendMultipartTextMessage(number, null,smsManager.divideMessage(message), null, null);
            }

            if(type.equals("dismiss")) {
                Intent i = new Intent(context, NotifyService.class);
                i.putExtra("command", "dismiss");
                i.putExtras(intent);
                context.startService(i);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
