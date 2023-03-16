package task3itra;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rules {

    private final List<String> combinations;

    public List<String> getCombinations() {
        return combinations;
    }

    public Rules(List<String> combinations) throws Exception {
        if(combinations.size() < 3) {
            throw new Exception("There is no combinations!");
        }
        if (combinations.size() % 2 == 0) {
            throw new Exception("The number of combinations must be odd");
        }
        Set<String> set = new HashSet<>(combinations);
        if (set.size() != combinations.size()) {
            throw new Exception("Two combinations can't be the same");
        }
        this.combinations = combinations;
    }
    public String determineWinner(String firstPlayer, String secondPlayer) throws Exception{
        if (!combinations.contains(firstPlayer)) {
            throw new Exception("There is no such combination ;( . Try again!");
        }
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
