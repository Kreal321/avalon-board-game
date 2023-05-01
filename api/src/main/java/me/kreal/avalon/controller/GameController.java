package me.kreal.avalon.controller;

import me.kreal.avalon.dto.GameDTO;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.dto.response.GameResponse;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.service.GameLogicService;
import me.kreal.avalon.util.enums.GameModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/game")
public class GameController {

    private final GameLogicService gameLogicService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GameController(GameLogicService gameLogicService, SimpMessagingTemplate messagingTemplate) {
        this.gameLogicService = gameLogicService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/records")
    public ResponseEntity<DataResponse> handleGameRecordsRequest(@AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.findRecordsByUser(userDetail);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
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

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/leave")
    public ResponseEntity<DataResponse> handleLeaveGameRequest(@AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.authUserLeaveGame(userDetail);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/join/{roomNum}")
    public ResponseEntity<DataResponse> handleJoinGameRequestWithGameNum(@PathVariable int roomNum, @AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.authUserJoinGameWithGameNum(userDetail, roomNum);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        messagingTemplate.convertAndSend("/topic/game/" + ((GameResponse) response.getData()).getGameId(), "New user joined");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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

        messagingTemplate.convertAndSend("/topic/game/" + gameId, "Game started");

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{gameId}/assassin/flop")
    public ResponseEntity<DataResponse> handleAssassinFlopRequestWithToken(@PathVariable Long gameId, @AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.authUserAssassinFloppedWithGameId(userDetail, gameId);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        messagingTemplate.convertAndSend("/topic/game/" + gameId, "Assassin flopped");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{gameId}/assassinate/{targetId}")
    public ResponseEntity<DataResponse> handleAssassinAssassinateRequestWithToken(@PathVariable Long gameId, @PathVariable Long targetId, @AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.authUserAssassinAssassinateWithGameIdAndPlayerId(userDetail, gameId, targetId);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        messagingTemplate.convertAndSend("/topic/game/" + gameId, "Assassin assassinated");

        return ResponseEntity.ok(response);
    }


}
