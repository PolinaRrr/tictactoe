package ru.geekbrains.tictactoe;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    static Random random = new Random();
    private static char[][] map; //матрица игры
    private static final int SIZE = 3; //разерность поля
    private static final char DOT_EMPTY = '·';
    private static final char DOT_X = 'Χ';
    private static final char DOT_O = 'О';
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initMap();
        printMap();

        while (true) {
            humanTurn();
            if (isEndGame(DOT_X)) {
                break;
            }
            computerTurn();
            if (isEndGame(DOT_O)) {
                break;
            }
        }
        System.out.println("Игра закончена!");

    }

    /**
     * метод подготовки игрового поля
     */
    private static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    /**
     * метод вывода игрового поля
     */
    private static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        //надо сократить
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

    }



    /**
     * метод хода человека
     */
    private static void humanTurn() {
        int x, y;

        do {
            System.out.println("Введите координаты ячейки");
            y = scanner.nextInt() - 1;
            x = scanner.nextInt() - 1;
        } while (!isCellValid(x, y));
        map[y][x] = DOT_X;
    }

    /**
     * метод валидации вводимых координат
     *
     * @param x - по горизонтали
     * @param y - по вертикали
     * @return boolean - признак валидности
     */
    private static boolean isCellValid(int x, int y) {
        //проверка координат
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return false;
        }
        //проверка заполненности
        if (map[y][x] != DOT_EMPTY) {
            return false;
        }
        return true;
    }

    /**
     * метод проверки на завершение игра
     *
     * @param playerSymbol символ текущего игрока
     *                     признак завершения
     */
    private static boolean isEndGame(char playerSymbol) {

        printMap();
        if (checkWin(playerSymbol)) {
            System.out.println("Выиграли " + playerSymbol);
            return true;
        }
        if (isMapFull()) {
            System.out.println("Ничья");
            return true;
        }
        return false;
    }

    /**
     * проверка на полную заполненность поля
     *
     * @return boolean признак оптимальности
     */
    private static boolean isMapFull() {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWin(char playerSymbol) {
        // проверяем победу по горизонтали
        for (int i = 0; i < SIZE; i++) {
            int sum = 0;
            for (int j = 0; j < SIZE; j++) {
                if (playerSymbol == map[i][j]) {
                    sum++;
                }
            }
            if (sum == SIZE) {
                return true;
            }
        }
        // проверяем победу по вертикали
        for (int i = 0; i < SIZE; i++) {
            int sum = 0;
            for (int j = 0; j < SIZE; j++) {
                if (playerSymbol == map[j][i]) {
                    sum++;
                }
            }
            if (sum == SIZE) {
                return true;
            }
        }
        // проверяем победу по диагонали слева направо
        int sum = 0;
        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] == playerSymbol) {
                sum++;
            }
        }
        if (sum == SIZE) {
            return true;
        }
        // проверяем победу по диагонали слева направо
        sum = 0;
        for (int i = 0; i < SIZE; i++) {
            if (map[i][SIZE-1-i] == playerSymbol) {
                sum++;
            }
        }
        if (sum == SIZE) {
            return true;
        }
        return false;
    }

    /**
     * метод хода компьютера
     */
    private static void computerTurn() {
        int x = -1;
        int y = -1;

        int maxScoreX = -1;
        int maxScoreY = -1;
        int maxScore = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int fieldScore = 0;

                if (map[i][j] == DOT_EMPTY) {
                    // ход влево вверх
                    if (i - 1 >= 0 && j - 1 >= 0 && map[i - 1][j - 1] == DOT_O) {
                        fieldScore++;
                    }
                    //ход вверх
                    if (i - 1 >= 0 && map[i - 1][j] == DOT_O) {
                        fieldScore++;
                    }
                    //ход вправо вверх
                    if (i - 1 >= 0 && j + 1 < SIZE && map[i - 1][j + 1] == DOT_O) {
                        fieldScore++;
                    }
                    //ход вправо вниз
                    if (i + 1 < SIZE && j + 1 < SIZE && map[i + 1][j + 1] == DOT_O) {
                        fieldScore++;
                    }
                    //ход вправо
                    if (j + 1 < SIZE && map[i][j + 1] == DOT_O) {
                        fieldScore++;
                    }
                    //ход вниз
                    if (i + 1 < SIZE && map[i + 1][j] == DOT_O) {
                        fieldScore++;
                    }
                    //ход влево вниз
                    if (i + 1 < SIZE && j - 1 >= 0 && map[i + 1][j - 1] == DOT_O) {
                        fieldScore++;
                    }
                    //ход влево
                    if (j - 1 >= 0 && map[i][j - 1] == DOT_O) {
                        fieldScore++;
                    }
                    // ход влево вверх от противника
                    if (i - 1 >= 0 && j - 1 >= 0 && map[i - 1][j - 1] == DOT_X) {
                        fieldScore += 2;
                    }
                    //ход вверх  от противника
                    if (i - 1 >= 0 && map[i - 1][j] == DOT_X) {
                        fieldScore += 2;
                    }
                    //ход вправо вверх  от противника
                    if (i - 1 >= 0 && j + 1 < SIZE && map[i - 1][j + 1] == DOT_X) {
                        fieldScore += 2;
                    }
                    //ход вправо вниз  от противника
                    if (i + 1 < SIZE && j + 1 < SIZE && map[i + 1][j + 1] == DOT_X) {
                        fieldScore += 2;
                    }
                    //ход вправо  от противника
                    if (j + 1 < SIZE && map[i][j + 1] == DOT_X) {
                        fieldScore += 2;
                    }
                    //ход вниз  от противника
                    if (i + 1 < SIZE && map[i + 1][j] == DOT_X) {
                        fieldScore += 2;
                    }
                    //ход влево вниз  от противника
                    if (i + 1 < SIZE && j - 1 >= 0 && map[i + 1][j - 1] == DOT_X) {
                        fieldScore += 2;
                    }
                    //ход влево  от противника
                    if (j - 1 >= 0 && map[i][j - 1] == DOT_X) {
                        fieldScore += 2;
                    }
                    System.out.println(fieldScore);
                }

                if (fieldScore > maxScore) {
                    maxScore = fieldScore;
                    maxScoreX = j;
                    maxScoreY = i;

                    System.out.println(maxScoreX);
                    System.out.println(maxScoreY);
                }
            }
            if (maxScoreX != -1) {
                x = maxScoreX;
                y = maxScoreY;
                System.out.println(maxScoreX);
                System.out.println(maxScoreY);
            }
        }
        map[y][x] = DOT_O;
    }
}
