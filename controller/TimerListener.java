package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;
import java.util.Random;

import model.Bullet;
import model.Shooter;
import model.UFO;
import view.GameBoard;
import view.TextDraw;

public class TimerListener implements ActionListener {

    public enum EventType {
        KEY_RIGHT, KEY_LEFT, KEY_SPACE
    }

    GameBoard gameBoard;
    private LinkedList<EventType> eventQueue;
    private final int BOMB_DROP_FREQ = 20;
    private int frameCounter = 0;
    private int ufoCounter = 0;

    Random random = new Random();

    private static final int FPS = 20;

    public TimerListener(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        eventQueue = new LinkedList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ++frameCounter;
        ++ufoCounter;
        update();
        processEventQueue();
        processCollision();
        gameBoard.getCanvas().repaint();
    }

    public void processEventQueue() {
        while (!eventQueue.isEmpty()) {
            var e = eventQueue.getFirst();
            eventQueue.removeFirst();
            Shooter shooter = gameBoard.getShooter();
            if (shooter == null) return;

            switch (e) {
                case KEY_LEFT:
                    shooter.moveLeft();
                    break;
                case KEY_RIGHT:
                    shooter.moveRight();
                    break;
                case KEY_SPACE:
                    if (shooter.canFireMoreBullet()) {
                        shooter.getWeapons().add(new Bullet(shooter.x, shooter.y));
                    }
                    break;
            }
        }

        if (frameCounter == BOMB_DROP_FREQ) {
            gameBoard.getEnemyComposite().dropBombs();
            frameCounter = 0;
        }

        if (ufoCounter >= gameBoard.ufoTimer && gameBoard.getUfo() == null) {
            UFO ufo = new UFO();
            gameBoard.setUfo(ufo);
            gameBoard.getCanvas().getGameElements().add(ufo);
            gameBoard.ufoTimer = ufoCounter + random.nextInt(FPS * 10);
        }
    }

    private void processCollision() {
        var shooter = gameBoard.getShooter();
        var enemyComposite = gameBoard.getEnemyComposite();
        var ufo = gameBoard.getUfo();

        shooter.removeBulletsOutOfBound();
        enemyComposite.removeBombsOutOfBound();
        enemyComposite.processCollision(shooter);
        if (ufo != null) {
            ufo.processCollision(gameBoard);
            if (ufo.collision || ufo.offscreen) {
                gameBoard.clearUfo();
            }
        }
        if (enemyComposite.noEnemies) {
            gameBoard.getCanvas().getGameElements().clear();
            gameBoard.getCanvas().getGameElements().add(new TextDraw("You Won - Score: " + enemyComposite.score, 100, 100, Color.yellow, 30));
        }
        if (enemyComposite.enemyReachedBottom || shooter.getComponents().isEmpty()) {
            gameBoard.getCanvas().getGameElements().clear();
            gameBoard.getCanvas().getGameElements().add(new TextDraw("You Lost - Score: " + enemyComposite.score, 100, 100, Color.yellow, 30));
        }
    }

    private void update() {
        for (var e: gameBoard.getCanvas().getGameElements()) {
            e.animate();
        }
    }

    public LinkedList<EventType> getEventQueue() {
        return eventQueue;
    }
    
}
