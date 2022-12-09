package jsonschema.spec.converter.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ElementSection extends Element {
    private String anchor;

    private String title;

    private String prefix;

    private String number;

    private final List<Element> elementList = new ArrayList<>();

    @Override
    public Set<String> allowedAttributes() {
        return Set.of("anchor", "title");
    }

    @Override
    public Set<Class<? extends Element>> allowedRepeatingChildElements() {
        return Set.of(ElementFigure.class, ElementT.class, ElementSection.class);
    }

    @Override
    public void processAttribute(String attributeName, String attributeValue) {
        switch (attributeName) {
            case "anchor" -> anchor = attributeValue;
            case "title" -> title = attributeValue;
        }
    }

    @Override
    public void processChildElement(Element childElement) {
        elementList.add(childElement);
    }

    @Override
    public void prepareBegin(Context context) {
        if (!context.insideBack()) {
            context.incrementSectionNumber();
            context.addSectionLevel();
        } else {
            context.incrementBackSectionNumber();
            context.addBackSectionLevel();
        }
    }

    @Override
    public void prepareEnd(Context context) {
        if (!context.insideBack()) {
            context.removeSectionLevel();
        } else {
            context.removeBackSectionLevel();
        }

        var sections = context.sections();
        if (context.insideBack()) {
            sections = context.backSections();
        }

        prefix = "#".repeat(sections.size() + 1);

        if (!context.insideBack()) {
            number = sections.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("."));
        } else {
            number = "";
            if (sections.size() == 1) {
                number = "Appendix ";
            }

            number += ((char) ('A' + sections.get(0) - 1));

            if (sections.size() > 1) {
                number += "." + sections.stream()
                        .skip(1)
                        .map(Object::toString)
                        .collect(Collectors.joining("."));
            }
        }

        if (anchor != null) {
            var preparedAnchor = (number + "-" + title)
                    .toLowerCase()
                    .replace(' ', '-')
                    .replace('"', '-')
                    .replace("$", "")
                    .replace(".", "");

            context.addAnchor(anchor, new Anchor(preparedAnchor, title, number));
        }
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var content = elementList.stream()
                .map(element -> element.convert(anchors))
                .collect(Collectors.joining());

        return prefix + " " + number + ". " + title + "\n\n" + content;
    }
}
