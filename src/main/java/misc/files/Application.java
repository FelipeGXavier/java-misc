package misc.files;

import java.io.File;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {

        FileParser<Employee> blockingCsvParser = new BlockingCsvParser();
        var path = new File(".").getCanonicalPath() +  "\\bin\\employees.csv";
        blockingCsvParser.parseFile(path);
        blockingCsvParser = new BlockingApacheCommonCsvParser();
        blockingCsvParser.parseFile(path);
    }
}
