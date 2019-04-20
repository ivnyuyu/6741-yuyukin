package ru.сft.focusstart.yuyukin.multitable;


public class MultiplicationTable {
    private Input input;
    private Output output;

    /**
     * <p>Конструктур с двумя параметрами</p>.<p>
     *
     * @param input  принимает значение откуда считывать информацию о размерности таблицы умножения
     * @param output принимает значение куда выводить таблицу умножения
     */
    public MultiplicationTable(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    /**
     * <p>Выводит таблицу умножения.<p>
     */
    public void show() {
        int sizeOfTable = 0;
        try {
            sizeOfTable = input.read();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
        output.write(sizeOfTable);
    }
}

