package com.example.vlada.geomusicandroidclient.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlada.geomusicandroidclient.Application;
import com.example.vlada.geomusicandroidclient.MainActivity;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.api.ServiceGenerator;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterFragment extends Fragment {

    private Button registerButton;
    private EditText mEditTextPassword;
    private EditText mEditTextEmail;
    private EditText mEditTextConfirm;
    private EditText mEditTextUserName;
    private View mProgressView;
    private View mRegisterFormView;
    private TextView register;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerButton = (Button) view.findViewById(R.id.registerButton);

        mEditTextEmail = (EditText) view.findViewById(R.id.editTextRegisterEmail);
        mEditTextPassword = (EditText) view.findViewById(R.id.editTextRegisterPassword);
        mEditTextConfirm = (EditText) view.findViewById(R.id.editTextRegisterConfirmPassword);
        mEditTextUserName = (EditText) view.findViewById(R.id.editTextRegisterUserName);

        mProgressView = view.findViewById(R.id.login_progress);
        mRegisterFormView = view.findViewById(R.id.register_form);

        registerButton.setOnClickListener(v -> {
            showProgress(true);
            ServiceGenerator.getGeomusicService().registration(mEditTextUserName.getText().toString(), mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString(), mEditTextConfirm.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            registrationResponse -> {
                                if (registrationResponse.getResult().getSucceeded()) {
                                    Application.getSharedInstance().getStorage().setLogin(true);
                                    Application.getSharedInstance().getStorage().saveUserInfo(registrationResponse.getUser());
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                } else {
                                    showProgress(false);
                                    Toast.makeText(getActivity(), "Failed to register", Toast.LENGTH_SHORT).show();
                                }

                            }, error -> {
                                showProgress(false);
                                Log.d("failure", "failure");
                                Toast.makeText(getActivity(), "Failed to register", Toast.LENGTH_SHORT).show();
                            });
        });


    }

    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
