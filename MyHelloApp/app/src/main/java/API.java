// import packages
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class API {
    private String baseUrl;
    private String token;

    // Constructor
    public Api(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
    }

    // get requests
    public String get(String endpoint) throws IOException {
        String url = baseUrl + endpoint;
        URL obj = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(("get"))
        con.setRequestProperty("Authroization", "Bearer" + token);
    }

    public static void main(String[] args) {
        String apiKey = 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;
        String url = // need to figure out what this is

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET"); // check if this needs to be "POST"
        con.setDoOutput(true);

        OutputStream out = con.getOutputStream();
        out.write(("api_key=" + apiKey).getBytes());
        out.flush();
        out.close();

        int responseCode = con.getResponseCode();
        if (resoponseCode == 200) {
            BufferedReader in = new BufferedReader(new InuputStreamReaders(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        }
    }
}
