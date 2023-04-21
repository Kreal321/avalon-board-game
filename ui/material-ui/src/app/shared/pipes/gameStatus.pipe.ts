import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'gameStatus'})

export class GameStatusPipe implements PipeTransform {

    constructor() {}
    
    transform(status: string | undefined, mode: string): boolean {
        if (!status) return false;
        switch (mode) {
            case 'isNotFinished':
                return !(status == "EVIL_WON_WITH_ASSASSINATION" || status == "EVIL_WON_WITH_QUEST" || status == "GOOD_WON");
            case 'isInProgress':
                return status == 'In Progress';
            case 'isFinished':
                return status == 'Finished';
            default:
                return false;
        }
    }
}