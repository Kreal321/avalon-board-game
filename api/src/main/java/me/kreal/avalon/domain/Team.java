package me.kreal.avalon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import me.kreal.avalon.util.enums.RoundStatus;
import me.kreal.avalon.util.enums.TeamType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`team`")
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "round_id")
    private Round round;

    @Column(name = "round_id", insertable = false, updatable = false)
    private Long roundId;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_type", nullable = false)
    private TeamType teamType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>();

    public void addTeamMembers(List<TeamMember> teamMembers) {
        for (TeamMember teamMember : teamMembers) {
            this.teamMembers.add(teamMember);
            teamMember.setTeam(this);
        }
    }

}
