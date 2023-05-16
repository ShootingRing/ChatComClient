package org.example.common.entities;

import org.example.common.utils.Request;
import org.example.common.utils.Response;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import static org.example.common.utils.RequestType.LOGIN;
import static org.example.common.utils.RequestType.REGISTER;

public class Register implements Serializable {
    @Serial
    private static final long serialVersionUID = -8504915906614662343L;
    public String username;
    public String password;
    public String account;

    public Register(String username, String password, String account){
        this.username = username;
        this.password = password;
        this.account = account;
    }

    public Response register() throws IOException {
        return new Request(
                REGISTER,
                this
        ).request();
    }
}
