package pl.edu.agh.generator.tokenizer;

import pl.edu.agh.generator.model.IpnSourceModel;

import java.util.List;

public class TokenizerService {

    private final YearTokenService yearTokenService = new YearTokenService();

    public void calculateTokensForSource(IpnSourceModel source) {
        calculateTokensWithPythonService(source);
        calculateTokensWithYearTokenService(source);
    }

    private void calculateTokensWithPythonService(IpnSourceModel source) {
        // TODO to implement
        // call REST api
    }

    private void calculateTokensWithYearTokenService(IpnSourceModel source) {
        yearTokenService.tokenize(source);
    }
}
