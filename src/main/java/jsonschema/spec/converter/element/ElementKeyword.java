package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;

public final class ElementKeyword extends Element {
    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementText.class);
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        return "";
    }
}
