package com.swayam.touchid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.swayam.touchid.Activity.HomeActivity;
import com.swayam.touchid.Helper.BiometricHelperClass;
import com.swayam.touchid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Binding View
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.tvStartAuthentication.setOnClickListener(this);

        displayBiometricButton();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStartAuthentication:
                onAuthBtnClick();
                break;
        }
    }

    private void onAuthBtnClick() {
        // With CryptoObject
        getBiometricPromptHandler().authenticate(getBiometricPrompt(), new BiometricPrompt.CryptoObject(BiometricHelperClass.getCipher()));
        //Without CryptoObject (If you want Authentication without CryptoObject use below code otherwise above code).
//        getBiometricPromptHandler().authenticate(getBiometricPrompt());
    }


    private void displayBiometricButton() {
        if (!isBiometricCompatibleDevice()) {
            binding.tvStartAuthentication.setEnabled(false);
        } else {
            binding.tvStartAuthentication.setEnabled(true);
            BiometricHelperClass.generateSecretKey();
        }
    }

    // Check Device Biometric Supported or Not
    private boolean isBiometricCompatibleDevice() {
        if (getBiometricManager().canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            return true;
        } else {
            return false;
        }
    }

    private BiometricManager getBiometricManager() {
        return BiometricManager.from(this);
    }

    //BiometricPrompt Dialog
    private BiometricPrompt.PromptInfo getBiometricPrompt() {
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.biometricprompt_dailog_title))
                .setSubtitle(getString(R.string.biometricprompt_dailog_subtitle))
                .setNegativeButtonText(getString(R.string.biometricprompt_dailog_cancel))
                .setConfirmationRequired(false)
                .build();
    }

    //Authentication Success
    private void onBiometricSuccess() {
        Intent home = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(home);
        finish();
    }

    //BiometricPrompt Events
    private BiometricPrompt getBiometricPromptHandler() {
        return new BiometricPrompt(this, ContextCompat.getMainExecutor(this),
                new BiometricPrompt.AuthenticationCallback() {

                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(MainActivity.this, getString(R.string.authentication_success), Toast.LENGTH_SHORT).show();
                        onBiometricSuccess();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(MainActivity.this, getString(R.string.authentication_cancel), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}