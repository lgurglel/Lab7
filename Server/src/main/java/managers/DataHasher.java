package managers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DataHasher {
    // TODO: 28.11.2021
    public static String hash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(data.getBytes());
            BigInteger integers = new BigInteger(1, bytes);
            String newData = integers.toString(16);
            while (newData.length() < 32) {
                newData = "0" + newData;
            }
            return newData;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Алгоритм хеширования не найден!");
            throw new IllegalStateException(e);
        }
    }
}
