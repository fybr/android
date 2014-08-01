package systems.jarvis.fybr.services.sync;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import systems.jarvis.fybr.receivers.BatteryReceiver;

public class BatterySync implements ISync {
    @Override
    public void register(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        BatteryReceiver receiver = new BatteryReceiver();
        context.registerReceiver(receiver, filter);
    }
}
