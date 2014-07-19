package systems.jarvis.fybr.services;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
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

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.providers.Auth;
import systems.jarvis.fybr.providers.Model;
import systems.jarvis.fybr.providers.Sms;

public class SmsService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        ContentResolver contentResolver = this.getContentResolver();
        final Context service = this;
        contentResolver.registerContentObserver(Uri.parse("content://sms"),true, new ContentObserver(new Handler()) {

            private String _last = getLast().id;

            private Sms getLast() {
                Cursor cursor = getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
                if(!cursor.moveToNext()) return null;
                int dateColumn = cursor.getColumnIndex("date");
                int bodyColumn = cursor.getColumnIndex("body");
                int addressColumn = cursor.getColumnIndex("address");
                int threadColumn = cursor.getColumnIndex("thread_id");
                int idColumn = cursor.getColumnIndex("_id");
                Sms model = new Sms();
                model.id = cursor.getString(idColumn);
                model.message = cursor.getString(bodyColumn);
                model.from = "me";
                model.thread = cursor.getString(addressColumn).replaceAll("[\\s\\(\\)\\-]", "").replaceAll("\\+\\d", "");
                return model;
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                Sms model = getLast();
                if(model.id.equals(_last))
                    return;
                _last = model.id;
                Api api = new Auth(service).connect();
                if(api == null) return;
                api.event(model);
                /*
                ContentValues values = new ContentValues();
                values.put("read",true);
                getContentResolver().update(Uri.parse("content://sms/sent"),values, "thread_id="+model.id, null);
                */
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0;
    }

}
