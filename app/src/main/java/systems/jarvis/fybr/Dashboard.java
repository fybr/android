package systems.jarvis.fybr;

import android.content.Intent;
import android.content.IntentSender;
import android.view.View;

import systems.jarvis.fybr.providers.Api;

public class Dashboard extends Base {

    @Override
    public void onConnect(final Api api) {
        setContentView(R.layout.activity_dashboard);


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
