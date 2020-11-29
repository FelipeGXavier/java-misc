package patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class Employee implements Subject<Employee> {

    private List<Observer<Employee>> observers = new ArrayList<>();
    private String name;

    public void setName(String name){
        this.name = name;
        System.out.println("State changing for employee");
        this.notifyObservers();
    }

    public String getName() {
        return name;
    }

    @Override
    public void register(Observer<Employee> observer) {
        this.observers.add(observer);
    }

    @Override
    public void unregister(Observer<Employee> observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        this.observers.forEach(observer -> observer.call(this));
    }
}
