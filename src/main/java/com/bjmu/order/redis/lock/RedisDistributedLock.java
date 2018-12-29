package com.bjmu.order.redis.lock;

import com.bjmu.order.exception.IllegalOptaionException;
import com.bjmu.order.util.BigDecimalUtils;
import com.bjmu.order.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @title : redis分布式锁
 * @describle :
 * <p>
 * Create By byron
 * @date 2017/7/5 15:26 星期三
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisDistributedLock {
    private static Logger log = LoggerFactory.getLogger(RedisDistributedLock.class);
    @Resource
    private RedisTemplate redisTemplate;
    /* 锁 */
    private String lock = "redis_distributed_lock";
    /* 锁超时时间(单位/ms) 默认1分钟 */
    private long lockTimeOut = 60*1000;
    /* 锁等待重试次数 默认6次 */
    private int lockWaitReTryTimes = 6;
    /* 锁状态 */
    private boolean locked;


    /**
     * 获取锁资源
     * @return
     * @throws InterruptedException
     */
    public synchronized boolean lock() throws IllegalOptaionException {
        try {
            int reTryTimes = 1;
            while(reTryTimes<=lockWaitReTryTimes){
                long expires = System.currentTimeMillis()+lockTimeOut+1;
                //设置锁超时时间
                String expireLockTime = String.valueOf(expires);
                boolean ret = this.setNX(lock,expireLockTime);
                if (ret){
                    //获取锁
                    locked = true;
                    return true;
                }
                /**
                 * 锁超时
                 *
                 * 判断锁超时时间，已防止死锁
                 */
                Long currentLockTimeVal = this.get(lock);
                long systemCurrentTimeVal = System.currentTimeMillis();
                log.info("当前锁超时时间-系统当前时间:ms"+(currentLockTimeVal-systemCurrentTimeVal));
                //判断当前锁值是否为空，否则判断锁是否超时(currentLockTimeVal<System.currentTimeMillis())
                if (StringUtils.isNotEmpty(currentLockTimeVal)&&currentLockTimeVal<systemCurrentTimeVal){
                    //获取上一个锁超时时间，并设置现在锁的超时时间
                    String oldLockTime = this.getSet(lock,expireLockTime);
                    /**
                     * 由于jedis.getSet是同步的，所以只有一个线程才能获取到上一个锁的超时时间
                     * 当多个线程同时执行到此处，由于只有一个线程的设置值和当前值相同，他才有权利获取锁
                     */
                    if (StringUtils.isNotEmpty(oldLockTime)&&oldLockTime.equals(String.valueOf(currentLockTimeVal))){
                        //获取锁
                        locked = true;
                        return true;
                    }
                }
                log.info("获取锁重试第"+reTryTimes+"次");
                reTryTimes++;
                /**
                 * 使用随机数保证每个线程等待时间公平
                 */
                BigDecimal time = BigDecimalUtils.op(0, BigDecimalUtils.BigDecimalType.MULTIPLY,String.valueOf(Math.random()),"10000");
                log.info("重试耗时:ms"+time.longValue());
                Thread.sleep(time.longValue());
            }
        } catch (InterruptedException e) {
            log.info("redis获取锁异常:{}",e.getMessage());
            throw new IllegalOptaionException("redis获取锁异常",e);
        }
        locked = false;
        return false;
    }

    /**
     * 释放锁资源
     */
    public synchronized void unlock(){
        //只有拥有锁资源线程方可释放锁资源
        if (locked){
            redisTemplate.delete(lock);
            locked = false;
        }else{
            log.info("在竞争锁等待重试次数内仍未获取到锁，sorry，我不要锁了，干自己事去了。。。。");
        }
    }

    public Long get(String key){
        return (Long) redisTemplate.opsForValue().get(key);
    }

    public boolean setNX(final String key,String value){
        boolean result = (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return connection.setNX(serializer.serialize(key), serializer.serialize(value));
        });
        return result;
    }

    public String getSet(String key,String value){
        String result = (String) redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] data = connection.getSet(serializer.serialize(key),serializer.serialize(value));
            return serializer.deserialize(data);
        });
        return result;
    }
}
