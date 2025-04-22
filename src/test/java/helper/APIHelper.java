package helper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import java.io.InputStream;

public class APIHelper {

    private static final Gson gson = new Gson();
    static HttpClient client;
    static int TIMEOUT = 2;

    public static JsonObject getJsonResponseFromRequest(String uri, String userToken) {
        //GET запрос к API
        HttpGet httpGet = new HttpGet(uri);
        client = HttpClients.createDefault();
        try {
            if (userToken != null)
                httpGet.addHeader("x-client-token", userToken);
            HttpResponse response = client.execute(httpGet);
            //Get ответ
            int code = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inStream = entity.getContent();
                String content = new String(inStream.readAllBytes());
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("statusCode", code);
                jsonObject.addProperty("response", content);
                entity.getContent().close();
                return jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static JsonObject postJsonResponseFromRequest(String uri, String params, String userToken) {
        //POST запрос к API
        HttpPost httpPost = new HttpPost(uri);
        client = HttpClients.createDefault();
        try {
            if (params != null) {
                httpPost.setEntity(new StringEntity(params, ContentType.APPLICATION_JSON));
                httpPost.setHeader("content-Type","application/json");
            }
            if (userToken != null)
                httpPost.addHeader("x-client-token", userToken);
            HttpResponse response = client.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inStream = entity.getContent();
                String content = new String(inStream.readAllBytes());
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("statusCode", code);
                jsonObject.addProperty("response", content);
                entity.getContent().close();
                return jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static JsonObject patchJsonResponseFromRequest(String uri, String params, String userToken) {
        //POST запрос к API
        HttpPatch httpPatch = new HttpPatch(uri);
        client = HttpClients.createDefault();
        try {
            if (params != null) {
                httpPatch.setEntity(new StringEntity(params, ContentType.APPLICATION_JSON));
                httpPatch.setHeader("content-Type","application/json");
            }
            if (userToken != null)
                httpPatch.addHeader("x-client-token", userToken);
            HttpResponse response = client.execute(httpPatch);
            int code = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inStream = entity.getContent();
                String content = new String(inStream.readAllBytes());
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("statusCode", code);
                jsonObject.addProperty("response", content);
                entity.getContent().close();
                return jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}

