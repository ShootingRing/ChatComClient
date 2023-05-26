package org.example.common.utils;

import static org.example.common.utils.DataBuffer.friendsMap;

public class ResponseHandler {
    public static void sendBack(Response response) {
        System.out.println("myself: " + (String)response.data);
    }

    public static void receiveMessage(Response response) {
        Message message = (Message)response.data;
        System.out.println(friendsMap.get(message.from) + ": " + message.content);
    }
}
