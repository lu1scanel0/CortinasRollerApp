package com.example.eva03morancanelo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;
import java.util.ArrayList;

public class ProductosActivity extends AppCompatActivity {

    private ListView listaCortinas;
    private ArrayList<Productos> listaProductos;
    private ProductoAdapter adapter;

    // Referencia a Firebase Database
    private DatabaseReference productosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        listaCortinas = findViewById(R.id.listaCortinas);
        listaProductos = new ArrayList<>();

        // Inicializar Firebase Database
        productosRef = FirebaseDatabase.getInstance().getReference("productos");

        // Configurar el adaptador personalizado
        adapter = new ProductoAdapter(this, listaProductos);
        listaCortinas.setAdapter(adapter);

        // Cargar productos desde Firebase
        cargarProductos();

        // Manejar el clic en un elemento de la lista
        listaCortinas.setOnItemClickListener((parent, view, position, id) -> {
            Productos productoSeleccionado = listaProductos.get(position);

            // Crear el Intent y pasar los datos del producto seleccionado
            Intent intent = new Intent(ProductosActivity.this, DetalleActivity.class);
            intent.putExtra("id", productoSeleccionado.getId());
            intent.putExtra("nombre", productoSeleccionado.getNombre());
            intent.putExtra("descripcion", productoSeleccionado.getDescripcion());
            intent.putExtra("precio", productoSeleccionado.getPrecio());
            intent.putExtra("imgUrl", productoSeleccionado.getImgUrl());

            // Iniciar la actividad de detalle
            startActivity(intent);
        });
    }

    private void cargarProductos() {
        productosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.clear();
                for (DataSnapshot productoSnapshot : snapshot.getChildren()) {
                    Productos producto = productoSnapshot.getValue(Productos.class);
                    listaProductos.add(producto);
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
