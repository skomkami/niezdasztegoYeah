package pl.edu.agh.service.clarin;

public class ClarinGetStatusFileResponse {
    public String name;
    public String fileID;

    @Override
    public String toString() {
        return "ClarinGetStatusFileResponse{" +
                "name='" + name + '\'' +
                ", fileID='" + fileID + '\'' +
                '}';
    }
}
