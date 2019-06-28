package ru.cft.focusstart.yuyukin;

public class ServerActivator {
    public static void main(String[] args) {
        try {
            Server server=new Server();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
