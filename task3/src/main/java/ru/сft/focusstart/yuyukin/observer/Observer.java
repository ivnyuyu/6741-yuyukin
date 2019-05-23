package ru.сft.focusstart.yuyukin.observer;

import ru.сft.focusstart.yuyukin.Cell;
import ru.сft.focusstart.yuyukin.GameStatus;

public interface Observer {
    public void updateCellStatus(Cell cell, int x, int y);
    public void updateGameStatus(GameStatus gameStatus);
}
