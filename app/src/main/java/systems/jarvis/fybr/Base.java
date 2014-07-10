package systems.jarvis.fybr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;

import systems.jarvis.fybr.providers.Api;

public abstract class Base extends Activity implements Api.ApiCallback {

    protected Api _api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _api = new Api(this);
        _api.connect(this);
    }

    @Override
    public abstract void onConnect(Api api, String token);

    @Override
    public void onDisconnect(Api api) {
        Intent i = new Intent(this, Cover.class);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        _api.connect(this);
    }
}
