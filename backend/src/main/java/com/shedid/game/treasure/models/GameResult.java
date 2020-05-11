package com.shedid.game.treasure.models;

import java.util.ArrayList;
import java.util.List;

public class GameResult {
    private int moves;
    private int turns;
    private int[][] board;
    private int scores;
    private List<Integer> topTen = new ArrayList<>();

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
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
