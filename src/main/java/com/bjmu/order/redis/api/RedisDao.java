package com.bjmu.order.redis.api;

import com.bjmu.order.util.StringUtils;
import org.springframework.data.redis.connection.RedisZSetCommands;

import java.util.List;
import java.util.Set;


public interface RedisDao {
    void setString(String prefix, String key, String value, Long expire);
    Object getObject(String prefix, String key);
    Set keys(String parten);
    boolean set(final String key, final String value);
    String get(final String key);
    boolean expire(final String key, long expire);
    boolean expire(String prefix, String key, long expire);
    <T> boolean setList(String key, List<T> list);
    <T> List<T> getList(String key, Class<T> clz);
    long lpush(final String key, Object obj);
    long rpush(final String key, Object obj);
    String lpop(final String key);
    String rpop(final String key);
    void setObject(String prefix, String key, Object object);
    Object getSerObject(String prefix, String key);
    Long incr(String prefix, String key);
    Long del(String prefix, String key);
    void zadd(String prefix, String key, double score, String member);
    Set<RedisZSetCommands.Tuple> zrange(String prefix, String key, int startIndex, int endIndex);
    Set<RedisZSetCommands.Tuple> zrevrange(String prefix, String key, int startIndex, int endIndex);
    default String getKey(String prefix, String key) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotEmpty(prefix)) {
            sb.append(prefix).append(StringUtils.SPLIT);
        }
        sb.append(key);
        return sb.toString();
    }
}
