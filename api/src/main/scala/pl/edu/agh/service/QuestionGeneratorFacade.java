package pl.edu.agh.service;

import pl.edu.agh.common.generator.generator.QuizGenerateService;
import pl.edu.agh.common.generator.model.IpnKnowledgeModel;
import pl.edu.agh.common.generator.model.IpnSourceModel;
import pl.edu.agh.generator.api.GenerateQuestionsRequestParams;
import pl.edu.agh.server.output.GeneratedQuiz;

import java.util.List;

public class QuestionGeneratorFacade {

    public static final int FETCH_SOURCES_COUNT = 10;

    private final IpnService ipnService;
    private final ClarinService clarinService;

    public QuestionGeneratorFacade(IpnService ipnService, ClarinService clarinService) {
        this.ipnService = ipnService;
        this.clarinService = clarinService;
    }

    public GeneratedQuiz generate(GenerateQuestionsRequestParams generateQuestionsRequestParams) {
        List<IpnSourceModel> sources = ipnService
                .getResultsSync(generateQuestionsRequestParams.searchText, FETCH_SOURCES_COUNT);
        sources.forEach(tokenizerService::calculateTokensForSource);

        return new QuizGenerateService().generate(new IpnKnowledgeModel(sources), generateQuestionsRequestParams.size);
    }
}
