package com.example.movietitledisplay.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movietitledisplay.R;
import com.google.firebase.auth.FirebaseAuth;

// This screen lets existing users log in with email and password
public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton, goToRegisterButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Link UI inputs and buttons
        emailInput = findViewById(R.id.editEmail);
        passwordInput = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.btnLogin);
        goToRegisterButton = findViewById(R.id.btnGoToRegister);

        // If login button is clicked
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                // Try to sign in using Firebase
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Logged in successfully, go to main screen
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish(); // prevent going back
                            } else {
                                Toast.makeText(this, "Login failed. Check credentials.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        // Go to Register screen if user taps that button
        goToRegisterButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}
