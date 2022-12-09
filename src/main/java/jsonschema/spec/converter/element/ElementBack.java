package jsonschema.spec.converter.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ElementBack extends Element {
    private final List<ElementReferences> elementReferencesList = new ArrayList<>();

    private final List<ElementSection> elementSectionList = new ArrayList<>();

    private String sectionNumber;

    @Override
    public Set<Class<? extends Element>> allowedRepeatingChildElements() {
        return Set.of(ElementReferences.class, ElementSection.class);
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementReferences childElementReferences)
            elementReferencesList.add(childElementReferences);
        if (childElement instanceof ElementSection childElementSection) elementSectionList.add(childElementSection);
    }

    @Override
    public void prepareBegin(Context context) {
        context.incrementSectionNumber();
        context.addSectionLevel();
        context.setInsideBack(true);
    }

    @Override
    public void prepareEnd(Context context) {
        context.setInsideBack(false);
        context.removeSectionLevel();

        sectionNumber = context.sections().stream()
                .map(Object::toString)
                .collect(Collectors.joining("."));
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var references = elementReferencesList.stream()
                .map(elementReferences -> elementReferences.convert(anchors))
                .collect(Collectors.joining());

        var sections = elementSectionList.stream()
                .map(elementSection -> elementSection.convert(anchors))
                .collect(Collectors.joining());

        return "## " + sectionNumber + ". References\n\n" + references + sections;
    }
}
