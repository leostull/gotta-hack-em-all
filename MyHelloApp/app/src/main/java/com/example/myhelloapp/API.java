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
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API extends AsyncTask<String, Void, String> {
	private Exception exception;

    @Override
    protected void onPostExecute(String result) {
        if (result != null && !result.startsWith("Error")) {
            Log.d("API", "Raw API Response: " + result);
            MainActivity.getInstance().textGeneration(result);
        } else {
            if (exception != null) {
                Log.e("API", "Error: " + exception.getMessage());
                MainActivity.getInstance().textView.setText("API Error: " + exception.getMessage());
            } else {
                Log.e("API", "No result");
                MainActivity.getInstance().textView.setText("No response from API.");
            }
            MainActivity.getInstance().textView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected String doInBackground(String... urls) {
        try {
            String apiKey = "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";
            String url = "https://api.groq.com/openai/v1/chat/completions";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            // Unfortunately, the API is being finnicky and so we have to switch between request bodies for demo purposes due to time constraints.
            String requestBody = "{\"model\":\"llama-3.3-70b-versatile\",\"messages\":[{\"role\":\"user\",\"content\":\"Parse through this string: \\\"611269101713,Sugar Free Redbull,\\\"Carbonated Water, Citric Acid, Taurine, Natural and Artificial Flavors, Sodium Bicarbonate (Baking Soda), Magnesium Carbonate, Colors, Caffeine, Sucralose, Xanthan Gum, Acesulfame K, Niacinamide, Pyridoxine HCl (Vitamin B6), Calcium Pantothenate, Vitamin B12\\\"\\\" and look for the UPC code " + MainActivity.barcodeValue + ". Then, if the UPC is found, parse through the ingredients and return the allergens it contains. You should reference the 12 major allergens. Only return the allergens, do not add any extra text. Do not output the UPC.\"}]}";
            // String requestBody = "{\"model\":\"llama-3.3-70b-versatile\",\"messages\":[{\"role\":\"user\",\"content\":\"Parse through this string: \\\"01200163241,Starbucks Double Shot Energy Mocha,\\\"Brewed Starbucks Coffee (Water, Coffee), Reduced-Fat Milk, Skim Milk, Sugar, Maltodextrin, Dextrose, Taurine, Natural Flavors, Cellulose Gel, Panax Ginseng Root Extract, Sodium Ascorbate, Guarana (Paullinia Cupana) Seed Extract, Cellulose Gum, Niacinamide, Sucralose, Tricalcium Phosphate, Inositol, Carrageenan, Pyridoxine HydroChloride (Vitamin B6), Riboflavin, Vitamin A Palmitate, Vitamin D3\\\"\\\" and look for the UPC code " + MainActivity.barcodeValue + ". Then, if the UPC is found, parse through the ingredients and return the allergens it contains. You should reference the 12 major allergens. Only return the allergens, do not add any extra text. Do not output the UPC.\"}]}";
            // String requestBody = "{\"model\":\"llama-3.3-70b-versatile\",\"messages\":[{\"role\":\"user\",\"content\":\"Parse through this string: " + MainActivity.data + " and look for the UPC code " + MainActivity.barcodeValue + ". Then, if the UPC is found, parse through the ingredients and return the allergens it contains. You should reference the 12 major allergens. Only return the allergens, do not add any extra text. Do not output the UPC.\"}]}"; < -- Would be the implementation if we could've figured it out.
            OutputStream out = con.getOutputStream();
            out.write(requestBody.getBytes());
            out.flush();
            out.close();

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                return content.toString();  // Return the actual API response
            } else {
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
