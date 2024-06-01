package com.example.asilapp.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asilapp.R;
import com.example.asilapp.Views.Activity.IntroActivity;

public class LoginChoseUserFragments extends Fragment {

    private static final String TYPE_PATIENT = "Patient";
    private static final String TYPE_DOCTOR = "Doctor";

    private RadioButton buttonChosePatient, buttonChoseDoctor;
    private TextView buttonChoseDefaultUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_choice_user, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        buttonChosePatient     = view.findViewById(R.id.RadioButton_Login_Button_Patient);
        buttonChoseDoctor      = view.findViewById(R.id.RadioButton_Login_Button_Doctor);
        buttonChoseDefaultUser = view.findViewById(R.id.TextView_Default_LogInAs_Default);
    }

    @Override
    public void onStart() {
        super.onStart();

        buttonChosePatient.setOnClickListener(v ->
                ((IntroActivity) requireActivity()).replaceFragment(new LoginFragment(TYPE_PATIENT)));
        buttonChoseDoctor.setOnClickListener(v ->
                ((IntroActivity) requireActivity()).replaceFragment(new LoginFragment(TYPE_DOCTOR)));
        buttonChoseDefaultUser.setOnClickListener(v ->
                ((IntroActivity) requireActivity()).replaceFragment(new LoginDefaultUserFragment()));
    }
}
