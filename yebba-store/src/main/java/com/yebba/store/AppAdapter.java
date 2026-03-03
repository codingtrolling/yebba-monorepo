package com.yebba.store;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {

    private Context mContext;
    private List<AppModel> mAppList;

    public AppAdapter(Context context, List<AppModel> appList) {
        this.mContext = context;
        this.mAppList = appList;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_app, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        AppModel app = mAppList.get(position);

        // Set Text Data
        holder.appName.setText(app.name);
        holder.appDescription.setText(app.description);

        // Load App Icon using Glide (Placeholder if no icon URL exists)
        Glide.with(mContext)
                .load(app.downloadUrl) // In a real app, you'd have an iconUrl field
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.appIcon);

        // Handle Download Button Click
        holder.btnDownload.setOnClickListener(v -> {
            startDownload(app.name, app.downloadUrl);
        });
    }

    private void startDownload(String appName, String url) {
        Toast.makeText(mContext, "Downloading " + appName + "...", Toast.LENGTH_SHORT).show();

        // Configure the Android DownloadManager
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("YEBBA Store: " + appName);
        request.setDescription("Downloading application package...");
        
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        
        // Save the file as a .apk in the public Downloads folder
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, appName + ".apk");

        // Get service and enqueue the download
        DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        if (manager != null) {
            manager.enqueue(request);
        } else {
            Toast.makeText(mContext, "Error: Download Manager not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    // ViewHolder class to hold UI references
    public static class AppViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appName, appDescription;
        Button btnDownload;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.app_icon);
            appName = itemView.findViewById(R.id.app_name);
            appDescription = itemView.findViewById(R.id.app_description);
            btnDownload = itemView.findViewById(R.id.btn_download);
        }
    }
}
