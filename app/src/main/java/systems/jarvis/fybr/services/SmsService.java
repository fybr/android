package systems.jarvis.fybr.services;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import java.util.Date;

import systems.jarvis.fybr.providers.Model;
import systems.jarvis.fybr.providers.Sms;

public class SmsService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        Log.i("Service", "Sms started");
        ContentResolver contentResolver = this.getContentResolver();
        final Context service = this;
        contentResolver.registerContentObserver(Uri.parse("content://sms"),true, new ContentObserver(new Handler()) {

            private Date _last = new Date();
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                Cursor cursor = getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
                if (cursor.moveToNext()) {
                    String protocol = cursor.getString(cursor.getColumnIndex("protocol"));
                    int type = cursor.getInt(cursor.getColumnIndex("type"));

                    if (protocol != null || type != 2) {
                        return;
                    }
                    int dateColumn = cursor.getColumnIndex("date");
                    int bodyColumn = cursor.getColumnIndex("body");
                    int addressColumn = cursor.getColumnIndex("address");

                    Date date = new Date(cursor.getLong(dateColumn));

                    if(date.before(_last)) return;

                    Sms model = new Sms();
                    model.message = cursor.getString(bodyColumn);
                    model.from = "me";
                    model.thread = cursor.getString(addressColumn).replaceAll("[\\s\\(\\)\\-]", "");
                    post(service, model);

                }
                cursor.close();
                _last = new Date();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0;
    }

    protected void post(Context context, Model model) {
        Gson gson = new Gson();
        Intent i = new Intent(context, PostService.class);
        i.putExtra("model", gson.toJson(model));
        i.putExtra("type", model.type);
        context.startService(i);
    }

}
