package task3itra;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> l = List.of("rock", "paper", "scissors", "lizard", "Spock", "veil", "hook");
        Rules rules = new Rules(l);
        String key = GeneratingKeyHMAC.generateKey(32);
        Random random = new Random();
        int k = random.nextInt(l.size());
        String HMAC = GeneratingKeyHMAC.hmacWithJava("HmacSHA256",l.get(k), key);
        System.out.println("HMAC: " + HMAC);
        System.out.println("Available moves:");
        for(int i = 0; i < l.size(); ++i) {
            System.out.println(i + 1 + "-" + " " + l.get(i));
        }
        System.out.println("0 - exit");
        System.out.println("? - help");
        System.out.println("Enter your more: ");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        if(Integer.parseInt(s) != l.size()) {
            throw new Exception("Impossible move. Try again");
        }
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
        System.out.println("Your move: " + l.get(Integer.parseInt(s) - 1));
        System.out.println("Computer move: " + l.get(k - 1));
        if(Integer.parseInt(s) == k) {
            System.out.println("Tie!");
        } else {
            String st = rules.determineWinner(l.get(Integer.parseInt(s) - 1), l.get(k - 1)).equals(l.get(Integer.parseInt(s) - 1)) ?
                    "You are win!" : "You are lose:(";
            System.out.println(st);
        }
        System.out.println("HMAC key: " + key);
    }
}