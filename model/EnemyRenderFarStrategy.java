package model;

import java.awt.Graphics2D;

public class EnemyRenderFarStrategy implements EnemyRenderStrategy {

    private Enemy enemy;

    public EnemyRenderFarStrategy (Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void renderAlgorithm(Graphics2D g2) {
        g2.setColor(enemy.color);
        g2.fillRect(enemy.x, enemy.y, enemy.width, enemy.height);
    }
    
}
