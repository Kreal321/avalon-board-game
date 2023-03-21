package me.kreal.avalon.service;

import me.kreal.avalon.dao.PlayerDao;
import me.kreal.avalon.dao.RecordDao;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.Record;
import me.kreal.avalon.domain.User;
import me.kreal.avalon.util.enums.VictoryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Record createNewRecord(Game g, User u) {

        // Check where user has joined the game
        Optional<Record> recordOptional = this.findRecordByGameAndUser(g, u);

        if (recordOptional.isPresent()) return recordOptional.get();

        // Create record
        Player p = this.playerService.createNewPlayer(g, u);

        Record r = Record.builder()
                .user(u)
                .game(g)
                .playerId(p.getPlayerId())
                .victoryStatus(VictoryStatus.IN_PROGRESS)
                .build();

        this.recordDao.save(r);

        return r;
    }



}
