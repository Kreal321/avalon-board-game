package me.kreal.avalon.controller;

import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.service.GameLogicService;
import me.kreal.avalon.service.PlayerService;
import me.kreal.avalon.util.enums.CharacterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/player")
public class PlayerController {

    private final GameLogicService gameLogicService;

    @Autowired
    public PlayerController(GameLogicService gameLogicService) {
        this.gameLogicService = gameLogicService;
    }

}
