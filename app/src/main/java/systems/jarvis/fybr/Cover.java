package systems.jarvis.fybr;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.providers.Auth;


public class Cover extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);

        final Activity dis = this;
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Api api = new Auth(dis).login("dharun@ly.ht", "lol").connect();
                if(api != null)
                    dis.finish();
            }
        });
    }
}
