package patterns.observer;

public interface Subject<T> {

    void register(Observer<T> observer);
    void unregister(Observer<T> observer);
    void notifyObservers();
}
