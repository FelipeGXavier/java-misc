package misc.network.http.core;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class HttpConnectJdk11 {

    private HttpClient client;

    public HttpConnectJdk11() {
        this.initConnection();
    }

    public String get(String url, HashMap<String, String> headers)
            throws URISyntaxException, IOException, InterruptedException {
        var request = HttpRequest.newBuilder(new URI(url)).GET();
        headers.forEach(request::header);
        return this.client.send(request.build(), HttpResponse.BodyHandlers.ofString()).body();
    }

    public String post(String url, Map<String, String> headers, Map<Object, Object> params)
            throws URISyntaxException, IOException, InterruptedException {
        var request = HttpRequest.newBuilder(new URI(url)).POST(this.ofFormData(params));
        headers.forEach(request::header);
        return this.client.send(request.build(), HttpResponse.BodyHandlers.ofString()).body();
    }

    private void initConnection() {
        var CONNECTION_TIMEOUT = 15000;
        this.client =
                HttpClient.newBuilder()
                        .connectTimeout(Duration.ofMillis(CONNECTION_TIMEOUT))
                        .version(HttpClient.Version.HTTP_1_1)
                        .followRedirects(HttpClient.Redirect.ALWAYS)
                        .build();
    }

    private HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
}
