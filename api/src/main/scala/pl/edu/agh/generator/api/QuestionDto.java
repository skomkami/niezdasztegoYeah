package pl.edu.agh.generator.api;

import java.util.List;

public class QuestionDto {
    public String question;
    public List<String> answers;
    public Integer correctAnswerIndex;
    public String sourceQuote;
    public String sourceUrl;

    public QuestionDto(String question, List<String> answers, Integer correctAnswerIndex, String sourceQuote, String sourceUrl) {
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
        this.sourceQuote = sourceQuote;
        this.sourceUrl = sourceUrl;
    }
}