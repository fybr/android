package systems.jarvis.fybr.providers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.util.List;

import systems.jarvis.fybr.services.PostService;

public class Api {

    private String _session;
    private Context _context;

    public Api(String session, Context context) {
        _session = session;
        _context = context;
    }

    public String login(String email, String password) {
        return postSync("users/login", "{ 'email' : '" + email + "', password : '" + password + "'}");
    }

    public String postSync(String path, String body) {

        try {
            BasicResponseHandler response = new BasicResponseHandler();
            DefaultHttpClient client = new DefaultHttpClient();
            String url = "http://api.fybr.ws/" + path + "?session=" + _session;
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(body));
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            Log.i("Http", url + " - " + body);
            return response.handleResponse(client.execute(post));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void event(Model model) {
        Gson gson = new Gson();
        Intent i = new Intent(_context, PostService.class);
        i.putExtra("body", gson.toJson(model));
        i.putExtra("path", "users/events/" + model.type + "/" + model.id);
        i.putExtra("session", _session);
        _context.startService(i);
    }

    public <T> void event(List<T> models, String type) {
        Gson gson = new Gson();
        postSync("users/events/" + type, gson.toJson(models));
    }

    public void post(String path, String body) {
        Gson gson = new Gson();
        Intent i = new Intent(_context, PostService.class);
        i.putExtra("body", body);
        i.putExtra("path", path);
        i.putExtra("session", _session);
        _context.startService(i);
    }
}
