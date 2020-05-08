package com.shedid.game.treasure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TreasureRestController {

    @RequestMapping(value="/start-game", method=RequestMethod.GET)
    @ResponseBody
    public String startGame() {
        return "{\"status\":\"" + true + "\"}";
    }
}
