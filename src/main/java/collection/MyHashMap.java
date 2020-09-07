package collection;

/**
 * Simulating HashMap machenism in Java.
 */
public class MyHashMap<K, V> {
    final Entry[] table;
    final int capacity;

    static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public MyHashMap(int capacity) {
        this.capacity = capacity;
        this.table = new Entry[capacity];
    }

    public void put(K key, V value) {
        if (key == null) {
            return;
        }

        int hashcode = hash(key);
        Entry newEntry = new Entry(key, value, null);

        if (table[hashcode] == null) {
            table[hashcode] = newEntry;
        }
        else {
            Entry previous = null;
            Entry current = table[hashcode];

            while (current != null) {
                if (current.key == key) {
                    if (previous != null) {
                        previous.next = newEntry;
                        newEntry.next = current.next;
                    }
                    else {
                        newEntry.next = current.next;
                        table[hashcode] = newEntry;
                    }
                    return;
                }
                previous = current;
                current = current.next;
            }
            previous.next = newEntry;
        }
    }

    public V get(K key) {
        int hashcode = hash(key);
         Entry<K, V> targetEntry = table[hashcode];

        while (targetEntry != null && targetEntry.key != key) {
            targetEntry = targetEntry.next;
        }

        return targetEntry == null ? null : targetEntry.value;
    }

    private int hash(K key) {
        return key.hashCode() % capacity;
    }
}
