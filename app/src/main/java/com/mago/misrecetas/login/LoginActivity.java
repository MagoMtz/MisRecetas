package com.mago.misrecetas.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.mago.misrecetas.recipemain.ui.view.RecipeMainActivity;
import com.mago.misrecetas.databinding.ActivityLoginBinding;
import com.mago.misrecetas.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private ActivityLoginBinding view;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = DataBindingUtil.setContentView(this, R.layout.activity_login);

        setupFacebook();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(getApplication());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setupFacebook() {
        if (AccessToken.getCurrentAccessToken() != null){
            navigateToMainScreen();
            return;
        }

        callbackManager = CallbackManager.Factory.create();

        //view.btnLogin.setPublishPermissions("publish_actions");
        view.btnLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                navigateToMainScreen();
            }

            @Override
            public void onCancel() {
                Snackbar.make(view.getRoot(), R.string.login_activity_msg_login_cancel, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                String errMsg = String.format(getString(R.string.login_activity_msg_login_error), error.getLocalizedMessage());
                Snackbar.make(view.getRoot(), errMsg, Snackbar.LENGTH_SHORT).show();
                Log.e(TAG, error.getLocalizedMessage());
            }
        });
    }

    private void navigateToMainScreen() {
        startActivity(new Intent(this, RecipeMainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}
