package org.example.UI;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import org.example.client.threads.ChatThread;
import org.example.common.entities.Login;
import org.example.common.entities.Register;
import org.example.common.entities.User;
import org.example.common.utils.Request;
import org.example.common.utils.Response;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

import static org.example.common.utils.DataBuffer.*;
import static org.example.common.utils.RequestType.HELLO;

public class MainForm {
    private JFrame frame;
    private JPanel loginPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JTextField account;
    private JPasswordField password;
    private JLabel accountLabel;
    private JLabel pwdLabel;
    private JPanel registerPanel;
    private JPanel wrapper;
    private JTextField regUsername;
    private JTextField regAccount;
    private JPasswordField regPassword;
    private JPasswordField regPasswordRep;
    private JButton regButton;
    private JButton regBackButton;

    private CardLayout cl;

    public MainForm() {
        FlatDarculaLaf.install();

        frame = new JFrame("ChatCom");

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        }catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        cl = new CardLayout();

        wrapper = new JPanel(cl);
        wrapper.add(loginPanel);
        wrapper.add(registerPanel);

        loginButton.addActionListener(e -> {
            //获取登录参数
            String acc = account.getText();
            String pwd = String.valueOf(password.getPassword());

            //对表单格式进行验证
            if(acc == null || acc.equals("")){
                JOptionPane.showMessageDialog(new JFrame(), "账号或密码不能为空", "警告", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(pwd == null || pwd.equals("")){
                JOptionPane.showMessageDialog(new JFrame(), "账号或密码不能为空", "警告", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Response res = new Login(acc, pwd).login();
                System.out.println("login: " + res.code);
                System.out.println("data: " + res.data);
                switch (res.code){
                    case SUCCESS -> {
                        JOptionPane.showMessageDialog(new JFrame(), "登陆成功！","登陆成功", JOptionPane.ERROR_MESSAGE);

                        userInfo = (User) res.data;

                        Request hello = new Request(
                                HELLO,
                                userInfo.uuid
                        );
                        hello.requestWithNoResponse(); //建立连接后发送HELLO请求

                        System.out.println("userInfo: " + gson.toJson(userInfo));

                        login(userInfo);
                    }

                    case WRONG_PASSWORD_OR_ACCOUNT -> {
                        JOptionPane.showMessageDialog(new JFrame(), "账号或密码错误，请重试！","账号或密码错误", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        registerButton.addActionListener(e -> {
            System.out.println("client: into register panel");
            cl.next(wrapper);
        });

        regButton.addActionListener(e -> {
            //请求
            try {

                //获取输入内容
                String username = regUsername.getText();
                String account = regAccount.getText();
                String password = String.valueOf(regPassword.getPassword());
                String passwordRep = String.valueOf(regPasswordRep.getPassword());

                //客户端本地输入验证
                if (Objects.equals(username, "") || username == null){
                    showMessage("请将信息填写完整", "警告", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (Objects.equals(account, "") || account == null){
                    showMessage("请将信息填写完整", "警告", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (Objects.equals(password, "")){
                    showMessage("请将信息填写完整", "警告", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (Objects.equals(passwordRep, "")){
                    showMessage("请将信息填写完整", "警告", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Objects.equals(passwordRep, password)){
                    showMessage("两次输入的密码请保持一致", "警告", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Response res = new Register(
                        username,
                        password,
                        account
                ).register();

                //视图操作
                switch (res.code){
                    case SUCCESS -> {
                        showMessage("注册成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    }

                    case USERNAME_EXISTS -> {
                        showMessage("用户名已存在", "警告", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    case ACCOUNT_EXISTS -> {
                        showMessage("账号已存在", "警告", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                //返回登录页面
                System.out.println("client: register success and back to login panel");
                cl.previous(wrapper);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        regBackButton.addActionListener(e -> {
            System.out.println("client: back to login panel");
            cl.previous(wrapper);
                });
    }



    public void createAndShow() {
        MainForm mainForm = new MainForm();
        frame.setContentPane(mainForm.wrapper);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        frame.setVisible(true);
    }

    public void showMessage(String message, String title, int messageType){
        JOptionPane.showMessageDialog(new JFrame(), message, title, messageType);
    }

    public void login(User user){
        //视图操作函数
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                new ChatForm().createAndShow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        frame.setVisible(false);
        frame.dispose();
    }
}
