package jsonschema.spec.converter.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ElementPostamble extends Element {
    private final List<Element> elementList = new ArrayList<>();

    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementXref.class);
    }

    @Override
    public Set<Class<? extends Element>> allowedRepeatingChildElements() {
        return Set.of(ElementText.class);
    }

    @Override
    public void processChildElement(Element childElement) {
        elementList.add(childElement);
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var content = elementList.stream()
                .map(element -> element.convert(anchors))
                .collect(Collectors.joining())
                .lines()
                .filter(line -> !line.isBlank())
                .collect(Collectors.joining("\n"))
                .stripIndent();

        return content + "\n\n";
    }
}
