package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;

public final class ElementSeriesInfo extends Element {
    private String name;

    private String value;

    @Override
    public Set<String> allowedAttributes() {
        return Set.of("name", "value");
    }

    @Override
    public void processAttribute(String attributeName, String attributeValue) {
        switch (attributeName) {
            case "name" -> name = attributeValue;
            case "value" -> value = attributeValue;
        }
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        return "";
    }

    public String name() {
        return name;
    }

    public String value() {
        return value;
    }
}
