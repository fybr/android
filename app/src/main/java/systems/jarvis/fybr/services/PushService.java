package systems.jarvis.fybr.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.providers.Auth;

public class PushService extends Service {

    private Auth _auth;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Context app = this.getApplicationContext();
        final Api api = _auth.connect();
        if(api == null) return 0;
        new AsyncTask() {
            @Override
            protected String doInBackground(Object[] params) {
                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(app);
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

        return 0;
    }

    @Override
    public void onCreate() {
        _auth = new Auth(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
