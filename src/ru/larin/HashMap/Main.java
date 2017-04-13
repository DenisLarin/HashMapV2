package ru.larin.HashMap;

import java.util.ArrayList;

/**
 * Created by denis__larin on 17.03.17.
 */
public class Main {
    public static void main(String[] args) {
        HashMap<String, Long> map = new HashMap<>();
        map.put("Denis",9854207803L);
        map.put("Denis2",0001L);
        System.out.println(map.get("Denis"));
        System.out.println(map.get("Denis2"));

//        System.out.println("____________");
//        System.out.println("hash для слова natash: " + "natash".hashCode());
    }
}
