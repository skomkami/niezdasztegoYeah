package pl.edu.agh.common.generator.generator;

import pl.edu.agh.common.generator.model.IpnKnowledgeModel;
import pl.edu.agh.server.dto.QuestionDto;
import pl.edu.agh.common.generator.model.TokenModel;
import pl.edu.agh.server.output.GeneratedQuiz;
import pl.edu.agh.server.output.Question;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuizGenerateService {

    private final QuestionGenerateService questionGenerateService = new QuestionGenerateService();
    private List<TokenModel> allTokens;

    public GeneratedQuiz generate(IpnKnowledgeModel ipnKnowledgeModel, int quantity) {
        extractAllTokens(ipnKnowledgeModel);
        return new GeneratedQuiz(
                buildQuestions(ipnKnowledgeModel, quantity)
        );
    }

    private void extractAllTokens(IpnKnowledgeModel ipnKnowledgeModel) {
        allTokens = ipnKnowledgeModel.sourceList.stream()
                .map(source -> source.tokens)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Question> buildQuestions(IpnKnowledgeModel ipnKnowledgeModel, int quantity) {
        return IntStream.range(0, quantity)
                .mapToObj(el -> questionGenerateService
                        .buildSingleQuestion(ipnKnowledgeModel, allTokens))
                .collect(Collectors.toList());
    }
}
