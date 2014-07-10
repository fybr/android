package systems.jarvis.fybr;

import android.content.Intent;
import android.content.IntentSender;
import android.view.View;

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.receivers.Boot;

public class Dashboard extends Base {

    @Override
    public void onConnect(final Api api, String token) {
        setContentView(R.layout.activity_dashboard);

        Boot.StartServices(this);


        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                api.setToken("");
                logout();
            }
        });
    }

    private void logout() {
        this.finish();
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }
}
