package pl.edu.agh.service;

import pl.edu.agh.common.AppLogger;
import pl.edu.agh.model.IpnKnowledgeModel;
import pl.edu.agh.model.IpnSourceModel;
import pl.edu.agh.model.GenerateQuestionsRequestParams;
import pl.edu.agh.server.output.GeneratedQuiz;
import pl.edu.agh.service.clarin.ClarinService;
import pl.edu.agh.service.custom.YearTokenService;

import java.util.ArrayList;
import java.util.List;

public class QuestionGeneratorFacade {

    public static final int FETCH_SOURCES_BATCH_COUNT = 5;

    private final IpnService ipnService;
    private final ClarinService clarinService;
    private final YearTokenService yearTokenService = new YearTokenService();

    public QuestionGeneratorFacade(IpnService ipnService, ClarinService clarinService) {
        this.ipnService = ipnService;
        this.clarinService = clarinService;
    }

    public GeneratedQuiz generate(GenerateQuestionsRequestParams generateQuestionsRequestParams) {
        Long startTime = System.currentTimeMillis();
        List<IpnSourceModel> sources = ipnService
                .getResultsSync(generateQuestionsRequestParams.searchText, FETCH_SOURCES_BATCH_COUNT);
        AppLogger.info("Loaded " + sources.size() + " ipn sources");
        sources.parallelStream()
                .forEach(clarinService::calculateTokensForSource);
//        sources.forEach(yearTokenService::calculateTokensForSource);
        AppLogger.info("Calculated tokens");
        System.out.println(sources);
        GeneratedQuiz generatedQuiz = new QuizGenerateService().generate(new IpnKnowledgeModel(new ArrayList<>(sources)), generateQuestionsRequestParams.size);
        Long endTime = System.currentTimeMillis();
        AppLogger.info("Generated " + generatedQuiz.questions().size() + " quiz questions within " +
                (endTime - startTime) + " miliseconds");
        return generatedQuiz;
    }
}
