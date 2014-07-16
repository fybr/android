package systems.jarvis.fybr.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import systems.jarvis.fybr.providers.Auth;

public class PushService extends Service {

    private Auth _auth;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String id = _auth.getPush();
        if(id.length() != 0) return 0;
        final Context app = this.getApplicationContext();
        _auth.connect(new Auth.AuthCallback() {
            @Override
            public void onConnect(final Auth auth, String token) {
                new AsyncTask() {
                    @Override
                    protected String doInBackground(Object[] params) {
                        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(app);
                        try {
                            String id = gcm.register("487325715729");
                            auth.setPush(id);
                            Log.i("Push", id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return "";
                    }
                }.execute(null, null, null);
            }

            @Override
            public void onDisconnect(Auth auth) {

            }
        });

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
