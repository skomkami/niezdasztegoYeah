package pl.edu.agh.generator.generator;

import pl.edu.agh.generator.api.QuestionDto;
import pl.edu.agh.generator.model.IpnKnowledgeModel;
import pl.edu.agh.generator.model.IpnSourceModel;
import pl.edu.agh.generator.model.TokenModel;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionGenerateService {

    private static final String BLANK_PLACEHOLDER = "_____";

    public QuestionDto buildSingleQuestion(IpnKnowledgeModel ipnKnowledgeModel, List<TokenModel> allTokens) {
        IpnSourceModel randomSourceModel = ipnKnowledgeModel.sourceList.get(new Random()
                .nextInt(ipnKnowledgeModel.sourceList.size()));

        TokenModel randomToken = randomSourceModel.tokens.get(new Random().nextInt(randomSourceModel.tokens.size()));
        List<TokenModel> similarTokens = allTokens.stream()
                .filter(token -> token.tokenType.equals(randomToken.tokenType))
                .collect(Collectors.toList());

        return new QuestionDto(
                randomSourceModel.sourceContent.replace(randomToken.text, BLANK_PLACEHOLDER),
                Stream.of(List.of(randomToken), similarTokens)
                        .flatMap(Collection::stream)
                        .map(token -> token.text)
                        .collect(Collectors.toList()),
                0,
                randomSourceModel.sourceContent,
                randomSourceModel.sourceUrl
        );
    }

}
