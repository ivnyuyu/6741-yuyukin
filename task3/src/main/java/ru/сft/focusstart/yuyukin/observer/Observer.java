package ru.сft.focusstart.yuyukin.observer;

import ru.сft.focusstart.yuyukin.GameStatus;
import ru.сft.focusstart.yuyukin.TypeCell;

public interface Observer {
    public void updateCellStatus(TypeCell cell, int x, int y);
    public void updateGameStatus(GameStatus gameStatus);
}
