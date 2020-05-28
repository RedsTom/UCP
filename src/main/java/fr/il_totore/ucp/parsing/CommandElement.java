package fr.il_totore.ucp.parsing;

import fr.il_totore.ucp.CommandContext;
import fr.il_totore.ucp.GeneralResult;

import java.util.ArrayList;
import java.util.List;

public interface CommandElement<S> {

    ParsingResult<S> parse(S sender, CommandArguments arguments, CommandContext<S> context);

    String getUsage(S sender);

    abstract class NamedElement<S> implements CommandElement<S> {

        private final String key;

        private boolean required = true;

        public NamedElement(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public boolean isRequired() {
            return required;
        }

        public NamedElement<S> optional() {
            required = false;
            return this;
        }
    }

    class SequenceElement<S> implements CommandElement<S> {

        private final CommandElement<S> element;

        private final List<CommandElement<S>> elements = new ArrayList<>();

        public SequenceElement(CommandElement<S> element) {
            this.element = element;
            init();
        }

        private void init() {
            elements.add(element);
        }

        public SequenceElement<S> and(CommandElement<S> element) {
            elements.add(element);
            return this;
        }

        @Override
        public ParsingResult<S> parse(S sender, CommandArguments arguments, CommandContext<S> context) {
            for (CommandElement<S> element : elements) {
                ParsingResult<S> result = element.parse(sender, arguments, context);
                if (result.getResultType() != GeneralResult.ResultType.SUCCESS) return result;
            }
            return new ParsingResult.ImplicitParsingResult<S>(GeneralResult.ResultType.SUCCESS).whilst("parsing " + arguments + " in " + context);
        }

        @Override
        public String getUsage(S sender) {
            return "";
        }
    }

}
