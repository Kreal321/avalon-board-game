import { Pipe, PipeTransform } from '@angular/core';
import { GameStatus } from 'src/app/core/enums/gameStatus.enum';

@Pipe({name: 'gameStatus'})

export class GameStatusPipe implements PipeTransform {

    constructor() {}
    
    transform(status: string | GameStatus | undefined, mode: string): boolean {
        if (!status) return false;
        switch (mode) {
            case 'isNotFinished':
                return !(status == GameStatus.EVIL_WON_WITH_ASSASSINATION || status == GameStatus.EVIL_WON_WITH_QUEST || status == GameStatus.GOOD_WON);
            case 'isInProgress':
                return status == GameStatus.IN_PROGRESS;
            case 'isFinished':
                return status == GameStatus.EVIL_WON_WITH_ASSASSINATION || status == GameStatus.EVIL_WON_WITH_QUEST || status == GameStatus.GOOD_WON;
            default:
                return false;
        }
    }
}