package fr.il_totore.ucp.registration;

import fr.il_totore.ucp.CommandSpec;
import fr.il_totore.ucp.parsing.ParsingResult;

import java.util.function.Predicate;

public interface CommandRegistry<S> {

    ParsingResult<S> parse(S sender, String command);

    void register(CommandSpec<S> spec);

    void unregister(CommandSpec<S> spec);

    void filter(Predicate<? super CommandSpec<S>> predicate);

}
