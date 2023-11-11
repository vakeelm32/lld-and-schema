package hashing;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashing {

    private final TreeMap<Long, String> ring;
    private final int numberOfReplicas;
    private final MessageDigest md;

    public ConsistentHashing(int numberOfReplicas) throws NoSuchAlgorithmException {
        this.ring = new TreeMap<>();
        this.numberOfReplicas = numberOfReplicas;
        this.md = MessageDigest.getInstance("MD5");
    }

    public void addServer(String server) {
        for (int i = 0; i < numberOfReplicas; i++) {
            long hash = generateHash(server + i);
            ring.put(hash, server);
        }
    }

    public void removeServer(String server) {
        for (int i = 0; i < numberOfReplicas; i++) {
            long hash = generateHash(server + i);
            ring.remove(hash);
        }
    }

    public String getServer(String key) {
        if (ring.isEmpty()) {
            return null;
        }
        long hash = generateHash(key);
        if (!ring.containsKey(hash)) {
            // The tailMap() method of SortedMap interface in Java is used to
            // return a view of the portion of this map whose keys are greater than or equal
            // to fromKey.
            SortedMap<Long, String> tailMap = ring.tailMap(hash);
            hash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        }
        System.out.println("Generated has " + hash);
        return ring.get(hash);
    }

    private long generateHash(String key) {
        md.reset();
        md.update(key.getBytes());
        byte[] digest = md.digest();
        long hash = ((long) (digest[3] & 0xFF) << 24) | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8) | ((long) (digest[0] & 0xFF));
        return hash;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // 3 here is the number of replicas of on the ring
        ConsistentHashing ch = new ConsistentHashing(3);
        ch.addServer("11.22.33.44");
        ch.addServer("99.22.33.44");
        ch.addServer("66.22.33.44");
        ch.addServer("77.22.33.44");
        ch.addServer("88.22.33.44");

        // print the ring,
        // key will sorted as we are using Treemap, value represents the server
        System.out.println("Printing Ring");

        ch.ring.keySet().forEach(hash -> {
            System.out.println(hash + "   ->   " + ch.ring.get(hash));
        });

        System.out.println("from 192.168.1.1 request goes to server: " + ch.getServer("192.168.1.1"));
        System.out.println("from 192.168.1.2 : request goes to server : " + ch.getServer("192.168.1.2"));

        ch.removeServer("11.22.33.44");
        System.out.println("After removing 11.22.33.44");

        System.out.println("Printing Ring");
        ch.ring.keySet().forEach(hash -> {
            System.out.println(hash + "   ->   " + ch.ring.get(hash));
        });

        System.out.println("192.168.1.1 request goes to server: " + ch.getServer("192.168.1.1"));
        System.out.println("192.168.1.2 : request goes to server : " + ch.getServer("192.168.1.2"));
    }
}