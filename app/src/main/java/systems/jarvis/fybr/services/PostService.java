package systems.jarvis.fybr.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import systems.jarvis.fybr.providers.Api;

public class PostService extends IntentService {

    public PostService() {
        super("PostService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String json = intent.getStringExtra("model");
        Log.i("Http", json);
        new Api(this).connect(new Api.ApiCallback() {
            @Override
            public void onConnect(Api api, String token) {
                try {
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost("http://192.168.1.106:8080/collect/" + token);
                    post.setEntity(new StringEntity(json));
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-type", "application/json");
                    client.execute(post, new BasicResponseHandler());
                    Log.i("Http", "Sent");
                }
                catch (Exception ex) {
                    Log.i("Http", ex.getMessage());
                }
            }

            @Override
            public void onDisconnect(Api api) {

            }
        });
    }
}
