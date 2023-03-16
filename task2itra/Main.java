package task2itra;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    private static byte[] checksum(String filePath, String algorithm) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }

        try (InputStream is = new FileInputStream(filePath);
             DigestInputStream dis = new DigestInputStream(is, md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return md.digest();
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String algorithm = "SHA3-256";
        String filePath;
         List<String> list = new ArrayList<>();
        File dir = new File("C:\\Users\\olgat\\D\\my-beginnings\\solutions\\src\\task2itra\\task2 (1)");
        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);
        for(int i = 0; i < 256; ++i) {
            filePath = lst.get(i).toString();
            byte[] hashInBytes = checksum(filePath, algorithm);
            list.add(bytesToHex(hashInBytes));
        }
       Collections.sort(list);
       for(String s : list) {
            System.out.println(s);
        }
        StringBuilder str = new StringBuilder();
        for (String s : list) {
            str.append(s);
        }
        str.append("trus0lya@yandex.by");
        str = new StringBuilder(str.toString());
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("out.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        printWriter.println(str);
        printWriter.close();
        System.out.println(str);
        filePath = String.format("C:\\Users\\olgat\\D\\my-beginnings\\solutions\\out.txt");
        byte[] hashInBytes = checksum(filePath, algorithm);
        System.out.println(bytesToHex(hashInBytes));
    }
}