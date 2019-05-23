package ru.сft.focusstart.yuyukin;

import ru.сft.focusstart.yuyukin.util.Convertor;

public class Cell {
    private int x;
    private int y;
    private boolean isFlagged=false;
    private boolean isOpen=false;
    private int value;
    public TypeCell typeCell;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        typeCell=TypeCell.Closed;
    }

    public void open(){
        if(!isFlagged){
            typeCell= Convertor.nameCell(value);
            isOpen=true;
        }
    }

    public void changeFlag(){
        if(isOpen){
            return;
        }
        if(isFlagged){
            typeCell=TypeCell.Closed;
            isFlagged=false;
        }else {
            typeCell=TypeCell.Flaged;
            isFlagged=true;
        }
    }

    public boolean isReveled(){
        return (isFlagged && value==-1) ||(!isFlagged && value!=-1);
    }

    public boolean isErrorFlagged(){
        return (isFlagged && value!=-1);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void incrementValue(){
        value++;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
