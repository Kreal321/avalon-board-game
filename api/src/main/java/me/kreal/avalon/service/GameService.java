package me.kreal.avalon.service;

import me.kreal.avalon.dao.GameDao;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.util.GameMapper;
import me.kreal.avalon.util.avalon.GameModeFactory;
import me.kreal.avalon.util.enums.GameModeType;
import me.kreal.avalon.util.enums.GameStatus;
import me.kreal.avalon.util.enums.RoundStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class GameService {

    private final GameDao gameDao;

    @Autowired
    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public Optional<Game> findGameByGameNum(int gameNum) {
        return this.gameDao.findGameByGameNum(gameNum);
    }

    public Optional<Game> findGameById(Long gameId) {
        return this.gameDao.getById(gameId);
    }

    public Optional<Game> findNotStartedGameByGameNum(int gameNum) {
        return this.gameDao.findGameByGameNumAndGameStatus(gameNum, GameStatus.NOT_STARTED);
    }

    public Game createGame(Game game) {

        assert game.getGameId() == null : "Game id must be null";

        this.gameDao.save(game);
        return game;

    }

    public Game updateGame(Game game) {

        assert game.getGameId() != null : "Game id must not be null";

        this.gameDao.update(game);
        return game;
    }

    public Game createNewGame(GameModeType gameMode, int gameNum) {

        Game game = Game.builder()
                .gameNum(gameNum)
                .gameSize(GameModeFactory.getGameMode(gameMode).getGameSize())
                .gameStatus(GameStatus.NOT_STARTED)
                .gameMode(gameMode)
                .players(new ArrayList<>())
                .records(new HashSet<>())
                .rounds(new ArrayList<>())
                .build();

        return this.createGame(game);

    }

    public Game startNewGame(Game g) {

        g.setGameStatus(GameStatus.IN_PROGRESS);
        g.setGameStartTime(new Timestamp(System.currentTimeMillis()));

        // assign player character
        GameModeFactory.getGameMode(g.getGameMode()).assignPlayerCharacter(g.getPlayers());

        this.updateGame(g);

        return g;
    }


    public DataResponse finishGame(Game game) {

        if (GameStatus.gameIsFinished(game.getGameStatus())) {
            return DataResponse.error("Game has already finished.");
        }

        if (GameStatus.assassinIsInAction(game.getGameStatus())) {
            return DataResponse.error("Assassin is in action.");
        }

        long successTimes = game.getRounds().stream()
                .filter(RoundStatus::roundHasQuest)
                .filter(round -> round.getRoundStatus() == RoundStatus.QUEST_SUCCESS)
                .count();

        if (successTimes >= 3) {
            game.setGameStatus(GameStatus.GOOD_WON);
        } else {
            game.setGameStatus(GameStatus.EVIL_WON_WITH_QUEST);
        }

        this.gameDao.save(game);

        return DataResponse.success("Game finished.", GameMapper.convertToResponse(game));

    }


}
