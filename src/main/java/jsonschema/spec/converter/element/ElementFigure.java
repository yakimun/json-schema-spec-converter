package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;

public final class ElementFigure extends Element {
    private ElementPreamble elementPreamble;

    private ElementArtwork elementArtwork;

    private ElementPostamble elementPostamble;

    @Override
    public Set<String> allowedAttributes() {
        return Set.of("align");
    }

    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementPreamble.class, ElementArtwork.class, ElementPostamble.class);
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementPreamble childElementPreamble) elementPreamble = childElementPreamble;
        if (childElement instanceof ElementArtwork childElementArtwork) elementArtwork = childElementArtwork;
        if (childElement instanceof ElementPostamble childElementPostamble) elementPostamble = childElementPostamble;
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var content = "";
        if (elementPreamble != null) content = elementPreamble.convert(anchors);
        content += elementArtwork.convert(anchors);
        if (elementPostamble != null) content += elementPostamble.convert(anchors);

        return content;
    }
}
