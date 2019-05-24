package ru.сft.focusstart.yuyukin.model;

import ru.сft.focusstart.yuyukin.util.Convert;

public class Cell {
    private static final int BOMB_VALUE=-1;
    private static final int EMPTY_VALUE=0;
    private int x;
    private int y;
    private boolean isFlagged = false;
    private boolean isOpen = false;
    private int value;
    public TypeCell typeCell;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        typeCell = TypeCell.Closed;
    }

    void open() {
        if (!isFlagged) {
            typeCell = Convert.nameCell(value);
            isOpen = true;
        }
    }
    boolean isFlagged(){
        return isFlagged;
    }

    void changeFlag() {
        if (isOpen) {
            return;
        }
        if (isFlagged) {
            typeCell = TypeCell.Closed;
            isFlagged = false;
        } else {
            typeCell = TypeCell.Flaged;
            isFlagged = true;
        }
    }

    boolean isBomb() {
        return value == BOMB_VALUE;
    }

    boolean isOpen() {
        return isOpen;
    }

    boolean isUncovered() {
        return (isFlagged && isBomb()) || (!isFlagged && !isBomb());
    }

    boolean hasNoBombNear() {
        return value == EMPTY_VALUE;
    }

    void setBomb() {
        this.value = BOMB_VALUE;
    }

    void incrementValue() {
        value++;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

}
