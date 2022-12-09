package jsonschema.spec.converter.element;

import java.util.Map;

public final class ElementCDataSection extends Element {
    private final String value;

    public ElementCDataSection(String value) {
        this.value = value;
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        return value.strip();
    }
}
