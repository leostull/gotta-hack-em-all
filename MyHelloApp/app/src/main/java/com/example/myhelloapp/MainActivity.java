package com.example.myhelloapp;

import static com.google.android.gms.common.internal.service.Common.API;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String barcodeValue;
    private static final int REQUESTCODE = 100;
    private static final String TAG = "BarcodeScanner";
    Button btnpicture;
    ImageView imageView;
    TextView initialTextView;

    TextView textView;
    
    // Variables for API
    private String baseUrl;
    private String token;
    
    // Sets displays for product information
    private void textGeneration(String displayMessage) {
    	textView.setText(displayMessage);
    	textView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnpicture = findViewById(R.id.btncamera_id);
        imageView = findViewById(R.id.imageview1);
        initialTextView = findViewById(R.id.start_text);
        textView = findViewById(R.id.barcode_text);

        btnpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUESTCODE);
            }
        });
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1); // 1 is a request code
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap imageBitmap;
        if (requestCode == REQUESTCODE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    // (Optional) Attempt to get a full-sized image if available
                    if (data.getData() != null) { // Check if there's a Uri in the Intent
                        try {
                            imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        } catch (IOException e) {
                            Log.e(TAG, "Error getting full-size image", e);
                            // Fallback to the thumbnail if there's an error
                        }
                    }
                    imageView.setImageBitmap(imageBitmap);
                    scanBarcode(imageBitmap);
                } else {
                    Toast.makeText(this, "No image data", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUESTCODE) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void scanBarcode(Bitmap imageBitmap){
        InputImage image = InputImage.fromBitmap(imageBitmap, 0);

        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).build();
        BarcodeScanner scanner = BarcodeScanning.getClient(options);
        scanner.process(image).addOnSuccessListener(barcodes -> {
            if (!barcodes.isEmpty()) {
                for (Barcode barcode : barcodes) {
                    barcodeValue = barcode.getRawValue();
                    textGeneration(barcodeValue);
                    try {
                        API();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    Log.d(TAG, "Barcode value: " + barcodeValue);
                }
            } else {
                textView.setText("No barcode found");
                textView.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(e -> {
            textView.setText("Error scanning barcode");
            textView.setVisibility(View.VISIBLE);
            Log.e(TAG, "Error scanning barcode", e);
        });
    }
    
    // main function originally from API
    public void API() throws MalformedURLException {
        try {
            textGeneration("Try reached");
            String apiKey = "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";
            String url = "https://api.groq.com/openai/v1/chat/completions";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer" + apiKey);
            con.setRequestProperty("Content-Type", "application/json");
            // con.setDoOutput(true);

            // OutputStream out = con.getOutputStream();
            // out.write(("api_key=" + apiKey).getBytes());

            System.out.println("Before API run.");


            // Creating request
            String requestBody = "{\"model\":\"llama-3.3-70b-versatile\",\"messages\":[{\"role\"user\",\"content\":\"What is the allergy information on " + barcodeValue + "?\"}]}";
            OutputStream out = con.getOutputStream();
            textGeneration("Output stream reached");
            out.write(requestBody.getBytes());
            textGeneration("Write reached");
            out.flush();
            out.close();

            textGeneration(requestBody);

            System.out.println("After API run.");

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
            }
        } catch (Exception e) {
            textGeneration("Error:" + e);
            System.out.println("Error:" + e);
        }
    }
}
