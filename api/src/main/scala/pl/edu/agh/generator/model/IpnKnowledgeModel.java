package pl.edu.agh.generator.model;

import java.util.List;

public class IpnKnowledgeModel {
    public List<IpnSourceModel> sourceList;

    public IpnKnowledgeModel(List<IpnSourceModel> sourceList) {
        this.sourceList = sourceList;
    }
}
