package pl.edu.agh.generator.generator;

import pl.edu.agh.generator.api.GenerateQuestionsResponseBody;
import pl.edu.agh.generator.api.QuestionDto;
import pl.edu.agh.generator.model.IpnKnowledgeModel;
import pl.edu.agh.generator.model.TokenModel;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuizGenerateService {

    private final QuestionGenerateService questionGenerateService = new QuestionGenerateService();
    private List<TokenModel> allTokens;

    public GenerateQuestionsResponseBody generate(IpnKnowledgeModel ipnKnowledgeModel, int quantity) {
        extractAllTokens(ipnKnowledgeModel);
        return new GenerateQuestionsResponseBody(
                buildQuestions(ipnKnowledgeModel, quantity)
        );
    }

    private void extractAllTokens(IpnKnowledgeModel ipnKnowledgeModel) {
        allTokens = ipnKnowledgeModel.sourceList.stream()
                .map(source -> source.tokens)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<QuestionDto> buildQuestions(IpnKnowledgeModel ipnKnowledgeModel, int quantity) {
        return IntStream.range(0, quantity)
                .mapToObj(el -> questionGenerateService
                        .buildSingleQuestion(ipnKnowledgeModel, allTokens))
                .collect(Collectors.toList());
    }
}
