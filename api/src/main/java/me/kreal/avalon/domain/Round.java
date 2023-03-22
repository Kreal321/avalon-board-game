package me.kreal.avalon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import me.kreal.avalon.util.enums.GameStatus;
import me.kreal.avalon.util.enums.RoundStatus;

import javax.persistence.*;
import java.io.Serializable;
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
@Table(name = "`round`")
public class Round implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "round_id")
    private Long roundId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "game_id", insertable = false, updatable = false)
    private Long gameId;

    @Column(name = "quest_num", nullable = false)
    private Integer questNum;

    @Column(name = "round_num", nullable = false)
    private Integer roundNum;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "leader_player_id")
    private Player leader;

    @Column(name = "team_size", nullable = false)
    private Integer teamSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "round_status", nullable = false)
    private RoundStatus roundStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "round")
    private List<Vote> votes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "round")
    private List<Team> teams = new ArrayList<>();

    public void addVote(Vote vote) {
        this.votes.add(vote);
        vote.setRound(this);
    }

    public void addTeam(Team team) {
        this.teams.add(team);
        team.setRound(this);
    }

}
