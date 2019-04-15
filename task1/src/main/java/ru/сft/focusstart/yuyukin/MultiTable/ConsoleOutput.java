package ru.сft.focusstart.yuyukin.MultiTable;


public class ConsoleOutput implements Output {

    /**
     * <p>Считает количество цифр произведения максимального значения таблицы умножения.</p>
     *
     * @param size, принимает наибольшее значение таблицы умножения
     * @return int, возвращает количество цифр в числе
     */
    private int countDigits(int size) {
        int count = 0;
        while (size != 0) {
            count++;
            size /= 10;
        }
        return count;
    }

    /**
     * <p>Выводит таблицу умножения в стандартную консоль вывода.<p>
     *
     * @param size, принимает размерность таблицы умножения(от 1 до 32)
     */
    @Override
    public void write(int size) {
        int countDigits = countDigits(size * size);
        String format = "%" + countDigits + "d";
        String line = String.format("%" + countDigits + "s", "").replace(' ', '-');
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                System.out.printf(format, i * j);
                if (size == j) {
                    continue;
                }
                System.out.print("|");
            }
            System.out.println();
            for (int j = 1; j <= size; j++) {
                System.out.print(line);
                if (size == j) {
                    continue;
                }
                System.out.print("+");
            }
            System.out.println();
        }

    }
}
