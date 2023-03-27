package me.kreal.avalon.service;

import lombok.Synchronized;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Record;
import me.kreal.avalon.domain.User;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.security.JwtProvider;
import me.kreal.avalon.util.GameMapper;
import me.kreal.avalon.util.avalon.GameModeFactory;
import me.kreal.avalon.util.enums.GameModeType;
import me.kreal.avalon.util.enums.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@Service
public class GameLogicService {

    private final UserService userService;
    private final GameService gameService;
    private final PlayerService playerService;
    private final RecordService recordService;
    private final RoundService roundService;
    private final VoteService voteService;
    private final TeamMemberService teamMemberService;
    private final JwtProvider jwtProvider;

    @Autowired
    public GameLogicService(UserService userService, GameService gameService, PlayerService playerService, RecordService recordService, RoundService roundService, VoteService voteService, TeamMemberService teamMemberService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.gameService = gameService;
        this.playerService = playerService;
        this.recordService = recordService;
        this.roundService = roundService;
        this.voteService = voteService;
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



    // Game logic methods
    @Synchronized
    public DataResponse createNewGame(int gameSize, GameModeType gameMode, int gameNum) {

        if (gameSize != GameModeFactory.getGameMode(gameMode).getGameSize()) {
            return DataResponse.error("Invalid game size or game mode");
        }

        if (this.gameService.findNotStartedGameByGameNum(gameNum).isPresent()) {
            return DataResponse.error("Game num is existed. do you want to join?");
        }

        Game game = this.gameService.createNewGame(gameMode, gameNum);

        return DataResponse.success("Game created")
                .data(GameMapper.convertToResponse(game));

    }

    public DataResponse authUserJoinGameWithGameNum(AuthUserDetail authUserDetail, int gameNum) {

        Optional<Game> notStartedGame = this.gameService.findNotStartedGameByGameNum(gameNum);

        if(!notStartedGame.isPresent()) {
            return DataResponse.error("Game not found or game has already started");
        }

        Game game = notStartedGame.get();

        User user = this.userService.findUserByAuthUserDetail(authUserDetail);

        Record record = this.recordService.findOrCreateNewRecord(game, user);

        return DataResponse.success("Join game success")
                .data(GameMapper.convertToResponse(game))
                .token(this.jwtProvider.createToken(record));

    }

    public DataResponse authUserFindGameWithId(AuthUserDetail authUserDetail, Long gameId) {

        Optional<Game> gameOptional = this.gameService.findGameById(gameId);

        if (!gameOptional.isPresent()) {
            return DataResponse.error("Game not found");
        }

        Game game = gameOptional.get();

        // game is finished

        if (GameStatus.gameIsFinished(game.getGameStatus())) {
            return DataResponse.success("Game found")
                    .data(GameMapper.convertToResponse(game));
        }

        // auth user is in the game and game is not finished

        if (game.getGameId().equals(authUserDetail.getGameId())) {
            return DataResponse.success("Joined Game")
                    .data(GameMapper.convertToResponse(game, authUserDetail.getPlayerId()));
        }

        User user = this.userService.findUserByAuthUserDetail(authUserDetail);

        Optional<Record> recordOptional = this.recordService.findRecordByGameAndUser(game, user);

        if (recordOptional.isPresent()) {
            return DataResponse.success("Joined Game")
                    .data(GameMapper.convertToResponse(game, recordOptional.get().getPlayerId()))
                    .token(this.jwtProvider.createToken(recordOptional.get()));
        }

        // auth user is not in the game

        return DataResponse.success("Game found")
                .data(GameMapper.convertToResponse(game));

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

        return DataResponse.success("Game started")
                .data(GameMapper.convertToResponse(game, authUserDetail.getPlayerId()));

    }

}