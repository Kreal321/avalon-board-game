import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from 'src/environments/environment';


@Injectable({
    providedIn: 'root'
})
export class StompService {

    private hostUrl;
    private socket;
    private socketClient;

    constructor() {
        this.hostUrl = environment.api + "/stomp";
        this.socket = new SockJS(this.hostUrl);
        this.socketClient = Stomp.over(this.socket);
     }
        
    subscribe(gameId: number, callback: any): void {

        this.socket = new SockJS(this.hostUrl);

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