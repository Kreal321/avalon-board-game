import { TeamMember } from "./teamMember";

export interface Team {
    teamId: number;
    roundId: number;
    teamType: string;
    teamMembers: TeamMember[];
}