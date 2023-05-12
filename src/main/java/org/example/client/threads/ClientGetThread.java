package org.example.client.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientGetThread implements Runnable {
    public Socket socket;

    public ClientGetThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            while(true) {
                String str = br.readLine();
                System.out.println(str);
            }
        }catch (IOException e){
            System.out.println("error");
        }
    }
}
