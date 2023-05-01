import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { DataResponse } from '../models/dataResponse.model';
import { GameModeType } from '../enums/gameModeType.enum';
import { TeamType } from '../enums/teamType.enum';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private hostUrl = 'http://localhost:8080';

  constructor(
    private http: HttpClient
  ) { }

  joinGameByGameNum(roomNum: number): Observable<DataResponse> {
    return this.http.post<DataResponse>(this.hostUrl + '/game/join/' + roomNum, {});
  }

  createGame(size: number, roomNum:number, gameMode:GameModeType): Observable<any> {
    return this.http.post<DataResponse>(this.hostUrl + '/game/new?size=' + size + "&roomNum=" + roomNum + "&gameMode=" + gameMode, {});
  }

  findGameByGameId(gameId: number): Observable<DataResponse> {
    return this.http.get<DataResponse>(this.hostUrl + '/game/' + gameId);
  }

  startGame(gameId: number): Observable<DataResponse> {
    return this.http.get<DataResponse>(this.hostUrl + '/game/' + gameId + '/start');
  }

  createTeam(gameId: number, roundId: number, team: number[], type: TeamType): Observable<DataResponse> {
    return this.http.post<DataResponse>(this.hostUrl + '/game/' + gameId + '/round/' + roundId + '/team/new', {teamMembers: team, teamType: type});
  }

  vote(gameId: number, roundId: number, accept: boolean): Observable<DataResponse> {
    return this.http.post<DataResponse>(this.hostUrl + '/game/' + gameId + '/round/' + roundId + '/vote/new?accept=' + accept, {});
  }

  mission(gameId: number, roundId: number, success: boolean): Observable<DataResponse> {
    return this.http.post<DataResponse>(this.hostUrl + '/game/' + gameId + '/round/' + roundId + '/mission/new?success=' + success, {});
  }

  assassinFlop(gameId: number): Observable<DataResponse> {
    return this.http.patch<DataResponse>(this.hostUrl + '/game/' + gameId + '/assassin/flop', {});
  }

  assassinAssassinate(gameId: number, targetId: number): Observable<DataResponse> {
    return this.http.post<DataResponse>(this.hostUrl + '/game/' + gameId + '/assassinate/' + targetId, {});
  }


}
