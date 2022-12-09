package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;

public final class ElementPostal extends Element {
    private ElementStreet elementStreet;

    private ElementCity elementCity;

    private ElementCountry elementCountry;

    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementStreet.class, ElementCity.class, ElementCountry.class);
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementStreet childElementStreet) elementStreet = childElementStreet;
        if (childElement instanceof ElementCity childElementCity) elementCity = childElementCity;
        if (childElement instanceof ElementCountry childElementCountry) elementCountry = childElementCountry;
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        return elementStreet.convert(anchors) + elementCity.convert(anchors) + elementCountry.convert(anchors);
    }
}
