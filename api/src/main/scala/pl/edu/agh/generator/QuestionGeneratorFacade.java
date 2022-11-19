package pl.edu.agh.generator;

import pl.edu.agh.generator.api.GenerateQuestionsRequestParams;
import pl.edu.agh.generator.api.GenerateQuestionsResponseBody;
import pl.edu.agh.generator.generator.QuestionGenerateService;
import pl.edu.agh.generator.ipn.IpnSearchService;
import pl.edu.agh.generator.morfeusz.MorfeuszResponseDto;
import pl.edu.agh.generator.morfeusz.MorfeuszService;

import java.util.List;

public class QuestionGeneratorFacade {

    public GenerateQuestionsResponseBody generate(
            GenerateQuestionsRequestParams generateQuestionsRequestParams) {
        List<String> searchSentences = new IpnSearchService().searchSentences(generateQuestionsRequestParams.searchText);
        MorfeuszResponseDto morfeuszResponseDto = new MorfeuszService().doMagic(searchSentences);
        GenerateQuestionsResponseBody questionsResponseBody = new QuestionGenerateService().generate(morfeuszResponseDto);
        return questionsResponseBody;
    }
}
