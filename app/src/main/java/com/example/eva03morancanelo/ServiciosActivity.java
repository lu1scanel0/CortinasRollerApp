package com.example.eva03morancanelo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;
import java.util.ArrayList;

public class ServiciosActivity extends AppCompatActivity {

    private ListView listaServicios;
    private ArrayList<Productos> listaServiciosData;
    private ProductoAdapter adapter;

    // Referencia a Firebase Database
    private DatabaseReference serviciosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        listaServicios = findViewById(R.id.listaServicios);
        listaServiciosData = new ArrayList<>();

        // Inicializar Firebase Database
        serviciosRef = FirebaseDatabase.getInstance().getReference("servicios");

        // Configurar el adaptador personalizado
        adapter = new ProductoAdapter(this, listaServiciosData);
        listaServicios.setAdapter(adapter);

        // Cargar servicios desde Firebase
        cargarServicios();

        // Manejar el clic en un elemento de la lista
        listaServicios.setOnItemClickListener((parent, view, position, id) -> {
            Productos servicioSeleccionado = listaServiciosData.get(position);

            // Crear el Intent y pasar los datos del servicio seleccionado
            Intent intent = new Intent(ServiciosActivity.this, DetalleActivity.class);
            intent.putExtra("id", servicioSeleccionado.getId());
            intent.putExtra("nombre", servicioSeleccionado.getNombre());
            intent.putExtra("descripcion", servicioSeleccionado.getDescripcion());
            intent.putExtra("precio", servicioSeleccionado.getPrecio());
            intent.putExtra("imgUrl", servicioSeleccionado.getImgUrl());

            // Iniciar la actividad de detalle
            startActivity(intent);
        });
    }

    private void cargarServicios() {
        serviciosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaServiciosData.clear();
                for (DataSnapshot servicioSnapshot : snapshot.getChildren()) {
                    Productos servicio = servicioSnapshot.getValue(Productos.class);
                    listaServiciosData.add(servicio);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores si es necesario
            }
        });
    }
}
