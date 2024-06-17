package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asilapp.Models.VideoItem;
import com.example.asilapp.R;
import com.example.asilapp.Views.Adapters.VideoListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MediaFragment extends Fragment {
    private RecyclerView videosRecyclerView;
    private VideoListAdapter videoListAdapter;
    private List<VideoItem> videoList;
    private WebView videoWebView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mymedia, container, false);
        videoWebView = view.findViewById(R.id.webView_video);
        videoWebView.getSettings().setJavaScriptEnabled(true);
        videoWebView.setWebViewClient(new WebViewClient());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videosRecyclerView = view.findViewById(R.id.recyclerView_videos);
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoList = getVideoList(); // Metodo per ottenere la lista dei video
        videoListAdapter = new VideoListAdapter(videoList, videoWebView);
        videosRecyclerView.setAdapter(videoListAdapter);
    }

    private List<VideoItem> getVideoList() {
        // Qui dovresti aggiungere i tuoi video alla lista
        List<VideoItem> list = new ArrayList<>();
        list.add(new VideoItem("Hand-washing", "https://www.youtube.com/embed/IisgnbMfKvI"));
        list.add(new VideoItem("First aid", "https://www.youtube.com/embed/2ynlaWUwMsA"));
        // Aggiungi altri video...
        return list;
    }
}
