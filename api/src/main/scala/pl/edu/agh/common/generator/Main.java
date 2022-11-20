package pl.edu.agh.common.generator;

import pl.edu.agh.service.QuestionGeneratorFacade;

// TODO replace with rest invocation
public class Main {

    public static void main(String[] args) {
        GenerateQuestionsResponseBody result = new QuestionGeneratorFacade()
                .generate(new GenerateQuestionsRequestParams(
                        args[0], 20
                ));
        System.out.println(result);
    }
}




