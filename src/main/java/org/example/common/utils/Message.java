package org.example.common.utils;

import lombok.Builder;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 8504915852014662338L;

    public String from; //消息发送方UUID
    public List<String> to; //消息接收方UUID列表
    public String content; //消息内容

    public void sendMessage() throws IOException {
        new Request(
                RequestType.SEND_MESSAGE,
                this
        ).chatRequest();
    }
}
