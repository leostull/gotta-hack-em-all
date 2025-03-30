// import packages
package com.example.myhelloapp;
import static com.example.myhelloapp.MainActivity.barcodeValue;

// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.io.OutputStream;
// import java.net.HttpURLConnection;
// import java.net.MalformedURLException;
// import java.net.URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API extends AsyncTask<String, Void, String> {
	private Exception exception;

    @Override
    protected void onPostExecute(String result){
        if (result != null && !result.startsWith("Error")){
            Log.d("API", "Raw API Response: " + result);
            MainActivity.getInstance().textGeneration(result);

        } else{
            if (exception != null){
                Log.e("API", "Error: " + exception.getMessage());
            } else{
                Log.e("API", "No result");
            }
        }
    }

    @Override
	protected String doInBackground(String...urls) {
		try {
            // textGeneration("Try reached");
            String apiKey = "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";
            String url = "https://api.groq.com/openai/v1/chat/completions";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            // OutputStream out = con.getOutputStream();
            // out.write(("api_key=" + apiKey).getBytes());

            // System.out.println("Before API run.");


            // Creating request
            String requestBody = "{\"model\":\"llama-3.3-70b-versatile\",\"messages\":[{\"role\":\"user\",\"content\":\"What is the allergy information for barcode " + barcodeValue + "?\"}]}";
            OutputStream out = con.getOutputStream();
            out.write(requestBody.getBytes());
            out.flush();
            out.close();

            MainActivity.getInstance().textGeneration(requestBody);

            // System.out.println("After API run.");

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                System.out.println(content.toString());
                return content.toString();
            } else{
                return "Error: HTTP " + responseCode;
            }
        } catch (Exception e) {
            this.exception = e;
            return "Error: " + e.getMessage();
        }
	}
//     private String baseUrl;
//     private String token;

//     // Constructor
//     public void Api(String baseUrl, String token) {
//         this.baseUrl = baseUrl;
//         this.token = token;
//     }


// //    public String get(String endpoint) throws IOException {
// //        String url = baseUrl + endpoint;
// //        URL obj = new URL(url);
// //        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
// //        con.setRequestMethod(("get"));
// //        con.setRequestProperty("Authroization", "Bearer" + token);
// //    }

//     public static void main(String[] args) throws MalformedURLException {
//         try {
//             String apiKey = "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";
//             String url = "https://api.groq.com/openai/v1/chat/completions";

//             URL obj = new URL(url);
//             HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//             con.setRequestMethod("POST");
//             con.setRequestProperty("Authorization", "Bearer" + apiKey);
//             con.setRequestProperty("Content-Type", "application/json");
//             // con.setDoOutput(true);

//             // OutputStream out = con.getOutputStream();
//             // out.write(("api_key=" + apiKey).getBytes());


//             // Creating request
//             String requestBody = "{\"model\":\"llama-3.3-70b-versatile\",\"messages\":[{\"role\"user\",\"content\":\"What is the allergy information on " + barcodeValue + "?\"}]}";
//             OutputStream out = con.getOutputStream();
//             out.write(requestBody.getBytes());
//             out.flush();
//             out.close();

//             int responseCode = con.getResponseCode();
//             if (responseCode == 200) {
//                 BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                 String inputLine;
//                 StringBuilder content = new StringBuilder();
//                 while ((inputLine = in.readLine()) != null) {
//                     content.append(inputLine);
//                 }
//                 in.close();
//                 System.out.println(content.toString());
//             }
//         } catch (Exception e) {
//             System.out.println("Error:" + e);
//         }
//     }
}
