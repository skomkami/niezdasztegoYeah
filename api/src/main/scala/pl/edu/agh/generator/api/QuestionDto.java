package pl.edu.agh.generator.api;

import java.util.List;

public class QuestionDto {
    public String question;
    public List<String> answers;
    public Integer correctAnswerIndex;
    public String sourceQuote;
    public String sourceUrl;
}