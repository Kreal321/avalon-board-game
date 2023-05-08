import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { DataResponse } from '../models/dataResponse.model';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private hostUrl;

  constructor(
    private http: HttpClient
  ) { 
    this.hostUrl = environment.api;
  }

  loginWithCredentials(username: string, userHash: string, password: string): Observable<DataResponse> {
    return this.http.post<DataResponse>(this.hostUrl + '/user/login', {username, userHash, password});
  }

  register(username: string, userHash: string, email: string, preferredName: string): Observable<DataResponse> {
    return this.http.post<DataResponse>(this.hostUrl + '/user/register', {username, userHash, email, preferredName});
  }

  registerForTempGuest(): Observable<DataResponse> {
    return this.http.post<DataResponse>(this.hostUrl + '/user/register/temp', {});
  }

  update(username: string, userHash: string, email: string, preferredName: string): Observable<DataResponse> {
    return this.http.patch<DataResponse>(this.hostUrl + '/user/update', {username, userHash, email, preferredName});
  }

  me(): Observable<DataResponse> {
    return this.http.get<DataResponse>(this.hostUrl + '/user/me');
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('token') != null;
  }

  getRecords(): Observable<DataResponse> {
    if (!this.isLoggedIn()) {
      return of({success: false, data: null, message: 'Not logged in', token: ''});
    }
    return this.http.get<DataResponse>(this.hostUrl + '/game/records');
  }

}
