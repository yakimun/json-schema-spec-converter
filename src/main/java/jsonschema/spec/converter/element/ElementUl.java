package jsonschema.spec.converter.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ElementUl extends Element {
    private final List<ElementLi> elementLiList = new ArrayList<>();

    @Override
    public Set<Class<? extends Element>> allowedRepeatingChildElements() {
        return Set.of(ElementLi.class);
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementLi childElementLi) elementLiList.add(childElementLi);
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var content = elementLiList.stream()
                .map(elementLi -> "- " + elementLi.convert(anchors))
                .collect(Collectors.joining("\n"));

        return content + "\n\n";
    }
}
