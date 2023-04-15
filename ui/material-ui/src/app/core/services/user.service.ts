import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { DataResponse } from '../models/dataResponse.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private hostUrl = 'http://localhost:8080';
  
  constructor(
    private http: HttpClient
  ) { }

  loginWithCredentials(username: string, userHash: string, password: string): Observable<DataResponse> {
    return this.http.post<DataResponse>(this.hostUrl + '/user/login', {username, userHash, password});
  }

  register(username: string, userHash: string, email: string, preferredName: string): Observable<DataResponse> {
    return this.http.post<DataResponse>(this.hostUrl + '/user/register', {username, userHash, email, preferredName});
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('token') != null;
  }

}
