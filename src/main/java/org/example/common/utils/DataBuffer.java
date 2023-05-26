package org.example.common.utils;

import com.google.gson.Gson;
import org.example.common.entities.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBuffer {
    public static Socket socket;
    public static Socket chatSocket;
    public static Gson gson = new Gson();
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;
    public static ObjectOutputStream coos;
    public static ObjectInputStream cois;
    public static User userInfo;
    public static Map<String, String> friendsMap = new HashMap<>(); // UUID -> Username
    public static List<String> onlineList = new ArrayList<>();
}
