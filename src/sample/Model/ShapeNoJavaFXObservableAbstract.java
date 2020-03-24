package sample.Model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class ShapeNoJavaFXObservableAbstract implements ShapeInter{

    private List<ShapeObserver> observersOrdered = new LinkedList<ShapeObserver>();
    private Set<ShapeObserver> observersSet = new HashSet<ShapeObserver>();

    @Override
    public void addObserver(ShapeObserver obs) {
        if (!observersSet.contains(obs)) {
            observersOrdered.add(obs);
            observersSet.add(obs);
        }
    }

    @Override
    public void removeObserver(ShapeObserver obs) {
        observersOrdered.remove(obs);
        observersSet.remove(obs);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void notifyObservers() {
        Object[] copy = observersOrdered.toArray();
        for (Object s : copy)
            ((ShapeObserver) s).update(this);
    }

}
