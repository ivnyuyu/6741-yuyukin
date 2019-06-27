package ru.cft.focusstart.yuyukin;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User {
    private String name;
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    public User(Socket socket, BufferedReader bufferedReader, PrintWriter printWriter, String name){
        this.socket=socket;
        this.bufferedReader=bufferedReader;
        this.printWriter=printWriter;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    @Override
    public String toString(){
        return name;
    }
}
