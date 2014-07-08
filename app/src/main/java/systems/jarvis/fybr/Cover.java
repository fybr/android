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

import systems.jarvis.fybr.providers.Api;
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
        _client.disconnect();
        final Activity dis = this;
        _client.connect();

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(_resolve != null) {
                    try {
                        _resolve.startResolutionForResult(dis, 1);
                    } catch (IntentSender.SendIntentException e) {

                    }
                }
            }
        });

    }


    private GoogleApiClient _client;

    @Override
    public void onConnected(Bundle bundle) {
        if(_resolve == null) {
            Plus.AccountApi.clearDefaultAccount(_client);
            _client.disconnect();
            _client.connect();
            return;
        }
        String token =Plus.AccountApi.getAccountName(_client);
        new Api(this).setToken(token);
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
    }
}
