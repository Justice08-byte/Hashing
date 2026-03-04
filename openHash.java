public class openHash {

    private KeyValue[] table;
    private int m;
    private int size = 0;

    public openHash(int m) {
        this.m = m;
        table = new KeyValue[m + 1]; // [1..m]
    }

    private int hash(String key) {
        return (Math.abs(key.hashCode()) % m) + 1;
    }

    public void insert(String key, String value) {
        int i = hash(key);

        while (table[i] != null && !table[i].key.equals(key)) {
            i = (i % m) + 1;  // linear probing
        }

        if (table[i] == null) size++;
        table[i] = new KeyValue(key, value);
    }

    public String lookup(String key) {
        int i = hash(key);

        while (table[i] != null) {
            if (table[i].key.equals(key))
                return table[i].value;

            i = (i % m) + 1;
        }

        return null;
    }

    public String remove(String key) {
        int i = hash(key);

        while (table[i] != null) {
            if (table[i].key.equals(key)) {
                String val = table[i].value;
                table[i] = null;
                size--;
                return val;
            }
            i = (i % m) + 1;
        }

        return null;
    }
}
