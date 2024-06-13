package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asilapp.Controllers.MediaDocuments;
import com.example.asilapp.Controllers.MediaVideos;
import com.example.asilapp.Controllers.PaymentsRecords;
import com.example.asilapp.Controllers.PaymentsReports;
import com.example.asilapp.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MediaFragment extends Fragment {
    private TabLayout mediaTabLayout;
    private TabItem mediaVideo, mediaDocuments;
    private ViewPager2 mediaViewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mymedia, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mediaTabLayout = view.findViewById(R.id.TabLayout_Patient_Mymedia);
        mediaVideo     = view.findViewById(R.id.TabItem_Patient_Mymedia_Videos);
        mediaDocuments = view.findViewById(R.id.TabItem_Patient_Mymedia_Documents);
        mediaViewPager = view.findViewById(R.id.ViewPager_Patient_Mymedia);

        // Configura l'adattatore per il ViewPager2
        mediaViewPager.setAdapter(new MediaPageAdapter(this));
        // Imposta i titoli delle schede per i fragment
        new TabLayoutMediator(mediaTabLayout, mediaViewPager, (tab, position) -> {
            if(position == 0){
                tab.setText(getString(R.string.video_illustrativi));
            }else{
                tab.setText(getString(R.string.raccolta_documenti));
            }
        }
        ).attach();
    }

    private static class MediaPageAdapter extends FragmentStateAdapter {
        public MediaPageAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new MediaVideos();
            } else {
                return new MediaDocuments();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
