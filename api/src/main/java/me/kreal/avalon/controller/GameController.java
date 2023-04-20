package me.kreal.avalon.controller;

import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.service.GameLogicService;
import me.kreal.avalon.service.GameService;
import me.kreal.avalon.util.avalon.GameModeFactory;
import me.kreal.avalon.util.enums.GameModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final GameLogicService gameLogicService;

    @Autowired
    public GameController(GameService gameService, GameLogicService gameLogicService) {
        this.gameService = gameService;
        this.gameLogicService = gameLogicService;
    }

    @PostMapping("/new")
    public ResponseEntity<DataResponse> handleNewGameRequest(@RequestParam int size, @RequestParam GameModeType gameMode, @RequestParam int roomNum, @AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.createNewGame(size, gameMode, roomNum);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        response = this.gameLogicService.authUserJoinGameWithGameNum(userDetail, roomNum);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/leave")
    public ResponseEntity<DataResponse> handleLeaveGameRequest(@AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.authUserLeaveGame(userDetail);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/join/{roomNum}")
    public ResponseEntity<DataResponse> handleJoinGameRequestWithGameNum(@PathVariable int roomNum, @AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.authUserJoinGameWithGameNum(userDetail, roomNum);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<DataResponse> handleFindGameByIdRequest(@PathVariable Long gameId, @AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.authUserFindGameWithId(userDetail, gameId);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{gameId}/start")
    public ResponseEntity<DataResponse> handleStartGameRequestWithToken(@PathVariable Long gameId, @AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.authUserStartGameWithId(userDetail, gameId);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }


}
