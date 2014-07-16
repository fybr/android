package systems.jarvis.fybr.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.providers.Auth;

public class PostService extends IntentService {

    public PostService() {
        super("PostService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String json = intent.getStringExtra("model");
        final String type = intent.getStringExtra("type");
        new Auth(this).connect(new Auth.AuthCallback() {

            @Override
            public void onConnect(Auth auth, Api api) {
                api.post("users/events/" + type, json);
            }

            @Override
            public void onFail(Auth auth) {

            }
        });
    }
}
