import java.util.LinkedList;

public class chainedHash {

    private LinkedList<KeyValue>[] table;
    private int m;

    public chainedHash(int m) {
        this.m = m;
        table = new LinkedList[m + 1];

        for (int i = 1; i <= m; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int hash(String key) {
        return (Math.abs(key.hashCode()) % m) + 1;
    }

    public void insert(String key, String value) {
        int i = hash(key);

        for (KeyValue kv : table[i]) {
            if (kv.key.equals(key)) {
                kv.value = value;
                return;
            }
        }

        table[i].add(new KeyValue(key, value));
    }

    public String lookup(String key) {
        int i = hash(key);

        for (KeyValue kv : table[i]) {
            if (kv.key.equals(key))
                return kv.value;
        }

        return null;
    }

    public String remove(String key) {
        int i = hash(key);

        for (KeyValue kv : table[i]) {
            if (kv.key.equals(key)) {
                table[i].remove(kv);
                return kv.value;
            }
        }

        return null;
    }
}
