package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;

public final class ElementEmail extends Element {
    private ElementText elementText;

    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementText.class);
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementText childElementText) elementText = childElementText;
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        return "Email: " + elementText.convert(anchors) + "\n\n";
    }
}
