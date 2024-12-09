package com.example.eva03morancanelo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String DATA_LOADED_KEY = "data_loaded";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegistro = findViewById(R.id.btn_registro);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Verificar si los datos ya han sido cargados
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean dataLoaded = settings.getBoolean(DATA_LOADED_KEY, false);

        if (!dataLoaded) {
            cargarDatosIniciales();

            // Marcar que los datos han sido cargados
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(DATA_LOADED_KEY, true);
            editor.apply();
        }
    }

    private void cargarDatosIniciales() {
        // Referencias a Firebase Database
        DatabaseReference productosRef = FirebaseDatabase.getInstance().getReference("productos");
        DatabaseReference serviciosRef = FirebaseDatabase.getInstance().getReference("servicios");

        // Crear los productos
        Productos[] productosArray = {
                new Productos("producto1", "Cortina Duo", "Descripción de Cortina Duo", 49990, "https://i.ibb.co/MgYC2BQ/duo.jpg"),
                new Productos("producto2", "Cortina SunsScreen", "Descripción de Cortina SunsScreen",69990, "https://i.ibb.co/hmbwdC7/sunscreen.jpg"),
                new Productos("producto3", "Cortina BlackOut", "Descripción de Cortina BlackOut", 64990, "https://i.ibb.co/Wg1ZDT1/blackout.jpg")
        };

        // Agregar los productos a Firebase
        for (Productos producto : productosArray) {
            productosRef.child(producto.getId()).setValue(producto);
        }

        // Crear los servicios
        Productos[] serviciosArray = {
                new Productos("servicio1", "Instalación", "Servicio de instalación profesional", 39990, "https://i.ibb.co/VQG3Qzv/reparacion.jpg"),
                new Productos("servicio2", "Reparación", "Servicio de reparación para productos dañados", 79990, "https://i.ibb.co/VQG3Qzv/reparacion.jpg"),
                new Productos("servicio3", "Mantenimiento", "Mantenimiento preventivo y correctivo", 60880, "https://i.ibb.co/VQG3Qzv/reparacion.jpg")
        };

        // Agregar los servicios a Firebase
        for (Productos servicio : serviciosArray) {
            serviciosRef.child(servicio.getId()).setValue(servicio);
        }
    }
}
