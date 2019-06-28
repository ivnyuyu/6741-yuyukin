package ru.cft.focusstart.yuyukin;

import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientInterface extends JFrame {
    private static final Gson GSON = new Gson();
    private static final LineBorder RED_MESSAGE_SERVER_BORDER = new LineBorder(Color.red, 5, true);
    private static final LineBorder BLUE_MESSAGE_USER_BORDER = new LineBorder(Color.blue, 5, true);

    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private JButton loginButton;
    private JLabel userNameLabel;
    private JPanel messageArea;
    private JTextArea sendMessageArea;
    private JButton sendMessage;
    private JScrollPane scrollPaneMessageArea;
    private JPanel authorizationPanel;
    private JTextField nickNameField = new JTextField(15);
    private JTextField portField = new JTextField(15);
    private JTextArea usersOnline = new JTextArea("Пока пусто:(");

    private volatile boolean isRegister = false;

    ClientInterface() {
        super("Чат");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGraphicalElement();
        setAuthorization();
        getMessage();
        sendMessage();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (isRegister) {
                    exitFromChat();
                }
            }
        });
    }

    private void exitFromChat() {
        Message message = new Message();
        message.setType(MessageType.LOG_OUT);
        writer.println(GSON.toJson(message));
        writer.flush();
        try {
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("Не удалось закрыть сокет " + ex.getMessage());
        }
    }

    private void logIn() {
        int result = JOptionPane.showConfirmDialog(null, authorizationPanel,
                "Please Enter Nickname and Port", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        String userName = nickNameField.getText();
        int portName;
        try {
            portName = Integer.parseInt(portField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Название порта должен содержать только цифры!");
            return;
        }
        if (userName == null) {
            return;
        }
        if (userName.trim().equals("")) {
            return;
        }
        Message message = new Message();
        message.setType(MessageType.AUTHORIZATION);
        message.setUserName(userName);
        try {
            clientSocket = new Socket("localhost", portName);
            writer = new PrintWriter(clientSocket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer.println(GSON.toJson(message));
            writer.flush();
            Message responseFromServer = GSON.fromJson(reader.readLine(), Message.class);
            if (responseFromServer.getStatus().equals(MessageStatus.OK)) {
                userNameLabel.setText(userName);
                loginButton.setText("Разлогиниться");
                isRegister = true;
            } else {
                JOptionPane.showMessageDialog(this,
                        "Данный никнейм уже занят!");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Не удалось подключиться к серверу, попробуйте позже!");
        }
    }

    private void logOut() {
        isRegister = false;
        userNameLabel.setText("Вход не выполнен");
        loginButton.setText("Присоединиться");
        exitFromChat();
    }

    private void setAuthorization() {
        loginButton.addActionListener(e -> {
            if (!isRegister) {
                logIn();
            } else {
                logOut();
            }
        });
    }

    private void sendMessage() {
        sendMessageArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                sendMessageArea.setText("");
            }
        });
        sendMessage.addActionListener(e -> {
            if (isRegister) {
                if (sendMessageArea.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Введите не пустое сообщение!");
                    return;
                }
                Message message = new Message();
                message.setData(sendMessageArea.getText());
                try {
                    writer.println(GSON.toJson(message));
                    writer.flush();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Вы отключены от сервера! Попробуйте подключиться!");
                }
            }
        });
    }

    private void initGraphicalElement() {
        JPanel topPanel = new JPanel();
        loginButton = new JButton("Присоединиться");
        userNameLabel = new JLabel("Вход не выполнен");
        topPanel.add(userNameLabel);
        topPanel.add(loginButton);

        messageArea = new JPanel();
        messageArea.setLayout(new BoxLayout(messageArea, BoxLayout.Y_AXIS));
        scrollPaneMessageArea = new JScrollPane(messageArea);

        JPanel userInActive = new JPanel();
        userInActive.setLayout(new BoxLayout(userInActive, BoxLayout.Y_AXIS));
        JScrollPane scrollPaneUserInActive = new JScrollPane(userInActive);
        userInActive.add(new JLabel("Пользователи в сети:"));
        usersOnline.setEditable(false);
        usersOnline.setLineWrap(true);
        usersOnline.setWrapStyleWord(true);
        JScrollPane scrollPaneUsers = new JScrollPane(usersOnline, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        userInActive.add(scrollPaneUsers);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        sendMessageArea = new JTextArea("Введите сообщение");
        sendMessageArea.setLineWrap(true);
        sendMessageArea.setWrapStyleWord(true);
        sendMessage = new JButton("Отправить");
        JScrollPane scrollPaneSendMessageArea = new JScrollPane(sendMessageArea);
        scrollPaneSendMessageArea.setPreferredSize(new Dimension(800, 100));
        bottomPanel.add(scrollPaneSendMessageArea, BorderLayout.CENTER);
        bottomPanel.add(sendMessage, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        add(scrollPaneMessageArea, BorderLayout.CENTER);
        add(scrollPaneUserInActive, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        setSize(1000, 1000);

        nickNameField = new JTextField(15);
        portField = new JTextField(15);

        authorizationPanel = new JPanel();
        authorizationPanel.add(new JLabel("Ник:"));
        authorizationPanel.add(nickNameField);
        authorizationPanel.add(Box.createHorizontalStrut(15));
        authorizationPanel.add(new JLabel("Порт"));
        authorizationPanel.add(portField);
        setResizable(false);
        setVisible(true);
    }

    private void getMessage() {
        Thread thread = new Thread(() -> {
            while (true) {
                if (isRegister) {
                    try {
                        Message message = GSON.fromJson(reader.readLine(), Message.class);
                        if (MessageType.USER_ONLINE.equals(message.getType())) {
                            usersOnline.setText(message.getData());
                        } else if (MessageType.MESSAGE.equals(message.getType())) {
                            JLabel messageLabel = new JLabel(message.toString());
                            messageLabel.setBorder(BLUE_MESSAGE_USER_BORDER);
                            messageArea.add(messageLabel);
                            messageArea.add(Box.createVerticalStrut(5));
                            scrollPaneMessageArea.revalidate();
                        } else if (MessageType.SERVER_MESSAGE.equals(message.getType())) {
                            JLabel messageLabel = new JLabel(message.toString());
                            messageLabel.setBorder(RED_MESSAGE_SERVER_BORDER);
                            messageArea.add(messageLabel);
                            messageArea.add(Box.createVerticalStrut(5));
                            scrollPaneMessageArea.revalidate();
                        }
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this,
                                "Вы отключились от сервера!");
                    }
                }
            }
        });
        thread.start();
    }
}
