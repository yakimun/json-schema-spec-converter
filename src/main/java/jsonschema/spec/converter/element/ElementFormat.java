package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;

public final class ElementFormat extends Element {
    private String target;

    @Override
    public Set<String> allowedAttributes() {
        return Set.of("target", "type");
    }

    @Override
    public void processAttribute(String attributeName, String attributeValue) {
        if (attributeName.equals("target")) target = attributeValue;
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        return "<" + target + ">";
    }
}
