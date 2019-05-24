package ru.сft.focusstart.yuyukin.view;

import ru.сft.focusstart.yuyukin.model.Cell;
import ru.сft.focusstart.yuyukin.model.GameField;
import ru.сft.focusstart.yuyukin.model.GameStatus;
import ru.сft.focusstart.yuyukin.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MinesweeperFrame implements Observer {
    private JButton[][] buttonsCell;
    private JFrame mainFrame = new JFrame();

    private JLabel gameStatusLabel;
    private GameField mineData;
    private GameStatus gameStatus;
    private static final Dimension BUTTON_PREFERRED_SIZE = new Dimension(50, 50);
    private static final int SIZE_FIELD = 9;
    private Cell[][] cells;

    public MinesweeperFrame(GameField mineData) {
        this.mineData = mineData;
        this.cells = mineData.getGameField();
        this.gameStatus = mineData.getGameStatus();
        mineData.registerObserver(this);
        startGame();
    }

    private void startGame() {
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buttonsCell = new JButton[SIZE_FIELD][SIZE_FIELD];
        JPanel mainPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(new FlowLayout());
        gameStatusLabel = new JLabel();
        gameStatusLabel.setPreferredSize(BUTTON_PREFERRED_SIZE);
        mainPanel.setLayout(new GridLayout(SIZE_FIELD, SIZE_FIELD));
        mainFrame.add(infoPanel, BorderLayout.NORTH);
        infoPanel.add(gameStatusLabel);

        for (int i = 0; i < buttonsCell.length; i++) {
            for (int j = 0; j < buttonsCell.length; j++) {
                buttonsCell[i][j] = new JButton();
                Icon icon = new ImageIcon(MinesweeperFrame.class.getResource(cells[i][j].typeCell.getUrlImage()));
                buttonsCell[i][j].setIcon(icon);
                buttonsCell[i][j].setPreferredSize(BUTTON_PREFERRED_SIZE);
                Cell cell = new Cell(i, j);
                buttonsCell[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mineData.changeField(cell, e.getButton());
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                mainPanel.add(buttonsCell[i][j]);
            }
        }
        gameStatusLabel.setIcon(new ImageIcon(MinesweeperFrame.class.getResource(gameStatus.getUrlImage())));
        mainFrame.add(mainPanel);
        mainFrame.setResizable(true);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }


    private void setButtonState(int i, int j) {
        Icon icon = new ImageIcon(MinesweeperFrame.class.getResource(cells[i][j].typeCell.getUrlImage()));
        buttonsCell[i][j].setIcon(icon);
    }

    private void setGameStatus(GameStatus gameStatus) {
        Icon closedIcon = new ImageIcon(MinesweeperFrame.class.getResource(gameStatus.getUrlImage()));
        gameStatusLabel.setIcon(closedIcon);
    }


    @Override
    public void updateCellStatus(Cell cell, int x, int y) {
        this.cells[x][y] = cell;
        setButtonState(x, y);
    }

    @Override
    public void updateGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        setGameStatus(gameStatus);
    }
}
