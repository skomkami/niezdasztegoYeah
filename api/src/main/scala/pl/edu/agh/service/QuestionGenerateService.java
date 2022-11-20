package pl.edu.agh.service;

import pl.edu.agh.common.AppLogger;
import pl.edu.agh.model.IpnKnowledgeModel;
import pl.edu.agh.model.IpnSourceModel;
import pl.edu.agh.model.TokenModel;
import pl.edu.agh.server.output.Question;
import scala.jdk.javaapi.CollectionConverters;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionGenerateService {

    private static final String BLANK_PLACEHOLDER = "_____";
    private static final int MAX_TEXT_RANGE = 100;
    private static final String DOTS = "[...]";
    private static final String SPACE = " ";

    public Question buildSingleQuestion(IpnKnowledgeModel ipnKnowledgeModel, List<TokenModel> allTokens) {
        if (ipnKnowledgeModel.sourceList.isEmpty()) {
            AppLogger.warn("No more knowledge downloaded. Skipping further generation...");
            return null;
        }
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

        List<String> possibleAnswers = Stream.of(List.of(randomToken), similarTokens)
                .flatMap(Collection::stream)
                .map(token -> token.text)
                .collect(Collectors.toList());

        scala.collection.immutable.List<String> resultAnswers = CollectionConverters.asScala(trimAndRandomizeAnswers(possibleAnswers, randomToken.text)).toList();

        if (resultAnswers.size() < 2) {
            AppLogger.warn("Only single answer for resource. Not creating question.");
            return null;
        }


        String blankedFullQuestion = randomSourceModel.sourceContent.replace(randomToken.text, BLANK_PLACEHOLDER);
        String trimmedQuestion = trimQuestion(blankedFullQuestion);
        String trimmedQuestionWithDots;

        if (trimmedQuestion.length() < blankedFullQuestion.length()) {
            trimmedQuestionWithDots = DOTS + SPACE + trimmedQuestion + SPACE + DOTS;
        } else {
            trimmedQuestionWithDots = trimmedQuestion;
        }

        String linkTextFragment = randomSourceModel.sourceContent;
        ipnKnowledgeModel.sourceList.remove(randomSourceModel);
        return new Question(
                trimmedQuestionWithDots,
                resultAnswers,
                resultAnswers.indexOf(randomToken.text),
                randomSourceModel.sourceContent,
                randomSourceModel.sourceUrl + "#:~:text=" + URLEncoder.encode(linkTextFragment, StandardCharsets.UTF_8).replace("+","%20")
        );
    }

    private String trimQuestion(String blankedFullQuestion) {
        int startIndex = Math.max(blankedFullQuestion.indexOf(BLANK_PLACEHOLDER) - MAX_TEXT_RANGE, 0);
        int endIndex = Math.min(blankedFullQuestion.lastIndexOf(BLANK_PLACEHOLDER) + MAX_TEXT_RANGE, blankedFullQuestion.length());
        if (startIndex < endIndex) {
            return blankedFullQuestion.substring(startIndex, endIndex);
        }
        return blankedFullQuestion;
    }

    private static List<String> trimAndRandomizeAnswers(List<String> answers, String correctAnswer) {
        Set<String> uniqueUnorderedAnswers = new HashSet<>(answers);
        List<String> result = new ArrayList<>(uniqueUnorderedAnswers);
        Collections.shuffle(result);
        result = result.subList(0, Math.min(4, uniqueUnorderedAnswers.size()));
        if (!result.contains(correctAnswer)) {
            result.set(0, correctAnswer);
            Collections.shuffle(result);
        }
        return result;
    }

}
