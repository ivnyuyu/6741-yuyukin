package ru.сft.focusstart.yuyukin;



import ru.сft.focusstart.yuyukin.MultiTable.ConsoleInput;
import ru.сft.focusstart.yuyukin.MultiTable.ConsoleOutput;
import ru.сft.focusstart.yuyukin.MultiTable.MultiplicationTable;


public class Main {
    public static void main(String[] args) {
        MultiplicationTable multiplicationTable = new MultiplicationTable(new ConsoleInput(), new ConsoleOutput());
        multiplicationTable.show();
    }
}
