package ru.сft.focusstart.yuyukin.MultiTable;

import java.util.Scanner;

public class ConsoleInput implements Input {
    final private static int MIN_SIZE_TABLE = 1;
    final private static int MAX_SIZE_TABLE = 32;

    /**
     * <p>Считывает со стандрартного ввода размер таблицы умножения. Максимально поддерживаемый диапазон от 1 до 32</p>
     * @throws  IllegalArgumentException возникает при попытки спарсить строку отличную от целочисленного значения или при
     * вводе значение вне диапазона от 1 до 32
     * @return int, возвращает размер таблицы умножения
     */
    @Override
    public int read() {
        String sizeOfTable = new Scanner(System.in).next();
        int size;
        try {
            size = Integer.parseInt(sizeOfTable);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введеное значение не является целочисленном числом: " + sizeOfTable);
        }
        if (MIN_SIZE_TABLE > size || size > MAX_SIZE_TABLE) {
            throw new IllegalArgumentException("Значение должно попадать в диапазон от 1 до 32");
        }
        return size;
    }
}
