package ru.larin.HashMap;

/**
 * Created by denis__larin on 16.03.17.
 */
public interface IHashMap <K,V> {
    public boolean put(K key, V value);
    public boolean remove(K key);
    public V get(K key);
    public int size();
}
