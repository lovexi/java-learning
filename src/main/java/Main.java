/**
 * Created by yangyang on 10/16/2016.
 */
import collection.MyHashMap;
public class Main {
    public static void main(String[] args) {
        MyHashMap<Integer, Integer> map = new MyHashMap<Integer, Integer>(5);

        map.put(1, 1);
        System.out.println(map.get(1));
        map.put(2, 2);
        System.out.println(map.get(2));
        map.put(1, 3);
        System.out.println(map.get(1));
        map.put(6, 19);
        System.out.println(map.get(6));

    }
}
