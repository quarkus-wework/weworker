package indi.youpan.wework.util;

import io.quarkus.redis.client.RedisClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class RedisUtil {
  @Inject private RedisClient redisClient;

  public void set(String key, String value) {
    redisClient.set(Arrays.asList(key, value));
  }

  public String get(String key) {
    return redisClient.get(key).toString();
  }
}
