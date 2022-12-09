package jsonschema.spec.converter.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class ElementReference extends Element {
    private String anchor;

    private String target;

    private ElementFront elementFront;

    private final List<ElementSeriesInfo> elementSeriesInfoList = new ArrayList<>();

    @Override
    public Set<String> allowedAttributes() {
        return Set.of("anchor", "target");
    }

    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementFront.class, ElementFormat.class);
    }

    @Override
    public Set<Class<? extends Element>> allowedRepeatingChildElements() {
        return Set.of(ElementSeriesInfo.class);
    }

    @Override
    public void processAttribute(String attributeName, String attributeValue) {
        switch (attributeName) {
            case "anchor" -> anchor = attributeValue;
            case "target" -> target = attributeValue;
        }
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementFront childElementFront) elementFront = childElementFront;
        if (childElement instanceof ElementSeriesInfo childElementSeriesInfo)
            elementSeriesInfoList.add(childElementSeriesInfo);
    }

    @Override
    public void prepareEnd(Context context) {
        if (anchor != null) {
            var preparedAnchor = anchor.toLowerCase()
                    .replace(' ', '-')
                    .replace('"', '-')
                    .replace("$", "")
                    .replace(".", "");

            context.addAnchor(anchor, new Anchor(preparedAnchor, "", ""));
        }
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var authorsComponents = new ArrayList<String>();
        var frontAuthors = elementFront.elementAuthorList();

        var lastIndex = frontAuthors.size() == 1 ? 1 : frontAuthors.size() - 1;
        for (int i = 0; i < lastIndex; i++) {
            var frontAuthor = frontAuthors.get(i);
            authorsComponents.add(authorSurname(frontAuthor));
            authorsComponents.add(authorInitials(frontAuthor));

            var role = authorRole(frontAuthor);
            if (role != null) {
                authorsComponents.add(role);
            }
        }

        var authors = authorsComponents.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));

        if (frontAuthors.size() > 1) {
            var lastFrontAuthor = frontAuthors.get(frontAuthors.size() - 1);

            if (frontAuthors.size() > 2) {
                authors += ",";
            }

            authors += " and " + authorInitials(lastFrontAuthor) + " " + authorSurname(lastFrontAuthor);

            var role = authorRole(lastFrontAuthor);
            if (role != null) {
                authors += ", " + role;
            }
        }

        if (!authors.isEmpty()) {
            authors += ", ";
        }

        var title = "\"" + elementFront.elementTitle().elementText().convert(anchors) + "\", ";

        var seriesInfo = new StringBuilder();
        var url = target;
        for (var elementSeriesInfo : elementSeriesInfoList) {
            if (elementSeriesInfo.name().equals("Internet-Draft")) {
                seriesInfo
                        .append("Work in Progress, ")
                        .append(elementSeriesInfo.name())
                        .append(", ")
                        .append(elementSeriesInfo.value())
                        .append(", ");
                url = "https://datatracker.ietf.org/doc/html/" + elementSeriesInfo.value();
            } else {
                seriesInfo
                        .append(elementSeriesInfo.name())
                        .append(" ")
                        .append(elementSeriesInfo.value())
                        .append(", ");
            }
        }

        var date = elementFront.elementDate().convert(anchors) + ", ";

        return "#### [" + anchor + "]\n\n" + authors + title + seriesInfo + date + "<<" + url + ">>.\n\n";
    }

    private String authorSurname(ElementAuthor elementAuthor) {
        if (elementAuthor.surname() != null) {
            return elementAuthor.surname();
        }

        if (elementAuthor.fullname() == null) {
            return null;
        }

        return elementAuthor.fullname().split(" ")[1];
    }

    private String authorInitials(ElementAuthor elementAuthor) {
        if (elementAuthor.fullname() == null) {
            return elementAuthor.initials();
        }

        return elementAuthor.fullname().split(" ")[0].charAt(0) + ".";
    }

    private String authorRole(ElementAuthor elementAuthor) {
        if (elementAuthor.role() == null) {
            return null;
        }

        if (!elementAuthor.role().equals("editor")) {
            throw new RuntimeException();
        }

        return "Ed.";
    }
}
