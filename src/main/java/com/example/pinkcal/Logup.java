package com.example.pinkcal;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class Logup extends AppCompatActivity {
    EditText password;
    EditText confirmpassword;
    CheckBox checkbox;
    Button btnsignIn;
    EditText txtCorUsu, txtPassUsu, txtComUsu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);

            String txtUsuario = txtCorUsu.getText().toString().trim();
            String txtPassword = txtPassUsu.getText().toString().trim();
            String txtComfirm= txtComUsu.getText().toString().trim();

            if (txtUsuario.isEmpty()){
                txtCorUsu.setError("Ingrese un correo electronico");
                txtCorUsu.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(txtUsuario).matches()) {
                txtCorUsu.setError("Ingrese un correo Valido");
                txtCorUsu.requestFocus();
                return;
            }
            if (txtPassword.isEmpty()){
                txtPassUsu.setError("Ingrese una contraseña");
                txtPassUsu.requestFocus();
                return;
            }
            if (txtPassword.length() < 8) {
                txtPassUsu.setError("Contraseña Minimo 8 Caracteres");
                txtPassUsu.requestFocus();
                return;
            }
            if (txtComfirm.isEmpty()) {
                txtComUsu.setError("Confirme Contraseña");
                txtComUsu.requestFocus();
                return;
            }
        //mostrar y ocultar contraseña
        password = findViewById(R.id.txtPassUsu);
        confirmpassword = findViewById(R.id.confirmpassword);
        checkbox = findViewById(R.id.checkbox);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password.setSelection(password.getText().length());
                    confirmpassword.setSelection(password.getText().length());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setSelection(password.getText().length());
                    confirmpassword.setSelection(password.getText().length());
                }
            }
        } );
    }

        // ir al MainActivity
        public void MainActivity(View view) {
        Intent MainActivity = new Intent(Logup.this, MainActivity.class);
        startActivity(MainActivity);
        }
}