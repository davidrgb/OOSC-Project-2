package model;

import java.awt.Color;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.Random;

import view.GameBoard;

public class EnemyComposite extends GameElement {

    public static final int NROWS = 2;
    public static final int NCOLS = 10;
    public static final int ENEMY_SIZE = 20;
    public static final int UNIT_MOVE = 5;

    private ArrayList<ArrayList<GameElement>> rows;
    private ArrayList<GameElement> bombs;
    private boolean movingToRight = true;
    private Random random = new Random();

    public boolean enemyReachedBottom = false;
    public boolean noEnemies = false;
    public int score = 0;
    public int advances = 0;

    private static final int ADVANCES_MID = 5;
    private static final int ADVANCES_CLOSE = 8;

    private ProximityState state;

    public EnemyComposite() {
        rows = new ArrayList<>();
        bombs = new ArrayList<>();

        for (int r = 0; r < NROWS; r++) {
            var oneRow = new ArrayList<GameElement>();
            rows.add(oneRow);
            for (int c = 0; c < NCOLS; c++) {
                oneRow.add(new Enemy(
                    c * ENEMY_SIZE * 2, r * ENEMY_SIZE * 2, ENEMY_SIZE, Color.yellow, true
                ));
            }
        }

        state = new ProximityStateFar(this);
    }

    @Override
    public void render(Graphics2D g2) {
        for (var r: rows) {
            for (var e: r) {
                e.render(g2);
            }
        }

        for (var b: bombs) {
            b.render(g2);
        }
    }

    @Override
    public void animate() {
        int dx = UNIT_MOVE;
        if (movingToRight) {
            if (rightEnd() >= GameBoard.WIDTH) {
                dx = -dx;
                movingToRight = false;
                advance();
            }
        } else {
            dx = -dx;
            if (leftEnd() <= 0) {
                dx = -dx;
                movingToRight = true;
                advance();
            }
        }

        for (var row: rows) {
            for (var e: row) {
                e.x += dx;
            }
        }

        for (var b: bombs) {
            b.animate();
        }
    }

    private int rightEnd() {
        int xEnd = -100;
        for (var row: rows) {
            if (row.size() == 0) continue;
            int x = row.get(row.size() - 1).x + ENEMY_SIZE;
            if (x > xEnd) xEnd = x;
        }
        return xEnd;
    }

    private int leftEnd() {
        int xEnd = 9000;
        for (var row: rows) {
            if (row.size() == 0) continue;
            int x = row.get(0).x;
            if (x < xEnd) xEnd = x;
        }
        return xEnd;
    }

    public void dropBombs() {
        for (var row: rows) {
            for (var e: row) {
                if (random.nextFloat() < 0.1F) {
                    bombs.add(new Bomb(e.x, e.y));
                }
            }
        }
    }

    public void removeBombsOutOfBound() {
        var remove = new ArrayList<GameElement>();
        for (var b: bombs) {
            if (b.y >= GameBoard.HEIGHT) {
                remove.add(b);
            }
        }
        bombs.removeAll(remove);
    }

    public void processCollision(Shooter shooter) {
        var removeBullets = new ArrayList<GameElement>();

        for (var row: rows) {
            var removeEnemies = new ArrayList<GameElement>();
            for (var enemy: row) {
                for (var bullet: shooter.getWeapons()) {
                    if (enemy.collideWith(bullet)) {
                        removeBullets.add(bullet);
                        removeEnemies.add(enemy);
                    }
                }
            }
            int sizeBefore = row.size();
            row.removeAll(removeEnemies);
            int sizeAfter = row.size();
            score += (sizeBefore - sizeAfter);
        }
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).isEmpty()) {
                if (i == rows.size() - 1) noEnemies = true;
            }
            else i = rows.size();
        }
        shooter.getWeapons().removeAll(removeBullets);

        var removeBombs = new ArrayList<GameElement>();
        removeBullets.clear();

        for (var b: bombs) {
            for (var bullet: shooter.getWeapons()) {
                if (b.collideWith(bullet)) {
                    removeBombs.add(b);
                    removeBullets.add(bullet);
                }
            }
        }

        shooter.getWeapons().removeAll(removeBullets);
        bombs.removeAll(removeBombs);

        removeBombs.clear();
        var removeSquare = new ArrayList<GameElement>();
        for (var b: bombs) {
            for (var square: shooter.getComponents()) {
                if (b.collideWith(square) && removeSquare.size() < 1) {
                    removeBombs.add(b);
                    removeSquare.add(square);
                }
            }
        }

        shooter.getComponents().removeAll(removeSquare);
        bombs.removeAll(removeBombs);

        removeSquare.clear();
        for (var row: rows) {
            var removeEnemies = new ArrayList<GameElement>();
            for (var enemy: row) {
                for (var square: shooter.getComponents()) {
                    if (enemy.collideWith(square)) {
                        removeSquare.add(square);
                        removeEnemies.add(enemy);
                    }
                }
            }
            int sizeBefore = row.size();
            row.removeAll(removeEnemies);
            int sizeAfter = row.size();
            score += (sizeBefore - sizeAfter);
        }
        shooter.getComponents().removeAll(removeSquare);
    }

    public void advance() {
        advances++;
        for (var row: rows) {
            for (var e: row) {
                e.y += 20;
                if (e.y >= GameBoard.HEIGHT) {
                    enemyReachedBottom = true;
                }
            }
        }
        if (advances == ADVANCES_MID || advances == ADVANCES_CLOSE) {
            goNextState();
        }
        
    }

    public void goNextState() {
        state.goNext(this);
    }

    public void setState(ProximityState state) {
        this.state = state;
    }

    public ArrayList<ArrayList<GameElement>> getRows() {
        return rows;
    }
}
