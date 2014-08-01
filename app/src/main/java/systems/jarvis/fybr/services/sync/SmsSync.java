package systems.jarvis.fybr.services.sync;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import java.util.HashSet;

import systems.jarvis.fybr.providers.Api;
import systems.jarvis.fybr.providers.Auth;
import systems.jarvis.fybr.providers.Sms;
import systems.jarvis.fybr.services.sync.ISync;

public class SmsSync implements ISync {

    private HashSet<String> _handled;

    @Override
    public void register(final Context context) {
        _handled = new HashSet<String>();
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(Uri.parse("content://sms"),true, new ContentObserver(new Handler()) {
            {
                getLast();
            }

            private Sms getLast() {
                Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
                Sms model = null;
                if(cursor.moveToNext()) {
                    int idColumn = cursor.getColumnIndex("_id");
                    String id = cursor.getString(idColumn);
                    if (_handled.add(id)) {
                        int dateColumn = cursor.getColumnIndex("date");
                        int bodyColumn = cursor.getColumnIndex("body");
                        int addressColumn = cursor.getColumnIndex("address");
                        int threadColumn = cursor.getColumnIndex("thread_id");
                        model = new Sms();
                        model.message = cursor.getString(bodyColumn);
                        model.from = "me";
                        model.thread = cursor.getString(addressColumn).replaceAll("[\\s\\(\\)\\-]", "").replaceAll("\\+\\d", "");
                    }
                }
                cursor.close();
                return model;
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                Sms model = getLast();
                if(model == null) return;
                Api api = new Auth(context).connect();
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
}
