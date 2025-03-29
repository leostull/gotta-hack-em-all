package com.example.myhelloapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

public class MainActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 100;
    private static final String TAG = "BarcodeScanner";
    Button btnpicture;
    ImageView imageView;
    TextView initialTextView;

    TextView textView;

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
        if (requestCode == REQUESTCODE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
                scanBarcode(imageBitmap);
            } else {
                Toast.makeText(this, "No image data", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUESTCODE) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
    private void scanBarcode(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).build();
        BarcodeScanner scanner = BarcodeScanning.getClient(options);
        scanner.process(image).addOnSuccessListener(barcodes -> {
            if (!barcodes.isEmpty()) {
                for (Barcode barcode : barcodes){
                    String barcodeValue = barcode.getRawValue();
                    textView.setText(barcodeValue);
                    textView.setVisibility(View.VISIBLE);
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
}

