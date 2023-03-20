package me.kreal.avalon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import me.kreal.avalon.util.enums.CharacterType;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`player`")
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long playerId;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "character_name")
    private CharacterType characterType;

    @Column(name = "seat_num")
    private Integer seatNum;
}
