package org.example.cache;

public interface Cache<K, V> {

    V processCachedData(K key);
}
