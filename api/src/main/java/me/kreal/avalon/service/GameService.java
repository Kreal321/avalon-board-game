package me.kreal.avalon.service;

import me.kreal.avalon.dao.GameDao;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.util.GameMapper;
import me.kreal.avalon.util.avalon.GameModeFactory;
import me.kreal.avalon.util.enums.GameModeType;
import me.kreal.avalon.util.enums.GameStatus;
import me.kreal.avalon.util.enums.RoundStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<Game> findNotStartedGameByGameNum(String gameNum) {
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

    public Game createNewGame(GameModeType gameMode, String gameNum) {

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

    @Transactional
    public Game assassinFlop(Game g) {

        g.setGameStatus(GameStatus.ASSASSIN_FLOP);

        this.updateGame(g);

        return g;
    }

    @Transactional
    public Game endGame(Game g, boolean assassinateResult) {

        g.setGameStatus(assassinateResult ? GameStatus.EVIL_WON_WITH_ASSASSINATION : GameStatus.GOOD_WON);
        g.setGameEndTime(new Timestamp(System.currentTimeMillis()));

        this.updateGame(g);

        return g;
    }


    @Transactional
    public boolean updateGameIfFinished(Game game) {

        long successTimes = game.getRounds().stream()
                .filter(RoundStatus::roundHasQuest)
                .filter(round -> round.getRoundStatus() == RoundStatus.QUEST_SUCCESS)
                .count();

        long failTimes = game.getRounds().stream()
                .filter(RoundStatus::roundHasQuest)
                .filter(round -> round.getRoundStatus() == RoundStatus.QUEST_FAIL)
                .count();

        if (successTimes >= 3) {
            game.setGameStatus(GameStatus.ASSASSIN_FLOP);
            this.gameDao.save(game);
            return true;
        } else if (failTimes >= 3) {
            game.setGameStatus(GameStatus.EVIL_WON_WITH_QUEST);
            this.gameDao.save(game);
            return true;
        }

        return false;
    }


}
