package ru.сft.focusstart.yuyukin.observer;

import ru.сft.focusstart.yuyukin.model.Cell;
import ru.сft.focusstart.yuyukin.model.GameStatus;

public interface Observer {
    public void updateCellStatus(Cell cell, int x, int y);
    public void updateGameStatus(GameStatus gameStatus);
}
