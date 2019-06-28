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
    private static final Gson gson = new Gson();
    private static final LineBorder redMessageServerBorder = new LineBorder(Color.red, 5, true);
    LineBorder blueMessageUserBorder = new LineBorder(Color.blue, 5, true);

    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private JButton loginInButton;
    private JLabel userNameLabel;
    private JPanel messageArea;
    private JTextArea jTextArea;
    private JButton sendMessage;
    private JScrollPane scrollPane;
    private JPanel myPanel;
    private JTextField nickNameField = new JTextField(15);
    private JTextField portField = new JTextField(15);
    private JTextArea users = new JTextArea("Пока пусто:(");

    private volatile boolean isRegister = false;

    private ClientInterface() {
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
                    Message message = new Message();
                    message.setType(MessageType.LOG_OUT);
                    writer.println(gson.toJson(message));
                    writer.flush();
                }
            }
        });
    }

    private void logIn() {
        int result = JOptionPane.showConfirmDialog(null, myPanel,
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
            writer.println(gson.toJson(message));
            writer.flush();
            Message responseFromServer = gson.fromJson(reader.readLine(), Message.class);
            if (responseFromServer.getStatus().equals(MessageStatus.OK)) {
                userNameLabel.setText(userName);
                loginInButton.setText("Разлогиниться");
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
        loginInButton.setText("Присоединиться");
        Message message = new Message();
        message.setType(MessageType.LOG_OUT);
        writer.println(gson.toJson(message));
        writer.flush();
        try {
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setAuthorization() {
        loginInButton.addActionListener(e -> {
            if (!isRegister) {
                logIn();
            } else {
                logOut();
            }
        });
    }

    private void sendMessage() {
        jTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jTextArea.setText("");
            }
        });
        sendMessage.addActionListener(e -> {
            if (isRegister) {
                Message message = new Message();
                message.setData(jTextArea.getText());
                System.out.println("Попытка отправить сообщение: " + message);
                try {
                    writer.println(gson.toJson(message));
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
        loginInButton = new JButton("Присоединиться");
        userNameLabel = new JLabel("Вход не выполнен");
        topPanel.add(userNameLabel);
        topPanel.add(loginInButton);

        messageArea = new JPanel();
        messageArea.setLayout(new BoxLayout(messageArea, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(messageArea);

        JPanel userInActive = new JPanel();
        userInActive.setLayout(new BoxLayout(userInActive, BoxLayout.Y_AXIS));
        JScrollPane scrollPaneUserInActive = new JScrollPane(userInActive);
        userInActive.add(new JLabel("Пользователи в сети:"));
        users.setEditable(false);
        users.setLineWrap(true);
        users.setWrapStyleWord(true);
        JScrollPane scrollPaneUsers = new JScrollPane(users, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        userInActive.add(scrollPaneUsers);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        jTextArea = new JTextArea("Введите сообщение");
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        sendMessage = new JButton("Отправить");
        JScrollPane jsp = new JScrollPane(jTextArea);
        jsp.setPreferredSize(new Dimension(800, 100));
        bottomPanel.add(jsp, BorderLayout.CENTER);
        bottomPanel.add(sendMessage, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(scrollPaneUserInActive, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        setSize(1000, 1000);

        nickNameField = new JTextField(15);
        portField = new JTextField(15);

        myPanel = new JPanel();
        myPanel.add(new JLabel("Ник:"));
        myPanel.add(nickNameField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Порт"));
        myPanel.add(portField);
        setResizable(false);
        setVisible(true);
    }

    private void getMessage() {
        Thread thread = new Thread(() -> {
            while (true) {
                if (isRegister) {
                    try {
                        Message message = gson.fromJson(reader.readLine(), Message.class);
                        if (MessageType.USER_ONLINE.equals(message.getType())) {
                            users.setText(message.getData());
                        } else if (MessageType.MESSAGE.equals(message.getType())) {
                            JLabel messageLabel = new JLabel(message.toString());
                            messageLabel.setBorder(blueMessageUserBorder);
                            messageArea.add(messageLabel);
                            messageArea.add(Box.createVerticalStrut(5));
                            scrollPane.revalidate();
                        } else if (MessageType.SERVER_MESSAGE.equals(message.getType())) {
                            JLabel messageLabel = new JLabel(message.toString());
                            messageLabel.setBorder(redMessageServerBorder);
                            messageArea.add(messageLabel);
                            messageArea.add(Box.createVerticalStrut(5));
                            scrollPane.revalidate();
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

    public static void main(String[] args) {
        ClientInterface clientInterface = new ClientInterface();
    }
}
