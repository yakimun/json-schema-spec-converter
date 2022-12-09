package jsonschema.spec.converter.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ElementT extends Element {
    private String hangText;

    private final List<Element> elementList = new ArrayList<>();

    @Override
    public Set<String> allowedAttributes() {
        return Set.of("hangText");
    }

    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementEref.class, ElementList.class, ElementSpanx.class, ElementSourcecode.class, ElementUl.class);
    }

    @Override
    public Set<Class<? extends Element>> allowedRepeatingChildElements() {
        return Set.of(ElementXref.class, ElementCref.class, ElementText.class);
    }

    @Override
    public void processAttribute(String attributeName, String attributeValue) {
        hangText = attributeValue;
    }

    @Override
    public void processChildElement(Element childElement) {
        elementList.add(childElement);
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var content = "";
        if (hangText != null) content = "*" + hangText + "* ";

        var elementsContent = new StringBuilder();
        var crefs = new ArrayList<ElementCref>();
        for (var element : elementList) {
            if (element instanceof ElementCref elementCref) {
                elementsContent
                        .append("[^")
                        .append(elementCref.number())
                        .append("]");
                crefs.add(elementCref);
            } else {
                elementsContent.append(element.convert(anchors));
            }
        }
        for (var cref : crefs) {
            elementsContent
                    .append("\n")
                    .append(cref.convert(anchors));
        }

        var processedElementsContent = elementsContent.toString().lines()
                .filter(line -> !line.isBlank())
                .map(String::stripLeading)
                .collect(Collectors.joining("\n"));

        if (!elementList.isEmpty() && elementList.get(0) instanceof ElementList) {
            content += "\n";
        }

        return content + processedElementsContent + "\n\n";
    }
}
