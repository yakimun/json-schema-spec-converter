package jsonschema.spec.converter.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ElementAbstract extends Element {
    private final List<ElementT> elementTList = new ArrayList<>();

    @Override
    public Set<Class<? extends Element>> allowedRepeatingChildElements() {
        return Set.of(ElementT.class);
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementT childElementT) elementTList.add(childElementT);
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var content = elementTList.stream()
                .map(elementT -> elementT.convert(anchors))
                .collect(Collectors.joining());

        return "## Abstract\n\n" + content;
    }
}
