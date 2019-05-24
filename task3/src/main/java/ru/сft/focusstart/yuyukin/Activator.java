package ru.сft.focusstart.yuyukin;

import ru.сft.focusstart.yuyukin.model.GameField;
import ru.сft.focusstart.yuyukin.view.MinesweeperFrame;

public class Activator {
        public static void main(String[] args) {
            GameField gameField = new GameField(9, 10);
            new MinesweeperFrame(gameField);
        }
}
