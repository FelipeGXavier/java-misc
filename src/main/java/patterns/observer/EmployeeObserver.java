package patterns.observer;

public class EmployeeObserver implements Observer<Employee> {

    @Override
    public void call(Employee employee) {
        System.out.println("Observer called " + employee.getName());
    }
}
