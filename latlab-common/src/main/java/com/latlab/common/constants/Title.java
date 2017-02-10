package com.latlab.common.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Title implements EnumResolvable {
    MR("Mr", "Mr."),
    MRS("Mrs", "Mrs."),
    MS("Ms", "Ms."),
    MASTER("Master", "Master"),
    MADAM("Madam", "Madam"),
    MISS("Miss", "Miss"),
    DR("Dr", "Dr."),
    REV("Rev", "Rev."),
    OTHER("Other", "Other");

    private final String label;
    private final String key;

    private Title(String key, String label) {
        this.key = key;
        this.label = label;
    }

    @Override
    public String getCode() {
        return this.key;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public static List<Title> asList() {
        return Arrays.asList(Title.values());
    }

    public static List<EnumResolvable> asResolvableList() {
        List<EnumResolvable> resolvables = new ArrayList<>();
        resolvables.addAll(asList());
        return resolvables;
    }

    @Override
    public String toString() {
        return label;
    }

}
