package model;

import view.GameBoard;

public class UFOObserver implements Observer {

    private GameBoard gameBoard;

    public UFOObserver (GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void ufoCollision() {
        gameBoard.getShooter().extraBullets++;
        int x = gameBoard.getShooter().x;
        int y = gameBoard.getShooter().y;
        gameBoard.getCanvas().getGameElements().remove(gameBoard.getShooter());
        gameBoard.setShooter(new Shooter(x, y));
        gameBoard.getCanvas().getGameElements().add(gameBoard.getShooter());
    }

    @Override
    public void ufoLeftScene() {
        gameBoard.clearUfo();
    }
    
}
