package com.shedid.game.treasure.controllers;

import com.shedid.game.treasure.models.GameResult;
import com.shedid.game.treasure.models.Response;
import com.shedid.game.treasure.service.TreasureService;
import com.shedid.game.treasure.models.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
public class TreasureRestController {
    private final GameResult gameResult = new GameResult();
    private final Response response = new Response();
    private final TreasureService service;

    @Autowired
    public TreasureRestController(TreasureService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/start-game")
    @ResponseBody
    public Response startGame() {
        this.service.initBoard();
        response.setStatus(true);
        response.setMessage("Start the Game.");
        gameResult.setBoard(this.service.getBoardMirror());
        gameResult.setNumberOfTurns(this.service.getNumberOfTurns());
        gameResult.setTurns(this.service.getTurns());
        gameResult.setScores(service.getScores());
        gameResult.setTopTen(service.getTopTen());
        response.setGameResult(gameResult);
        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/set-positions", consumes = "application/json", produces = "application/json")
    public Response setPositions(@RequestBody Position position) {
        if (service.getGameOver()) {
            response.setStatus(true);
            response.setMessage("You have '" + (3 - this.service.getTurns()) + "' turns.");
            response.setGameResult(this.service.newTurn());
            return response;
        }
        if (!service.isValid(position.getRow(), position.getColumn())) {
            response.setStatus(false);
            response.setMessage("Please Enter row and column in correct range.");
            return response;
        }
        if (service.isUsedBefore(position.getRow(), position.getColumn())) {
            response.setStatus(false);
            response.setMessage("This is a open position please enter other column and row.");
            return response;
        }
        gameResult.setBoard(this.service.playGame(position.getRow(), position.getColumn()));
        gameResult.setNumberOfTurns(this.service.getNumberOfTurns());
        gameResult.setTurns(this.service.getTurns());
        gameResult.setScores(service.getScores());
        gameResult.setTopTen(service.getTopTen());

        response.setStatus(true);
        response.setMessage("You have '" + (3 - this.service.getTurns()) + "' turns.");
        response.setGameResult(gameResult);
        return response;
    }
}
