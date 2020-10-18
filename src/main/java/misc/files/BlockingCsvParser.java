package misc.files;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlockingCsvParser implements FileParser<Employee> {

    @Override
    public List<Employee> parseFile(String path) throws IOException {
        List<Employee> employees = new ArrayList<>();
        try (FileInputStream inputStreamFile = new FileInputStream(path);
                Scanner scanner = new Scanner(inputStreamFile)) {
            boolean firstIteration = true;
                while (scanner.hasNextLine()) {
                if (firstIteration) {
                    firstIteration = false;
                    scanner.nextLine();
                    continue;
                }
                String line = scanner.nextLine();
                String[] values = line.split(";");
                employees.add(this.createEmployee(values));
            }
        }
        return employees;
    }

    private Employee createEmployee(String[] values) {
        return new Employee.Builder()
                .withName(values[0])
                .withSurname(values[1])
                .withBirthDate(LocalDate.parse(values[2]))
                .build();
    }
}
