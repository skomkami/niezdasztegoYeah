package pl.edu.agh.generator;

import pl.edu.agh.generator.api.GenerateQuestionsRequestParams;
import pl.edu.agh.generator.api.GenerateQuestionsResponseBody;
import pl.edu.agh.generator.generator.QuestionGenerateService;
import pl.edu.agh.generator.ipn.IpnSearchService;
import pl.edu.agh.generator.model.IpnKnowledgeModel;
import pl.edu.agh.generator.tokenizer.TokenizerService;

import java.util.Collection;
import java.util.stream.Collectors;

public class QuestionGeneratorFacade {

    public GenerateQuestionsResponseBody generate(
            GenerateQuestionsRequestParams generateQuestionsRequestParams) {
        IpnKnowledgeModel ipnKnowledgeModel = new IpnSearchService().searchSentences(generateQuestionsRequestParams.searchText);
        new TokenizerService()
                .getTokensForSentences(ipnKnowledgeModel.sourceList.stream()
                        .map(source -> source.sentences)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
                );
        return new QuestionGenerateService().generate(ipnKnowledgeModel);
    }
}
