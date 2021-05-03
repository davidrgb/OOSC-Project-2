package model;

public interface Subject {
    
    void addUFOListener(Observer o);
    void removeUFOListener(Observer o);
    void notifyObservers(UFO.Event event);
}
