package jsonschema.spec.converter.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class ElementList extends Element {
    public static final String FENCE = "|---|";

    private String style;

    private int level;

    private final List<ElementT> elementTList = new ArrayList<>();

    @Override
    public Set<String> allowedAttributes() {
        return Set.of("style");
    }

    @Override
    public Set<Class<? extends Element>> allowedRepeatingChildElements() {
        return Set.of(ElementT.class);
    }

    @Override
    public void processAttribute(String attributeName, String attributeValue) {
        style = attributeValue;
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementT childElementT) elementTList.add(childElementT);
    }

    @Override
    public void prepareBegin(Context context) {
        context.incrementListLevel();
        level = context.listLevel();
    }

    @Override
    public void prepareEnd(Context context) {
        context.decrementListLevel();
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var counter = new AtomicInteger();
        var content = elementTList.stream()
                .map(elementT -> {
                    var prefix = "    ".repeat(level - 1);
                    if (style != null && style.equals("numbers")) {
                        prefix += counter.incrementAndGet() + ".";
                    } else {
                        prefix += "-";
                    }

                    // Add fences to hold padding in nested lists.
                    return FENCE + prefix + " " + elementT.convert(anchors);
                })
                .collect(Collectors.joining());

        return content + "\n\n";
    }
}
