package patterns.observer;

public class Application {

    public static void main(String[] args) {
        var employee = new Employee();
        var observer = new EmployeeObserver();
        employee.register(observer);
        System.out.println(employee.getName());
        employee.setName("Felipe");
        System.out.println(employee.getName());
        employee.setName("João");
        System.out.println(employee.getName());
    }
}
