package org.example.common.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -1234915906614662334L;

    public String username;
    public String account;
    public String password;
    public Integer groupId;
    public String uuid;
}
