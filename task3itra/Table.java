package task3itra;

public class Table {
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
