package pl.edu.agh.common.generator.generator;

import pl.edu.agh.common.generator.model.IpnKnowledgeModel;
import pl.edu.agh.common.generator.model.IpnSourceModel;
import pl.edu.agh.common.generator.model.TokenModel;
import pl.edu.agh.server.dto.QuestionDto;
import pl.edu.agh.server.output.Question;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionGenerateService {

    private static final String BLANK_PLACEHOLDER = "_____";

    public Question buildSingleQuestion(IpnKnowledgeModel ipnKnowledgeModel, List<TokenModel> allTokens) {
        IpnSourceModel randomSourceModel = ipnKnowledgeModel.sourceList.get(new Random()
                .nextInt(ipnKnowledgeModel.sourceList.size()));

        TokenModel randomToken = randomSourceModel.tokens.get(new Random().nextInt(randomSourceModel.tokens.size()));
        List<TokenModel> similarTokens = allTokens.stream()
                .filter(token -> token.tokenType.equals(randomToken.tokenType))
                .collect(Collectors.toList());

        return new Question(
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
