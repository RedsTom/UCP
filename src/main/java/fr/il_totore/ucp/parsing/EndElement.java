package fr.il_totore.ucp.parsing;

import fr.il_totore.ucp.CommandContext;
import fr.il_totore.ucp.GeneralResult;
import fr.il_totore.ucp.utils.TriFunction;

import java.util.Optional;
import java.util.function.Function;

public abstract class EndElement<S> extends CommandElement.NamedElement<S> {

    public EndElement(String key) {
        super(key);
    }

    @Override
    public String getUsage(S sender) {
        return isRequired() ? "<" + getKey() + ">" : "[" + getKey() + "]";
    }

    public static class LambdaElement<S, T> extends EndElement<S> {

        private TriFunction<S, CommandArguments, CommandContext<S>, ParsingResult<S>> parseFunction = (a, b, c) -> null;
        private Optional<T> default_ = Optional.empty();

        public LambdaElement(String key) {
            super(key);
        }

        @Override
        public ParsingResult<S> parse(S sender, CommandArguments arguments, CommandContext<S> context) {
            return parseFunction.apply(sender, arguments, context);
        }

        public LambdaElement<S, T> lambda(TriFunction<S, CommandArguments, CommandContext<S>, ParsingResult<S>> func) {
            this.parseFunction = func;
            return this;
        }

        public LambdaElement<S, T> casting(Function<String, T> func) {
            return lambda((s, t, u) -> castToValue(s, t, u, func));
        }

        private ParsingResult<S> castToValue(S sender, CommandArguments args, CommandContext<S> context, Function<String, T> func) {
            Optional<T> value;
            try {
                value = Optional.of(func.apply(args.next().get()));
            } catch (Exception e) {
                value = Optional.empty();
            }
            if (!value.isPresent() && !default_.isPresent())
                return new ParsingResult.ImplicitParsingResult<S>(GeneralResult.ResultType.FAILURE).whilst("parsing " + args + " in " + context);
            return new ParsingResult.ImplicitParsingResult<S>(GeneralResult.ResultType.SUCCESS).whilst("parsing " + args + " in " + context);
        }

        public LambdaElement<S, T> orElseOption(Optional<T> default_) {
            this.default_ = default_;
            return this;
        }

        public LambdaElement<S, T> orElse(T default_) {
            this.default_ = Optional.ofNullable(default_);
            return this;
        }
    }
}
