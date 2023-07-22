import java.util.Iterator;

public class HashMap<K, V> implements Iterable {

    private static final int INIT_BUCKET_COUNT = 16;
    private static final double LOAD_FACTOR = 0.5;

    private static int size;

    private Bucket[] buckets;

    @Override
    public Iterator iterator() {
        return new HashMapIterator();
    }

    class HashMapIterator implements Iterator {

        int i = -1;
        @Override
        public boolean hasNext() {
            return i++ < buckets.length-1;
        }

        @Override
        public String next() {
            if (buckets[i] != null) {
                var currentNode = buckets[i].head;

                if (currentNode.next == null) {
                    return buckets[i].head.value.key.toString() + " " + buckets[i].head.value.value.toString();
                }

                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(currentNode.value.key.toString());
                    sb.append(currentNode.value.value.toString());
                    currentNode = currentNode.next;
                } while (currentNode != null);
                return sb.toString();
            }
            return "null";
        }
    }

    class Entity{
        K key;
        V value;
    }

    class Bucket<K, V>{

        Node head;

        class Node{
            Node next;
            Entity value;
        }

        public V add(Entity entity){
            Node node = new Node();
            node.value = entity;

            if (head == null){
                head = node;
                return null;
            }

            Node currentNode = head;
            while (true){
                if (currentNode.value.key.equals(entity.key)){
                    V buf = (V)currentNode.value.value;
                    currentNode.value.value = entity.value;
                    return buf;
                }
                if (currentNode.next != null){
                    currentNode = currentNode.next;
                }
                else {
                    currentNode.next = node;
                    return null;
                }
            }
        }

        public V get(K key){
            Node node = head;
            while (node != null){
                if (node.value.key.equals(key)){
                    return (V)node.value.value;
                }
                node = node.next;
            }
            return null;
        }

        public V remove(K key){
            if (head == null)
                return null;
            if (head.value.key.equals(key)){
                V buf = (V)head.value.value;
                head = head.next;
                return buf;
            }
            else{
                Node node = head;
                while (node.next != null){
                    if (node.next.value.key.equals(key)){
                        V buf = (V)node.next.value.value;
                        node.next = node.next.next;
                        return buf;
                    }
                    node = node.next;
                }
                return null;
            }
        }
    }

    private int calculateBucketIndex(K key)
    {
        return Math.abs(key.hashCode()) % buckets.length;
    }


    private void recalculate(){
        size = 0;
        Bucket<K, V>[] old = buckets;
        buckets = new Bucket[old.length * 2];
        for (int i = 0; i < old.length; i++){
            Bucket<K, V> bucket = old[i];
            if (bucket != null){
                Bucket.Node node = bucket.head;
                while (node != null){
                    put((K)node.value.key, (V)node.value.value);
                    node = node.next;
                }
            }
            old[i] = null;
        }
    }

    public V put(K key, V value){

        if (buckets.length * LOAD_FACTOR <= size){
            recalculate();
        }

        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null){
            bucket = new Bucket();
            buckets[index] = bucket;
        }

        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        V res = (V)bucket.add(entity);
        if (res == null){
            size++;
        }
        return res;
    }

    public V get(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        return (V)bucket.get(key);
    }

    public V remove(K key){
        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null)
            return null;
        V res = (V)bucket.remove(key);
        if (res != null){
            size--;
        }
        return res;
    }

    public HashMap(){
        buckets = new Bucket[INIT_BUCKET_COUNT];
    }

    public HashMap(int initCount){
        buckets = new Bucket[initCount];
    }
}