package me.kreal.avalon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import me.kreal.avalon.util.enums.VictoryStatus;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`record`")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "player_id")
    private Long playerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "victory_status", nullable = false)
    private VictoryStatus victoryStatus;

}
