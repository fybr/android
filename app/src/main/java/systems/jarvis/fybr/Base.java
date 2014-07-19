package systems.jarvis.fybr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.providers.Auth;

public abstract class Base extends Activity {

    protected Auth _auth;
    protected Api _api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connect();
    }

    private void connect() {
        _auth = new Auth(this);
        _api = _auth.connect();
        if(_api == null) {
            Intent i = new Intent(this, Cover.class);
            startActivityForResult(i, 1);
            return;
        }
        ready();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        connect();
    }

    public abstract void ready();
}
