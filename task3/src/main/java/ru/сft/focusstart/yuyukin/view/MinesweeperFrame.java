package ru.сft.focusstart.yuyukin.view;

import ru.сft.focusstart.yuyukin.Cell;
import ru.сft.focusstart.yuyukin.GameField;
import ru.сft.focusstart.yuyukin.GameStatus;
import ru.сft.focusstart.yuyukin.TypeCell;
import ru.сft.focusstart.yuyukin.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MinesweeperFrame implements Observer {
    JButton[][] buttons;
    JFrame mainPage;
    private TypeCell[][] cells;
    JPanel jPanel;
    JPanel infoPanel;
    GameField mineData;
    GameStatus gameStatus;
    Dimension buttonPreferredSize = new Dimension(50, 50);

    void startGame() {
        mainPage.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buttons = new JButton[9][9];
        jPanel = new JPanel();
        infoPanel=new JPanel();
        infoPanel.setLayout(new BorderLayout());
        JButton button=new JButton();
        button.setPreferredSize(buttonPreferredSize);
        infoPanel.add(button, BorderLayout.EAST);
        JButton button2=new JButton();
        button2.setPreferredSize(buttonPreferredSize);
        infoPanel.add(button2, BorderLayout.WEST);
        jPanel.setLayout(new GridLayout(9, 9));
        mainPage.add(infoPanel, BorderLayout.NORTH);
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                buttons[i][j] = new JButton();
                Icon closedIcon = new ImageIcon(MinesweeperFrame.class.getResource(cells[i][j].getUrlImage()));
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
        button.setIcon(new ImageIcon(MinesweeperFrame.class.getResource("/icons/win.png")));
        button2.setIcon(new ImageIcon(MinesweeperFrame.class.getResource("/icons/win.png")));
        mainPage.add(jPanel);
        mainPage.setResizable(true);
        mainPage.pack();
        mainPage.setVisible(true);
    }

    MinesweeperFrame(GameField mineData) {
        mainPage = new JFrame();
        this.mineData = mineData;
        this.cells=mineData.getOutField();
        mineData.registerObserver(this);
        startGame();

    }
    private void setButtonState(int i, int j){
        Icon closedIcon = new ImageIcon(MinesweeperFrame.class.getResource(cells[i][j].getUrlImage()));
        buttons[i][j].setIcon(closedIcon);
    }

    public static void main(String[] args) {
        GameField gameField = new GameField(9, 10);
        MinesweeperFrame a = new MinesweeperFrame(gameField);
        gameField.show();
    }

    @Override
    public void updateCellStatus(TypeCell cell, int x, int y) {
        this.cells[x][y] = cell;
        setButtonState(x,y);
    }

    @Override
    public void updateGameStatus(GameStatus gameStatus){
        this.gameStatus=gameStatus;
    }
}
