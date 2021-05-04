package model;

public class ProximityStateMid implements ProximityState {
    
    public ProximityStateMid(EnemyComposite context) {
        for (var row: context.getRows()) {
            for (var e: row) {
                var enemy = (Enemy) e;
                enemy.setRenderStrategy(new EnemyRenderMidStrategy(enemy));
            }
        }
    }

    @Override
    public void goNext(EnemyComposite context) {
        context.setState(new ProximityStateClose(context));
    }

}
