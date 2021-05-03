package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import java.util.ArrayList;

import model.GameElement;

public class MyCanvas extends JPanel {
    
    private GameBoard gameBoard;
    private ArrayList<GameElement> gameElements = new ArrayList<>();

    private TextDraw scoreDisplay;

    public MyCanvas(GameBoard gameBoard, int width, int height) {
        this.gameBoard = gameBoard;
        setBackground(Color.black);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (gameBoard.getEnemyComposite() != null && scoreDisplay == null) {
            scoreDisplay = new TextDraw("Score: " + gameBoard.getEnemyComposite().score, 5, GameBoard.HEIGHT - 10, Color.white, 18);
            gameElements.add(scoreDisplay);
        }

        for (var e: gameElements) {
            e.render(g2);
        }
    }

    public ArrayList<GameElement> getGameElements() {
        return gameElements;
    }
}
