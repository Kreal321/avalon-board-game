import { Player } from "./player.model";

export interface TeamMember {
    teamMemberId: number;
    teamId: number;
    player: Player;
}