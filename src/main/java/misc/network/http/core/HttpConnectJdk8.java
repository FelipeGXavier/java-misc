package misc.network.http.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpConnectJdk8 {

    private final int CONNECTION_TIMEOUT = 15000;
    private final int READ_TIMEOUT = 15000;

    public HttpConnectJdk8() {}

    public String get(String requestUrl, HashMap<String, String> headers) throws IOException {
        var url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        this.setHeaders(headers, connection);
        connection.setRequestMethod("GET");
        InputStream responseStream = connection.getInputStream();
        return this.responseStreamToString(responseStream);
    }

    public String post(
            String requestUrl, HashMap<String, String> params, HashMap<String, String> headers)
            throws IOException {
        var url = new URL(requestUrl);
        var connection = (HttpURLConnection) url.openConnection();
        this.setHeaders(headers, connection);
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(true);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection
                .getOutputStream()
                .write(getPostDataString(params).getBytes(StandardCharsets.UTF_8));
        InputStream responseStream = connection.getInputStream();
        return this.responseStreamToString(responseStream);
    }

    public JsonNode responseStringToJsonNode(String json) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.readTree(json);
    }

    private String getPostDataString(HashMap<String, String> params) {
        var result = new StringBuilder();
        var first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) first = false;
            else result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return result.toString();
    }

    private void setHeaders(HashMap<String, String> headers, HttpURLConnection connection) {
        headers.forEach(connection::setRequestProperty);
    }

    private String responseStreamToString(InputStream responseStream) {
        return new BufferedReader(new InputStreamReader(responseStream))
                .lines()
                .parallel()
                .collect(Collectors.joining("\n"));
    }
}
