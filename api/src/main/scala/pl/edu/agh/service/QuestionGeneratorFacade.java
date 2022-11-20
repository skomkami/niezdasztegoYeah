package pl.edu.agh.service;

import pl.edu.agh.model.IpnKnowledgeModel;
import pl.edu.agh.model.IpnSourceModel;
import pl.edu.agh.generator.api.GenerateQuestionsRequestParams;
import pl.edu.agh.server.output.GeneratedQuiz;
import pl.edu.agh.service.clarin.ClarinService;
import pl.edu.agh.service.custom.YearTokenService;

import java.util.List;

public class QuestionGeneratorFacade {

    public static final int FETCH_SOURCES_COUNT = 5;

    private final IpnService ipnService;
    private final ClarinService clarinService;
    private final YearTokenService yearTokenService = new YearTokenService();

    public QuestionGeneratorFacade(IpnService ipnService, ClarinService clarinService) {
        this.ipnService = ipnService;
        this.clarinService = clarinService;
    }

    public GeneratedQuiz generate(GenerateQuestionsRequestParams generateQuestionsRequestParams) {
        List<IpnSourceModel> sources = ipnService
                .getResultsSync(generateQuestionsRequestParams.searchText, FETCH_SOURCES_COUNT);
        sources.forEach(clarinService::calculateTokensForSource);
//        sources.forEach(yearTokenService::calculateTokensForSource);
        System.out.println(sources);
        return new QuizGenerateService().generate(new IpnKnowledgeModel(sources), generateQuestionsRequestParams.size);
    }
}
