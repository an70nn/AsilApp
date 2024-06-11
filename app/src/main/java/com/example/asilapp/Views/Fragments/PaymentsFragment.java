package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asilapp.Controllers.PaymentsRecords;
import com.example.asilapp.Controllers.PaymentsReports;
import com.example.asilapp.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PaymentsFragment extends Fragment {
    private TabLayout paymentsTabLayout;
    private TabItem paymentsRecords, paymentsReports;
    private ViewPager2 paymentsViewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payments, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        paymentsTabLayout = view.findViewById(R.id.TabLayout_Payments);
        paymentsRecords   = view.findViewById(R.id.TabItem_Payments_Records);
        paymentsReports   = view.findViewById(R.id.TabItem_Payments_Reports);
        paymentsViewPager = view.findViewById(R.id.ViewPager_Payments_Fragments);

        // Configura l'adattatore per il ViewPager2
        paymentsViewPager.setAdapter(new PaymentsFragment.PaymentsPagerAdapter(this));
        // Imposta i titoli delle schede per i fragment
        new TabLayoutMediator(paymentsTabLayout, paymentsViewPager,
                (tab, position) -> tab.setText(position == 0 ? "Payments records" : "Payments report")
        ).attach();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private static class PaymentsPagerAdapter extends FragmentStateAdapter{
        public PaymentsPagerAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new PaymentsRecords();
            } else {
                return new PaymentsReports();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
