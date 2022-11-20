package pl.edu.agh.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenModel {
    public static final String TYPE_YEAR = "YEAR";

    public String text;
    public String tokenType;

    public TokenModel(String text, String tokenType) {
        this.text = text;
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
