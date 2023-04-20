import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { DataResponse } from '../models/dataResponse.model';
import { GameModeType } from '../enums/gameModeType.enum';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private hostUrl = 'http://localhost:8080';

  constructor(
    private http: HttpClient
  ) { }

  joinGameByGameNum(roomNum: number): Observable<DataResponse> {
    return this.http.get<DataResponse>(this.hostUrl + '/game/join/' + roomNum);
  }

  createGame(size: number, roomNum:number, gameMode:GameModeType): Observable<any> {
    return this.http.post<DataResponse>(this.hostUrl + '/game/new?size=' + size + "&roomNum=" + roomNum + "&gameMode=" + gameMode, {});
  }

}