package fr.il_totore.ucp.parsing;

public class SingleParameter {

    private final String value;
    private final int index;

    public SingleParameter(String value, int index) {
        this.value = value;
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public int getStartIndex() {
        return index;
    }

    public int getEndIndex() {
        return index + value.length();
    }

    @Override
    public String toString() {
        return getValue();
    }
}
