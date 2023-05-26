package org.example.UI;

import com.formdev.flatlaf.ui.FlatTableCellBorder;
import org.example.common.entities.User;
import org.example.common.utils.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.common.utils.DataBuffer.*;

public class ChatForm {
    JFrame frame;
    private JPanel wrapper;
    private JPanel chatPanel;
    private JTextArea inputArea;
    private JScrollPane messageArea;
    private JToolBar inputToolBar;
    private JList friendsList;
    private JScrollPane friendsPanel;
    private JButton sendButton;
    private JButton bufferButton;

    public ChatForm() throws IOException {
        frame = new JFrame("ChatCom");

        if(userInfo == null) {
            showMessage("未获取到登录信息，请重新登陆", "警告", JOptionPane.ERROR_MESSAGE);
            return;
        }

        getList();

        sendButton.addActionListener(e -> {
            try {
                sentMessage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyChar() == KeyEvent.VK_ENTER )
                {
                    try {
                        sentMessage();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };

        inputArea.addKeyListener(keyListener);
    }

    public void createAndShow() throws IOException {

        frame.setContentPane(wrapper);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    public void sentMessage() throws IOException {
        String content = inputArea.getText();
        List<String> to = onlineList; //默认发给所有在线用户，即公屏聊天模式

        Message message = Message.builder()
                .from(userInfo.uuid)
                .to(to)
                .content(content)
                .build();

        message.sendMessage();

    }
    private void getList() throws IOException {
        Response res = new Request(
                RequestType.GET_FRIENDS_LIST,
                userInfo
        ).request();

        if(res.code != ResponseCode.SUCCESS){
            showMessage("未获取到登录信息，请重新登陆", "警告", JOptionPane.ERROR_MESSAGE);
            return;
        }

        friendsMap = (Map<String, String>) res.data;

        res = new Request(
                RequestType.GET_ONLINE_FRIENDS,
                userInfo
        ).request();

        if(res.code != ResponseCode.SUCCESS){
            showMessage("未获取到登录信息，请重新登陆", "警告", JOptionPane.ERROR_MESSAGE);
            return;
        }

        onlineList = (List<String>) res.data;

        DefaultListModel<String> model = new DefaultListModel<>();
        for (String key : friendsMap.keySet()) {
            model.addElement(friendsMap.get(key) + (onlineList.contains(key)?"(online)":"(offline)"));
            System.out.println(friendsMap.get(key) + (onlineList.contains(key)?"(online)":"(offline)"));
        }

        friendsList.setModel(model);
    }

    private void showMessage(String message, String title, int messageType){
        JOptionPane.showMessageDialog(new JFrame(), message, title, messageType);
    }
}
