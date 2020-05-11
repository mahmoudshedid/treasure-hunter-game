package com.shedid.game.treasure.service;

import com.shedid.game.treasure.models.GameResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("treasureService")
public class TreasureService {
    private final int row = 5;
    private final int column = 5;
    private final int[][] boardMirror = new int[row][column];
    private final int scores = 9;
    private final List<Integer> topTen = new ArrayList<>();
    private int[][] board;
    private int turns = 0;
    private int numberOfTurns = 1;
    private int found = 0;
    private boolean gameOver = false;
    private GameResult gameResult = new GameResult();

    public void initBoard() {
        this.found = 0;
        this.numberOfTurns = 0;
        this.turns = 0;

        this.board = new int[row][column];
        for (int[] array : this.board) {
            Arrays.fill(array, 1);
        }

        if (!this.gameOver) {
            for (int[] array : this.boardMirror) {
                Arrays.fill(array, 0);
            }
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
            this.boardMirror[row][column] = this.board[row][column];
            if (this.board[row][column] == -1) this.found++;
            if (this.found == 3) this.gameOver();
            this.turns++;
            if (this.turns == 3) {
                this.numberOfTurns++;
                this.turns = 0;
            }
        }
        return this.boardMirror;
    }

    public GameResult newTurn() {
        for (int[] array : this.boardMirror) {
            Arrays.fill(array, 0);
        }
//        System.out.println(Arrays.deepToString(this.boardMirror));
        this.gameResult.setBoard(this.boardMirror);
        this.gameResult.setNumberOfTurns(this.numberOfTurns);
        this.gameResult.setTurns(this.turns);
        this.gameResult.setScores(this.scores);
        this.gameResult.setTopTen(this.getTopTen());
        this.gameOver = false;

        return this.gameResult;
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

    public int getScores() {
        return this.scores;
    }

    public int[][] getBoardMirror() {
        return this.boardMirror;
    }

    public List<Integer> getTopTen() {
        List<Integer> newTopTen = this.removeDuplicates(this.topTen);
        return newTopTen.stream().sorted(Collections.reverseOrder()).limit(10).collect(Collectors.toList());
    }

    public boolean getGameOver() {
        return this.gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private void gameOver() {
        this.topTen.add(this.scores - this.numberOfTurns);
        this.found = 0;
        this.numberOfTurns = 0;
        this.turns = 0;
        this.gameOver = true;
        this.initBoard();
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

    // Function to remove duplicates from an ArrayList
    public List<Integer> removeDuplicates(List<Integer> list) {
        // Create a new ArrayList
        List<Integer> newList = new ArrayList<>();
        // Traverse through the first list
        for (Integer element : list) {
            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) newList.add(element);
        }
        // return the new list
        return newList;
    }

    private void displayBoard() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                String outPut = this.board[i][j] == -1 ? "" : " ";
                System.out.print("|" + outPut + this.board[i][j]);
            }
            System.out.println(" ");
        }
        System.out.println("===================");
    }
}
