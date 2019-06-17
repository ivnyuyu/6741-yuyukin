package ru.cft.focusstart.yuyukin.task1;

import java.util.concurrent.Callable;

class Task implements Callable<Integer> {
    private int min;
    private int max;

    Task(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Integer call() {

        int sum = 0;
        for (int i = min; i < max; i++) {
            if (isPrimeNumber(i)) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * Проверяет является ли число простым.
     */
    private boolean isPrimeNumber(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
