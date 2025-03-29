package com.example.myhelloapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myhelloapp.R; // Replace with your package name

public class MainActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 100;
    Button btnpicture;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnpicture = findViewById(R.id.btncamera_id);
        imageView = findViewById(R.id.imageview1);

        btnpicture.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, REQUESTCODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUESTCODE) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                imageView.setImageBitmap((android.graphics.Bitmap) bundle.get("data"));
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}