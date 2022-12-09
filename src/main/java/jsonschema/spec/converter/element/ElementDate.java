package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ElementDate extends Element {
    private String day;

    private String month;

    private String year;

    @Override
    public Set<String> allowedAttributes() {
        return Set.of("day", "month", "year");
    }

    @Override
    public void processAttribute(String attributeName, String attributeValue) {
        switch (attributeName) {
            case "day" -> day = attributeValue;
            case "month" -> month = attributeValue;
            case "year" -> year = attributeValue;
        }
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        return Stream.of(day, month, year)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }
}
