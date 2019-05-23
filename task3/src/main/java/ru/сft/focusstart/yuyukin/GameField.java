package ru.сft.focusstart.yuyukin;

import ru.сft.focusstart.yuyukin.observer.Observer;
import ru.сft.focusstart.yuyukin.observer.Subject;

import java.util.ArrayList;
import java.util.Random;

public class GameField implements Subject {
    private GameStatus gameStatus;
    private int countBomb;
    private int sizeField;
    private int foundBomb;
    private ArrayList observers;
    private int pressedXcoordinate;
    private int pressedYcoordinate;
    private Cell[][] gameField;

    public GameField(int sizeField, int countBomb) {
        this.gameField = new Cell[sizeField][sizeField];
        this.gameField = new Cell[sizeField][sizeField];
        this.countBomb = countBomb;
        this.sizeField = sizeField;
        for (int i = 0; i < sizeField; i++) {
            for (int j = 0; j < sizeField; j++) {
                gameField[i][j] = new Cell(i, j);
            }
        }
        observers = new ArrayList();
        gameStatus = GameStatus.Running;
        generateField();
    }

    public Cell[][] getGameField() {
        return gameField;
    }

    public GameStatus getGameStatus(){
        return gameStatus;
    }

    private void generateField() {
        int a = countBomb;
        while (a != 0) {
            int xCoordinateBomb = new Random().nextInt(sizeField);
            int yCoordinateBomb = new Random().nextInt(sizeField);
            if (gameField[xCoordinateBomb][yCoordinateBomb].getValue() == 0) {
                gameField[xCoordinateBomb][yCoordinateBomb].setValue(-1);
                a--;
            }
        }
        for (int i = 0; i < sizeField; i++) {
            for (int j = 0; j < sizeField; j++) {
                if (gameField[i][j].getValue() == -1) {
                    incrementNeighbourCells(i, j);
                }
            }
        }
    }

    private void incrementNeighbourCells(int i, int j) {
        if (i + 1 < sizeField && gameField[i + 1][j].getValue() != -1) {
            gameField[i + 1][j].incrementValue();
        }
        if (i - 1 >= 0 && gameField[i - 1][j].getValue() != -1) {
            gameField[i - 1][j].incrementValue();
        }
        if (i + 1 < sizeField && j + 1 < sizeField && gameField[i + 1][j + 1].getValue() != -1) {
            gameField[i + 1][j + 1].incrementValue();
        }
        if (i - 1 >= 0 && j - 1 >= 0 && gameField[i - 1][j - 1].getValue() != -1) {
            gameField[i - 1][j - 1].incrementValue();
        }
        if (j - 1 >= 0 && gameField[i][j - 1].getValue() != -1) {
            gameField[i][j - 1].incrementValue();
        }
        if (i + 1 < sizeField && j - 1 >= 0 && gameField[i + 1][j - 1].getValue() != -1) {
            gameField[i + 1][j - 1].incrementValue();
        }
        if (j + 1 < sizeField && gameField[i][j + 1].getValue() != -1) {
            gameField[i][j + 1].incrementValue();
        }
        if (i - 1 >= 0 && j + 1 < sizeField && gameField[i - 1][j + 1].getValue() != -1) {
            gameField[i - 1][j + 1].incrementValue();
        }
    }

    public void changeField(Cell cell, int but) {
        if (gameStatus != GameStatus.Running) {
            return;
        }
        if (but == 3) {
            gameField[cell.getX()][cell.getY()].changeFlag();
            if(gameField[cell.getX()][cell.getY()].isReveled()){
                foundBomb++;
            }else {
                foundBomb--;
            }
            if(countBomb==foundBomb){
                gameStatus=GameStatus.Won;
            }
            pressedXcoordinate=cell.getX();
            pressedYcoordinate=cell.getY();

            notifyObserver();
            return;
        }

        if (gameField[cell.getX()][cell.getY()].getValue() == -1) {
            gameStatus = GameStatus.Lost;
            notifyObserver();
            showAllBombs();
        }
        if (gameField[cell.getX()][cell.getY()].getValue() == 0) {
            setZero(cell.getX(), cell.getY());
            return;
        }
        gameField[cell.getX()][cell.getY()].open();
        pressedXcoordinate=cell.getX();
        pressedYcoordinate=cell.getY();
        notifyObserver();
    }


    private void showAllBombs() {
        for (int i = 0; i < sizeField; i++) {
            for (int j = 0; j < sizeField; j++) {
                if (gameField[i][j].getValue() == -1) {
                    pressedXcoordinate=i;
                    pressedYcoordinate=j;
                    gameField[i][j].typeCell = TypeCell.Mine;
                    notifyObserver();
                } else {
                    if (gameField[i][j].typeCell == TypeCell.Flaged) {
                        pressedXcoordinate=i;
                        pressedYcoordinate=j;
                        gameField[i][j].typeCell = TypeCell.Nomine;
                        notifyObserver();
                    }
                }
            }
        }
    }

    private void setZero(int x, int y) {
        if (x >= 0 && x < sizeField && y >= 0 && y < sizeField) {
            if (gameField[x][y].typeCell == TypeCell.Closed) {
                gameField[x][y].open();
                pressedXcoordinate=x;
                pressedYcoordinate=y;
                notifyObserver();
                if (gameField[x][y].getValue() == 0) {
                    setZero(x - 1, y - 1);
                    setZero(x, y - 1);
                    setZero(x + 1, y - 1);
                    setZero(x + 1, y);
                    setZero(x + 1, y + 1);
                    setZero(x, y + 1);
                    setZero(x - 1, y + 1);
                    setZero(x - 1, y);
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
        observers.remove(0);
    }

    @Override
    public void notifyObserver() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = (Observer) observers.get(i);
            observer.updateCellStatus(gameField[pressedXcoordinate][pressedYcoordinate], pressedXcoordinate, pressedYcoordinate);
            observer.updateGameStatus(gameStatus);
        }
    }
}
