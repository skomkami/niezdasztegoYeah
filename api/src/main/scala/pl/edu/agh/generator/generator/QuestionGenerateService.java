package pl.edu.agh.generator.generator;

import pl.edu.agh.generator.api.GenerateQuestionsResponseBody;
import pl.edu.agh.generator.api.QuestionDto;
import pl.edu.agh.generator.model.IpnKnowledgeModel;

import java.util.List;
import java.util.Map;

public class QuestionGenerateService {

    private Map<String, List<String>> suggestions;

    public GenerateQuestionsResponseBody generate(IpnKnowledgeModel ipnKnowledgeModel) {
        prepareSuggestions(ipnKnowledgeModel);
        return new GenerateQuestionsResponseBody(
                buildQuestions(ipnKnowledgeModel)
        );
    }

    private void prepareSuggestions(IpnKnowledgeModel ipnKnowledgeModel) {

    }

    private List<QuestionDto> buildQuestions(IpnKnowledgeModel ipnKnowledgeModel) {
        return null;
    }
}
