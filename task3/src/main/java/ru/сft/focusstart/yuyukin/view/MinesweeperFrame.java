package ru.сft.focusstart.yuyukin.view;

import ru.сft.focusstart.yuyukin.Cell;
import ru.сft.focusstart.yuyukin.GameField;
import ru.сft.focusstart.yuyukin.GameStatus;
import ru.сft.focusstart.yuyukin.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MinesweeperFrame implements Observer {
    private JButton[][] buttons;
    private JFrame mainPage = new JFrame();
    private JPanel jPanel;
    private JPanel infoPanel;
    private JButton gameStatusLabel;
    private GameField mineData;
    private GameStatus gameStatus;
    private static final Dimension buttonPreferredSize = new Dimension(50, 50);
    private Cell[][] cells;

    private MinesweeperFrame(GameField mineData) {

        this.mineData = mineData;
        this.cells=mineData.getGameField();
        this.gameStatus=mineData.getGameStatus();
        mineData.registerObserver(this);
        startGame();
    }

    private void startGame() {
        mainPage.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buttons = new JButton[9][9];
        jPanel = new JPanel();
        infoPanel=new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(new FlowLayout());
        gameStatusLabel=new JButton();
        gameStatusLabel.addActionListener(e->{
            GameField gameField=new GameField(9,10);
            new MinesweeperFrame(gameField);
        });
        gameStatusLabel.setPreferredSize(buttonPreferredSize);
        jPanel.setLayout(new GridLayout(9, 9));
        mainPage.add(infoPanel, BorderLayout.NORTH);
        infoPanel.add(gameStatusLabel);

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                buttons[i][j] = new JButton();
                Icon closedIcon = new ImageIcon(MinesweeperFrame.class.getResource(cells[i][j].typeCell.getUrlImage()));
                System.out.println(cells[i][j].typeCell.getUrlImage());
                buttons[i][j].setIcon(closedIcon);
                buttons[i][j].setPreferredSize(buttonPreferredSize);
                Cell cell = new Cell(i, j);
                buttons[i][j].addMouseListener(new MouseListener() {
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
                jPanel.add(buttons[i][j]);
            }
        }
        gameStatusLabel.setIcon(new ImageIcon(MinesweeperFrame.class.getResource(gameStatus.getUrlImage())));
        mainPage.add(jPanel);
        mainPage.setResizable(true);
        mainPage.pack();
        mainPage.setVisible(true);
    }


    private void setButtonState(int i, int j){
        Icon closedIcon = new ImageIcon(MinesweeperFrame.class.getResource(cells[i][j].typeCell.getUrlImage()));
        buttons[i][j].setIcon(closedIcon);
    }

    private void setGameStatus(GameStatus gameStatus){
        Icon closedIcon = new ImageIcon(MinesweeperFrame.class.getResource(gameStatus.getUrlImage()));
        System.out.println(gameStatus.getUrlImage());
        gameStatusLabel.setIcon(closedIcon);
    }

    public static void main(String[] args) {
        GameField gameField = new GameField(9, 10);
        new MinesweeperFrame(gameField);
    }

    @Override
    public void updateCellStatus(Cell cell, int x, int y) {
        this.cells[x][y] = cell;
        setButtonState(x,y);
    }

    @Override
    public void updateGameStatus(GameStatus gameStatus){
        this.gameStatus=gameStatus;
        setGameStatus(gameStatus);
    }
}
