package ru.сft.focusstart.yuyukin.multitable;

import java.util.Scanner;

public class ConsoleInput implements Input {
    final private static int MIN_SIZE_TABLE = 1;
    final private static int MAX_SIZE_TABLE = 32;

    /**
     * <p>Считывает со стандрартного ввода размер таблицы умножения. Максимально поддерживаемый диапазон от 1 до 32</p>
     *
     * @return int, возвращает размер таблицы умножения
     * @throws IllegalArgumentException возникает при попытки спарсить строку отличную от целочисленного значения или при
     *                                  вводе значение вне диапазона от 1 до 32
     */
    @Override
    public int read() throws Exception {
        String sizeOfTable = new Scanner(System.in).next();
        int size;
        try {
            size = Integer.parseInt(sizeOfTable);
        } catch (NumberFormatException e) {
            throw new Exception("Введеное значение не является целочисленном числом: " + sizeOfTable);
        }
        if (MIN_SIZE_TABLE > size || size > MAX_SIZE_TABLE) {
            throw new Exception("Значение должно попадать в диапазон от 1 до 32");
        }
        return size;
    }
}
