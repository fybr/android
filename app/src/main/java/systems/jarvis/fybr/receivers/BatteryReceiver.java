package systems.jarvis.fybr.receivers;

import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.providers.Auth;
import systems.jarvis.fybr.providers.Battery;
import systems.jarvis.fybr.providers.Sms;

public class BatteryReceiver extends Receiver {

    @Override
    public void execute(Context context, Intent intent) {
        int level = intent.getIntExtra("level", 0);
        Api api = new Auth(context).connect();
        if(api == null) return;
        Battery model = new Battery();
        model.level = level;
        api.event(model);
    }

}
