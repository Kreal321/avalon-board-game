import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
    providedIn: 'root'
})
export class StompService {

    socket = new SockJS("http://localhost:8080/stomp");
    socketClient = Stomp.over(this.socket);

    constructor() { }
        
    subscribe(gameId: number, callback: any): void {

        this.socket = new SockJS("http://localhost:8080/stomp");
        this.socketClient = Stomp.over(this.socket);

        const connected: boolean = this.socketClient.connected;
        if (connected) {
            this.subscribeToGame(gameId, callback);
            return;
        }

        this.socketClient.connect({}, () => {
            this.subscribeToGame(gameId, callback);
        });


    }

    private subscribeToGame(gameId: number, callback: any): void {
        this.socketClient.subscribe(`/topic/game/${gameId}`, (message) => {
            callback(message);
        });
    }

    disconnect(): void {
        this.socketClient.disconnect(
            () => console.log("Disconnected")
        );
    }



}