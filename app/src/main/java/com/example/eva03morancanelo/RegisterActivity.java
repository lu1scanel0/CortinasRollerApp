package com.example.eva03morancanelo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_registro;
    private EditText editTextNombre;
    private EditText editTextMail;
    private EditText editTextPass;
    private FirebaseAuth mAuth; // Para Firebase Authentication

    // Referencia a Firebase Database
    private DatabaseReference usuariosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextMail = findViewById(R.id.editTextMail);
        editTextPass = findViewById(R.id.editTextPass);
        btn_registro = findViewById(R.id.btn_registro);

        mAuth = FirebaseAuth.getInstance(); // Inicializar FirebaseAuth
        usuariosRef = FirebaseDatabase.getInstance().getReference("usuarios"); // Referencia al nodo "usuarios"

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = editTextNombre.getText().toString().trim();
                String email = editTextMail.getText().toString().trim();
                String pass = editTextPass.getText().toString().trim();

                if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(RegisterActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                } else {
                    registrarUsuario(nombre, email, pass);
                }
            }
        });
    }

    private void registrarUsuario(String nombre, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Obtener el ID del usuario recién registrado
                        String userId = mAuth.getCurrentUser().getUid();

                        // Crear un HashMap con los datos adicionales
                        HashMap<String, Object> userData = new HashMap<>();
                        userData.put("nombre", nombre);
                        userData.put("correo", email);

                        // Guardar los datos en Realtime Database bajo el nodo "usuarios/userId"
                        usuariosRef.child(userId).setValue(userData)
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, MenuPrincipalActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Error al guardar datos: " + dbTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        Toast.makeText(RegisterActivity.this, "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
