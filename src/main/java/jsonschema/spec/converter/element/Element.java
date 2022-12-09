package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;

public abstract class Element {
    public Set<String> allowedAttributes() {
        return Set.of();
    }

    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of();
    }

    public Set<Class<? extends Element>> allowedRepeatingChildElements() {
        return Set.of();
    }

    public void processAttribute(String attributeName, String attributeValue) {
    }

    public void processChildElement(Element childElement) {
    }

    public void prepareBegin(Context context) {
    }

    public void prepareEnd(Context context) {
    }

    public abstract String convert(Map<String, Anchor> anchors);
}
