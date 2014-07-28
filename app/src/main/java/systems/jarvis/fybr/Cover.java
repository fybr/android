package systems.jarvis.fybr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        final EditText email = (EditText)findViewById(R.id.email);
        final EditText password = (EditText)findViewById(R.id.password);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Auth auth = new Auth(dis);
                new Api(dis).login(email.getText().toString(), password.getText().toString(), new Api.Callback() {

                    @Override
                    public void success(String result) {
                        auth.setSession(result);
                        dis.finish();
                    }

                    @Override
                    public void error() {
                        CharSequence text = "Invalid credentials...";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(dis, text, duration);
                        toast.show();
                    }
                });
            }
        });

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Auth auth = new Auth(dis);
                new Api(dis).register(email.getText().toString(), password.getText().toString(), new Api.Callback() {
                    @Override
                    public void success(String result) {
                        auth.setSession(result);
                        dis.finish();
                    }

                    @Override
                    public void error() {
                        CharSequence text = "Error registering...";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(dis, text, duration);
                        toast.show();
                    }
                });
            }
        });
    }
}
