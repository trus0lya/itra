package task3itra;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RulesTest {

    List<String> l = List.of("rock", "paper", "scissors", "lizard", "Spock");
    Rules rules = new Rules(l);

    public RulesTest() throws Exception {
    }

    @Test
    public void determineWinner() throws Exception {
        assertTrue(rules.determineWinner("rock", "rock").equals("tie"));
        assertTrue(rules.determineWinner("paper", "paper").equals("tie"));
        assertTrue(rules.determineWinner("scissors", "scissors").equals("tie"));
        assertTrue(rules.determineWinner("lizard", "lizard").equals("tie"));
        assertTrue(rules.determineWinner("Spock", "Spock").equals("tie"));

        assertTrue(rules.determineWinner("rock", "paper").equals("paper"));
        assertTrue(rules.determineWinner("rock", "scissors").equals("scissors"));
        assertTrue(rules.determineWinner("rock", "lizard").equals("rock"));
        assertTrue(rules.determineWinner("rock", "Spock").equals("rock"));


        assertTrue(rules.determineWinner("paper", "scissors").equals("scissors"));
        assertTrue(rules.determineWinner("paper", "lizard").equals("lizard"));
        assertTrue(rules.determineWinner("paper", "Spock").equals("paper"));

        assertTrue(rules.determineWinner("scissors", "lizard").equals("lizard"));
        assertTrue(rules.determineWinner("scissors", "Spock").equals("Spock"));

        assertTrue(rules.determineWinner("lizard", "Spock").equals("Spock"));
    }
}