package systems.jarvis.fybr.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import systems.jarvis.fybr.providers.Auth;
import systems.jarvis.fybr.providers.Model;
import systems.jarvis.fybr.services.PostService;

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
