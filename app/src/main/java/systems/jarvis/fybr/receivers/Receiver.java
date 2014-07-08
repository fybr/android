package systems.jarvis.fybr.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public abstract class Receiver extends BroadcastReceiver {

    public Receiver() {
        Log.i("Receiver", this.getClass().getSimpleName() + " started");
    }

    @Override
    public final void onReceive(Context context, Intent intent) {
        execute(context, intent);
    }

    protected abstract void execute(Context context, Intent intent);

}
