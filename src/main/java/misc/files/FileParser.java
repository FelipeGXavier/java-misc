package misc.files;

import java.io.IOException;
import java.util.List;

public interface FileParser<T> {


    List<T> parseFile(String path) throws IOException;


}
