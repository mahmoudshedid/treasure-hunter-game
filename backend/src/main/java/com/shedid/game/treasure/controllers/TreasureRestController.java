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
        response.setStatus(true);
        response.setMessage("Start the Game.");
        response.setGameResult(this.service.startGame());
        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/set-positions", consumes = "application/json", produces = "application/json")
    public Response setPositions(@RequestBody Position position) {
        if (service.getTurnOver()) {
            response.setStatus(true);
            response.setMessage("You have '" + (3 - this.service.getMoves()) + "' Moves.");
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
        response.setGameResult(this.service.playGame(position.getRow(), position.getColumn()));
        response.setStatus(true);
        response.setMessage("You have '" + (3 - this.service.getMoves()) + "' Moves.");
        return response;
    }
}
