package pl.edu.agh.service.clarin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import pl.edu.agh.model.IpnSourceModel;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ClarinService {

    private static final String START_TASK_URI = "startTask";
    private static final String GET_STATUS_URI = "getStatus/";
    private static final String RESULT_URI = "download";

    private static final String user = "demo";
    private static final String NLPREST_URL = "https://ws.clarin-pl.eu/nlprest2/base/";
    public static final String LPMN = "any2txt|wcrft2|liner2({\"model\":\"top9\"})";
    public static final String APPLICATION = "ws.clarin-pl.eu";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void calculateTokensForSource(IpnSourceModel ipnSourceModel) {
        try {
            String taskId = startClarinTask(ipnSourceModel.sourceContent);
            System.out.println(taskId);
            Thread.sleep(1000);
            String response = getTaskStatus(taskId);
            System.out.println(response);
            ClarinGetStatusResponse responseObject = objectMapper.readValue(response, ClarinGetStatusResponse.class);
            System.out.println(responseObject);
            String resultResponse = downloadResult(responseObject.value.get(0).fileID);
            ipnSourceModel.tokens = new ClarinResultXmlParser().parse(resultResponse);
        } catch (URISyntaxException | IOException | InterruptedException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private String startClarinTask(String data) throws URISyntaxException, IOException, InterruptedException {
        return sendRequest("POST", START_TASK_URI,
                new ClarinStartTaskRequest(LPMN, data, APPLICATION, "demo"));
    }

    private String getTaskStatus(String taskId) throws URISyntaxException,
            IOException, InterruptedException {
        return sendRequest("GET", GET_STATUS_URI + taskId, null);
    }

    private String downloadResult(String fileID) throws URISyntaxException, IOException, InterruptedException {
        return sendRequest("GET", RESULT_URI + fileID, null);
    }

    private String sendRequest(String method, String uri, Object requestObject) throws
            URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(NLPREST_URL + uri))
                .method(method, buildRequestBody(requestObject))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private HttpRequest.BodyPublisher buildRequestBody(Object requestObject) throws JsonProcessingException {
        return HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestObject));
    }


    public static String nlpFileUpload(String fileName) throws IOException {
        return ClientBuilder.newClient().target(NLPREST_URL + "upload").request().
                post(Entity.entity(new File(fileName), MediaType.APPLICATION_OCTET_STREAM)).
                readEntity(String.class);
    }

    public static void nlpFileDownload(String id, String fileName) throws IOException {
        URL url = new URL(NLPREST_URL + "download" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            Files.copy(is, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
        } else {
            throw new IOException("Error downloading file");
        }
    }

    private static String getRes(Response res) throws IOException {
        if (res.getStatus() != 200) {
            throw new IOException("Error in nlprest processing");
        }
        return res.readEntity(String.class);
    }

    public static String nlpProcess(String lpmn, String id) throws IOException, InterruptedException {
        JSONObject request = new JSONObject();
        Client client = ClientBuilder.newClient();
        request.put("file", id);
        request.put("lpmn", lpmn);
        request.put("user", user);
        String taskid = client.target(NLPREST_URL + "startTask").request().
                post(Entity.entity(request.toString(), MediaType.APPLICATION_JSON)).readEntity(String.class);

        String status = "";
        JSONObject jsonres = new JSONObject();
        while (!status.equals("DONE")) {
            String res = getRes(client.target(NLPREST_URL + "getStatus/" + taskid).request().get());

            jsonres = new JSONObject(res);

            status = jsonres.getString("status");
            if (status.equals("ERROR")) {
                throw new IOException("Error in processing");
            }
            if (status.equals("PROCESSING")) {
                System.out.println(String.format("%.2f", jsonres.getDouble("value") * 100) + "%");
            }
            if (status.equals("DONE")) {
                System.out.println("100%");
            }
            Thread.sleep(500);
        }
        return jsonres.getJSONArray("value").getJSONObject(0).getString("fileID");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String id = nlpFileUpload("demo.txt");
        String lpmn = "any2txt|wcrft2|liner2({\"model\":\"top9\"})|makezip";
        id = nlpProcess(lpmn, id);
        nlpFileDownload(id, "result.zip");
        System.out.println("Done");
    }

}
