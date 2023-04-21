import { Team } from "./team.model.";

export interface Round {
    roundId: number;
    questNum: number;
    roundNum: number;
    leaderId: number;
    teamSize: number;
    roundStatus: string;
    teams: Team[];
}