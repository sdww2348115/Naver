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
        //step1.加读锁
        readLock.lock();
        try {
            V value = instance.get(key);
            //step2.如果未获取到数据，读锁升级为写锁
            if (Objects.isNull(value)) {
                writeLock.lock();
                try {
                    value = load(key);
                    instance.put(key, value);
                } finally {
                    writeLock.unlock();
                }
            }
            return value;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 模拟数据库操作，查询一个key/value
     * @param key
     * @return
     */
    private V load(K key) {
        return null;
    }


}
