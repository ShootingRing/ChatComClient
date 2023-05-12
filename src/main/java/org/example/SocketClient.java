package org.example;

import org.example.UI.MainForm;
import org.example.client.threads.ClientThread;
import org.example.common.utils.DataBuffer;
import org.example.common.utils.Response;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

import static org.example.common.utils.DataBuffer.*;

public class SocketClient {
    public static String name = "client 1";
    public static void main(String[] args) throws Exception {
        //建立Socket连接
        //Socket socket = new Socket("47.98.44.250", 5208);

        try {
            socket = new Socket("127.0.0.1", 5208);
            System.out.println(name + " linking...");
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "连接失败，请检查网络环境后重试！","连接失败", JOptionPane.ERROR_MESSAGE);//否则连接失败
            System.exit(0);
        }

        //启动UI界面
        javax.swing.SwingUtilities.invokeLater(() -> new MainForm().createAndShow());

    }
}
