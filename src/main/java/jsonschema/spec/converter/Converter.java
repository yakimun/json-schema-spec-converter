package jsonschema.spec.converter;

import jsonschema.spec.converter.element.Context;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;

public class Converter {
    public String convert(Path path) throws ParserConfigurationException, IOException, SAXException {
        var builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        builder.setEntityResolver(new CachingEntityResolver());

        var document = builder.parse(path.toString());

        var nodeProcessor = new NodeProcessor();
        var context = new Context();
        var rfc = nodeProcessor.process(document.getDocumentElement(), context);

        return rfc.convert(context.anchors());
    }
}
