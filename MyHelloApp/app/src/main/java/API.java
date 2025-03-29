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
    public void Api(String baseUrl, String token) {
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
        String url = "https://nam04.safelinks.protection.outlook.com/?url=https%3A%2F%2Fwww.kaggle.com%2Fdatasets%2Fuom190346a%2Ffood-ingredients-and-allergens&data=05%7C02%7Camoss1%40mines.edu%7Cd455bb083f5046eaf63f08dd6ea48e99%7C997209e009b346239a4d76afa44a675c%7C0%7C0%7C638788375230259900%7CUnknown%7CTWFpbGZsb3d8eyJFbXB0eU1hcGkiOnRydWUsIlYiOiIwLjAuMDAwMCIsIlAiOiJXaW4zMiIsIkFOIjoiTWFpbCIsIldUIjoyfQ%3D%3D%7C0%7C%7C%7C&sdata=qeZlohm47VohXbw0QKj1JlpHyHTT60EVVZWzse6slCI%3D&reserved=0"

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
