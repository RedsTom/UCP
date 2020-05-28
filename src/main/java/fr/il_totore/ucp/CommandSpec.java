package fr.il_totore.ucp;

import fr.il_totore.ucp.parsing.CommandElement;
import fr.il_totore.ucp.parsing.InputTokenizer;
import fr.il_totore.ucp.parsing.SimpleSplitTokenizer;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface CommandSpec<S> {

    String getName();

    Optional<CommandElement<S>> getElement();

    BiFunction<S, CommandContext<S>, GeneralResult> getExecutor();

    Optional<String> getDescription();

    InputTokenizer getTokenizer();

    class ImplicitSpec<S> implements CommandSpec<S> {

        private final String name;

        private Optional<CommandElement<S>> element = Optional.empty();
        private BiFunction<S, CommandContext<S>, GeneralResult> executor;
        private Optional<String> description = Optional.empty();
        private Function<S, Boolean> permission = (s) -> true;
        private InputTokenizer tokenizer = new SimpleSplitTokenizer(" ");

        public ImplicitSpec(String name) {
            this.name = name;
        }

        public ImplicitSpec<S> requiring(CommandElement<S> commandElement) {
            this.element = Optional.of(commandElement);
            return this;
        }

        public ImplicitSpec<S> executing(BiFunction<S, CommandContext<S>, GeneralResult> commandExecutor) {
            this.executor = commandExecutor;
            return this;
        }

        public ImplicitSpec<S> describedAs(String description) {
            this.description = Optional.of(description);
            return this;
        }

        public ImplicitSpec<S> withPermission(Function<S, Boolean> predicate) {
            this.permission = predicate;
            return this;
        }

        public ImplicitSpec<S> tokenized(InputTokenizer tokenizer) {
            this.tokenizer = tokenizer;
            return this;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Optional<CommandElement<S>> getElement() {
            return element;
        }

        @Override
        public BiFunction<S, CommandContext<S>, GeneralResult> getExecutor() {
            return executor;
        }

        @Override
        public Optional<String> getDescription() {
            return description;
        }

        @Override
        public InputTokenizer getTokenizer() {
            return tokenizer;
        }
    }

}
