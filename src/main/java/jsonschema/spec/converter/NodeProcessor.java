package jsonschema.spec.converter;

import org.w3c.dom.Node;
import jsonschema.spec.converter.element.Context;
import jsonschema.spec.converter.element.Element;
import jsonschema.spec.converter.element.ElementAbstract;
import jsonschema.spec.converter.element.ElementAddress;
import jsonschema.spec.converter.element.ElementArtwork;
import jsonschema.spec.converter.element.ElementAuthor;
import jsonschema.spec.converter.element.ElementBack;
import jsonschema.spec.converter.element.ElementCDataSection;
import jsonschema.spec.converter.element.ElementCity;
import jsonschema.spec.converter.element.ElementCountry;
import jsonschema.spec.converter.element.ElementCref;
import jsonschema.spec.converter.element.ElementDate;
import jsonschema.spec.converter.element.ElementEmail;
import jsonschema.spec.converter.element.ElementEref;
import jsonschema.spec.converter.element.ElementFigure;
import jsonschema.spec.converter.element.ElementFormat;
import jsonschema.spec.converter.element.ElementFront;
import jsonschema.spec.converter.element.ElementKeyword;
import jsonschema.spec.converter.element.ElementList;
import jsonschema.spec.converter.element.ElementMiddle;
import jsonschema.spec.converter.element.ElementNote;
import jsonschema.spec.converter.element.ElementOrganization;
import jsonschema.spec.converter.element.ElementPostal;
import jsonschema.spec.converter.element.ElementPostamble;
import jsonschema.spec.converter.element.ElementPreamble;
import jsonschema.spec.converter.element.ElementReference;
import jsonschema.spec.converter.element.ElementReferences;
import jsonschema.spec.converter.element.ElementRfc;
import jsonschema.spec.converter.element.ElementSection;
import jsonschema.spec.converter.element.ElementSeriesInfo;
import jsonschema.spec.converter.element.ElementSourcecode;
import jsonschema.spec.converter.element.ElementSpanx;
import jsonschema.spec.converter.element.ElementStreet;
import jsonschema.spec.converter.element.ElementT;
import jsonschema.spec.converter.element.ElementText;
import jsonschema.spec.converter.element.ElementTitle;
import jsonschema.spec.converter.element.ElementUri;
import jsonschema.spec.converter.element.ElementWorkgroup;
import jsonschema.spec.converter.element.ElementXref;

import java.util.HashSet;

public class NodeProcessor {
    public Element process(Node node, Context context) {
        var element = createElement(node);
        element.prepareBegin(context);
        processAttributes(node, element);
        processChildNodes(node, context, element);
        element.prepareEnd(context);

        return element;
    }

    private Element createElement(Node node) {
        return switch (node.getNodeName()) {
            case "abstract" -> new ElementAbstract();
            case "address" -> new ElementAddress();
            case "artwork" -> new ElementArtwork();
            case "author" -> new ElementAuthor();
            case "back" -> new ElementBack();
            case "city" -> new ElementCity();
            case "country" -> new ElementCountry();
            case "cref" -> new ElementCref();
            case "date" -> new ElementDate();
            case "email" -> new ElementEmail();
            case "eref" -> new ElementEref();
            case "figure" -> new ElementFigure();
            case "format" -> new ElementFormat();
            case "front" -> new ElementFront();
            case "keyword" -> new ElementKeyword();
            case "list" -> new ElementList();
            case "middle" -> new ElementMiddle();
            case "note" -> new ElementNote();
            case "organization" -> new ElementOrganization();
            case "postal" -> new ElementPostal();
            case "postamble" -> new ElementPostamble();
            case "preamble" -> new ElementPreamble();
            case "reference" -> new ElementReference();
            case "references" -> new ElementReferences();
            case "rfc" -> new ElementRfc();
            case "section" -> new ElementSection();
            case "seriesInfo" -> new ElementSeriesInfo();
            case "sourcecode" -> new ElementSourcecode();
            case "spanx" -> new ElementSpanx();
            case "street" -> new ElementStreet();
            case "t" -> new ElementT();
            case "title" -> new ElementTitle();
            case "uri" -> new ElementUri();
            case "workgroup" -> new ElementWorkgroup();
            case "xref" -> new ElementXref();
            case "#cdata-section" -> new ElementCDataSection(node.getNodeValue());
            case "#text" -> new ElementText(node.getNodeValue());
            default -> throw new RuntimeException();
        };
    }

    private void processAttributes(Node node, Element element) {
        var attributes = node.getAttributes();

        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                var attribute = attributes.item(i);
                var name = attribute.getNodeName();

                if (!element.allowedAttributes().contains(name)) {
                    throw new RuntimeException(node.getNodeName() + "." + name);
                }

                element.processAttribute(name, attribute.getNodeValue());
            }
        }
    }

    private void processChildNodes(Node node, Context context, Element element) {
        var childNodes = node.getChildNodes();
        var encounteredChildNodeTypes = new HashSet<Class<? extends Element>>();

        for (int i = 0; i < childNodes.getLength(); i++) {
            var childNode = childNodes.item(i);

            if (childNode.getNodeName().equals("#comment") || childNode.getNodeName().equals("#text") && childNode.getNodeValue().isBlank()) {
                continue;
            }
            var childElement = process(childNode, context);

            if (!element.allowedChildElements().contains(childElement.getClass()) && !element.allowedRepeatingChildElements().contains(childElement.getClass())) {
                throw new RuntimeException(node.getNodeName() + "." + childNode.getNodeName());
            }

            if (encounteredChildNodeTypes.contains(childElement.getClass()) && !element.allowedRepeatingChildElements().contains(childElement.getClass())) {
                throw new RuntimeException(node.getNodeName() + "." + childNode.getNodeName());
            }

            encounteredChildNodeTypes.add(childElement.getClass());
            element.processChildElement(childElement);
        }
    }
}
