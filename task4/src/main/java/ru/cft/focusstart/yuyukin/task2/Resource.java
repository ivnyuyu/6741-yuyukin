package ru.cft.focusstart.yuyukin.task2;

public class Resource {
    private int id;
    private static int currentId;

    public Resource() {
        id = nextId();
    }

    private static synchronized int nextId() {
        return ++currentId;
    }

    @Override
    public String toString() {
        return "Ресурс #" + id;
    }
}
