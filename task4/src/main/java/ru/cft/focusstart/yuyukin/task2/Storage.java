package ru.cft.focusstart.yuyukin.task2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
public class Storage {
    private volatile BlockingQueue<Resource> storage;

    public Storage(int capacity) {
        storage = new ArrayBlockingQueue<>(capacity);
    }

    public void addNewResource(Resource resource) throws Exception {
        storage.put(resource);
    }

    public Resource takeResource() throws Exception {
        return storage.take();
    }
}
