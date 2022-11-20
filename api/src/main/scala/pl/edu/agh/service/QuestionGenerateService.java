package pl.edu.agh.service;

import pl.edu.agh.common.AppLogger;
import pl.edu.agh.model.IpnKnowledgeModel;
import pl.edu.agh.model.IpnSourceModel;
import pl.edu.agh.model.TokenModel;
import pl.edu.agh.server.output.Question;
import scala.jdk.javaapi.CollectionConverters;

import java.util.ArrayList;
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
        if (randomSourceModel.tokens.size() < 1) {
            AppLogger.warn("Not generating question for source. Tokens not found. Resource: " + randomSourceModel.sourceContent);
            return null;
        }
        TokenModel randomToken = randomSourceModel.tokens.get(new Random().nextInt(randomSourceModel.tokens.size()));
        List<TokenModel> similarTokens = allTokens.stream()
                .filter(token -> token.tokenType.equals(randomToken.tokenType))
                .collect(Collectors.toList());

        List<String> answers = Stream.of(List.of(randomToken), similarTokens)
                .flatMap(Collection::stream)
                .map(token -> token.text)
                .collect(Collectors.toList());
        if (answers.size() < 2) {
            AppLogger.warn("Only single answer for resource. Not creating question.");
            return null;
        }
        return new Question(
                randomSourceModel.sourceContent.replace(randomToken.text, BLANK_PLACEHOLDER),
                CollectionConverters.asScala(answers.subList(0, Math.min(4,answers.size()))).toList(),
                0,
                randomSourceModel.sourceContent,
                randomSourceModel.sourceUrl
        );
    }

}
