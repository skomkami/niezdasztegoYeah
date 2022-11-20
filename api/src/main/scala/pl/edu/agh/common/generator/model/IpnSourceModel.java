package pl.edu.agh.common.generator.model;

import java.util.List;

public class IpnSourceModel {
    public String sourceUrl;
    public String sourceContent;
    public List<TokenModel> tokens;

    public IpnSourceModel(String sourceUrl, String sourceContent) {
        this.sourceUrl = sourceUrl;
        this.sourceContent = sourceContent;
    }
}
