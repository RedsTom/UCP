package fr.il_totore.ucp.parsing;

import fr.il_totore.ucp.CommandContext;
import fr.il_totore.ucp.GeneralResult;

import java.util.Optional;

public abstract class ParsingResult<S> extends GeneralResult {

    public abstract Optional<CommandArguments> getArguments();

    public abstract Optional<CommandContext<S>> getContext();

    public static class ImplicitParsingResult<R> extends ParsingResult<R> {

        private final ResultType resultType;

        private Optional<String> message = Optional.empty();
        private Optional<CommandArguments> arguments = Optional.empty();
        private Optional<CommandContext<R>> context = Optional.empty();

        public ImplicitParsingResult(ResultType resultType) {
            this.resultType = resultType;
        }

        public ImplicitParsingResult<R> whilst(String message) {
            this.message = Optional.of(message);
            return this;
        }

        public ImplicitParsingResult<R> forTooManyArguments() {
            whilst("Too many arguments");
            return this;
        }

        public ImplicitParsingResult<R> forNotEnoughArguments() {
            whilst("Not enough arguments");
            return this;
        }

        public ImplicitParsingResult<R> using(CommandArguments arguments) {
            this.arguments = Optional.of(arguments);
            return this;
        }

        public ImplicitParsingResult<R> in(CommandContext<R> context) {
            this.context = Optional.of(context);
            return this;
        }

        public ImplicitParsingResult<R> asParsingResult() {
            return this;
        }

        public ImplicitParsingResult<R> parsing(CommandArguments arguments) {
            whilst("parsing command").using(arguments);
            return this;
        }

        @Override
        public Optional<CommandArguments> getArguments() {
            return arguments;
        }

        @Override
        public Optional<CommandContext<R>> getContext() {
            return context;
        }

        @Override
        public ResultType getResultType() {
            return resultType;
        }

        @Override
        public Optional<String> getMessage() {
            return message;
        }
    }

}
