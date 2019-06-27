package ru.cft.focusstart.yuyukin;

import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

public class Users {
    Gson gson=new Gson();
    private CopyOnWriteArrayList<User> users;

    public Users(){
        this.users=new CopyOnWriteArrayList<>();
    }

    public boolean isNameFree(String name){
        for(User user: users){
            if(user.getName().equals(name)){
                return false;
            }
        }
        return true;
    }

    public void addNewUser(User user){
        users.add(user);
    }

    public void sendMessageForAllUsers(String message){
        for(User user: users){
            user.getPrintWriter().println(message);
            user.getPrintWriter().flush();
        }
    }

    public void receiveMessageForAllUsers(){
        for(int i=0; i<users.size();i++){
            try {
                if(users.get(i).getBufferedReader().ready()){
                    Message message=gson.fromJson(users.get(i).getBufferedReader().readLine(), Message.class);
                    System.out.println("Сообщение пришло на сервер");
                    if (message.getType().equals(MessageType.LOG_OUT)) {
                        deleteUser(users.get(i));
                        Message response=new Message();
                        response.setType(MessageType.SERVER_MESSAGE);
                        response.setData(String.format("%s has left from chat", users.get(i).getName()));
                        sendMessageForAllUsers(gson.toJson(response));
                    }else {
                        message.setTime(LocalDateTime.now().toString());
                        message.setUserName(users.get(i).getName());
                        message.setType(MessageType.MESSAGE);
                        System.out.println(message);
                        sendMessageForAllUsers(gson.toJson(message));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteUser(User user){
        users.remove(user);
    }

    public String toString(){
        StringBuilder builder=new StringBuilder();
        for(int i=0; i<users.size();i++){
            builder.append(users.get(i)).append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }

}
