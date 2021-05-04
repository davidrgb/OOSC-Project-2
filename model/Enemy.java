package model;

import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends GameElement {

    private EnemyRenderStrategy renderStrategy;

    public Enemy(int x, int y, int size, Color color, boolean filled) {
        super(x, y, color, filled, size, size);
    }

    @Override
    public void render(Graphics2D g2) {
        renderStrategy.renderAlgorithm(g2);
    }

    @Override
    public void animate() {
        
    }

    public void setRenderStrategy(EnemyRenderStrategy renderStrategy) {
        this.renderStrategy = renderStrategy;
    }
}
