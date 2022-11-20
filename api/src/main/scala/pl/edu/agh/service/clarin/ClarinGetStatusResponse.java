package pl.edu.agh.service.clarin;

import java.util.List;

public class ClarinGetStatusResponse {
    public String status;
    public List<ClarinGetStatusFileResponse> value;

    @Override
    public String toString() {
        return "ClarinGetStatusResponse{" +
                "status='" + status + '\'' +
                ", value=" + value +
                '}';
    }
}
