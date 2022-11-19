package pl.edu.agh.generator;

import pl.edu.agh.generator.api.GenerateQuestionsRequestParams;
import pl.edu.agh.generator.api.GenerateQuestionsResponseBody;
import pl.edu.agh.generator.generator.QuizGenerateService;
import pl.edu.agh.generator.ipn.IpnSearchService;
import pl.edu.agh.generator.model.IpnKnowledgeModel;
import pl.edu.agh.generator.model.IpnSourceModel;
import pl.edu.agh.generator.tokenizer.TokenizerService;

import java.util.List;

public class QuestionGeneratorFacade {

    public static final int FETCH_SOURCES_COUNT = 10;

    private final TokenizerService tokenizerService = new TokenizerService();

    public GenerateQuestionsResponseBody generate(GenerateQuestionsRequestParams generateQuestionsRequestParams) {
        List<IpnSourceModel> sources = new IpnSearchService()
                .getSources(generateQuestionsRequestParams.searchText, FETCH_SOURCES_COUNT);
        sources.forEach(tokenizerService::calculateTokensForSource);

        return new QuizGenerateService().generate(new IpnKnowledgeModel(sources), generateQuestionsRequestParams.size);
    }
}
