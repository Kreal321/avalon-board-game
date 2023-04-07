import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import Swal from 'sweetalert2'
import { catchError, map, tap } from 'rxjs/operators';
import { DataResponse } from '../models/dataResponse.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      // 'Authorization': 'Bearer ' + localStorage.getItem('token'),
      // 'Authorization': 'Bearer ' + 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMiMwMDAyIn0.4kjieQsXKOzw2lovOUYw93Muoab3n46aBBSrjXbvc2o'

    })
  };

  private hostUrl = 'http://localhost:8080';
  
  constructor(
    private http: HttpClient
  ) { }

  private handleError() {
    return (response: HttpErrorResponse) => {
      if (response.status == 403) {
        Swal.fire({
          title: 'You are not logged in',
          text: 'Please login first',
          icon: 'error',
        })
      } else if (response.status == 400) {
        Swal.fire({
          title: 'Bad Request',
          text: response.error.message,
          icon: 'error',
        })
      } else {
        Swal.fire({
          title: 'Network Error',
          text: 'Please try again later',
          icon: 'error',
        })
        console.error('An unexpected error occurred:', response.error.message);
      }
      return of(response.error as DataResponse);
    };
  }

  // loginWithToken(token: string): Observable<any> {
  //   return this.http.get<DataResponse>(this.hostUrl + '/user/login', this.httpOptions).pipe(
  //     catchError(this.handleError()),
  //     tap(response => response.token == null ? null : localStorage.setItem('token', response.token)),
  //     map(response => response.data)
  //   );
  // }

  loginWithCredentials(username: string, userHash: string, password: string): Observable<any> {
    return this.http.post<DataResponse>(this.hostUrl + '/user/login', {username, userHash, password}, this.httpOptions).pipe(
      catchError(this.handleError()),
      tap(response => response.token == null ? null : localStorage.setItem('token', response.token)),
      map(response => response.data)
    );
  }

}
