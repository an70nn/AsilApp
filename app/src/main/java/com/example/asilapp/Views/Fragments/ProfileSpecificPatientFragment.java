package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asilapp.Controllers.ProfileAnagrafic;
import com.example.asilapp.Controllers.ProfileMeasurement;
import com.example.asilapp.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileSpecificPatientFragment extends Fragment {
    private TextView specificPatientFullName, specificPatientAge;
    private ImageView specificPatientPic;
    private TabLayout specificPatientTabLayout;
    private TabItem specificPatientAnagrafic, specificPatientMeasurement;
    private ViewPager2 specificPatientViewPager;
    private Bundle profileAnagraficArgs;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listpatients_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        specificPatientFullName    = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Fullname);
        specificPatientAge         = view.findViewById(R.id.TextView_Doctor_ListPatient_Profile_Age);
        specificPatientPic         = view.findViewById(R.id.ImageView_Doctor_ListPatient_Profile_PicUser);
        specificPatientTabLayout   = view.findViewById(R.id.TabLayout_Doctor_ProfileSpecificPatient);
        specificPatientAnagrafic   = view.findViewById(R.id.TabItem_Doctor_ProfileSpecificPatient_Anagrafic);
        specificPatientMeasurement = view.findViewById(R.id.TabItem_Doctor_ProfileSpecificPatient_Measurement);
        specificPatientViewPager   = view.findViewById(R.id.ViewPager_Doctor_ListPatients_Profile);

        //Passaggio dei valori dell'Item del paziente (dal ListPatientsFragment) al Profilo specifico del paziente
        Bundle args = getArguments();
        if (args != null) {
            String fullName = args.getString("fullName");
            String age = args.getString("age");
            specificPatientFullName.setText(fullName);
            specificPatientAge.setText(age);

            // Passa i dati al fragment ProfileAnagraficFragment
            profileAnagraficArgs = new Bundle();
            //Anziché fare String name = args.getString("phone");
            // E poi profileAnagraficArgs.putString("name", name);
            profileAnagraficArgs.putString("name",       args.getString("name"));
            profileAnagraficArgs.putString("surname",    args.getString("surname"));
            profileAnagraficArgs.putString("gender",     args.getString("gender"));
            profileAnagraficArgs.putString("birthPlace", args.getString("birthPlace"));
            profileAnagraficArgs.putString("birthDate",  args.getString("birthDate"));
            profileAnagraficArgs.putString("country",    args.getString("country"));
            profileAnagraficArgs.putString("phone",      args.getString("phone"));
            profileAnagraficArgs.putString("centerId",   args.getString("centerId"));
            profileAnagraficArgs.putString("email",      args.getString("email"));
            profileAnagraficArgs.putString("password",   args.getString("password"));



        }

        // Configura l'adattatore per il ViewPager2
        specificPatientViewPager.setAdapter(new ProfileSpecificPatientFragment.ProfilePagerAdapter(this, profileAnagraficArgs));
        // Imposta i titoli delle schede per i fragment
        new TabLayoutMediator(specificPatientTabLayout, specificPatientViewPager,
                (tab, position) -> tab.setText(position == 0 ? "Anagrafica" : "Misurazioni")
        ).attach();
    }

    private static class ProfilePagerAdapter extends FragmentStateAdapter {
        private final Bundle profileArgs;
        public ProfilePagerAdapter(Fragment fragment, Bundle profileArgs) {
            super(fragment);
            this.profileArgs = profileArgs;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                ProfileAnagrafic profileAnagraficFragment = new ProfileAnagrafic();
                profileAnagraficFragment.setArguments(profileArgs);
                return profileAnagraficFragment;
            } else {
                return new ProfileMeasurement();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
