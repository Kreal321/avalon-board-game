import { Pipe, PipeTransform } from '@angular/core';
import { GameStatus } from 'src/app/core/enums/gameStatus.enum';
import { RoundStatus } from 'src/app/core/enums/roundStatus.enum';

@Pipe({name: 'enumPrint'})

export class EnumPrintPipe implements PipeTransform {

    constructor() {}
    
    transform(text: undefined | RoundStatus | GameStatus): string {        
        switch (text) {
            case RoundStatus.INITIAL_TEAM:
            case RoundStatus.DISCUSSING:
            case RoundStatus.FINAL_TEAM_VOTING:
            case RoundStatus.FINAL_TEAM_VOTING_SUCCESS:
            case RoundStatus.FINAL_TEAM_VOTING_FAIL:
            case RoundStatus.QUEST_SUCCESS:
            case RoundStatus.QUEST_FAIL:
                return text.split("_").map(word => word.charAt(0) + word.slice(1).toLowerCase()).join(" ");

            default:
                if (text === undefined || text == null) return "";
                return text.split("_").map(word => word.charAt(0) + word.slice(1).toLowerCase()).join(" ");
        }
    }
}