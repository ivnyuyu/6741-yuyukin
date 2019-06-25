package ru.cft.focusstart.yuyukin.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);
    private static final int DEFAULT_DELAY = 2000;

    private int delay = DEFAULT_DELAY;
    private BlockingQueue<Resource> storage;

    Consumer(BlockingQueue<Resource> storage) {
        this.storage = storage;
    }

    private void consume() {
        boolean isOk = true;
        while (isOk) {
            try {
                Resource resource = storage.take();
                log.info(String.format("Потребитель %s забрал со склада %s ", Thread.currentThread().getName(), resource));
                Thread.sleep(delay);
                log.info(String.format("Потребитель %s потребил %s ", Thread.currentThread().getName(), resource));
            } catch (Exception e) {
                log.error(String.format("Произошло прерывание потока %s", Thread.currentThread().getName()));
                isOk = false;
            }
        }
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        consume();
    }
}
