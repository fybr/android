package systems.jarvis.fybr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import systems.jarvis.fybr.providers.Auth;

public abstract class Base extends Activity implements Auth.AuthCallback {

    protected Auth _auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _auth = new Auth(this);
        _auth.connect(this);
    }

    @Override
    public abstract void onConnect(Auth auth, String token);

    @Override
    public void onDisconnect(Auth auth) {
        Intent i = new Intent(this, Cover.class);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        _auth.connect(this);
    }
}
