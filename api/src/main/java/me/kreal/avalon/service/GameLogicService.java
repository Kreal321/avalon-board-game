package me.kreal.avalon.service;

import lombok.Synchronized;
import me.kreal.avalon.domain.*;
import me.kreal.avalon.dto.request.TeamRequest;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.security.JwtProvider;
import me.kreal.avalon.util.GameMapper;
import me.kreal.avalon.util.RecordMapper;
import me.kreal.avalon.util.avalon.GameModeFactory;
import me.kreal.avalon.util.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GameLogicService {

    private final UserService userService;
    private final GameService gameService;
    private final PlayerService playerService;
    private final RecordService recordService;
    private final RoundService roundService;
    private final VoteService voteService;
    private final TeamService teamService;
    private final TeamMemberService teamMemberService;
    private final JwtProvider jwtProvider;

    @Autowired
    public GameLogicService(UserService userService, GameService gameService, PlayerService playerService, RecordService recordService, RoundService roundService, VoteService voteService, TeamService teamService, TeamMemberService teamMemberService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.gameService = gameService;
        this.playerService = playerService;
        this.recordService = recordService;
        this.roundService = roundService;
        this.voteService = voteService;
        this.teamService = teamService;
        this.teamMemberService = teamMemberService;
        this.jwtProvider = jwtProvider;
    }

    // Helper methods
    public Optional<Game> findGameFromAuthUserDetail(AuthUserDetail authUserDetail) {

        // User is not in a game
        if (authUserDetail.getGameId() == null || authUserDetail.getPlayerId() == null) {
            return Optional.empty();
        }

        return this.gameService.findGameById(authUserDetail.getGameId());
    }


    // Records
    public DataResponse findRecordsByUser(AuthUserDetail authUserDetail) {

        User u = this.userService.findUserByAuthUserDetail(authUserDetail);

        List<Record> records = this.recordService.findRecordsByUser(u);

        return DataResponse.success("Records found")
                .data(records.stream()
                        .map(RecordMapper::convertToResponse)
                        .collect(Collectors.toList()));
    }


    // Game logic methods
    @Synchronized
    public DataResponse createNewGame(int gameSize, GameModeType gameMode, String gameNum) {

        if (gameSize != GameModeFactory.getGameMode(gameMode).getGameSize()) {
            return DataResponse.error("Invalid game size or game mode");
        }

        if (this.gameService.findNotStartedGameByGameNum(gameNum).isPresent()) {
            return DataResponse.error("Game num is existed. do you want to join?");
        }

        Game game = this.gameService.createNewGame(gameMode, gameNum);

        return DataResponse.success("Game created");

    }

    public DataResponse authUserLeaveGame(AuthUserDetail authUserDetail) {

        authUserDetail.setGameId(null);
        authUserDetail.setPlayerId(null);

        return DataResponse.success("Leave game success");
    }

    public DataResponse authUserJoinGameWithGameNum(AuthUserDetail authUserDetail, String gameNum) {

        Optional<Game> notStartedGame = this.gameService.findNotStartedGameByGameNum(gameNum);

        if (!notStartedGame.isPresent()) {
            return DataResponse.error("Game not found or game has already started");
        }

        Game game = notStartedGame.get();

        if (game.getGameSize() <= game.getPlayers().size()) {
            return DataResponse.error("Game is full");
        }

        User user = this.userService.findUserByAuthUserDetail(authUserDetail);

        Record record = this.recordService.findOrCreateNewRecord(game, user);

        return DataResponse.success("Join game success")
                .data(GameMapper.convertToSimplifiedResponse(game))
                .token(this.jwtProvider.createToken(record));

    }

    public DataResponse authUserRenameWithGameIdAndPlayerId(AuthUserDetail authUserDetail, Long gameId, Long playerId, String newName) {

        if (authUserDetail.getGameId() == null || authUserDetail.getPlayerId() == null) {
            return DataResponse.error("You are not in a game");
        }

        Optional<Game> gameOptional = this.gameService.findGameById(gameId);

        if (!gameOptional.isPresent()) {
            return DataResponse.error("Game not found");
        }

        Game game = gameOptional.get();

        if (!game.getGameId().equals(authUserDetail.getGameId())) {
            return DataResponse.error("You are not in this game");
        }

        if (GameStatus.gameIsFinished(game.getGameStatus())) {
            return DataResponse.error("Game is finished");
        }

        Player player = this.playerService.findPlayerByAuthUserDetail(authUserDetail);
        this.playerService.playerRename(player, newName);

        return DataResponse.success("Rename success");

    }

    public DataResponse authUserFindGameWithId(AuthUserDetail authUserDetail, Long gameId) {

        Optional<Game> gameOptional = this.gameService.findGameById(gameId);

        if (!gameOptional.isPresent()) {
            return DataResponse.error("Game not found");
        }

        Game game = gameOptional.get();

        User user = this.userService.findUserByAuthUserDetail(authUserDetail);

        Optional<Record> recordOptional = this.recordService.findRecordByGameAndUser(game, user);

        // auth user is not in the game
        if (!recordOptional.isPresent()) {
            return DataResponse.success("Game found. You are not in the game.")
                    .data(GameMapper.convertToResponse(game))
                    .token(this.jwtProvider.createToken(user));
        }

        // auth user is in the game and game is not started
        if (game.getGameStatus() == GameStatus.NOT_STARTED) {
            return DataResponse.success("Game found")
                    .data(GameMapper.convertToResponse(game, recordOptional.get().getPlayer()))
                    .token(this.jwtProvider.createToken(recordOptional.get()));
        }

        // auth user is in the game and game is started
        return DataResponse.success("Joined Game")
                .data(GameMapper.convertToResponse(game, recordOptional.get().getPlayerId()))
                .token(this.jwtProvider.createToken(recordOptional.get()));

    }

    @Transactional
    public DataResponse authUserStartGameWithId(AuthUserDetail authUserDetail, Long gameId) {

        Optional<Game> gameOptional = this.gameService.findGameById(gameId);

        if (!gameOptional.isPresent()) {
            return DataResponse.error("Game not found");
        }

        Game game = gameOptional.get();

        if (!game.getGameId().equals(authUserDetail.getGameId())) {
            return DataResponse.error("You are not in this game. Do you want to join?");
        }

        // game has already started
        if (game.getGameStatus() != GameStatus.NOT_STARTED) {
            return DataResponse.error("Game has already started");
        }

        // game size is not correct
        if (game.getPlayers().size() > game.getGameSize()) {
            return DataResponse.error("Number of players is larger than game size. Please increase the game size.");
        } else if (game.getPlayers().size() < game.getGameSize()) {
            return DataResponse.error("Number of players is smaller than game size. Please decrease the game size.");
        }

        this.gameService.startNewGame(game);

        // create first round
        this.roundService.createNewRound(game);

        return DataResponse.success("Game started");

    }

    public DataResponse authUserCreateTeamWithGameIdAndRoundId(AuthUserDetail authUserDetail, TeamRequest teamRequest, Long gameId, Long roundId) {

        if (authUserDetail.getGameId() == null || authUserDetail.getPlayerId() == null) {
            return DataResponse.error("You are not in a game");
        }

        if (!authUserDetail.getGameId().equals(gameId)) {
            return DataResponse.error("You are not in this game.");
        }

        Optional<Round> roundOptional = this.roundService.findRoundById(roundId);

        if (!roundOptional.isPresent()) {
            return DataResponse.error("Round not found");
        }

        Round round = roundOptional.get();

        if (!round.getGame().getGameId().equals(gameId)) {
            return DataResponse.error("Round not found");
        }

        if (GameStatus.gameIsFinished(round.getGame().getGameStatus())) {
            return DataResponse.error("Game is over");
        }

        if (GameStatus.assassinIsInAction(round.getGame().getGameStatus())) {
            return DataResponse.error("Assassin is in action");
        }

        // check team

        if (!round.getLeader().getPlayerId().equals(authUserDetail.getPlayerId())) {
            return DataResponse.error("You are not the leader");
        }

        if (teamRequest.getTeamType() == TeamType.INITIAL && round.getRoundStatus() != RoundStatus.INITIAL_TEAM) {
            return DataResponse.error("Round is not in initial team status");
        }

        if (teamRequest.getTeamType() == TeamType.FINAL && round.getRoundStatus() != RoundStatus.DISCUSSING) {
            return DataResponse.error("Round is not in final team status");
        }

        if (teamRequest.getTeamMembers().size() != round.getTeamSize()) {
            return DataResponse.error("Team size is not correct");
        }

        List<Player> teamMembers = round.getGame().getPlayers().stream()
                .filter(player -> teamRequest.getTeamMembers().contains(player.getPlayerId()))
                .collect(Collectors.toList());

        if (teamMembers.size() != round.getTeamSize()) {
            return DataResponse.error("Team members are not in the game");
        }

        this.roundService.createNewTeamForRound(round, teamRequest.getTeamType(), teamMembers);


        return DataResponse.success("Team created");

    }

    public DataResponse authUserCreateVoteWithGameIdAndRoundId(AuthUserDetail authUserDetail, boolean accept, Long gameId, Long roundId) {

        if (authUserDetail.getGameId() == null || authUserDetail.getPlayerId() == null) {
            return DataResponse.error("You are not in a game");
        }

        if (!authUserDetail.getGameId().equals(gameId)) {
            return DataResponse.error("You are not in this game.");
        }

        Optional<Round> roundOptional = this.roundService.findRoundById(roundId);

        if (!roundOptional.isPresent()) {
            return DataResponse.error("Round not found");
        }

        Round round = roundOptional.get();

        if (!round.getGame().getGameId().equals(gameId)) {
            return DataResponse.error("Round not found");
        }

        if (GameStatus.gameIsFinished(round.getGame().getGameStatus())) {
            return DataResponse.error("Game is over");
        }

        if (GameStatus.assassinIsInAction(round.getGame().getGameStatus())) {
            return DataResponse.error("Assassin is in action");
        }

        // check vote

        if (round.getRoundStatus() != RoundStatus.FINAL_TEAM_VOTING) {
            return DataResponse.error("Round is not in voting status");
        }

        if (this.voteService.findVoteByRoundIdAndPlayerId(roundId, authUserDetail.getPlayerId()).isPresent()) {
            return DataResponse.error("You have already voted");
        }

        this.roundService.createNewVoteForRound(round, authUserDetail.getPlayerId(), accept);

        return DataResponse.success("Vote created");
    }

    public DataResponse authUserCreateMissionWithGameIdAndRoundId(AuthUserDetail authUserDetail, boolean success, Long gameId, Long roundId) {

        if (authUserDetail.getGameId() == null || authUserDetail.getPlayerId() == null) {
            return DataResponse.error("You are not in a game");
        }

        if (!authUserDetail.getGameId().equals(gameId)) {
            return DataResponse.error("You are not in this game.");
        }

        Optional<Round> roundOptional = this.roundService.findRoundById(roundId);

        if (!roundOptional.isPresent()) {
            return DataResponse.error("Round not found");
        }

        Round round = roundOptional.get();

        if (!round.getGame().getGameId().equals(gameId)) {
            return DataResponse.error("Round not found");
        }

        if (GameStatus.gameIsFinished(round.getGame().getGameStatus())) {
            return DataResponse.error("Game is over");
        }

        if (GameStatus.assassinIsInAction(round.getGame().getGameStatus())) {
            return DataResponse.error("Assassin is in action");
        }

        // check mission

        if (round.getRoundStatus() != RoundStatus.FINAL_TEAM_VOTING_SUCCESS) {
            return DataResponse.error("Round is not in pending mission status");
        }

        Team finalTeam = this.teamService.findFinalTeamByRoundId(roundId).get();

        Optional<TeamMember> teamMemberOptional = finalTeam.getTeamMembers().stream()
                .filter(member -> member.getPlayerId().equals(authUserDetail.getPlayerId()))
                .findAny();

        if (!teamMemberOptional.isPresent()) {
            return DataResponse.error("You are not in the team");
        }

        TeamMember teamMember = teamMemberOptional.get();

        if (teamMember.getStatus() != TeamMemberStatus.MISSION_PENDING) {
            return DataResponse.error("You have already voted");
        }

        if (!success && CharacterType.isGood(teamMember.getPlayer().getCharacterType())) {
            return DataResponse.error("You are a good character, you cannot fail the quest.");
        }

        this.roundService.createNewMissionForRound(round, finalTeam, teamMember, success);

        // check if game is over

        if (RoundStatus.roundIsFinished(round.getRoundStatus())) {
            if (this.gameService.updateGameIfFinished(round.getGame())) {
                return DataResponse.success("Game is over");
            } else {
                // create new round
                this.roundService.createNewRound(round.getGame());
            }
        }

        return DataResponse.success("Mission created");
    }

    public DataResponse authUserAssassinFloppedWithGameId(AuthUserDetail authUserDetail, Long gameId) {

        if (authUserDetail.getGameId() == null || authUserDetail.getPlayerId() == null) {
            return DataResponse.error("You are not in a game");
        }

        // player is not in the game
        Player player = this.playerService.findPlayerByAuthUserDetail(authUserDetail);

        if (!player.getGame().getGameId().equals(gameId) || !authUserDetail.getGameId().equals(gameId)) {
            return DataResponse.error("You are not in this game.");
        }

        // player is not the assassin
        if (player.getCharacterType() != CharacterType.ASSASSIN) {
            return DataResponse.error("You are not the assassin");
        }

        // game not started or is finished
        if (player.getGame().getGameStatus() != GameStatus.IN_PROGRESS) {
            return DataResponse.error("You cannot flop the assassin now");
        }

        this.gameService.assassinFlop(player.getGame());

        return DataResponse.success("Assassin flopped");

    }

    public DataResponse authUserAssassinAssassinateWithGameIdAndPlayerId(AuthUserDetail authUserDetail, Long gameId, Long targetId) {

        if (authUserDetail.getGameId() == null || authUserDetail.getPlayerId() == null) {
            return DataResponse.error("You are not in a game");
        }

        // player is not in the game
        Player current = this.playerService.findPlayerByAuthUserDetail(authUserDetail);

        if (!current.getGame().getGameId().equals(gameId) || !authUserDetail.getGameId().equals(gameId)) {
            return DataResponse.error("You are not in this game.");
        }

        // player is not the assassin
        if (current.getCharacterType() != CharacterType.ASSASSIN) {
            return DataResponse.error("You are not the assassin");
        }

        // game not in assassin flop status
        if (current.getGame().getGameStatus() != GameStatus.ASSASSIN_FLOP) {
            return DataResponse.error("You cannot assassinate now");
        }

        // success or not
        Optional<Player> targetOptional = this.playerService.findPlayerById(targetId);

        if (!targetOptional.isPresent()) {
            return DataResponse.error("Target Player not found");
        }

        Player target = targetOptional.get();

        if (!target.getGame().getGameId().equals(gameId)) {
            return DataResponse.error("Target Player not in the game");
        }

        this.playerService.assassinatePlayer(target);

        this.gameService.endGame(current.getGame(), target.getCharacterType() == CharacterType.MERLIN);

        return DataResponse.success("Assassin assassinated");

    }
}
