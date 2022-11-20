package pl.edu.agh.common.generator.model;

public class TokenModel {
    public static final String TYPE_YEAR = "YEAR";

    public String text;
    public String tokenType;

    public TokenModel(String text, String tokenType) {
        this.text = text;
        this.tokenType = tokenType;
    }
}
