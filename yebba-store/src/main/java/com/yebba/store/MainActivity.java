package com.yebba.store;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_APK_REQUEST = 101;
    private RecyclerView recyclerView;
    private AppAdapter adapter;
    private List<AppModel> appList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase and UI
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.app_list);
        appList = new ArrayList<>();
        
        setupRecyclerView();
        loadAppsFromYebbaCloud();

        // The "Publish" Button
        FloatingActionButton fab = findViewById(R.id.fab_publish);
        fab.setOnClickListener(v -> openFilePicker());
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppAdapter(this, appList);
        recyclerView.setAdapter(adapter);
    }

    private void loadAppsFromYebbaCloud() {
        db.collection("apps").addSnapshotListener((value, error) -> {
            if (error != null) return;
            appList.clear();
            for (QueryDocumentSnapshot doc : value) {
                AppModel app = doc.toObject(AppModel.class);
                appList.add(app);
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/vnd.android.package-archive");
        startActivityForResult(Intent.createChooser(intent, "Select APK to Publish"), PICK_APK_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_APK_REQUEST && resultCode == RESULT_OK && data != null) {
            publishApkToStore(data.getData());
        }
    }

    private void publishApkToStore(Uri fileUri) {
        String fileName = "yebba_" + System.currentTimeMillis() + ".apk";
        StorageReference ref = FirebaseStorage.getInstance().getReference().child("apks/" + fileName);

        Toast.makeText(this, "Publishing to YEBBA Store...", Toast.LENGTH_SHORT).show();

        ref.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {
            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                saveAppMetadata(fileName, uri.toString());
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void saveAppMetadata(String name, String downloadUrl) {
        AppModel newApp = new AppModel(name, "Community App", downloadUrl);
        db.collection("apps").add(newApp)
                .addOnSuccessListener(documentReference -> 
                    Toast.makeText(MainActivity.this, "App Live on YEBBA!", Toast.LENGTH_LONG).show());
    }
}
