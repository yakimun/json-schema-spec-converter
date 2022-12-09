package jsonschema.spec.converter.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ElementFront extends Element {
    private ElementTitle elementTitle;

    private final List<ElementAuthor> elementAuthorList = new ArrayList<>();

    private ElementDate elementDate;

    private ElementAbstract elementAbstract;

    private ElementNote elementNote;

    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementTitle.class, ElementDate.class, ElementWorkgroup.class, ElementAbstract.class, ElementNote.class);
    }

    @Override
    public Set<Class<? extends Element>> allowedRepeatingChildElements() {
        return Set.of(ElementAuthor.class, ElementKeyword.class);
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementTitle childElementTitle) elementTitle = childElementTitle;
        if (childElement instanceof ElementAuthor childElementAuthor) elementAuthorList.add(childElementAuthor);
        if (childElement instanceof ElementDate childElementDate) elementDate = childElementDate;
        if (childElement instanceof ElementAbstract childElementAbstract) elementAbstract = childElementAbstract;
        if (childElement instanceof ElementNote childElementNote) elementNote = childElementNote;
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        var content = elementTitle.convert(anchors) + elementAbstract.convert(anchors);
        if (elementNote != null) content += elementNote.convert(anchors);

        return content;
    }

    public ElementTitle elementTitle() {
        return elementTitle;
    }

    public List<ElementAuthor> elementAuthorList() {
        return elementAuthorList;
    }

    public ElementDate elementDate() {
        return elementDate;
    }
}
