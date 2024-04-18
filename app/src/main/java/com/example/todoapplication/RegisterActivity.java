package com.example.todoapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword, editTextEmail;
    private Button saveButton ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        saveButton = findViewById(R.id.buttonRegister);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        RadioGroup radioGroupGender = findViewById(R.id.radioGroupGender);
        int selectedRadioButtonId = radioGroupGender.getCheckedRadioButtonId();
        int gender;
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Geçerli bir e-posta adresi girin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedRadioButtonId == R.id.radioButtonMale) {
            gender = 1;
        } else if (selectedRadioButtonId == R.id.radioButtonFemale) {
            gender = 2;
        } else {
            Toast.makeText(this, "Cinsiyet seçiniz", Toast.LENGTH_SHORT).show();
            return;
        }

        CheckBox checkBoxTerms = findViewById(R.id.checkBoxTerms);
        boolean termsAccepted = checkBoxTerms.isChecked();

        if (!termsAccepted) {
            Toast.makeText(this, "Lütfen koşulları kabul edin", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("email", email);
        values.put("cender", gender);
        values.put("isRead", termsAccepted ? 1 : 0);

        long newRowId = db.insert("users", null, values);

        if (newRowId != -1) {
            showSuccessDialog();
        } else {
            Toast.makeText(this, "Kullanıcı kaydedilirken bir hata oluştu", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Kullanıcı başarıyla kaydedildi")
                .setCancelable(false)
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
