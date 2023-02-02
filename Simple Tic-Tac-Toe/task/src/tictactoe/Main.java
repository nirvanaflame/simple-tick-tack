package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String FIELD = "| %s %s %s |%n";

    public static void main(String[] args) {
        var ground = new char[3][3];

        fillEmpty(ground);
        printGround(ground);

        var player1 = 'X';
        var player2 = 'O';
        int turn = 0;
        char move;
        while (true) {
            if (turn++ % 2 == 0) {
                move = player1;
            } else {
                move = player2;
            }
            makeMove(ground, move);
            var winners = checkWinner(ground);

            if (!winners.isEmpty()) {
                printGround(ground);
                printStatus(ground, winners);
                break;
            } else {
                printGround(ground);
            }
        }
    }

    private static void printStatus(char[][] ground, List<Character> winners) {
        if (!winners.isEmpty()) {
            System.out.printf("%s wins", winners.get(0));
            return;
        }
        if (!hasEmpty(ground)) {
            System.out.println("Draw");
        }
    }

    private static void makeMove(char[][] ground, char move) {
        var scan = new Scanner(System.in);
        while (true) {
            try {
                var i = scan.nextInt() - 1;
                var j = scan.nextInt() - 1;

                if (i >= 3 || j >= 3) {
                    throw new IndexOutOfBoundsException();
                }

                char position = ground[i][j];
                if (position == ' ') {
                    ground[i][j] = move;
                } else {
                    throw new IllegalStateException();
                }

                break;
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Coordinates should be from 1 to 3!");
            } catch (IllegalStateException e) {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
    }

    private static List<Character> checkWinner(char[][] values) {
        var winner = new ArrayList<Character>();

        winner.addAll(checkRowAndColumn(values));
        winner.addAll(checkDiagonals(values));

        return winner;
    }

    private static List<Character> checkDiagonals(char[][] values) {
        var winner = new ArrayList<Character>();

        int j = 0;
        var rightToLeft = new char[3];
        for (int i = 0; i < 3; i++) {
            rightToLeft[i] = values[i][j++];
        }
        addWinner(winner, rightToLeft);
        var leftToRight = new char[3];
        for (int i = 0; i < 3; i++) {
            leftToRight[i] = values[i][--j];
        }
        addWinner(winner, leftToRight);

        return winner;
    }

    private static void fillEmpty(char[][] ground) {
        for (int i = 0; i < ground.length; i++) {
            for (int j = 0; j < ground.length; j++) {
                if (ground[i][j] == 0) {
                    ground[i][j] = ' ';
                }
            }
        }
    }

    private static List<Character> checkRowAndColumn(char[][] values) {
        var win = new ArrayList<Character>();
        for (int i = 0; i < 3; i++) {
            addWinner(win, values[i]);
        }

        for (int j = 0; j < 3; j++) {
            var col = new char[3];
            for (int i = 0; i < 3; i++) {
                col[i] = values[i][j];
            }
            addWinner(win, col);
        }

        return win;
    }

    private static void addWinner(ArrayList<Character> win, char[] col) {
        if (isWin(col)) {
            win.add(col[0]);
        }
    }

    private static boolean isWin(char[] in) {
        if (in[0] == ' ' || in[1] == ' ' || in[2] == ' ') return false;
        return in[0] == in[1] && in[1] == in[2];
    }

    private static boolean hasEmpty(char[][] ground) {
        for (char[] chars : ground) {
            for (char aChar : chars) {
                if (aChar == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    private static void printGround(char[][] ground) {
        System.out.println("---------");
        System.out.printf(FIELD, ground[0][0], ground[0][1], ground[0][2]);
        System.out.printf(FIELD, ground[1][0], ground[1][1], ground[1][2]);
        System.out.printf(FIELD, ground[2][0], ground[2][1], ground[2][2]);
        System.out.println("---------");
    }
}
