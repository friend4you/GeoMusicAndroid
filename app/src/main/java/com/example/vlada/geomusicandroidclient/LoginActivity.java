package com.example.vlada.geomusicandroidclient;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vlada.geomusicandroidclient.api.ServiceGenerator;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.model.VKApiUser;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vlada on 20.01.2017.
 */

public class LoginActivity extends Activity {

    final String ACCESS_TOKEN = "access_token";
    Button loginButton;
    SharedPreferences sPref;
    private EditText mEditTextPassword;
    private EditText mEditTextEmail;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.loginButton);

        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);

        mProgressView = findViewById(R.id.login_progress);
        mLoginFormView = findViewById(R.id.login_form);
        sPref = getSharedPreferences("settings",MODE_PRIVATE);



        loginButton.setOnClickListener(v -> {

            showProgress(true);
            ServiceGenerator.getGeomusicService().login(mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            loginResponse -> {
                                Log.d("response", loginResponse.getEmail());
                                SharedPreferences.Editor ed = sPref.edit();
                                ed.putBoolean("is_authorized", true);
                                ed.putString("email", mEditTextEmail.getText().toString());
                                ed.putString("password", mEditTextPassword.getText().toString());
                                ed.apply();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                                Settings.setAuthorized(true);
                            }, error -> {
                                showProgress(false);
                                Log.d("failure", "failure");
                                Toast.makeText(this, "Failed to login", Toast.LENGTH_SHORT).show();

                            });
        });

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void vkLogin() {
        VKSdk.login(this, VKScope.AUDIO, VKScope.OFFLINE, VKScope.FRIENDS);
        VKAccessToken.currentToken().saveTokenToSharedPreferences(this, ACCESS_TOKEN);

    }

    public void checkLogin() {

        ServiceGenerator.getGeomusicService().login(mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loginResponse -> {
                            Log.d("response", loginResponse.getEmail());

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            Settings.setEmail(mEditTextEmail.getText().toString());
                            Settings.setPassword(mEditTextPassword.getText().toString());
                            Settings.setAuthorized(true);
                        }, error -> {
                            showProgress(false);
                            Log.d("failure", "failure");
                            Toast.makeText(this, "Failed to login", Toast.LENGTH_SHORT);
                        });


        /*if (VKAccessToken.currentToken().accessToken == "") {
            vkLogin();
        } else {
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("user_id", res.userId);
                ed.apply();
                Log.d("user_id", res.userId);
            }

            @Override
            public void onError(VKError error) {

            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
