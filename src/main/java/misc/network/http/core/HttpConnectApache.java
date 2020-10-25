package misc.network.http.core;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpConnectApache {

    private CloseableHttpClient client;
    private CloseableHttpResponse response;
    private HttpClientContext context;

    public HttpConnectApache() {
        this.initConnection();
        this.context = HttpClientContext.create();
    }

    public String get(String url, Map<String, String> headers)
            throws URISyntaxException, IOException {
        var request = new HttpGet(new URI(url));
        headers.forEach(request::setHeader);
        this.response = this.client.execute(request, this.context);
        return this.responseStreamToString(this.response.getEntity().getContent());
    }

    public String post(String url, List<NameValuePair> params, HashMap<String, String> headers)
            throws URISyntaxException, IOException {
        var request = new HttpPost(new URI(url));
        request.setEntity(new UrlEncodedFormEntity(params));
        headers.forEach(request::setHeader);
        this.response = this.client.execute(request, this.context);
        return this.responseStreamToString(this.response.getEntity().getContent());
    }

    public List<Cookie> getCookies() {
        return this.context.getCookieStore().getCookies();
    }

    public CloseableHttpResponse getResponse() {
        return this.response;
    }

    private String responseStreamToString(InputStream responseStream) {
        return new BufferedReader(new InputStreamReader(responseStream))
                .lines()
                .parallel()
                .collect(Collectors.joining("\n"));
    }

    private void initConnection() {
        var CONNECTION_TIMEOUT = 18000;
        var SOCKET_TIMEOUT = 18000;
        var REQUEST_TIMEOUT = 18000;
        this.client =
                HttpClients.custom()
                        .setDefaultRequestConfig(
                                RequestConfig.custom()
                                        .setConnectTimeout(CONNECTION_TIMEOUT)
                                        .setRelativeRedirectsAllowed(true)
                                        .setSocketTimeout(SOCKET_TIMEOUT)
                                        .setCookieSpec(CookieSpecs.DEFAULT)
                                        .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                                        .build())
                        .build();
    }
}
