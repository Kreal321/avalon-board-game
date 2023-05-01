package me.kreal.avalon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import me.kreal.avalon.util.enums.GameModeType;
import me.kreal.avalon.util.enums.GameStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`game`")
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "game_num", nullable = false)
    private String gameNum;

    @Column(name = "game_size", nullable = false)
    private Integer gameSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_status", nullable = false)
    private GameStatus gameStatus;

    @Column(name = "game_start_time")
    private Timestamp gameStartTime;

    @Column(name = "game_end_time")
    private Timestamp gameEndTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_mode", nullable = false)
    private GameModeType gameMode;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "game")
    private List<Player> players = new ArrayList<>();

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "game")
    private Set<Record> records = new HashSet<>();

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "game")
    private List<Round> rounds = new ArrayList<>();

    public void addPlayer(Player player) {
        this.players.add(player);
        player.setGame(this);
    }
}
