package com.shedid.game.treasure.models;

import java.util.ArrayList;
import java.util.List;

public class GameResult {
    private int turns;
    private int numberOfTurns;
    private int[][] board;
    private int scores;
    private List<Integer> topTen = new ArrayList<>();

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public List<Integer> getTopTen() {
        return topTen;
    }

    public void setTopTen(List<Integer> topTen) {
        this.topTen = topTen;
    }
}
