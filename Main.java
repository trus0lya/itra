package task3itra;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static void main(String[] args) throws Exception  {
        List<String> l = new ArrayList<>();
        Collections.addAll(l, args);
        Rules rules = null;
        try {
            rules = new Rules(l);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        String key = GeneratingKeyHMAC.generateKey(32);
        Random random = new Random();
        int k = random.nextInt(l.size()) + 1;
        String HMAC = GeneratingKeyHMAC.hmacWithJava("HmacSHA3-256",l.get(k - 1), key);
        System.out.println("HMAC: " + HMAC);
        System.out.println("Available moves:");
        for(int i = 0; i < l.size(); ++i) {
            System.out.println(i + 1 + " - " + l.get(i));
        }
        System.out.println("0 - exit");
        System.out.println("? - help");
        System.out.println("Enter your more: ");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        if(s.equals("0"))
            return;
        if(s.equals("?")) {
            Table table = new Table(rules);
            System.out.println("The left column shows your possible moves, and the top row shows the computer's moves." +
                    "\nThe intersection of rows and columns shows the results of the game." +
                    "\nGood luck!");
            table.showTable();
            return;
        }
        if(Integer.parseInt(s) > l.size()) {
            System.out.println(ANSI_RED + "Impossible move. Try again!" + ANSI_RESET);
            return;
        }
        System.out.println("Your move: " + l.get(Integer.parseInt(s) - 1));
        System.out.println("Computer's move: " + l.get(k - 1));
        if(Integer.parseInt(s) == k) {
            System.out.println("Tie!");
        } else {
            String st = rules.determineWinner(l.get(Integer.parseInt(s) - 1), l.get(k - 1)).equals("Win") ?
                    "You are win!" : "You are lose:(";
            System.out.println(st);
        }
        System.out.println("HMAC key: " + key);
    }
}

class Rules {

    private final List<String> combinations;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public List<String> getCombinations() {
        return combinations;
    }

    public Rules(List<String> combinations) throws Exception {
        if(combinations.size() < 3) {
            throw new Exception(ANSI_RED + "Not enough combinations! You need at least 3 combinations.\n" +
                    "For example: rock paper scissors." + ANSI_RESET);
        }
        if (combinations.size() % 2 == 0) {
            throw new Exception(ANSI_RED + "The number of combinations must be odd.\n" +
                    "For example: rock paper scissors lizard Spock." + ANSI_RESET);
        }
        Set<String> set = new HashSet<>(combinations);
        if (set.size() != combinations.size()) {
            throw new Exception(ANSI_RED + "Two combinations can't be the same. You need at least 3 DIFFERENT combinations.\n" +
                    "For example rock paper scissors." + ANSI_RESET);
        }
        this.combinations = combinations;
    }
    public String determineWinner(String firstPlayer, String secondPlayer) {
        int firstPlayerIndex = combinations.indexOf(firstPlayer);
        int secondPlayerIndex = combinations.indexOf(secondPlayer);
        if (firstPlayerIndex == secondPlayerIndex) {
            return "Draw";
        }
        int cnt = 0;
        while (firstPlayerIndex != secondPlayerIndex) {
            if (firstPlayerIndex == combinations.size() - 1) {
                firstPlayerIndex = 0;
            } else {
                firstPlayerIndex++;
            }
            cnt++;
        }
        return cnt > combinations.size() / 2 ? "Win" : "Lose";
    }
}

class Table {
    private final Rules rules;

    public Table(Rules rules) {
        this.rules = rules;
    }

    public void showTable() throws Exception {
        int n = rules.getCombinations().size();
        String[][] arr = new String[n + 1][n + 1];
        for(int i = 1; i < n + 1; ++i) {
            arr[0][i] = rules.getCombinations().get(i - 1);
        }
        for(int j = 1; j < n + 1; ++j) {
            arr[j][0] = rules.getCombinations().get(j - 1);
        }

        for(int i = 1; i < n + 1; ++i) {
            for(int j = 1; j < n + 1; ++j) {
                arr[i][j] = rules.determineWinner(arr[i][0], arr[0][j]);
            }
        }
        arr[0][0] = "";
        write(arr);
    }

    public void write(String[][] arr) {
        int max = 0;
        for (String[] strings : arr) {
            max = Math.max(max, strings.length);
        }
        int[] maxsize = new int[max];
        for (String[] strings : arr) {
            for (int j = 0; j < strings.length; ++j) {
                int size = strings[j].length();
                if (maxsize[j] < size) {
                    maxsize[j] = size;
                }
            }
        }
        int lw = 0;
        for (int k : maxsize) {
            lw += k;
        }
        lw += maxsize.length * 2 + 1;
        for(int i = 0; i < lw; ++i) {
            System.out.print('-');
        }
        System.out.println();
        for (String[] strings : arr) {
            System.out.print("|");
            for (int j = 0; j < strings.length; ++j) {
                System.out.printf(" %" + maxsize[j] + "s", strings[j]);
                System.out.print("|");
            }
            System.out.println();
        }
        for(int i = 0; i < lw; ++i) {
            System.out.print('-');
        }
    }
}

class GeneratingKeyHMAC {
    public static String generateKey(int n) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }
    public static String hmacWithJava(String algorithm, String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        return bytesToHex(mac.doFinal(data.getBytes()));
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
