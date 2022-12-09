package jsonschema.spec.converter.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ElementMiddle extends Element {
    private final List<ElementSection> elementSectionList = new ArrayList<>();

    @Override
    public Set<Class<? extends Element>> allowedRepeatingChildElements() {
        return Set.of(ElementSection.class);
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementSection childElementSection) elementSectionList.add(childElementSection);
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        return elementSectionList.stream()
                .map(elementSection -> elementSection.convert(anchors))
                .collect(Collectors.joining());
    }
}
