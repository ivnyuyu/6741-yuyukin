package ru.cft.focusstart.yuyukin;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User {
    private String name;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public User(BufferedReader bufferedReader, PrintWriter printWriter, String name) {
        this.bufferedReader = bufferedReader;
        this.printWriter = printWriter;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    @Override
    public String toString() {
        return name;
    }
}
