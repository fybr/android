package systems.jarvis.fybr.providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class Auth {

    private Context _context;
    private SharedPreferences _preferences;
    private String email;
    private HttpClient _http;

    public Auth(Context context) {
        _context = context;
        _preferences = PreferenceManager.getDefaultSharedPreferences(_context);
    }

    public void connect(AuthCallback cb) {
        email = _preferences.getString("email", "");
        if(email.equalsIgnoreCase("")) {
            cb.onDisconnect(this);
            return;
        }
        Log.i("Auth", email);
        _http = new DefaultHttpClient();
        cb.onConnect(this, email);
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putString("email", email);
        editor.commit();
    }

    public void setPush(String id) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putString("pushId", id);
        editor.commit();
    }

    public String getPush() {
        return _preferences.getString("pushId", "");
    }

    public String id = "";

    public interface AuthCallback {

        public void onConnect(Auth auth, String user);

        public void onDisconnect(Auth auth);
    }

}

