package model;

public class ProximityStateClose implements ProximityState {

    public ProximityStateClose(EnemyComposite context) {
        for (var row: context.getRows()) {
            for (var e: row) {
                var enemy = (Enemy) e;
                enemy.setRenderStrategy(new EnemyRenderCloseStrategy(enemy));
            }
        }
    }

    @Override
    public void goNext(EnemyComposite context) {
        context.setState(null);        
    }
    
}
