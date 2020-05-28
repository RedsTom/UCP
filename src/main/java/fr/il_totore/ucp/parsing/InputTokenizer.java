package fr.il_totore.ucp.parsing;

import java.util.List;

public interface InputTokenizer {

    String getCommandName(String text);

    List<SingleParameter> tokenize(String text);

}
