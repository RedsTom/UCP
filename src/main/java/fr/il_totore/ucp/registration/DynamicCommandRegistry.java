package fr.il_totore.ucp.registration;

import fr.il_totore.ucp.CommandSpec;

import java.util.List;
import java.util.function.Predicate;

public abstract class DynamicCommandRegistry<S> implements CommandRegistry<S> {

    private final List<CommandSpec<S>> sequence;

    public DynamicCommandRegistry(List<CommandSpec<S>> sequence) {
        this.sequence = sequence;
    }

    @Override
    public void register(CommandSpec<S> spec) {
        sequence.add(spec);
    }

    @Override
    public void unregister(CommandSpec<S> spec) {
        sequence.remove(spec);
    }

    @Override
    public void filter(Predicate<? super CommandSpec<S>> predicate) {
        sequence.removeIf(predicate);
    }

    @Override
    public List<CommandSpec<S>> getRegisteredCommands() {
        return sequence;
    }
}
