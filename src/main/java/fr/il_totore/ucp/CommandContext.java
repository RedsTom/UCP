package fr.il_totore.ucp;

import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class CommandContext<S> {

    private final CommandSpec<S> spec;
    private final Multimap<String, S> args;

    public CommandContext(CommandSpec<S> spec, Multimap<String, S> args) {
        this.spec = spec;
        this.args = args;
    }

    public <T> Set<T> get(String key) {
        return (Set<T>) args.get(key);
    }

    public <T> Optional<T> getFirst(String key) {
        return (Optional<T>) args.get(key).stream().findFirst();
    }

    public <T> Optional<T> getLast(String key) {

        final Optional<Set<T>> iterableOpt = Optional.ofNullable(get(key));
        if (!(iterableOpt.isPresent())) return Optional.empty();
        T lastElement = null;
        int i = 0;
        for (T t : iterableOpt.get()) {
            if (i == (iterableOpt.get().size() - 1)) lastElement = t;
            i++;
        }
        return Optional.ofNullable(lastElement);
    }

    public Stream<S> getSlice(String key, int start, int end) {
        return (Stream<S>) get(key).stream().skip(start).limit(end - start + 1);
    }

    public <T> Optional<T> getAt(String key, int index) {
        List<T> elements = new ArrayList<>(get(key));
        return Optional.of(elements.get(index));
    }

    public void putArgument(String key, S value) {
        args.put(key, value);
    }

    public CommandSpec<S> getSpec() {
        return spec;
    }

    public GeneralResult execute(S sender) {
        return spec.getExecutor().apply(sender, this);
    }
}
