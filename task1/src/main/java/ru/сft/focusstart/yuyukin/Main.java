package ru.сft.focusstart.yuyukin;


import ru.сft.focusstart.yuyukin.multitable.ConsoleInput;
import ru.сft.focusstart.yuyukin.multitable.ConsoleOutput;
import ru.сft.focusstart.yuyukin.multitable.MultiplicationTable;


public class Main {
    public static void main(String[] args) {
        MultiplicationTable multiplicationTable = new MultiplicationTable(new ConsoleInput(), new ConsoleOutput());
        multiplicationTable.show();
    }
}
