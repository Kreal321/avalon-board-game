package me.kreal.avalon.controller;

import me.kreal.avalon.dto.request.TeamRequest;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.service.GameLogicService;
import me.kreal.avalon.service.PlayerService;
import me.kreal.avalon.service.RoundService;
import me.kreal.avalon.util.enums.TeamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/game/{gameId}/round/{roundId}")
public class RoundController {

    private final GameLogicService gameLogicService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public RoundController(GameLogicService gameLogicService, SimpMessagingTemplate messagingTemplate) {
        this.gameLogicService = gameLogicService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/team/new")
    public ResponseEntity<DataResponse> handleNewTeamRequest(@PathVariable("gameId") Long gameId, @PathVariable("roundId") Long roundId, @RequestBody TeamRequest teamRequest, @AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.authUserCreateTeamWithGameIdAndRoundId(userDetail, teamRequest, gameId, roundId);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        messagingTemplate.convertAndSend("/topic/game/" + gameId, "New Team Record");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("/vote/new")
    public ResponseEntity<DataResponse> handleNewVoteRequest(@PathVariable("gameId") Long gameId, @PathVariable("roundId") Long roundId, @RequestParam("accept") Boolean accept, @AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.authUserCreateVoteWithGameIdAndRoundId(userDetail, accept, gameId, roundId);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        messagingTemplate.convertAndSend("/topic/game/" + gameId, "New Vote Record");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("/mission/new")
    public ResponseEntity<DataResponse> handleNewMissionRequest(@PathVariable("gameId") Long gameId, @PathVariable("roundId") Long roundId, @RequestParam("success") Boolean success, @AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.gameLogicService.authUserCreateMissionWithGameIdAndRoundId(userDetail, success, gameId, roundId);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        messagingTemplate.convertAndSend("/topic/game/" + gameId, "New Mission Record");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

}
