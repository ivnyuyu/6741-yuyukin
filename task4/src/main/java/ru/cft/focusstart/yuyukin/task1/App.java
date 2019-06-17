package ru.cft.focusstart.yuyukin.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class App {
    private static final int INTERVAL = 200_000;
    private static final int THREAD_COUNT = 3;
    private static final int MAX_VALUE_SEARCH = 1_000_000;
    private static final int MIN_VALUE_SEARCH = 2;

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        List<Future<Integer>> futureList = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
        String log = "";
        for (int i = MIN_VALUE_SEARCH; i < MAX_VALUE_SEARCH; i += INTERVAL) {
            futureList.add(service.submit(new Task(i, i + INTERVAL)));
        }
        for (Future<Integer> a : futureList) {
            try {
                atomicInteger.addAndGet(a.get());
            } catch (Exception e) {
                log = "(данные не точные, т.к. произошла ошибка работы некоторого потока)";
            }
        }
        service.shutdown();
        System.out.println(String.format("Простых чисел на промежутке%s от %d до %d : %d", log, MIN_VALUE_SEARCH, MAX_VALUE_SEARCH, atomicInteger.get()));
    }
}


