package systems.jarvis.fybr.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import systems.jarvis.fybr.services.sync.BatterySync;
import systems.jarvis.fybr.services.sync.ContactSync;
import systems.jarvis.fybr.services.sync.SmsSync;

public class SyncService extends Service {

    public SyncService() {

    }

    @Override
    public void onCreate() {
        new ContactSync().register(this);
        new SmsSync().register(this);
        new BatterySync().register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
