package com.example.asilapp.Views.Adapters;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Models.VideoItem;
import com.example.asilapp.R;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {
    private List<VideoItem> videoList;
    private WebView videoWebView;

    public VideoListAdapter(List<VideoItem> videoList, WebView videoWebView) {
        this.videoList = videoList;
        this.videoWebView = videoWebView;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoItem videoItem = videoList.get(position);
        holder.title.setText(videoItem.getTitle());
        holder.itemView.setOnClickListener(v -> {
            // Carica l'URL del video nella WebView
            videoWebView.loadUrl(videoItem.getVideoUrl());
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.videoTitle);
        }
    }
}
