package jsonschema.spec.converter;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        if (args.length < 1) {
            throw new RuntimeException("Path argument is required.");
        }

        var path = Paths.get(args[0]).toRealPath();
        var spec = path.getFileName().toString().split("\\.")[0];

        var converter = new Converter();
        Files.write(Paths.get(spec + ".md"), converter.convert(path).getBytes());
    }
}
