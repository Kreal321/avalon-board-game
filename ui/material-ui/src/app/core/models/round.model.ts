import { RoundStatus } from "../enums/roundStatus.enum";
import { Player } from "./player.model";
import { Team } from "./team.model.";
import { Vote } from "./vote.model";

export interface Round {
    roundId: number;
    questNum: number;
    gameId: number;
    roundNum: number;
    leaderId: number;
    leader: Player;
    teamSize: number;
    roundStatus: RoundStatus;
    teams: Team[];
    votes: Vote[];
}