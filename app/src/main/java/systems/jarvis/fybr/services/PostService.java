package systems.jarvis.fybr.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

public class PostService extends IntentService {

    public PostService() {
        super("PostService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String json = intent.getStringExtra("model");
        Log.i("Http", json);
    }
}
