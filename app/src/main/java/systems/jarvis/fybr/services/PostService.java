package systems.jarvis.fybr.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.providers.Auth;

public class PostService extends IntentService {

    public PostService() {
        super("PostService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final String body = intent.getStringExtra("body");
        final String path = intent.getStringExtra("path");
        final String session = intent.getStringExtra("session");
        try {
            BasicResponseHandler response = new BasicResponseHandler();
            DefaultHttpClient client = new DefaultHttpClient();
            String url = "http://api.fybr.ws/" + path + "?session=" + session;
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(body));
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            Log.i("Http", url + " - " + body);
            response.handleResponse(client.execute(post));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
