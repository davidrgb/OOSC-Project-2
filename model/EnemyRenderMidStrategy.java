package model;

import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyRenderMidStrategy implements EnemyRenderStrategy {
    
    private Enemy enemy;

    public EnemyRenderMidStrategy (Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void renderAlgorithm(Graphics2D g2) {
        g2.setColor(Color.orange);
        g2.fillRect(enemy.x, enemy.y, enemy.width, enemy.height);        
    }

}
