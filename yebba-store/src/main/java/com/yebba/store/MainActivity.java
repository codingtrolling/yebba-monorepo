package com.yebba.store;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_APK_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fab_publish).setOnClickListener(v -> {
            // Open File Picker to select an APK
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/vnd.android.package-archive");
            startActivityForResult(Intent.createChooser(intent, "Select APK to Publish"), PICK_APK_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_APK_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri apkUri = data.getData();
            uploadToYebba(apkUri);
        }
    }

    private void uploadToYebba(Uri uri) {
        // Here you would implement your Retrofit/OkHttp logic to 
        // POST the file to your "YEBBA Server"
        Toast.makeText(this, "Uploading to YEBBA Store...", Toast.LENGTH_SHORT).show();
    }
}
