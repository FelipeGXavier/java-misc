package misc.cache.queue;

import misc.network.http.core.HttpConnectApache;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class Consumer {

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        var redis = new Jedis("localhost");
        var producer = new Producer(redis);
        producer.publish("Foo bar");
        producer.publish("Foo baz");
        producer.publish("Foo bar baz");
        while (true) {
            System.out.println("Waiting for messages");
            var messages = redis.blpop(0, "queue");
            System.out.println("Message received");
            var payload = messages.get(1);
            doSomeBlockOperation(payload);
        }
    }

    public static void doSomeBlockOperation(String payload) throws IOException, URISyntaxException {
        var connect = new HttpConnectApache();
        connect.get("https://siconv.com.br/", new HashMap<>());
        System.out.println(payload);
    }
}
