package fr.il_totore.ucp.registration;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import fr.il_totore.ucp.CommandContext;
import fr.il_totore.ucp.CommandSpec;
import fr.il_totore.ucp.GeneralResult;
import fr.il_totore.ucp.parsing.CommandArguments;
import fr.il_totore.ucp.parsing.ParsingResult;
import fr.il_totore.ucp.utils.ListUtils;

import java.util.List;
import java.util.Optional;

public class PrefixedCommandRegistry<S> extends DynamicCommandRegistry<S> {

    private final List<CommandSpec<S>> sequence;
    private final String prefix;

    protected PrefixedCommandRegistry(List<CommandSpec<S>> sequence, String prefix) {
        super(sequence);
        this.sequence = sequence;
        this.prefix = prefix;
    }

    @Override
    public ParsingResult<S> parse(S sender, String command) {
        if (!(command.startsWith(prefix)))
            return new ParsingResult.ImplicitParsingResult<S>(GeneralResult.ResultType.FAILURE).whilst("evaluating prefix");
        final String stripedCommand = command.substring(1);
        final Optional<CommandSpec<S>> spec = ListUtils.find(sequence, (cmdSpec) -> cmdSpec.getTokenizer().getCommandName(stripedCommand).equalsIgnoreCase(cmdSpec.getName()));
        if (!spec.isPresent())
            return new ParsingResult.ImplicitParsingResult<S>(GeneralResult.ResultType.FAILURE).whilst("matching command");
        final CommandContext<S> context = new CommandContext<S>(spec.get(), Multimaps.forMap(Maps.newHashMap()));
        final CommandArguments arguments = new CommandArguments(command, spec.get().getTokenizer().tokenize(stripedCommand));
        if (spec.get().getElement().isPresent()) return spec.get().getElement().get().parse(sender, arguments, context);
        else
            return new ParsingResult.ImplicitParsingResult<S>(GeneralResult.ResultType.SUCCESS).whilst("parsing empty arguments").in(context);
    }
}
