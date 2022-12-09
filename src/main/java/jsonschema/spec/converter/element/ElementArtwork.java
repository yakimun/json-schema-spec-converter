package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;

public final class ElementArtwork extends Element {
    private ElementCDataSection elementCDataSection;

    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementCDataSection.class);
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementCDataSection childElementCDataSection) elementCDataSection = childElementCDataSection;
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        return "```\n" + elementCDataSection.convert(anchors) + "\n```\n\n";
    }
}
