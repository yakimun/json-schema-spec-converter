package jsonschema.spec.converter.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private final Map<String, Anchor> anchors = new HashMap<>();

    private final List<Integer> sections = new ArrayList<>(List.of(0));

    private int listLevel;

    private int footnoteCount;

    private boolean insideBack;

    private final List<Integer> backSections = new ArrayList<>(List.of(0));

    public Map<String, Anchor> anchors() {
        return anchors;
    }

    public void addAnchor(String key, Anchor anchor) {
        anchors.put(key, anchor);
    }

    public List<Integer> sections() {
        return sections;
    }

    public void addSectionLevel() {
        sections.add(0);
    }

    public void removeSectionLevel() {
        sections.remove(sections.size() - 1);
    }

    public void incrementSectionNumber() {
        var index = sections.size() - 1;
        sections.set(index, sections.get(index) + 1);
    }

    public int listLevel() {
        return listLevel;
    }

    public void incrementListLevel() {
        listLevel++;
    }

    public void decrementListLevel() {
        listLevel--;
    }

    public int incrementFootnoteCount() {
        return ++footnoteCount;
    }

    public boolean insideBack() {
        return insideBack;
    }

    public void setInsideBack(boolean value) {
        insideBack = value;
    }

    public List<Integer> backSections() {
        return backSections;
    }

    public void addBackSectionLevel() {
        backSections.add(0);
    }

    public void removeBackSectionLevel() {
        backSections.remove(backSections.size() - 1);
    }

    public void incrementBackSectionNumber() {
        var index = backSections.size() - 1;
        backSections.set(index, backSections.get(index) + 1);
    }
}
