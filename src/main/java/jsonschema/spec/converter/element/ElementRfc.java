package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ElementRfc extends Element {
    private ElementFront elementFront;

    private ElementMiddle elementMiddle;

    private ElementBack elementBack;

    @Override
    public Set<String> allowedAttributes() {
        return Set.of("category", "docName", "ipr", "submissionType");
    }

    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementFront.class, ElementMiddle.class, ElementBack.class);
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementFront childElementFront) elementFront = childElementFront;
        if (childElement instanceof ElementMiddle childElementMiddle) elementMiddle = childElementMiddle;
        if (childElement instanceof ElementBack childElementBack) elementBack = childElementBack;
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var content = elementFront.convert(anchors) + elementMiddle.convert(anchors) + elementBack.convert(anchors);

        // Add authors
        content += "## Authors' Addresses\n\n";
        content += elementFront.elementAuthorList().stream()
                .map(elementAuthor -> elementAuthor.convert(anchors))
                .collect(Collectors.joining());

        // Remove fences that hold padding in nested lists.
        content = content.lines()
                .map(line -> {
                    if (line.startsWith(ElementList.FENCE)) return line.substring(ElementList.FENCE.length());
                    return line;
                })
                .collect(Collectors.joining("\n"));

        // Remove spaces before footnotes.
        content = content.replaceAll("\\s+((\\[\\^\\d+])+)\\s", "$1\n");

        return content;
    }
}
