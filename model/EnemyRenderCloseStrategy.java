package model;

import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyRenderCloseStrategy implements EnemyRenderStrategy {

    private Enemy enemy;

    public EnemyRenderCloseStrategy (Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void renderAlgorithm(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.drawRect(enemy.x, enemy.y, enemy.width, enemy.height);        
    }
    
}
