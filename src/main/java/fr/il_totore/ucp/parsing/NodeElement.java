package fr.il_totore.ucp.parsing;

import fr.il_totore.ucp.CommandContext;
import fr.il_totore.ucp.GeneralResult;

import java.util.ArrayList;
import java.util.List;

public class NodeElement<S> extends CommandElement.NamedElement<S> {

    protected List<BranchElement<S>> branches = new ArrayList<>();

    public NodeElement(String key) {
        super(key);
    }

    @Override
    public ParsingResult<S> parse(S sender, CommandArguments arguments, CommandContext<S> context) {
        for (BranchElement<S> branch : branches) {
            if (branch.isValid(sender, arguments, context)) {
                return branch.parse(sender, arguments, context);
            }
        }
        return (ParsingResult<S>) new ParsingResult.ImplicitParsingResult(GeneralResult.ResultType.FAILURE).whilst("parsing " + arguments + " in " + context);
    }

    @Override
    public String getUsage(S sender) {
        return "";
    }

    public static class ImplicitNodeElement<S> extends NodeElement<S> {

        public ImplicitNodeElement(String key) {
            super(key);
        }

        public ImplicitNodeElement<S> choosing(BranchElement<S> element) {
            branches.add(element);
            return this;
        }

        public ImplicitNodeElement<S> or(BranchElement<S> element) {
            choosing(element);
            return this;
        }

    }
}
