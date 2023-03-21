package me.kreal.avalon.controller;

import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.service.GameService;
import me.kreal.avalon.util.enums.GameModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/new")
    public ResponseEntity<DataResponse> newGameRequest(@RequestParam int size, @RequestParam GameModeType gameMode, @RequestParam int roomNum) {
        return ResponseEntity.ok(this.gameService.createNewGame(size, gameMode, roomNum));
    }

    @GetMapping("/join/{gameNum}")
    public ResponseEntity<DataResponse> handleJoinGameRequestWithGameNum(@PathVariable int gameNum, @AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameService.handleJoinGameRequest(gameNum, userDetail);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/join")
    public ResponseEntity<DataResponse> handleJoinGameRequestWithToken(@AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameService.joinGameByToken(userDetail);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<DataResponse> handleFindGameByIdRequest(@PathVariable Long gameId, @AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameService.findGameByIdAndUsername(gameId, userDetail.getUsername());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/start")
    public ResponseEntity<DataResponse> handleStartGameRequestWithToken(@AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameService.startGameByToken(userDetail);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<DataResponse> handleGetGameInfoRequestWithToken(@AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameService.getGameInfoByToken(userDetail);

        return ResponseEntity.ok(response);
    }

}
