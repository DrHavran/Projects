import Level.Level;

import javax.swing.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        Logic logic = new Logic();

        JFrame frame = new JFrame();
        frame.setSize(1, 1);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP    -> logic.move("up");
                    case KeyEvent.VK_DOWN  -> logic.move("down");
                    case KeyEvent.VK_LEFT  -> logic.move("left");
                    case KeyEvent.VK_RIGHT -> logic.move("right");
                    case KeyEvent.VK_Q     -> System.exit(0);
                }
                clearScreen();
                printBoard(logic.getLevel());
                if (logic.isSolved()) {
                    System.out.println("Solved!");
                    System.exit(0);
                }
            }
        });

        clearScreen();
        printBoard(logic.getLevel());
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void printBoard(Level level) {
        int[][] walls  = level.getWalls();
        int[][] goals  = level.getGoals();
        int[][] player = level.getPlayerGrid();
        int[][] box    = level.getBoxGrid();

        int height = walls.length;
        int width  = walls[0].length;

        for (int y = 0; y < height; y++) {
            StringBuilder row = new StringBuilder();
            for (int x = 0; x < width; x++) {
                if (box[y][x] == 1 && goals[y][x] == 1)      row.append('*');
                else if (box[y][x] == 1)                     row.append('B');
                else if (player[y][x] == 1 && goals[y][x] == 1) row.append('+');
                else if (player[y][x] == 1)                  row.append('P');
                else if (goals[y][x] == 1)                   row.append('.');
                else if (walls[y][x] == 1)                   row.append('#');
                else                                         row.append(' ');
            }
            System.out.println(row);
        }
    }
}