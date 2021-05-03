package model;

import java.awt.Color;
import java.awt.Graphics2D;

import view.GameBoard;

public class UFO extends GameElement {

    public static final int UFO_WIDTH = 10;
    public static final int UFO_HEIGHT = 5;

    public static final int UNIT_MOVE = 5;

    public boolean collision;
    public boolean offscreen;

    public UFO () {
        super(0, 0, Color.blue, true, UFO_WIDTH, UFO_HEIGHT);
        collision = false;
        offscreen = false;
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        if (filled) {
            g2.fillRect(x, y, width, height);
        }
        else {
            g2.drawRect(x, y, width, height);
        }
    }

    @Override
    public void animate() {
        x += UNIT_MOVE;
    }

    public void processCollision(GameBoard gameBoard) {
        for (int i = 0; i < gameBoard.getShooter().getWeapons().size(); i++) {
            if (this.collideWith(gameBoard.getShooter().getWeapons().get(i))) {
                gameBoard.getShooter().getWeapons().remove(i);
                i = gameBoard.getShooter().getWeapons().size();
                collision = true;
            }
        }
        if (x > gameBoard.WIDTH) {
            offscreen = true;
        }
        if (collision) {
            int x = gameBoard.getShooter().x;
            int y = gameBoard.getShooter().y;
            gameBoard.getCanvas().getGameElements().remove(gameBoard.getShooter());
            gameBoard.setShooter(new Shooter(x, y));
            gameBoard.getCanvas().getGameElements().add(gameBoard.getShooter());
        }
    }
    
}
