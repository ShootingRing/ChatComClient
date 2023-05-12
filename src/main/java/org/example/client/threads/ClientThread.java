package org.example.client.threads;

import org.example.common.utils.Response;

import java.io.IOException;
import java.net.Socket;

import static org.example.common.utils.DataBuffer.ois;

public class ClientThread implements Runnable {
    public Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try{
            while(true) {
                //读取response
                Response response = (Response) ois.readObject();

                //TODO 处理response
                System.out.println("response:" + response);
            }
        }catch (IOException e){
            System.out.println("error");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
