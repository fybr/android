package systems.jarvis.fybr.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import systems.jarvis.fybr.providers.Api;

public class PushService extends Service {

    @Override
    public void onCreate() {
        final Context app = this.getApplicationContext();
        new Api(this).connect(new Api.ApiCallback() {
            @Override
            public void onConnect(final Api api, String token) {
                if(api.getPush().length() == 0) {
                    new AsyncTask() {
                        @Override
                        protected String doInBackground(Object[] params) {
                            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(app);
                            try {
                                String id = gcm.register("487325715123123729");
                                api.setPush(id);
                                Log.i("Push", id);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return "";
                        }
                    }.execute(null, null, null);
                }

            }

            @Override
            public void onDisconnect(Api api) {

            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
