package pl.edu.agh.service.custom;

import pl.edu.agh.model.IpnSourceModel;
import pl.edu.agh.model.TokenModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YearTokenService {

    private static final Pattern PATTERN = Pattern.compile(".*(\\d\\d\\d\\d)\\s?r.*");

    public void calculateTokensForSource(IpnSourceModel sourceModel) {
        Matcher matcher = PATTERN.matcher(sourceModel.sourceContent);
        // TODO implement more than one
        if (matcher.matches() && matcher.groupCount() > 0) {
            sourceModel.tokens.add(new TokenModel(matcher.group(), TokenModel.TYPE_YEAR));
        }
    }

}
