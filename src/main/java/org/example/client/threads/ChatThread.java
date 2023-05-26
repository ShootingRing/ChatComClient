package org.example.client.threads;

import org.example.common.utils.Response;
import org.example.common.utils.ResponseHandler;

import java.io.IOException;

import static org.example.common.utils.DataBuffer.cois;

public class ChatThread implements Runnable {
    @Override
    public void run() {
        try{
            //读取response
            System.out.println("chatThread is reading");
            Response response = (Response) cois.readObject();

            System.out.println("chatThread catch response:" + response);

            switch (response.code){
                case SEND_BACK -> {
                    ResponseHandler.sendBack(response);
                    break;
                }
                case RECEIVE_MESSAGE -> {
                    ResponseHandler.receiveMessage(response);
                    break;
                }
                default -> {
                    break;
                }
            }

        }catch (IOException e){
            System.out.println("error");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
