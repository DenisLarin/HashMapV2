package ru.larin.HashMap;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by denis__larin on 17.03.17.
 */
public class Main {
    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Denis","00000");
        map.put("Denis2","adnasjdnaksd");
        System.out.println(map.get("Denis"));
        System.out.println(map.get("Denis2"));
        map.put("Denis","+79854207803");
        map.put("Denis2","All is Work");
        System.out.println(map.get("Denis"));
        System.out.println(map.get("Denis2"));
        map.remove("Denis");
        System.out.println(map.get("Denis"));
        System.out.println(map.size());
    }
}
