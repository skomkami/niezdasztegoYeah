package pl.edu.agh.model;

public class GenerateQuestionsRequestParams {
    public String searchText;
    public Integer size;

    public GenerateQuestionsRequestParams(String searchText, Integer size) {
        this.searchText = searchText;
        this.size = size;
    }
}
