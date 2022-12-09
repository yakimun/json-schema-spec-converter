package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;

public final class ElementAddress extends Element {
    private ElementPostal elementPostal;

    private ElementEmail elementEmail;

    private ElementUri elementUri;

    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementPostal.class, ElementEmail.class, ElementUri.class);
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementPostal childElementPostal) elementPostal = childElementPostal;
        if (childElement instanceof ElementEmail childElementEmail) elementEmail = childElementEmail;
        if (childElement instanceof ElementUri childElementUri) elementUri = childElementUri;
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var content = "";
        if (elementPostal != null) content += elementPostal.convert(anchors);
        content += elementEmail.convert(anchors);
        if (elementUri != null) content += elementUri.convert(anchors);

        return content;
    }
}
