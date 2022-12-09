package jsonschema.spec.converter.element;

import java.util.Map;
import java.util.Set;

public final class ElementAuthor extends Element {
    private String fullname;

    private String initials;

    private String role;

    private String surname;

    private ElementOrganization elementOrganization;

    private ElementAddress elementAddress;

    @Override
    public Set<String> allowedAttributes() {
        return Set.of("fullname", "initials", "role", "surname");
    }

    @Override
    public Set<Class<? extends Element>> allowedChildElements() {
        return Set.of(ElementOrganization.class, ElementAddress.class);
    }

    @Override
    public void processAttribute(String attributeName, String attributeValue) {
        switch (attributeName) {
            case "fullname" -> fullname = attributeValue;
            case "initials" -> initials = attributeValue;
            case "role" -> role = attributeValue;
            case "surname" -> surname = attributeValue;
        }
    }

    @Override
    public void processChildElement(Element childElement) {
        if (childElement instanceof ElementOrganization childElementOrganization) elementOrganization = childElementOrganization;
        if (childElement instanceof ElementAddress childElementAddress) elementAddress = childElementAddress;
    }

    @Override
    public String convert(Map<String, Anchor> anchors) {
        return elementOrganization.convert(anchors) + elementAddress.convert(anchors);
    }

    public String fullname() {
        return fullname;
    }

    public String initials() {
        return initials;
    }

    public String role() {
        return role;
    }

    public String surname() {
        return surname;
    }
}
