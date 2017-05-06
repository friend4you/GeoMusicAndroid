package com.example.vlada.geomusicandroidclient.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.example.vlada.geomusicandroidclient.SelectCategory;
import com.example.vlada.geomusicandroidclient.api.ServiceGenerator;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginFragment extends Fragment {

    private Button loginButton;
    private EditText mEditTextPassword;
    private EditText mEditTextEmail;
    private View mProgressView;
    private View mLoginFormView;
    private TextView register;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginButton = (Button) view.findViewById(R.id.loginButton);

        mEditTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) view.findViewById(R.id.editTextPassword);

        mProgressView = view.findViewById(R.id.login_progress);
        mLoginFormView = view.findViewById(R.id.login_form);

        loginButton.setOnClickListener(v -> {

            showProgress(true);
            ServiceGenerator.getGeomusicService().login(mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            loginResponse -> {
                                if (loginResponse.getResult() == 0) {
                                    Log.d("response", loginResponse.getUser().getEmail());
                                    Application.getSharedInstance().getStorage().setLogin(true);
                                    Application.getSharedInstance().getStorage().saveUserInfo(loginResponse.getUser());
                                    if(loginResponse.getUser().getCategories() == null){
                                        Intent intent = new Intent(getActivity(), SelectCategory.class);
                                        startActivity(intent);
                                    }
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                } else {
                                    showProgress(false);
                                    Toast.makeText(getActivity(), "Failed to login", Toast.LENGTH_SHORT).show();
                                }

                            }, error -> {
                                showProgress(false);
                                Log.d("failure", "failure");
                                Toast.makeText(getActivity(), "Failed to login", Toast.LENGTH_SHORT).show();
                            });
        });
        register = (TextView) view.findViewById(R.id.textViewRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new RegisterFragment());
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
