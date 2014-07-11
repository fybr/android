package systems.jarvis.fybr.receivers;

import android.content.Context;
import android.content.Intent;

import systems.jarvis.fybr.services.PushService;
import systems.jarvis.fybr.services.SmsService;

public class Boot extends Receiver {

    @Override
    protected void execute(Context context, Intent intent) {
        StartServices(context);
    }

    public static void StartServices(Context context) {
        {
            Intent i = new Intent(context, SmsService.class);
            context.startService(i);
        }

        {
            Intent i = new Intent(context, PushService.class);
            context.startService(i);
        }
    }
}