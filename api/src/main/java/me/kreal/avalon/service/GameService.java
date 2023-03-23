package me.kreal.avalon.service;

import me.kreal.avalon.dao.GameDao;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.Record;
import me.kreal.avalon.domain.User;
import me.kreal.avalon.dto.response.CharacterInfo;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.security.JwtProvider;
import me.kreal.avalon.util.GameMapper;
import me.kreal.avalon.util.avalon.GameMode;
import me.kreal.avalon.util.avalon.GameModeFactory;
import me.kreal.avalon.util.enums.GameModeType;
import me.kreal.avalon.util.enums.GameStatus;
import me.kreal.avalon.util.enums.RoundStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;

@Service
public class GameService {

    private final GameDao gameDao;
    private final UserService userService;
    private final RecordService recordService;
    private final RoundService roundService;
    private final JwtProvider jwtProvider;

    @Autowired
    public GameService(GameDao gameDao, UserService userService, RecordService recordService, RoundService roundService, JwtProvider jwtProvider) {
        this.gameDao = gameDao;
        this.userService = userService;
        this.recordService = recordService;
        this.roundService = roundService;
        this.jwtProvider = jwtProvider;
    }


    @Transactional
    public Optional<Game> findGameByGameNum(int gameNum) {
        return this.gameDao.findGameByGameNum(gameNum);
    }

    @Transactional
    public Optional<Game> findGameById(Long gameId) {
        return this.gameDao.getById(gameId);
    }

    public DataResponse findGameByIdAndUsername(Long gameId, String username) {
        Optional<Game> gameOptional = this.findGameById(gameId);

        if (!gameOptional.isPresent()) {
            return DataResponse.error("Cannot find game with id = " + gameId);
        }

        Game game = gameOptional.get();

        if (GameStatus.gameIsFinished(game.getGameStatus())) {
            return DataResponse.builder()
                    .success(true)
                    .data(GameMapper.convertToResponse(game))
                    .build();
        }

        Optional<User> userOptional = this.userService.findUserByUsername(username);

        if (!userOptional.isPresent()) {
            return DataResponse.builder()
                    .success(true)
                    .data(GameMapper.convertToResponse(game))
                    .build();
        }

        Optional<Record> recordOptional = this.recordService.findRecordByGameAndUser(game, userOptional.get());

        // not in the game, but can watch
        if (!recordOptional.isPresent()) {
            return DataResponse.builder()
                    .success(true)
                    .data(GameMapper.convertToResponse(game))
                    .build();
        }

        // in the game
        return DataResponse.builder()
                .success(true)
                .data(GameMapper.convertToResponse(game))
                .token(this.jwtProvider.createToken(recordOptional.get()))
                .build();
    }

    @Transactional
    public DataResponse createNewGame(int gameSize, GameModeType gameMode, int gameNum) {

        if (gameSize != GameModeFactory.getGameMode(gameMode).getGameSize()) {
            return DataResponse.error("Invalid game size or game mode");
        }

        if (this.gameDao.findGameByGameNumAndGameStatus(gameNum, GameStatus.NOT_STARTED).isPresent()) {
            return DataResponse.error("Game num is existed. do you want to join?");
        }

        Game game = Game.builder()
                .gameNum(gameNum)
                .gameSize(gameSize)
                .gameStatus(GameStatus.NOT_STARTED)
                .gameMode(gameMode)
                .players(new ArrayList<>())
                .records(new HashSet<>())
                .rounds(new ArrayList<>())
                .build();

        this.gameDao.save(game);

        return DataResponse.success("Game created successfully.", GameMapper.convertToResponse(game));

    }

    public DataResponse joinGameWithToken(AuthUserDetail userDetail) {
        if (userDetail.getGameId() != null) {
            // assume game exist because we can trust jwt token
            Game g = this.findGameById(userDetail.getGameId()).get();
            if (g.getGameStatus() == GameStatus.IN_PROGRESS || g.getGameStatus() == GameStatus.NOT_STARTED) {
                return DataResponse.builder()
                        .success(true)
                        .data(g)
                        .build();
            }
        }

        return DataResponse.builder()
                .success(false)
                .message("You need to join game by game number.")
                .build();
    }

    @Transactional
    public DataResponse startGameByToken(AuthUserDetail userDetail) {
        if (userDetail.getGameId() == null) {
            return DataResponse.error("You are not in a game.");
        }

        // assume game exist because we can trust jwt token
        Game g = this.findGameById(userDetail.getGameId()).get();

        if (g.getGameStatus() != GameStatus.NOT_STARTED) {
            return DataResponse.error("Game has already started.");
        }

        return this.startGame(g);

    }


    @Transactional
    public DataResponse joinNotStartedGameByGameNum(int gameNum, String username) {

        Optional<Game> gameOptional = this.gameDao.findGameByGameNumAndGameStatus(gameNum, GameStatus.NOT_STARTED);

        if(!gameOptional.isPresent()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Room num is not valid or game has already started.")
                    .build();
        }

        Optional<User> userOptional = this.userService.findUserByUsername(username);

        if(!userOptional.isPresent()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Username is not existed")
                    .build();
        }

        Record r = recordService.findOrCreateNewRecord(gameOptional.get(), userOptional.get());

        return DataResponse.builder()
                .success(true)
                .data(gameOptional.get())
                .token(this.jwtProvider.createToken(r))
                .build();

    }

    public DataResponse startGame(Game g) {

        if (g.getPlayers().size() > g.getGameSize()) {
            return DataResponse.error("Number of players is larger than game size. Please increase the game size.");
        } else if (g.getPlayers().size() < g.getGameSize()) {
            return DataResponse.error("Number of players is smaller than game size. Please decrease the game size.");
        }

        g.setGameStatus(GameStatus.IN_PROGRESS);
        g.setGameStartTime(new Timestamp(System.currentTimeMillis()));

        GameModeFactory.getGameMode(g.getGameMode()).assignPlayerCharacter(g.getPlayers());

        this.gameDao.update(g);

        this.roundService.createNewRound(g);

        return DataResponse.builder()
                .success(true)
                .message("Game started!")
                .data(GameMapper.convertToResponse(g))
                .build();


    }

    public DataResponse getGameLatestInfoByToken(AuthUserDetail userDetail) {
        if (userDetail.getGameId() == null || userDetail.getPlayerId() == null) {
            return DataResponse.error("You are not in any game.");
        }

        // assume game exist because we can trust jwt token
        Game g = this.findGameById(userDetail.getGameId()).get();

        return DataResponse.builder()
                .success(true)
                .message("Info")
                .data(GameMapper.convertToResponse(g, userDetail.getPlayerId()))
                .build();
    }


}
