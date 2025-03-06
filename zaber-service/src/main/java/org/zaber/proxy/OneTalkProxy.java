package org.zaber.proxy;

import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author : otter
 */
@Component
public class OneTalkProxy {
    public HitokotoResponse callHitokotoAPI() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .url("https://v1.hitokoto.cn")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful() && response.body() != null) {
            String responseBody = response.body().string();
            Gson gson = new Gson();
            HitokotoResponse hitokotoResponse = gson.fromJson(responseBody, HitokotoResponse.class);
            return hitokotoResponse;
        }
        return null;
    }
}

