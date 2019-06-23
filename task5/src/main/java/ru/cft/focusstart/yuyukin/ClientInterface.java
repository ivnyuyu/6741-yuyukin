package ru.cft.focusstart.yuyukin;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class ClientInterface extends JFrame {
    Socket clientSocket;
    JButton loginInButton;
    JPanel topPanel;
    JLabel userName;
    JPanel messageArea;
    JPanel userInActive;
    JPanel bottomPanel;
    JTextArea jTextArea;
    JButton sendMessage;

    public ClientInterface() {
        super("Чат");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        topPanel = new JPanel();
        loginInButton = new JButton("Присоединиться");
        userName = new JLabel("Вход не выполнен");
        topPanel.add(userName);
        topPanel.add(loginInButton);

        messageArea=new JPanel();
        messageArea.add(new JLabel("Пока список сообщений пуст!"));
        userInActive=new JPanel();
        userInActive.add(new JLabel("Пользователи в сети:"));

        bottomPanel=new JPanel(new BorderLayout());
        jTextArea=new JTextArea("Введите сообщение");
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        sendMessage=new JButton("Отправить");
        JScrollPane jsp = new JScrollPane(jTextArea);
        jsp.setPreferredSize(new Dimension(800,100));

        loginInButton.addActionListener(e->{
            String userName = JOptionPane.showInputDialog(
                    this,
                    "Введите имя под которым вы хотите сидеть в чате");
            try {
                clientSocket=new Socket("localhost",3443 );
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Не удалось подключиться к серверу, попробуйте позже!");
            }
        });

        bottomPanel.add(jsp, BorderLayout.CENTER);
        bottomPanel.add(sendMessage, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        add(messageArea,BorderLayout.CENTER);
        add(userInActive,BorderLayout.EAST);
        add(bottomPanel,BorderLayout.SOUTH);
        setSize(1000, 1000);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        ClientInterface clientInterface = new ClientInterface();
    }
}
