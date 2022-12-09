package jsonschema.spec.converter.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ElementReferences extends Element {
    private String title;

    private String sectionNumber;

    private final List<ElementReference> elementReferenceList = new ArrayList<>();

    @Override
    public Set<String> allowedAttributes() {
        return Set.of("title");
    }

    @Override
    public Set<Class<? extends Element>> allowedRepeatingChildElements() {
        return Set.of(ElementReference.class);
    }

    @Override
    public void processAttribute(String attributeName, String attributeValue) {
        title = attributeValue;
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementReference childElementReference)
            elementReferenceList.add(childElementReference);
    }

    @Override
    public void prepareBegin(Context context) {
        context.incrementSectionNumber();
        context.addSectionLevel();
    }

    @Override
    public void prepareEnd(Context context) {
        context.removeSectionLevel();

        sectionNumber = context.sections().stream()
                .map(Object::toString)
                .collect(Collectors.joining("."));
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var content = elementReferenceList.stream()
                .map(elementReference -> elementReference.convert(anchors))
                .collect(Collectors.joining());

        return "### " + sectionNumber + ". " + title + "\n\n" + content;
    }
}
