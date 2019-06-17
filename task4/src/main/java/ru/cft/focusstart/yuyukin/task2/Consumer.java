package ru.cft.focusstart.yuyukin.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);
    private static final int DEFAULT_DELAY = 400;

    private int delay = DEFAULT_DELAY;
    private Storage storage;

    Consumer(Storage storage) {
        this.storage = storage;
    }

    private void consume() {
        boolean isOk = true;
        while (isOk) {
            try {
                Thread.sleep(delay);
                Resource a = storage.takeResource();
                log.info(String.format("Потребитель %s забрал со склада %s ", Thread.currentThread().getName(), a));
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
