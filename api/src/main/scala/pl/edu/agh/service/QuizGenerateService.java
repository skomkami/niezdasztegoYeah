package pl.edu.agh.service;

import pl.edu.agh.model.IpnKnowledgeModel;
import pl.edu.agh.model.TokenModel;
import pl.edu.agh.server.output.GeneratedQuiz;
import pl.edu.agh.server.output.Question;
import scala.jdk.javaapi.CollectionConverters;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuizGenerateService {

    private final QuestionGenerateService questionGenerateService = new QuestionGenerateService();
    private List<TokenModel> allTokens;

    public GeneratedQuiz generate(IpnKnowledgeModel ipnKnowledgeModel, int quantity) {
        extractAllTokens(ipnKnowledgeModel);
        scala.collection.immutable.List<Question> questionList = CollectionConverters.asScala(buildQuestions(ipnKnowledgeModel, quantity)).toList();
        return new GeneratedQuiz(
                questionList
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
                .filter(el -> !Objects.isNull(el))
                .collect(Collectors.toList());
    }
}
