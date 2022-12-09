package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ElementPreamble extends Element {
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
        var content = elementText.convert(anchors).lines()
                .filter(line -> !line.isBlank())
                .collect(Collectors.joining("\n"))
                .stripIndent();

        return content + "\n\n";
    }
}
