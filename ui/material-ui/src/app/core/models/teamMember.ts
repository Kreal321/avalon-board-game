import { TeamMemberStatus } from "../enums/teamMemberStatus.enum";
import { Player } from "./player.model";

export interface TeamMember {
    teamMemberId: number;
    teamId: number;
    player: Player;
    status: TeamMemberStatus;
}