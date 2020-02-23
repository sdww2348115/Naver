package org.example.cache;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CachedData<K, V> implements Cache<K, V> {

    /**
     * 利用LinkedHashMap，可扩展为LRU算法的Cache
     * 由于存在读写锁，没有必要使用ConcurrentHashMap
     */
    private LinkedHashMap<K, V> instance;

    private final ReentrantReadWriteLock.ReadLock readLock;

    private final ReentrantReadWriteLock.WriteLock writeLock;

    private CachedData() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        readLock = reentrantReadWriteLock.readLock();
        writeLock = reentrantReadWriteLock.writeLock();
        instance = new LinkedHashMap<>();
    }

    @Override
    public V processCachedData(K key) {

        //尝试直接从缓存中获取数据
        readLock.lock();
        try {
            V value = instance.get(key);
            if (Objects.nonNull(value)) {
                return value;
            }
        } finally {
            readLock.unlock();
        }

        //如果读锁直接升级写锁，可能造成多个读锁同时竞争写锁，造成死锁
        writeLock.lock();
        try {
            V value = instance.get(key);
            //需要再check一次，可能在等待锁的过程中其他线程已经填充了缓存值
            if (Objects.isNull(value)) {
                value = load(key);
                instance.put(key, value);
            }
            return value;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 模拟数据库操作，查询一个key/value
     * @param key 查询数据库所需信息
     * @return 数据库中对应的值
     */
    private V load(K key) {
        return null;
    }


}
