package ru.сft.focusstart.yuyukin.model;

import ru.сft.focusstart.yuyukin.observer.Observer;
import ru.сft.focusstart.yuyukin.observer.Subject;

import java.util.ArrayList;
import java.util.Random;

public class GameField implements Subject {
    private ArrayList<Observer> observers;
    private GameStatus gameStatus;
    private int countBomb;
    private int sizeField;
    private int foundBomb;
    private int pressedXCoordinate;
    private int pressedYCoordinate;
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
        observers = new ArrayList<>();
        gameStatus = GameStatus.Running;
        generateField();
    }

    public Cell[][] getGameField() {
        return gameField;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    private void generateField() {
        int countBombNeedSet = this.countBomb;
        while (countBombNeedSet != 0) {
            int xCoordinateBomb = new Random().nextInt(sizeField);
            int yCoordinateBomb = new Random().nextInt(sizeField);
            if (gameField[xCoordinateBomb][yCoordinateBomb].hasNoBombNear()) {
                gameField[xCoordinateBomb][yCoordinateBomb].setBomb();
                countBombNeedSet--;
            }
        }
        for (int i = 0; i < sizeField; i++) {
            for (int j = 0; j < sizeField; j++) {
                if (gameField[i][j].isBomb()) {
                    incrementNeighbourCells(i, j);
                }
            }
        }
    }

    private void incrementNeighbourCells(int i, int j) {
        if (i + 1 < sizeField && !gameField[i + 1][j].isBomb()) {
            gameField[i + 1][j].incrementValue();
        }
        if (i - 1 >= 0 && !gameField[i - 1][j].isBomb()) {
            gameField[i - 1][j].incrementValue();
        }
        if (i + 1 < sizeField && j + 1 < sizeField && !gameField[i + 1][j + 1].isBomb()) {
            gameField[i + 1][j + 1].incrementValue();
        }
        if (i - 1 >= 0 && j - 1 >= 0 && !gameField[i - 1][j - 1].isBomb()) {
            gameField[i - 1][j - 1].incrementValue();
        }
        if (j - 1 >= 0 && !gameField[i][j - 1].isBomb()) {
            gameField[i][j - 1].incrementValue();
        }
        if (i + 1 < sizeField && j - 1 >= 0 && !gameField[i + 1][j - 1].isBomb()) {
            gameField[i + 1][j - 1].incrementValue();
        }
        if (j + 1 < sizeField && !gameField[i][j + 1].isBomb()) {
            gameField[i][j + 1].incrementValue();
        }
        if (i - 1 >= 0 && j + 1 < sizeField && !gameField[i - 1][j + 1].isBomb()) {
            gameField[i - 1][j + 1].incrementValue();
        }
    }

    private void rightClick(int x, int y) {

        gameField[x][y].changeFlag();
        if (gameField[x][y].isUncovered()) {
            foundBomb++;
        } else {
            foundBomb--;
        }

        pressedXCoordinate = x;
        pressedYCoordinate = y;

        notifyObserver();
    }

    private void leftClick(int x,int y){
        if (gameField[x][y].hasNoBombNear()) {
            openZeroCell(x, y);
        }
        gameField[x][y].open();
        pressedXCoordinate = x;
        pressedYCoordinate = y;
        notifyObserver();
    }

    public void changeField(Cell cell, int but) {
        if (gameStatus != GameStatus.Running) {
            return;
        }
        if(but==3){
            rightClick(cell.getX(),cell.getY());
        }else{

            leftClick(cell.getX(),cell.getY());
        }

        checkGameIsOver(cell.getX(),cell.getY());


    }

    private void checkGameIsOver(int x, int y) {
        if (gameField[x][y].isBomb() && gameField[x][y].isOpen()) {
            gameStatus = GameStatus.Lost;
            notifyObserver();
            showAllBombs();
        }
        if (countBomb == foundBomb) {
            gameStatus = GameStatus.Won;
            notifyObserver();
        }
    }


    private void showAllBombs() {
        for (int i = 0; i < sizeField; i++) {
            for (int j = 0; j < sizeField; j++) {
                if (gameField[i][j].isBomb()) {
                    pressedXCoordinate = i;
                    pressedYCoordinate = j;
                    gameField[i][j].open();
                    notifyObserver();
                } else {
                    if (gameField[i][j].isFlagged()) {
                        pressedXCoordinate = i;
                        pressedYCoordinate = j;
                        gameField[i][j].typeCell = TypeCell.Nomine;
                        notifyObserver();
                    }
                }
            }
        }
    }

    private void openZeroCell(int x, int y) {
        if (x >= 0 && x < sizeField && y >= 0 && y < sizeField) {
            if (gameField[x][y].typeCell == TypeCell.Closed) {
                gameField[x][y].open();
                pressedXCoordinate = x;
                pressedYCoordinate = y;
                notifyObserver();
                if (gameField[x][y].hasNoBombNear()) {
                    openZeroCell(x - 1, y - 1);
                    openZeroCell(x, y - 1);
                    openZeroCell(x + 1, y - 1);
                    openZeroCell(x + 1, y);
                    openZeroCell(x + 1, y + 1);
                    openZeroCell(x, y + 1);
                    openZeroCell(x - 1, y + 1);
                    openZeroCell(x - 1, y);
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
        for (Observer observer : observers) {
            observer.updateCellStatus(gameField[pressedXCoordinate][pressedYCoordinate], pressedXCoordinate, pressedYCoordinate);
            observer.updateGameStatus(gameStatus);
        }
    }
}
