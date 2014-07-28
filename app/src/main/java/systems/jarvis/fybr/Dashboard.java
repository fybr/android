package systems.jarvis.fybr;

import android.content.Intent;
import android.view.View;

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.providers.Auth;
import systems.jarvis.fybr.receivers.Boot;

public class Dashboard extends Base {

    private void logout() {
        this.finish();
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }

    @Override
    public void ready() {
        setContentView(R.layout.activity_dashboard);

        Boot.StartServices(this);

        /*
        Intent intent=new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        startActivity(intent);
        */


        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _auth.logout();
                logout();
            }
        });
        findViewById(R.id.notifications).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            }
        });
    }
}
