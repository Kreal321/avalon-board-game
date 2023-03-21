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
import me.kreal.avalon.util.avalon.GameMode;
import me.kreal.avalon.util.avalon.GameModeFactory;
import me.kreal.avalon.util.enums.GameModeType;
import me.kreal.avalon.util.enums.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class GameService {

    private final GameDao gameDao;
    private final UserService userService;
    private final RecordService recordService;
    private final JwtProvider jwtProvider;

    @Autowired
    public GameService(GameDao gameDao, UserService userService, RecordService recordService, JwtProvider jwtProvider) {
        this.gameDao = gameDao;
        this.userService = userService;
        this.recordService = recordService;
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
            return DataResponse.builder()
                    .success(false)
                    .message("Cannot find game with id = " + gameId)
                    .build();
        }

        return DataResponse.builder()
                .success(true)
                .data(gameOptional.get())
                .build();
    }

    @Transactional
    public DataResponse createNewGame(int gameSize, GameModeType gameMode, int gameNum) {
        if (this.gameDao.findGameByGameNumAndGameStatus(gameNum, GameStatus.NOT_STARTED).isPresent()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Game num is existed. do you want to join?")
                    .build();
        }

        Game game = Game.builder()
                .gameNum(gameNum)
                .gameSize(gameSize)
                .gameStatus(GameStatus.NOT_STARTED)
                .gameMode(gameMode)
                .players(new HashSet<>())
                .build();

        this.gameDao.save(game);

        return DataResponse.builder()
                .success(true)
                .data(game)
                .build();

    }

    public DataResponse handleJoinGameRequest(int gameNum, AuthUserDetail userDetail) {

        DataResponse response = this.joinGameByToken(userDetail);

        if (response.getSuccess()) return response;

        return this.joinNotStartedGameByGameNum(gameNum, userDetail.getUsername());

    }

    public DataResponse joinGameByToken(AuthUserDetail userDetail) {
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
            return DataResponse.builder()
                    .success(false)
                    .message("You are not in any game.")
                    .build();
        }

        // assume game exist because we can trust jwt token
        Game g = this.findGameById(userDetail.getGameId()).get();
        return this.startGame(g);

    }


    @Transactional
    public DataResponse joinNotStartedGameByGameNum(int gameNum, String username) {

        Optional<Game> gameOptional = this.gameDao.findGameByGameNumAndGameStatus(gameNum, GameStatus.NOT_STARTED);

        if(!gameOptional.isPresent()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Room num is not existed")
                    .build();
        }

        Optional<User> userOptional = this.userService.findUserByUsername(username);

        if(!userOptional.isPresent()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Username is not existed")
                    .build();
        }

        Record r = recordService.createNewRecord(gameOptional.get(), userOptional.get());

        return DataResponse.builder()
                .success(true)
                .data(gameOptional.get())
                .token(this.jwtProvider.createToken(r))
                .build();

    }

    public DataResponse startGame(Game g) {

        if (g.getPlayers().size() > g.getGameSize()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Number of players is larger than game size. Please increase the game size.")
                    .build();
        } else if (g.getPlayers().size() < g.getGameSize()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Number of players is smaller than game size. Please decrease the game size.")
                    .build();
        }

        g.setGameStatus(GameStatus.IN_PROGRESS);
        g.setGameStartTime(new Timestamp(System.currentTimeMillis()));

        GameModeFactory.getGameMode(GameModeType.FIVE_BASIC).assignPlayerCharacter(g.getPlayers());

        this.gameDao.update(g);
        return DataResponse.builder()
                .success(true)
                .message("Game started!")
                .data(g)
                .build();


    }

    public DataResponse getGameInfoByToken(AuthUserDetail userDetail) {
        if (userDetail.getGameId() == null || userDetail.getPlayerId() == null) {
            return DataResponse.builder()
                    .success(false)
                    .message("You are not in any game.")
                    .build();
        }

        // assume game exist because we can trust jwt token
        Game g = this.findGameById(userDetail.getGameId()).get();

        return DataResponse.builder()
                .success(true)
                .message("You are not in any game.")
                .data(GameModeFactory.getGameMode(g.getGameMode()).getCharacterInfo(g, userDetail.getPlayerId()))
                .build();
    }


}
