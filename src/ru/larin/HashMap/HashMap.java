package ru.larin.HashMap;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by denis__larin on 16.03.17.
 */
public class HashMap<K,V> implements IHashMap<K,V> {
    private int capacity = 100;
    private int size;
    private double load_factor;
    private ArrayList<Pair<K, V>>[] map;
    public HashMap() {
        map = new ArrayList[capacity];
        load_factor = 0;
        size = 0;
    }
    private int enterHashCode(K key){
        return key.hashCode()/capacity;
    }
    @Override
    public void put(K key, V value) {
        boolean up = false;
        int hash = enterHashCode(key);
        //пока hashcode больше вместимости массива
        while(hash>=capacity){
            //System.out.println("step: key = " + key + " value = " + value + " hashS = " + hash);
            /*увеличиваем вместимость в 5раз*/
            capacity*=5;
            //System.out.println("capacity: " + capacity);
            /*пересчитываем hashcode*/
            hash = enterHashCode(key);
            //System.out.println("hash: " + hash);
            /*true -- было произведено изменение capacity*/
            up = true;
        }

        /*если в массиве было больше чем 1 элемент и было расширение capacity*/
        if (size >= 1 && up){
            up = !up;
            /*временный массив arraylist'ов*/
            ArrayList<Pair<K,V>>[] temp = new ArrayList[map.length];
            /*идем от 0 до размера map и переносим все значения из map в temp*/
            for (int i = 0; i < map.length; i++) {
                try{
                    if(!map[i].isEmpty()){
                        temp[i] = new ArrayList<Pair<K,V>>();
                        for (int j = 0; j < map[i].size(); j++) {
                            temp[i].add(map[i].get(j));
                        }
                    }
                }
                catch (NullPointerException e){
                    continue;
                }
            }
            /*увеличиваем размер map*/
            map = new ArrayList[capacity];
            /*рекурсивно вызываем функцию добавления (для заполнения map)*/
            for (int i = 0; i < temp.length; i++) {
                try {
                    if (!temp[i].isEmpty()) {
                        for (int j = 0; j < temp[i].size(); j++) {
                            put(temp[i].get(j).getKey(), temp[i].get(j).getValue());
                        }
                    }
                }catch (NullPointerException e){
                    continue;
                }
            }
        }
        /*если в map был пуст и увеличивался capacity*/
        else if(size <1 && up){
            up = !up;
            /*расширяем map*/
            map = new ArrayList[capacity];
            map[hash] = new ArrayList<Pair<K,V>>();
            /*добавляем новый элемент в map с индексом по хэш*/
            map[hash].add(new Pair<K, V>(key,value));
            size++;
        }
        else{
            /*если элемент в map по индексу hash не инициализирован, то мы инициализируем его*/
            if(map[hash] == null) {
                map[hash] = new ArrayList<Pair<K, V>>();
            }
            else{
                /*если добавляем элемент с существующем ключом (одинаковом хэшем), то просто меняем этот элемент и выходим из функции*/
                for (int i = 0; i < map[hash].size(); i++) {
                    if(map[hash].get(i).getKey() == key){
                        map[hash].set(i,new Pair<K, V>(key, value));
                        return;
                    }
                }
            }
            /*добавляем элемент в map по индесу hash*/
            map[hash].add(new Pair<K, V>(key, value));
            size++;
        }

    }

    private boolean checkLoadFactor() {
        if((double)this.size/capacity > 0.5)
            return true;
        return false;
    }

    @Override
    public boolean remove(K key) {
        if(key == null)
            return false;
        int hashcode = enterHashCode(key);
        int hashposition;
        try{
            hashposition = map[hashcode].size();
        }catch (Exception e){
            return false;
        }
        for (int i = 0; i < hashposition; i++) {
            if (map[hashcode].get(i).equals(key)){
                map[hashcode].remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (key == null)
            return null;
        int hashcode = enterHashCode(key);
        int hashposition;
        try {
            hashposition = map[hashcode].size();
        }catch (Exception e){
            return null;
        }
        for (int i = 0; i < hashposition; i++) {
            if(map[hashcode].get(i).getKey().equals(key))
                return map[hashcode].get(i).getValue();
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    private double getLoad_factor() {
        return load_factor;
    }
}
