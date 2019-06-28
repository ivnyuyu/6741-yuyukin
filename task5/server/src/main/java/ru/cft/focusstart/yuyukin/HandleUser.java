package ru.cft.focusstart.yuyukin;

import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

public class HandleUser {
    private static final Gson GSON = new Gson();

    private CopyOnWriteArrayList<User> users;

    HandleUser() {
        this.users = new CopyOnWriteArrayList<>();
    }

    boolean isNameFree(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    void addNewUser(User user) {
        users.add(user);
    }

    void sendMessageForAllUsers(Message message) {
        message.setTime(LocalDateTime.now().toString());
        for (User user : users) {
            user.getPrintWriter().println(GSON.toJson(message));
            user.getPrintWriter().flush();
        }
    }

    void receiveMessageForAllUsers() {
        for (int i = 0; i < users.size(); i++) {
            try {
                if (users.get(i).getBufferedReader().ready()) {
                    Message receiveMessage = GSON.fromJson(users.get(i).getBufferedReader().readLine(), Message.class);
                    if (receiveMessage.getType().equals(MessageType.LOG_OUT)) {
                        String nameAbandonedUser = users.get(i).getName();
                        deleteUser(users.get(i));
                        Message messageAboutAbandonedUser = new Message();
                        messageAboutAbandonedUser.setType(MessageType.SERVER_MESSAGE);
                        messageAboutAbandonedUser.setData(String.format("%s has left from chat", nameAbandonedUser));
                        messageAboutAbandonedUser.setUserName("Server");
                        sendMessageForAllUsers(messageAboutAbandonedUser);

                        Message messageAboutUserOnline = new Message();
                        messageAboutUserOnline.setType(MessageType.USER_ONLINE);
                        messageAboutUserOnline.setData(users.toString());
                        sendMessageForAllUsers(receiveMessage);
                    } else {
                        receiveMessage.setUserName(users.get(i).getName());
                        receiveMessage.setType(MessageType.MESSAGE);
                        sendMessageForAllUsers(receiveMessage);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteUser(User user) {
        users.remove(user);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < users.size(); i++) {
            builder.append(users.get(i)).append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }

}
