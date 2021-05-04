package model;

public class ProximityStateFar implements ProximityState {

    public ProximityStateFar(EnemyComposite context) {
        for (var row: context.getRows()) {
            for (var e: row) {
                var enemy = (Enemy) e;
                enemy.setRenderStrategy(new EnemyRenderFarStrategy(enemy));
            }
        }
    }

    @Override
    public void goNext(EnemyComposite context) {
        context.setState(new ProximityStateMid(context));
    }
    
}
