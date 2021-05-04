package model;

import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends GameElement {

    private EnemyRenderStrategy renderStrategy;

    public Enemy(int x, int y, int size, Color color, boolean filled) {
        super(x, y, color, filled, size, size);
        //renderStrategy = new EnemyRenderFarStrategy(this);
    }

    @Override
    public void render(Graphics2D g2) {
        /*g2.setColor(color);
        if (filled) {
            g2.fillRect(x, y, width, height);
        }
        else {
            g2.drawRect(x, y, width, height);
        }*/
        renderStrategy.renderAlgorithm(g2);
    }

    @Override
    public void animate() {
        
    }

    public void setRenderStrategy(EnemyRenderStrategy renderStrategy) {
        this.renderStrategy = renderStrategy;
    }
}
