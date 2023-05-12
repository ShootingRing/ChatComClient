package org.example.UI;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import org.example.common.entities.Login;
import org.example.common.utils.Response;

import javax.swing.*;
import java.io.IOException;

public class MainForm {
    private JPanel panel1;
    private JButton loginButton;
    private JButton registerButton;
    private JTextField account;
    private JPasswordField password;
    private JLabel accountLabel;
    private JLabel pwdLabel;

    public MainForm() {
        loginButton.addActionListener(e -> {
            //获取登录参数
            String acc = account.getText();
            String pwd = String.valueOf(password.getPassword());

            try {
                Response res = new Login(acc, pwd).login();
                System.out.println("login: " + res.code);
                switch (res.code){
                    case SUCCESS -> {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "登陆成功！","登陆成功", JOptionPane.ERROR_MESSAGE);
                    }

                    case WRONG_PASSWORD_OR_ACCOUNT -> {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "账号或密码错误，请重试！","账号或密码错误", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        registerButton.addActionListener(e -> {

        });
    }

    public void createAndShow() {
        FlatDarculaLaf.install();

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        }catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        JFrame frame = new JFrame("ChatCom");

        MainForm mainForm = new MainForm();
        frame.setContentPane(mainForm.panel1);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
    }
}
