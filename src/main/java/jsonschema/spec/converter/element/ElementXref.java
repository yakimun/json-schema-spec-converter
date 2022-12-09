package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;

public final class ElementXref extends Element {
    private String target;

    private String format;

    private ElementText elementText;

    @Override
    public Set<String> allowedAttributes() {
        return Set.of("target", "format");
    }

    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementText.class);
    }

    @Override
    public void processAttribute(String attributeName, String attributeValue) {
        switch (attributeName) {
            case "target" -> target = attributeValue;
            case "format" -> format = attributeValue;
        }
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementText childElementText) elementText = childElementText;
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var anchor = anchors.get(target);

        var text = "";
        if (elementText != null) {
            text = elementText.convert(anchors);
        } else if (format != null) {
            text = switch (format) {
                case "title" -> anchor.title();
                case "counter" -> anchor.sectionNumber();
                default -> throw new RuntimeException();
            };
        } else {
            var firstChar = anchor.sectionNumber().charAt(0);

            if (Character.isDigit(firstChar)) text = "Section ";
            if (Character.isLetter(firstChar) && !anchor.sectionNumber().startsWith("Appendix")) text = "Appendix ";
            text += anchor.sectionNumber();
        }

        return "[" + text + "](" + "#" + anchor.value() + ")";
    }
}
