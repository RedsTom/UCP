package fr.il_totore.ucp.parsing;

import java.util.ArrayList;
import java.util.List;

public class SimpleSplitTokenizer implements InputTokenizer {

    private final String separator;

    public SimpleSplitTokenizer(String separator) {
        this.separator = separator;
    }

    @Override
    public String getCommandName(String text) {
        return text.split(separator)[0];
    }

    @Override
    public List<SingleParameter> tokenize(String text) {

        final String[] splitString = text.split(separator);
        final List<SingleParameter> paramList = new ArrayList<>();
        int index = 0;
        if (splitString.length == 1) return paramList;
        for (int i = 1; i < splitString.length; i++) {
            paramList.add(new SingleParameter(splitString[i], index));
            index += splitString[i].length();
        }
        return paramList;
    }
}
