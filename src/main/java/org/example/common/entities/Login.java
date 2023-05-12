package org.example.common.entities;

import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.example.common.utils.Request;
import org.example.common.utils.Response;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

import static org.example.common.utils.DataBuffer.gson;
import static org.example.common.utils.RequestType.LOGIN;
import static org.example.common.utils.ResponseCode.SUCCESS;
import static org.example.common.utils.ResponseCode.WRONG_PASSWORD_OR_ACCOUNT;

public class Login implements Serializable {
    @Serial
    private static final long serialVersionUID = -8504915906614662338L;
    public String acc;
    public String pwd;

    public Login(String acc, String pwd) {
        this.acc = acc;
        this.pwd = pwd;
    }

    public Response login() throws IOException {
        return new Request(
                LOGIN,
                this
        ).request();
    }
}
