package ru.сft.focusstart.yuyukin;

import ru.сft.focusstart.yuyukin.observer.Observer;
import ru.сft.focusstart.yuyukin.observer.Subject;
import ru.сft.focusstart.yuyukin.util.Convertor;

import java.util.ArrayList;
import java.util.Random;

public class GameField implements Subject {
    private GameStatus gameStatus;
    private int[][] arr;
    private TypeCell[][] outField;
    private int countBomb;
    private int sizeField;
    private ArrayList observers;
    private Cell pressedCell;

    public GameField(int sizeField, int countBomb) {
        this.arr = new int[sizeField][sizeField];
        this.outField = new TypeCell[sizeField][sizeField];
        this.countBomb = countBomb;
        this.sizeField = sizeField;
        observers = new ArrayList();
        gameStatus=GameStatus.Running;
        generateField();
        for (int i = 0; i < outField.length; i++) {
            for (int j = 0; j < outField.length; j++) {
                outField[i][j] = TypeCell.Closed;
            }
        }
    }

    public TypeCell[][] getOutField() {
        return outField;
    }

    private void generateField() {
        int a = countBomb;
        while (a != 0) {
            int xCoordinateBomb = new Random().nextInt(sizeField);
            int yCoordinateBomb = new Random().nextInt(sizeField);
            if (arr[xCoordinateBomb][yCoordinateBomb] == 0) {
                arr[xCoordinateBomb][yCoordinateBomb] = -1;
                a--;
            }
        }
        for (int i = 0; i < sizeField; i++) {
            for (int j = 0; j < sizeField; j++) {
                if (arr[i][j] == -1) {
                    incrementNeighbourCells(i, j);
                }
            }
        }
    }

    private void incrementNeighbourCells(int i, int j) {
        if (i + 1 < sizeField && arr[i + 1][j] != -1) {
            arr[i + 1][j]++;
        }
        if (i - 1 >= 0 && arr[i - 1][j] != -1) {
            arr[i - 1][j]++;
        }
        if (i + 1 < sizeField && j + 1 < sizeField && arr[i + 1][j + 1] != -1) {
            arr[i + 1][j + 1]++;
        }
        if (i - 1 >= 0 && j - 1 >= 0 && arr[i - 1][j - 1] != -1) {
            arr[i - 1][j - 1]++;
        }
        if (j - 1 >= 0 && arr[i][j - 1] != -1) {
            arr[i][j - 1]++;
        }
        if (i + 1 < sizeField && j - 1 >= 0 && arr[i + 1][j - 1] != -1) {
            arr[i + 1][j - 1]++;
        }
        if (j + 1 < sizeField && arr[i][j + 1] != -1) {
            arr[i][j + 1]++;
        }
        if (i - 1 >= 0 && j + 1 < sizeField && arr[i - 1][j + 1] != -1) {
            arr[i - 1][j + 1]++;
        }
    }

    public void show() {
        for (int i = 0; i < sizeField; i++) {
            for (int j = 0; j < sizeField; j++) {
                System.out.printf("%2d ", arr[i][j]);
            }
            System.out.println();
        }
    }

    public void changeField(Cell cell, int but) {
        if(gameStatus!=GameStatus.Running){
            return;
        }
        if (but == 3) {
            if (outField[cell.x][cell.y] == TypeCell.Closed) {
                outField[cell.x][cell.y] = TypeCell.Flaged;
            } else if (outField[cell.x][cell.y] == TypeCell.Flaged) {
                outField[cell.x][cell.y] = TypeCell.Closed;
            }
            pressedCell = cell;
            notifyObserver();
            return;
        }
        if(outField[cell.x][cell.y]==TypeCell.Flaged){
            return;
        }
        if(arr[cell.x][cell.y]==-1){
            gameStatus=GameStatus.Lost;
            showAllBombs();
        }
        if(arr[cell.x][cell.y]==0){
            setZero(cell.x,cell.y);
            return;
        }
        outField[cell.x][cell.y] = Convertor.nameCell(arr[cell.x][cell.y]);
        pressedCell = cell;
        notifyObserver();
    }

    private void showAllBombs() {
        for (int i = 0; i < sizeField; i++) {
            for (int j = 0; j < sizeField; j++) {
                if(arr[i][j]==-1){
                    pressedCell=new Cell(i,j);
                    outField[i][j]=TypeCell.Mine;
                    notifyObserver();
                }else{
                    if(outField[i][j]==TypeCell.Flaged){
                        pressedCell=new Cell(i,j);
                        outField[i][j]=TypeCell.Nomine;
                        notifyObserver();
                    }
                }
            }
        }
    }

    private void setZero(int x,int y){
       if(x>=0 && x<sizeField  && y>=0 && y<sizeField){
           if(outField[x][y]==TypeCell.Closed){
               outField[x][y]=Convertor.nameCell(arr[x][y]);
               pressedCell=new Cell(x,y);
               notifyObserver();
               if(arr[x][y]==0){
                   setZero(x-1,y-1);
                   setZero(x,y-1);
                   setZero(x+1,y-1);
                   setZero(x+1,y);
                   setZero(x+1,y+1);
                   setZero(x,y+1);
                   setZero(x-1,y+1);
                   setZero(x-1,y);
               }
           }
       }
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {

    }

    @Override
    public void notifyObserver() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = (Observer) observers.get(i);
            observer.updateCellStatus(outField[pressedCell.x][pressedCell.y], pressedCell.x, pressedCell.y);
        }
    }
}
