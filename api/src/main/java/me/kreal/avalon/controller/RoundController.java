package me.kreal.avalon.controller;

import me.kreal.avalon.dto.request.TeamRequest;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.service.PlayerService;
import me.kreal.avalon.service.RoundService;
import me.kreal.avalon.util.enums.TeamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/round")
public class RoundController {

    private final RoundService roundService;

    @Autowired
    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    @PostMapping("/{roundId}/team/new")
    public ResponseEntity<DataResponse> handleNewTeamRequest(@PathVariable("roundId") Long roundId, @Valid @RequestBody TeamRequest teamRequest, @AuthenticationPrincipal AuthUserDetail userDetail, BindingResult result) {

        if (userDetail.getGameId() == null || userDetail.getPlayerId() == null) {
            return ResponseEntity.ok(DataResponse.error("You are not in a game"));
        }

        if (result.hasErrors()) {
            return ResponseEntity.ok(DataResponse.error(result.getFieldErrors().get(0).getDefaultMessage()));
        }

        return ResponseEntity.ok(this.roundService.createNewTeamForRound(roundId, userDetail.getGameId(), userDetail.getPlayerId(), teamRequest));

    }

    @PostMapping("/{roundId}/vote/new")
    public ResponseEntity<DataResponse> handleNewVoteRequest(@PathVariable("roundId") Long roundId, @RequestParam("accept") Boolean accept, @AuthenticationPrincipal AuthUserDetail userDetail) {

        if (userDetail.getGameId() == null || userDetail.getPlayerId() == null) {
            return ResponseEntity.ok(DataResponse.error("You are not in a game"));
        }

        return ResponseEntity.ok(this.roundService.createNewVoteForRound(roundId, userDetail.getGameId(), userDetail.getPlayerId(), accept));

    }


}
