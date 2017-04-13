package ru.larin.HashMap;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by denis__larin on 13.04.17.
 */
public class HashMap<K,V> implements IHashMap<K,V> {
    private int capacity = 100;
    private int size = 0;
    private ArrayList<Pair<K,V>>[] map;

    public HashMap() {
        map = new ArrayList[capacity];
    }

    @Override
    public boolean put(K key, V value) {
        boolean up = false;
        int hashCode = enterHashCode(key);
        //расширяем массив если hashcode больше чем capasity
        while (hashCode >= capacity) {
            up = true;
            capacity *= 2;
            //пересчитываем hashcode относительно capacity
            hashCode = enterHashCode(key);
        }
        //если в таблице нет элементов, то расширяем и добавляем новый элемент
        if (size == 0 && up) {
            map = new ArrayList[capacity];
            map[hashCode] = new ArrayList<Pair<K, V>>();
            map[hashCode].add(new Pair<>(key, value));
            size++;
            up=!up;
            return true;
        }
        //если в массиве были элементы и он расширялся
        else if(size>0 && up){
            up = !up;
            //копируем элементы из map в temp
            ArrayList<Pair<K,V>>[] temp = Arrays.copyOf(map,map.length);
            //расширяем map
            map = new ArrayList[capacity];
            //переносим из temp в map
            size = 0;
            copyIntoMap(temp);
            //добавляем новый элемент в map
            put(key,value);
        }
        else if (!up){
            //если не инициализирована
            if (map[hashCode] == null){
                map[hashCode] = new ArrayList<Pair<K,V>>();
                map[hashCode].add(new Pair<>(key, value));
                size++;
                return true;
            }
            else{
                for (int i = 0; i < map[hashCode].size(); i++) {
                    if(map[hashCode].get(i).getKey().equals(key)){
                        map[hashCode].set(i,new Pair<>(key, value));
                    }
                }
            }
        }
        return false;
    }

    private void copyIntoMap(ArrayList<Pair<K, V>>[] temp) {
        for (int i = 0; i < temp.length; i++) {
            if(temp[i]!=null){
                int deep = temp[i].size();
                for (int j = 0; j < deep; j++) {
                    put(temp[i].get(j).getKey(),temp[i].get(j).getValue());
                }
            }
        }
    }

    private int enterHashCode(K key) {
        return key.hashCode()/capacity;
    }

    @Override
    public boolean remove(K key) {
        if (size == 0){
            System.out.println("таблица пустая");
            return false;
        }
        if(key == null)
            return false;
        int hashcode = enterHashCode(key);
        int deep;
        try{
            deep = map[hashcode].size();
        }catch (NullPointerException e){
            return false;
        }
        for (int i = 0; i < deep; i++) {
            if(map[hashcode].get(i).getKey().equals(key)){
                map[hashcode].remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if(key == null) {
            return null;
        }
        int hashcode = enterHashCode(key);
        int deep;
        try{
            deep = map[hashcode].size();
        }catch (NullPointerException e){
            return null;
        }
        for (int i = 0; i < deep; i++) {
            if(map[hashcode].get(i).getKey().equals(key)){
                return map[hashcode].get(i).getValue();
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }
}
