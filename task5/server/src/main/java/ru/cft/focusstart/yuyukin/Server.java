package ru.cft.focusstart.yuyukin;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        Users users=new Users();
        ServerSocket server = new ServerSocket(9090);

        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Сервер ожидает пользователя");
                    Socket clientSocket = server.accept();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
                    String userName = bufferedReader.readLine();
                    Gson gson=new Gson();
                    Message message=gson.fromJson(userName,Message.class);
                    System.out.println(message);
                    if(users.isNameFree(message.getUserName())){
                        users.addNewUser(new User(clientSocket, bufferedReader, printWriter, message.getUserName()));
                        Message responseMsg=new Message();
                        responseMsg.setStatus("OK");
                        printWriter.println(gson.toJson(responseMsg));
                        printWriter.flush();
                        Message messageAboutUserOnlain=new Message();
                        messageAboutUserOnlain.setData(users.toString());
                        messageAboutUserOnlain.setType(MessageType.USER_ONLINE);
                        users.sendMessageForAllUsers(gson.toJson(messageAboutUserOnlain));

                        Message newUserMessage=new Message();
                        newUserMessage.setType(MessageType.SERVER_MESSAGE);
                        newUserMessage.setData(String.format("User %s joined to chat", message.getUserName()));
                        users.sendMessageForAllUsers(gson.toJson(newUserMessage));
                    }else{
                        Message responseMsg=new Message();
                        responseMsg.setStatus(MessageStatus.ERROR);
                        printWriter.println(gson.toJson(responseMsg));
                        printWriter.flush();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        Thread receiveMessage=new Thread(()->{
            while (true){
                users.receiveMessageForAllUsers();
            }
        });
        receiveMessage.start();
    }
}
