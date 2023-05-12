package org.example.common.utils;

import java.io.*;
import java.net.Socket;

import static org.example.common.utils.DataBuffer.gson;

public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = -8504915906614662334L;
    public ResponseCode code;
    public String message;
    public String data;

    public Response(ResponseCode code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = gson.toJson(data);
    }

    public void response(Socket socket) {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ){
            //返回response
//            String res = gson.toJson(this);
//
//            if(!res.endsWith("\n")) {
//                res += "\n";
//            }

            oos.writeObject(this);
            oos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
