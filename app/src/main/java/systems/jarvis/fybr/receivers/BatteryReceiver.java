package systems.jarvis.fybr.receivers;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.provider.Telephony;

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.providers.Auth;
import systems.jarvis.fybr.providers.Battery;
import systems.jarvis.fybr.providers.Sms;
import systems.jarvis.fybr.receivers.Receiver;


public class BatteryReceiver extends Receiver {
    private int _last = 0;

    @Override
    protected void execute(Context context, Intent intent) {
        int level = intent.getIntExtra("level", 0);
        if(level == _last) return;
        _last = level;
        Api api = new Auth(context).connect();
        if(api == null) return;
        Battery model = new Battery();
        model.level = level;
        api.event(model);

    }
}
