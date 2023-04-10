import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpHeaders, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import Swal from 'sweetalert2'
import { DataResponse } from '../models/dataResponse.model';



@Injectable()
export class AuthService implements HttpInterceptor {

    constructor(
        private router: Router
    ) { }

    intercept(
        req: HttpRequest<any>,
        next: HttpHandler
    ): Observable<HttpEvent<any>> {

        const headers = new HttpHeaders()
            .set('Content-Type', 'application/json')
            .set('Authorization', 'Bearer ' + localStorage.getItem('token'));

        console.log('intercepted request ... ');

        return next.handle(req.clone(
            {
                headers
            }
        )).pipe(
            tap({
                next: (event: HttpResponse<DataResponse>) => {
                    if (event instanceof HttpResponse<DataResponse>) {
                        (event.body as DataResponse).token == null ? null : localStorage.setItem('token', (event.body as DataResponse).token);
                    }
                }
            }),
            catchError((error: HttpErrorResponse) => {
                if (error instanceof HttpErrorResponse) {
                    if (error.status == 403) {
                        Swal.fire({
                            title: 'You are not logged in',
                            text: 'Please login first',
                            icon: 'error',
                            confirmButtonText: 'Login',
                        }).then((request) => {
                            if (request.isConfirmed) {
                                this.router.navigate(['/login']);
                            }
                        })
                    } else if (error.status == 400) {
                        Swal.fire({
                            title: 'Bad Request',
                            text: error.error.message,
                            icon: 'error',
                        })
                    } else {
                        Swal.fire({
                            title: 'Network Error',
                            text: 'Please try again later',
                            icon: 'error',
                        })
                        console.error('An unexpected error occurred: ', error.error.message);
                        throwError(() => error);
                    }
                }
                return of([]);
            })
        ) as Observable<HttpEvent<any>>;

    }

}
