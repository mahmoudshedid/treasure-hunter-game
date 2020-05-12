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
    private int move = 0;
    private int turns = 1;
    private int found = 0;
    private boolean turnOver = false;
    private final GameResult gameResult = new GameResult();

    /**
     * For Start new game
     * @return GameResult
     */
    public GameResult startGame() {
        this.turnOver = false;
        this.topTen.clear();
        this.initBoard();
        this.gameResult.setBoard(this.boardMirror);
        this.gameResult.setTurns(this.turns);
        this.gameResult.setMoves(this.move);
        this.gameResult.setScores(this.scores);
        this.gameResult.setTopTen(this.getTopTen());

        return this.gameResult;
    }

    /**
     * Get same data if refresh browser or close and open.
     * @return GameResult
     */
    public GameResult currentTurn() {
        if (this.board == null) return this.startGame();
        this.gameResult.setBoard(this.boardMirror);
        this.gameResult.setTurns(this.turns);
        this.gameResult.setMoves(this.move);
        this.gameResult.setScores(this.scores);
        this.gameResult.setTopTen(this.getTopTen());
        return this.gameResult;
    }

    /**
     * Start play game by giving row and column.
     * @param row int
     * @param column int
     * @return GameResult
     */
    public GameResult playGame(int row, int column) {
        if (this.move != 3) {
            this.move++;
            this.boardMirror[row][column] = this.board[row][column];
            if (this.board[row][column] == -1) this.found++;
            if (this.found == 3) this.turnOver();
            if (this.move == 3) {
                this.turns++;
                this.move = 0;
            }
        }
        this.gameResult.setBoard(this.boardMirror);
        this.gameResult.setTurns(this.turns);
        this.gameResult.setMoves(this.move);
        this.gameResult.setScores(this.scores);
        this.gameResult.setTopTen(this.getTopTen());

        return this.gameResult;
    }

    /**
     * For start new turn
     * @return GameResult
     */
    public GameResult newTurn() {
        for (int[] array : this.boardMirror) {
            Arrays.fill(array, 0);
        }
        this.move = 0;
        this.gameResult.setBoard(this.boardMirror);
        this.gameResult.setTurns(this.turns);
        this.gameResult.setMoves(this.move);
        this.gameResult.setScores(this.scores);
        this.gameResult.setTopTen(this.getTopTen());
        this.turnOver = false;

        return this.gameResult;
    }

    /**
     * Check if given row and column is valed.
     * @param row int
     * @param column int
     * @return boolean
     */
    public boolean isValid(int row, int column) {
        return ((row >= 0 && row < this.row) && (column >= 0 && column < this.row));
    }

    /**
     * Check if position is used before.
     * @param row int
     * @param column int
     * @return boolean
     */
    public boolean isUsedBefore(int row, int column) {
        return (this.boardMirror[row][column] > 0 || this.boardMirror[row][column] == -1);
    }

    /**
     * For get moves in game
     * @return int
     */
    public int getMoves() {
        return this.move;
    }

    /**
     * Get is turn over.
     * @return boolean
     */
    public boolean getTurnOver() {
        return this.turnOver;
    }

    /**
     * Init the game board
     */
    private void initBoard() {
        this.found = 0;
        this.turns = 0;
        this.move = 0;

        this.board = new int[row][column];
        for (int[] array : this.board) {
            Arrays.fill(array, 1);
        }

        if (!this.turnOver) {
            for (int[] array : this.boardMirror) {
                Arrays.fill(array, 0);
            }
        }
        this.setTreasures();
        System.out.println("[⊕] Start the Game.");
        this.displayBoard();
    }

    /**
     * Make turn is over to start new turn.
     */
    private void turnOver() {
        this.topTen.add(this.scores - this.turns);
        this.found = 0;
        this.turns = 0;
        this.move = 0;
        this.turnOver = true;
        this.initBoard();
    }

    /**
     * Create 3 treasures in game
     */
    private void setTreasures() {
        int numberOfPlaces = this.column;
        int limit = 3;
        int mines = 0;
        while (numberOfPlaces > 0) {
            int row = randomNumber();
            int column = randomNumber();
            if (this.board[row][column] != -1) {
                this.board[row][column] = -1;
                this.setClosestProximity(row, column, true);
                mines++;
                numberOfPlaces--;
                if (mines == limit) break;
            }
        }
    }

    /**
     * Create all closes proximity cells fot treasures. It has a recursive function
     * @param row int
     * @param column int
     * @param primary boolean
     */
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

    /**
     * Get top ten as sorted and with limit and without duplicates value.
     * @return List
     */
    private List<Integer> getTopTen() {
        List<Integer> newTopTen = this.removeDuplicates(this.topTen);
        return newTopTen.stream().sorted(Collections.reverseOrder()).limit(10).collect(Collectors.toList());
    }

    /**
     * Get random number for treasures in board.
     * @return int
     */
    private int randomNumber() {
        Random random = new Random();
        int number;
        do number = random.nextInt(this.column);
        while (number > this.column);
        return number;
    }

    /**
     * Function to remove duplicates from an ArrayList
     * @param list List
     * @return List
     */
    private List<Integer> removeDuplicates(List<Integer> list) {
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

    /**
     * Display real bored "Please don't tell user (^_^)" this function is exist.
     */
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
