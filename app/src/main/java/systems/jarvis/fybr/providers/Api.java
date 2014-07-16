package systems.jarvis.fybr.providers;

import android.util.Log;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class Api {

    private String _session;

    public Api(String session) {
        _session = session;
    }

    public String login(String email, String password) {
        post("auth/login", "{ 'email' : '" + email + "', password : '" + password + "'}");
        return "09e01bba-8058-4665-b54a-ee9464cbcf88";
    }

    public BasicResponseHandler post(String path, String body) {

        BasicResponseHandler response = new BasicResponseHandler();
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            String url = "http://fybr.jarvis.system/" + path + "?session=" + _session;
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(body));
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            Log.i("Http", url + " - " + body);
            client.execute(post,response);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
