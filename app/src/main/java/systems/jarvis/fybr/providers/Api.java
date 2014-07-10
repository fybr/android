package systems.jarvis.fybr.providers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import systems.jarvis.fybr.R;

public class Api {

    private Context _context;
    private SharedPreferences _preferences;
    private String _token;
    private HttpClient _http;

    public Api(Context context) {
        _context = context;
        _preferences = PreferenceManager.getDefaultSharedPreferences(_context);
    }

    public void connect(ApiCallback cb) {
        _token = _preferences.getString(_context.getString(R.string.preferenceToken), "");
        if(_token.equalsIgnoreCase("")) {
            cb.onDisconnect(this);
            return;
        }
        Log.i("Auth", _token);
        _http = new DefaultHttpClient();
        cb.onConnect(this, _token);
    }

    public void post(Object o) {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        Log.i("Http", json);

    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putString(_context.getString(R.string.preferenceToken), token);
        editor.commit();
    }

    public interface ApiCallback {

        public void onConnect(Api api, String token);

        public void onDisconnect(Api api);
    }

}

