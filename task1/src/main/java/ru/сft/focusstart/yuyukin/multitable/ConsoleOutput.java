package ru.сft.focusstart.yuyukin.multitable;


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
        String line = getLine(size, countDigits);

        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                System.out.printf(format, i * j);
                if (size == j) {
                    continue;
                }
                System.out.print("|");
            }
            System.out.println();

            System.out.print(line);

            System.out.println();
        }

    }

    private String getLine(int size, int countDigits) {
        StringBuilder b = new StringBuilder();
        String line = String.format("%" + countDigits + "s", "").replace(' ', '-');
        for (int j = 1; j <= size; j++) {
            b.append(line);
            if (size == j) {
                continue;
            }
            b.append("+");
        }
        return b.toString();
    }
}
