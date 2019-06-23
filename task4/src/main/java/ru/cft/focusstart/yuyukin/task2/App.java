package ru.cft.focusstart.yuyukin.task2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    private static final int NUMBER_PRODUCERS = 8;
    private static final int NUMBER_CONSUMERS = 1;
    private static final int STORAGE_CAPACITY = 5;

    public static void main(String[] args) {
        ExecutorService producers = Executors.newFixedThreadPool(NUMBER_PRODUCERS);
        ExecutorService consumers = Executors.newFixedThreadPool(NUMBER_CONSUMERS);
        Storage storage = new Storage(STORAGE_CAPACITY);
        for (int i = 0; i < NUMBER_PRODUCERS; i++) {
            producers.submit(new Producer(storage));
        }
        for (int i = 0; i < NUMBER_CONSUMERS; i++) {
            consumers.submit(new Consumer(storage));
        }
    }
}
