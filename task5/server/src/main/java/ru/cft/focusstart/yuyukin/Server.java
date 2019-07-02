package ru.cft.focusstart.yuyukin;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

class Server {
    private static final Gson GSON = new Gson();
    private HandleUser handleUser;
    private ServerSocket server;

    Server() throws Exception {
        handleUser = new HandleUser();
        Properties properties = new Properties();
        try (InputStream propertiesStream = Server.class.getClassLoader().getResourceAsStream("server.properties")) {
            properties.load(propertiesStream);
        }
        int port;
        try {
            port = Integer.valueOf(properties.getProperty("server.port"));
        } catch (NumberFormatException e) {
            throw new Exception("Порт должен содержать только цифры");
        }
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            throw new Exception("Не удалось поднять сервер на порте" + port);
        }
        acceptNewUser();
        receiveMessage();
    }

    private void acceptNewUser() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Сервер ожидает пользователя");
                    Socket clientSocket = server.accept();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
                    String userName = bufferedReader.readLine();
                    Message message = GSON.fromJson(userName, Message.class);
                    if (handleUser.isNameFree(message.getUserName())) {
                        handleUser.addNewUser(new User(bufferedReader, printWriter, message.getUserName()));
                        responseAboutAuthorization(printWriter, MessageStatus.OK);
                        notifyAllUsersAboutActiveUser();
                        notifyAllUsersAboutNewUser(message);
                    } else {
                        responseAboutAuthorization(printWriter, MessageStatus.ERROR);
                    }
                } catch (Exception e) {
                    System.err.println("Не удалось установить соединение с пользователем из-за: " + e.getMessage());
                }
            }
        });
        thread.start();
    }

    private void responseAboutAuthorization(PrintWriter printWriter, String status) {
        Message responseMsg = new Message();
        responseMsg.setStatus(status);
        printWriter.println(GSON.toJson(responseMsg));
        printWriter.flush();
    }

    private void notifyAllUsersAboutActiveUser() {
        Message messageAboutUserOnline = new Message();
        messageAboutUserOnline.setData(handleUser.toString());
        messageAboutUserOnline.setType(MessageType.USER_ONLINE);
        handleUser.sendMessageForAllUsers(messageAboutUserOnline);
    }

    private void notifyAllUsersAboutNewUser(Message message) {
        Message newUserMessage = new Message();
        newUserMessage.setType(MessageType.SERVER_MESSAGE);
        newUserMessage.setData(String.format("User %s joined to chat", message.getUserName()));
        newUserMessage.setUserName("Server");
        handleUser.sendMessageForAllUsers(newUserMessage);
    }

    private void receiveMessage() {
        Thread receiveMessage = new Thread(() -> {
            while (true) {
                handleUser.receiveMessageForAllUsers();
            }
        });
        receiveMessage.start();
    }
}
