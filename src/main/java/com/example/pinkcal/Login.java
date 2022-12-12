package com.example.pinkcal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {
	EditText password;
	CheckBox checkbox;
	EditText txtCorUsu, txtPassUsu;
	Button btn_signIn;


	private static final int RC_SIGN_IN = 100;
	private GoogleSignInClient googleSignInClient;

	private FirebaseAuth firebaseAuth;

	private static final String TAG = "GOOGLE_SIGN_IN_TAG";

	@SuppressLint("MissingInflatedId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Configure el inicio de sesión de Google
		GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken("481869298056-mglrvjg8ogkpka9hfarotth5lhasu509.apps.googleusercontent.com").requestEmail()
				.build();

		googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

		// inicia la autenticacion
		firebaseAuth = FirebaseAuth.getInstance();

		// mostrar o ocultar la contraseña
		password = findViewById(R.id.txtPassUsu);
		checkbox = findViewById(R.id.checkbox);

		checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
					password.setSelection(password.getText().length());
				} else {
					password.setTransformationMethod(PasswordTransformationMethod.getInstance());
					password.setSelection(password.getText().length());
				}
			}
		});

	}

	// iniciar sesion
	public void Logup(View view) {
		// TODO move to google SignIn Button
		// Log.d(TAG, "onClick: beging google signin");
		// Intent intent = googleSignInClient.getSignInIntent();
		// startActivityForResult(intent, RC_SIGN_IN);
		Intent registro = new Intent(Login.this, Logup.class);
		startActivity(registro);
	}

	// ir al MainActivity
	public void MainActivity(View view) {
		Intent MainActivity = new Intent(Login.this, MainActivity.class);
		startActivity(MainActivity);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RC_SIGN_IN) {
			Log.d(TAG, "onActivityResult: Google SignIn intent result");
			Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
			try {
				// auth with firebase
				GoogleSignInAccount account = accountTask.getResult(ApiException.class);
				firebaseAuthWithGoogleAccount(account);
				Intent MainActivity = new Intent(Login.this, MainActivity.class);
				startActivity(MainActivity);
			} catch (Exception e) {
				Log.d(TAG, "onActivityResult: " + e.getMessage());
			}
		}
	}

	private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
		Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth with google account");
		AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
		firebaseAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
			@Override
			public void onSuccess(AuthResult authResult) {

				Log.d(TAG, "onSuccess: Logged");

				// iniciar sesion en el
				FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

				// obtener info del usuario
				String uid = firebaseUser.getUid();
				String email = firebaseUser.getEmail();

				Log.d(TAG, "onSuccess: Email " + email);
				Log.d(TAG, "onSuccess: Uid " + uid);

				// comprobar si el usuario es nuevo o existe
				if (authResult.getAdditionalUserInfo().isNewUser()) {
					// user is new
					Log.d(TAG, "onSuccess: Account Created" + email);
					Toast.makeText(Login.this, "Account Created" + email, Toast.LENGTH_SHORT).show();
				} else {
					// usuario existente
					Log.d(TAG, "onSuccess: existing user" + email);
					Toast.makeText(Login.this, "existing user" + email, Toast.LENGTH_SHORT).show();

				}
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				Log.d(TAG, "onFailure: LoggIn failed " + e.getMessage());
			}
		});
	}
	public void userLogin () {
		String txtUsuario = txtCorUsu.getText().toString().trim();
		String txtPassword = txtPassUsu.getText().toString().trim();

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
	}

	public GoogleSignInClient getGoogleSignInClient() {
		return googleSignInClient;
	}

	private class string {
	}
}
