package ru.cft.focusstart.yuyukin.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    private static final int DEFAULT_DELAY = 1000;

    BlockingQueue<Resource> storage;
    private int delay = DEFAULT_DELAY;

    Producer(BlockingQueue storage) {
        this.storage = storage;
    }

    private void produce() {
        boolean isOk = true;
        while (isOk) {
            try {
                Resource newResource = new Resource();
                log.info(String.format("Производитель %s произвел %s ", Thread.currentThread().getName(), newResource));
                storage.put(newResource);
                log.info(String.format("Производитель %s поместил на склад %s ", Thread.currentThread().getName(), newResource));
                Thread.sleep(delay);
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
        produce();
    }
}
