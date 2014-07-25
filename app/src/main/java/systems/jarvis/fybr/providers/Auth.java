package systems.jarvis.fybr.providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class Auth {

    private Context _context;
    private SharedPreferences _preferences;
    private HttpClient _http;

    public Auth(Context context) {
        _context = context;
        _preferences = PreferenceManager.getDefaultSharedPreferences(_context);
    }

    public Api connect() {
        String session = _preferences.getString("session", "");
        if(session.equalsIgnoreCase("")) {
            return null;
        }
        Log.i("Auth", session);
        _http = new DefaultHttpClient();
        return new Api(session, _context);
    }

    public void setSession(String session) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putString("session", session);
        editor.commit();
    }

    public Auth logout() {
        this.setSession("");
        return this;
    }


}

