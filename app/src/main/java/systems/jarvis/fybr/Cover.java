package systems.jarvis.fybr;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import systems.jarvis.fybr.receivers.Boot;


public class Cover extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private ConnectionResult _resolve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        _client = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        final Activity dis = this;
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(_resolve != null) {
                    try {
                        _resolve.startResolutionForResult(dis, 1);
                        _resolve = null;
                    } catch (IntentSender.SendIntentException e) {

                    }
                }
                else {
                    _client.connect();
                }
            }
        });

        _client.connect();

    }


    private GoogleApiClient _client;

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("", "Connected " + Plus.AccountApi.getAccountName(_client));
        Boot.Start(this);
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                _client.connect();
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        _resolve = result;
        Log.i("", "Failed");

    }
}
