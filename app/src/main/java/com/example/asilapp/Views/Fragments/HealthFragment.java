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

import com.example.asilapp.Controllers.HealthRecords;
import com.example.asilapp.Controllers.HealthReports;
import com.example.asilapp.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HealthFragment extends Fragment {
    private TabLayout healthTabLayout;
    private TabItem healthParameters, healthReports;
    private ViewPager2 healthViewPager;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        healthTabLayout  = view.findViewById(R.id.TabLayout_Health);
        healthParameters = view.findViewById(R.id.TabItem_Health_Medical_Parameters);
        healthReports    = view.findViewById(R.id.TabItem_Health_Medical_Reports);
        healthViewPager  = view.findViewById(R.id.ViewPager_Health_Fragments);

        // Configura l'adattatore per il ViewPager2
        healthViewPager.setAdapter(new HealthFragment.HealthPagerAdapter(this));
        // Imposta i titoli delle schede per i fragment
        new TabLayoutMediator(healthTabLayout, healthViewPager,
                (tab, position) -> tab.setText(position == 0 ? "Medical records" : "Medical reports")
        ).attach();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private static class HealthPagerAdapter extends FragmentStateAdapter {
        public HealthPagerAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new HealthRecords();
            } else {
                return new HealthReports();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

}