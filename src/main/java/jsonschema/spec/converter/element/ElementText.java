package jsonschema.spec.converter.element;

import java.util.Map;

public final class ElementText extends Element {
    private final String value;

    public ElementText(String value) {
        this.value = value;
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        return value;
    }
}
