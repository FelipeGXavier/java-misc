package misc.files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BlockingApacheCommonCsvParser implements FileParser<Employee> {

    @Override
    public List<Employee> parseFile(String path) throws IOException {
        List<Employee> employees = new ArrayList<>();
        LineIterator lineIterator = FileUtils.lineIterator(new File(path), "UTF-8");
        try {
            boolean firstIteration = true;
            while (lineIterator.hasNext()) {
                if (firstIteration) {
                    firstIteration = false;
                    lineIterator.nextLine();
                    continue;
                }
                String line = lineIterator.nextLine();
                String[] values = line.split(";");
                employees.add(this.createEmployee(values));
            }
        } finally {
            LineIterator.closeQuietly(lineIterator);
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
