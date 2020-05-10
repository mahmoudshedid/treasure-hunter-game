package com.shedid.game.treasure.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service("treasureService")
public class TreasureService {
    private final int row = 5;
    private final int column = 5;
    private final int[][] boardMirror = new int[row][column];
    private final List<Integer> scores = new ArrayList<>();
    private int[][] board;
    private int turns = 0;
    private int numberOfTurns = 1;
    private int found = 0;

    public void initBoard() {
        this.turns = 0;
        this.board = new int[row][column];
        for (int[] array : this.board) {
            Arrays.fill(array, 1);
        }

        for (int[] array : this.boardMirror) {
            Arrays.fill(array, 0);
        }

        int numberOfPlaces = this.column;
        int limit = 3;
        int mines = 0;
        while (numberOfPlaces > 0) {
            int row = randomNumber();
            int column = randomNumber();
            if (board[row][column] != -1) {
                board[row][column] = -1;
                setClosestProximity(row, column, true);
                mines++;
                numberOfPlaces--;
                if (mines == limit) break;
            }
        }
        System.out.println("[⊕] Start the Game.");
        this.displayBoard();
    }

    public int[][] playGame(int row, int column) {
        if (this.turns != 3) {
            this.turns++;
            this.boardMirror[row][column] = this.board[row][column];
            if (this.board[row][column] == -1) {
                this.found++;
            }
        } else {
            this.scores.add(this.found * 4);
            this.numberOfTurns++;
            this.initBoard();
        }

        return this.boardMirror;
    }

    public boolean isValid(int row, int column) {
        return ((row >= 0 && row < this.row) && (column >= 0 && column < this.row));
    }

    public boolean isUsedBefore(int row, int column) {
        return (this.boardMirror[row][column] > 0 || this.boardMirror[row][column] == -1);
    }

    public int getNumberOfTurns() {
        return this.numberOfTurns;
    }

    public int getTurns() {
        return this.turns;
    }

    public List<Integer> getScores() {
        return this.scores;
    }

    public int[][] getBoardMirror() {
        return this.boardMirror;
    }

    private void setClosestProximity(int row, int column, boolean primary) {
        int numberSet = primary ? 3 : 2;
        if (row >= 0 && row < 4 && this.board[row + 1][column] != -1 && this.board[row + 1][column] != 3) {
            this.board[row + 1][column] = numberSet;
            if (primary) setClosestProximity(row + 1, column, false);
        }
        if (row <= 4 && row > 0 && this.board[row - 1][column] != -1 && this.board[row - 1][column] != 3) {
            this.board[row - 1][column] = numberSet;
            if (primary) setClosestProximity(row - 1, column, false);
        }

        if (column >= 0 && column < 4 && this.board[row][column + 1] != -1 && this.board[row][column + 1] != 3) {
            this.board[row][column + 1] = numberSet;
            if (primary) setClosestProximity(row, column + 1, false);
        }
        if (column <= 4 && column > 0 && this.board[row][column - 1] != -1 && this.board[row][column - 1] != 3) {
            this.board[row][column - 1] = numberSet;
            if (primary) setClosestProximity(row, column - 1, false);
        }
    }

    private int randomNumber() {
        Random random = new Random();
        int number;
        do number = random.nextInt(this.column);
        while (number > this.column);
        return number;
    }

    private void displayBoard() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                String outPut = this.board[i][j] == -1 ? " " : "  ";
                System.out.print(outPut + this.board[i][j]);
            }
            System.out.println(" ");
        }
        System.out.println("===================");
    }
}
