package fr.il_totore.ucp.parsing;

import java.util.List;
import java.util.Optional;

public class CommandArguments {

    private final String raw;
    private final List<SingleParameter> params;

    private int index = -1;

    public CommandArguments(String raw, List<SingleParameter> params) {
        this.raw = raw;
        this.params = params;
    }

    public String getRawInput() {
        return raw;
    }

    public boolean hasNext() {
        return index < params.size() - 1;
    }

    public Optional<String> next() {
        if (!hasNext()) return Optional.empty();
        index += 1;
        return Optional.of(params.get(index).getValue());
    }

    public Optional<String> peek() {
        if (!hasNext()) return Optional.empty();
        return Optional.of(params.get(index + 1).getValue());
    }

    public int getCurrentIndex() {
        return index;
    }

    public void reset(int index) {
        this.index = index;
    }

    public void reset() {
        reset(-1);
    }

    public void back(int n) {
        index -= n;
        if (index < -1) reset();
    }
}
