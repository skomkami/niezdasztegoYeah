package pl.edu.agh.service.clarin;

public class ClarinStartTaskRequest {
    public String lpmn;
    public String text;
    public String application;
    public String user;

    public ClarinStartTaskRequest(String lpmn, String text, String application, String user) {
        this.lpmn = lpmn;
        this.text = text;
        this.application = application;
        this.user = user;
    }
}
