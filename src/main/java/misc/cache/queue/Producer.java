package misc.cache.queue;

import redis.clients.jedis.Jedis;

public class Producer {

    private Jedis redis;

    public Producer(Jedis redis) {
        this.redis = redis;
    }

    public void publish(String text) {
        this.redis.rpush("queue", text);
    }
}
