package pl.edu.agh.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class IpnSourceModel {
    public String sourceUrl;
    public String sourceContent;
    public List<TokenModel> tokens;

    public IpnSourceModel(String sourceUrl, String sourceContent) {
        this.sourceUrl = sourceUrl;
        this.sourceContent = sourceContent;
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
