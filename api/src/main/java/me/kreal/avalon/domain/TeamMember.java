package me.kreal.avalon.domain;

import lombok.*;
import me.kreal.avalon.util.enums.TeamMemberStatus;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`team_member`")
public class TeamMember implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tm_id")
    private Long teamMemberId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(name = "player_id", nullable = false, insertable = false, updatable = false)
    private Long playerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tm_status", nullable = false)
    private TeamMemberStatus status;

}
