package com.underestimatedme.studyguava;

import com.google.common.base.Stopwatch;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Uninterruptibles;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * <p></p>
 *
 * @author mj
 * @date Caches.java v1.0
 */
public class Caches {

    /**
     * 我们一般会将频繁读取的数据保存在redis等缓存服务中，在每次请求的时候，优先从缓存中读取以减小数据开销，提升性能
     * 在访问远程缓存的情况下，一般耗时为3-5ms，虽然这个值不大，但是在一个流程中频繁读取的情况下，也会拖慢系统响应速度，
     * 此时我们需要使用本地缓存以提升性能，本地缓存顾名思义即数据存储在本地内存中，适用于频繁读取，并且数据量不太大的场景，在应用重启时本地缓存会全部丢失。
     * 而guava提供了一个易用的本地缓存
     */

    // fixme:此处为方便测试，设置5秒钟后缓存过期
    private static final String CACHE_CONFIG = "initialCapacity=50,maximumSize=1000,expireAfterWrite=5s";

    /**
     * 以下为guava cache最简单的使用方法
     * cachebuilder.from 更便于参数化控制缓存效果
     */
    private LoadingCache<String, Optional<Integer>> cacheLoader = CacheBuilder.from(CACHE_CONFIG)
            .build(new CacheLoader<String, Optional<Integer>>() {
                @Override
                public Optional<Integer> load(String key) {
                    /**
                     * 在此处初始化缓存，从配置,redis或db从读取
                     * 备:使用optional的原因是load无法处理空值
                     */
                    return Optional.ofNullable(initCache(key));
                }
            });

    public Integer initCache(String key) {
        // fixme:假设加载缓存需要1秒钟
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        return new Random().nextInt();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String key1 = "paramA";
        Caches caches = new Caches();
        Optional<Integer> val = caches.cacheLoader.get(key1);
        System.out.println(String.format("key:%s,val:%s,time cost:%s", key1, val.get(), stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        stopwatch.reset();
        stopwatch.start();
        val = caches.cacheLoader.get(key1);
        // 此处读取本地缓存，加载时间为0
        System.out.println(String.format("key:%s,val:%s,time cost:%s", key1, val.get(), stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        stopwatch.reset();
        stopwatch.start();
        String key2 = "paramB";
        val = caches.cacheLoader.get(key2);
        System.out.println(String.format("key:%s,val:%s,time cost:%s", key2, val.get(), stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        stopwatch.reset();
//        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        // 手动清除本地缓存
        caches.cacheLoader.invalidateAll();
        System.out.println("size:"+caches.cacheLoader.asMap().size());
        stopwatch.start();
        val = caches.cacheLoader.get(key1);
        // 此时耗时为1秒
        System.out.println(String.format("key:%s,val:%s,time cost:%s", key1, val.get(), stopwatch.elapsed(TimeUnit.MILLISECONDS)));

    }

}
