package pl.edu.agh.generator.api;

import java.util.List;

public class GenerateQuestionsResponseBody {
    public List<QuestionDto> questions;

    public GenerateQuestionsResponseBody(List<QuestionDto> questions) {
        this.questions = questions;
    }
}
