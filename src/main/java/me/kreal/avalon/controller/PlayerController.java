package me.kreal.avalon.controller;

import me.kreal.avalon.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


//    @PostMapping("/new")
//    public ResponseEntity<DataResponse> newPlayerRequest(@RequestBody PlayerNewRequest request) {
//
//        return ResponseEntity.ok(this.playerService.createNewPlayer(request));
//
//    }
}
