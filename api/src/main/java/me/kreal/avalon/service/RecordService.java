package me.kreal.avalon.service;

import lombok.Synchronized;
import me.kreal.avalon.dao.PlayerDao;
import me.kreal.avalon.dao.RecordDao;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.Record;
import me.kreal.avalon.domain.User;
import me.kreal.avalon.util.enums.VictoryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecordService {

    private final RecordDao recordDao;
    private final PlayerService playerService;

    @Autowired
    public RecordService(RecordDao recordDao, PlayerService playerService) {
        this.recordDao = recordDao;
        this.playerService = playerService;
    }

    public Optional<Record> findRecordByGameAndUser(Game g, User u) {
        return this.recordDao.findRecordByGameAndUser(g, u);
    }

    @Synchronized
    public Record findOrCreateNewRecord(Game game, User user) {

        // Check where user has joined the game
        Optional<Record> recordOptional = this.findRecordByGameAndUser(game, user);

        if (recordOptional.isPresent()) return recordOptional.get();

        // Create record
        Player p = this.playerService.createNewPlayer(game, user);

        Record r = Record.builder()
                .user(user)
                .game(game)
                .player(p)
                .victoryStatus(VictoryStatus.IN_PROGRESS)
                .build();

        this.recordDao.save(r);

        return r;
    }

    public List<Record> findRecordsByUser(User u) {
        return this.recordDao.findRecordsByUser(u).stream()
                .sorted((r1, r2) -> r2.getRecordId().compareTo(r1.getRecordId()))
                .collect(Collectors.toList());
    }

}
