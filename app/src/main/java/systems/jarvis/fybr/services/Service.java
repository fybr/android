package systems.jarvis.fybr.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Dharun on 7/1/2014.
 */
public class Service extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public Service(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
