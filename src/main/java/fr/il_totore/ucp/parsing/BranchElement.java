package fr.il_totore.ucp.parsing;

import fr.il_totore.ucp.CommandContext;
import fr.il_totore.ucp.GeneralResult;

import java.util.Optional;
import java.util.function.BiFunction;

public abstract class BranchElement<S> implements CommandElement<S> {

    private Optional<CommandElement<S>> child = Optional.empty();

    public static <S> LabelledBranchElement<S> label(String key) {
        return new LabelledBranchElement<>(key);
    }

    public abstract boolean isValid(S sender, CommandArguments arguments, CommandContext<S> context);

    public Optional<CommandElement<S>> getChild() {
        return child;
    }

    public BranchElement<S> of(CommandElement<S> child) {
        this.child = Optional.of(child);
        return this;
    }

    @Override
    public ParsingResult<S> parse(S sender, CommandArguments arguments, CommandContext<S> context) {
        return child.map(element -> element.parse(sender, arguments, context)).orElseGet(() -> new ParsingResult.ImplicitParsingResult<S>(GeneralResult.ResultType.FAILURE).whilst("parsing " + arguments + " in " + context));
    }

    @Override
    public String getUsage(S sender) {
        return "";
    }

    public static class LabelledBranchElement<S> extends BranchElement<S> {

        private final String key;
        private BiFunction<String, String, Boolean> stringValidator = String::equalsIgnoreCase;

        public LabelledBranchElement(String key) {
            this.key = key;
        }

        @Override
        public boolean isValid(S sender, CommandArguments arguments, CommandContext<S> context) {
            final boolean valid = stringValidator.apply(key, arguments.next().orElse(null));
            if (!valid) arguments.back(1);
            return valid;
        }

        public LabelledBranchElement<S> using(BiFunction<String, String, Boolean> stringValidator) {
            this.stringValidator = stringValidator;
            return this;
        }
    }

    public class UntaggableBranchElement<S> extends BranchElement<S> {

        @Override
        public boolean isValid(S sender, CommandArguments arguments, CommandContext<S> context) {
            final int index = arguments.getCurrentIndex();
            final boolean result = parse(sender, arguments, context).getResultType().isSuccessful();
            arguments.reset(index);
            return result;
        }

    }

}
