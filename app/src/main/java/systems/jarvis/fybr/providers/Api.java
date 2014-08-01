package systems.jarvis.fybr.providers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class Api {

    private String _session;
    private Context _context;

    public Api(String session, Context context) {
        _session = session;
        _context = context;
    }

    public Api(Context context) {
        _context = context;
    }

    public void login(String email, String password, Callback callback) {
        post("users/login", "{ 'email' : '" + email + "', password : '" + password + "'}", callback);
    }

    public void register(String email, String password, Callback callback) {
        post("users", "{ 'email' : '" + email + "', password : '" + password + "'}", callback);
    }

    public static class Callback {

        public void success(String result) {

        }

        public void error() {

        }

    }

    public void post(final String path, final String body, final Callback callback) {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    BasicResponseHandler response = new BasicResponseHandler();
                    DefaultHttpClient client = new DefaultHttpClient();
                    String url = "http://api.fybr.ws/" + path + "?session=" + _session;
                    HttpPost post = new HttpPost(url);
                    post.setEntity(new StringEntity(body));
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-type", "application/json");
                    Log.i("Http", url + " - " + body);
                    String result = response.handleResponse(client.execute(post));
                    if (result.isEmpty()) {
                        this.publishProgress(false);
                    } else {
                        this.publishProgress(true, result);
                    }
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.publishProgress(false);
                return null;
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                if(callback == null) return;
                boolean result = (Boolean)values[0];
                if(result)
                    callback.success((String)values[1]);
                else
                    callback.error();
            }
        }.execute();
    }

    public void event(Model model) {
        Gson gson = new Gson();
        this.post("users/events/" + model.type + "/" + model.id, gson.toJson(model), null);
    }

    public <T> void event(List<T> models, String type) {
        Gson gson = new Gson();
        post("users/events/" + type, gson.toJson(models), null);
    }

}
