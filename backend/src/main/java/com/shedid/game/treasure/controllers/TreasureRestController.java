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
    @PostMapping(value = "/set-full-name", consumes = "application/json", produces = "application/json")
    public Response setFullName(@RequestBody String fullName) {
        this.response.setFullName(fullName);
        if (!this.checkFullName()) return this.response;
        this.response.setStatus(true);
        this.response.setMessage("Start the Game.");
        this.response.setGameResult(this.service.startGame());
        return this.response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/start-game")
    @ResponseBody
    public Response startGame() {
        if (!this.checkFullName()) return this.response;
        this.response.setStatus(true);
        this.response.setMessage("Start the Game.");
        this.response.setGameResult(this.service.startGame());
        return this.response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/current-turn")
    @ResponseBody
    public Response currentTurn() {
        if (!this.checkFullName()) return this.response;
        this.response.setStatus(true);
        this.response.setMessage("Please resume the game.");
        this.response.setGameResult(this.service.currentTurn());
        return this.response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/set-positions", consumes = "application/json", produces = "application/json")
    public Response setPositions(@RequestBody Position position) {
        if (!this.checkFullName()) return this.response;
        if (service.getTurnOver()) {
            this.response.setStatus(true);
            this.response.setMessage("You have '" + (3 - this.service.getMoves()) + "' Moves.");
            this.response.setGameResult(this.service.newTurn());
            return this.response;
        }
        if (!service.isValid(position.getRow(), position.getColumn())) {
            this.response.setStatus(false);
            this.response.setMessage("Please Enter row and column in correct range.");
            return this.response;
        }
        if (service.isUsedBefore(position.getRow(), position.getColumn())) {
            this.response.setStatus(false);
            this.response.setMessage("This is a open position please enter other column and row.");
            return this.response;
        }
        this.response.setGameResult(this.service.playGame(position.getRow(), position.getColumn()));
        this.response.setStatus(true);
        this.response.setMessage("You have '" + (3 - this.service.getMoves()) + "' Moves.");
        return this.response;
    }

    private boolean checkFullName() {
        String regex = "^[a-zA-Z\\s]+$";
        if (this.response.getFullName() == null || !this.response.getFullName().matches(regex)) {
            this.response.setStatus(false);
            this.response.setMessage("Please add a valid full name.");
            return false;
        }
        return true;
    }
}
