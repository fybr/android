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

    public void connect(AuthCallback cb) {
        String session = _preferences.getString("session", "");
        if(session.equalsIgnoreCase("")) {
            cb.onFail(this);
            return;
        }
        Log.i("Auth", session);
        _http = new DefaultHttpClient();
        cb.onConnect(this, new Api(session));
    }

    private void setSession(String session) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putString("session", session);
        editor.commit();
    }

    public void setPush(String id) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putString("push", id);
        editor.commit();
    }

    public Auth login(String email, String password) {
        this.setSession(new Api("").login(email, password));
        return this;
    }

    public Auth logout() {
        this.setSession("");
        this.setPush("");
        return this;
    }

    public String getPush() {
        return _preferences.getString("push", "");
    }

    public String id = "";

    public interface AuthCallback {

        public void onConnect(Auth auth, Api api);

        public void onFail(Auth auth);
    }

}

