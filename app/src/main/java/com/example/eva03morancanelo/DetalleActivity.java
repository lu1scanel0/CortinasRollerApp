package com.example.eva03morancanelo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetalleActivity extends AppCompatActivity {

    private TextView tv_nombre, tv_total;
    private LinearLayout carritoContainer;
    private Button btn_plus, btn_minus, btn_finalizar;
    private ImageView imagenProducto;
    private Productos productoActual;
    private int cantidad = 0;

    private final Carrito carrito = Carrito.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        // Inicialización de las vistas
        tv_nombre = findViewById(R.id.tv_nombre);
        carritoContainer = findViewById(R.id.carritoContainer);
        tv_total = findViewById(R.id.tv_total);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_finalizar = findViewById(R.id.btn_finalizar);
        imagenProducto = findViewById(R.id.imagen);

        // Obtener datos del Intent
        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String descripcion = intent.getStringExtra("descripcion");
        int precio = intent.getIntExtra("precio", 0);
        String imgUrl = intent.getStringExtra("imgUrl");
        String id = intent.getStringExtra("id");

        // Crear el producto actual
        productoActual = new Productos(id, nombre, descripcion, precio, imgUrl);
        tv_nombre.setText(nombre);

        // Cargar la imagen usando Glide
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error);

        Glide.with(this)
                .load(imgUrl)
                .apply(requestOptions)
                .into(imagenProducto);

        // Configurar los listeners de los botones
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cantidad++;
                carrito.agregarProducto(productoActual);
                actualizarCarrito();
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cantidad > 0) {
                    cantidad--;
                    carrito.eliminarProducto(productoActual);
                    actualizarCarrito();
                }
            }
        });

        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (carrito.estaVacio()) {
                    Toast.makeText(DetalleActivity.this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
                } else {
                    // Iniciar la actividad de Delivery
                    Intent intent = new Intent(DetalleActivity.this, DeliveryActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        actualizarCarrito(); // Actualizar el carrito al iniciar
    }

    // Método para actualizar el contenido del carrito
    private void actualizarCarrito() {
        carritoContainer.removeAllViews(); // Limpiar la vista

        // Agregar los productos al contenedor del carrito
        for (Productos producto : carrito.getProductos()) {
            TextView detalle = new TextView(this);
            detalle.setText("1 - " + producto.getNombre() + " - $" + producto.getPrecio());
            detalle.setTextSize(18);
            carritoContainer.addView(detalle);
        }

        // Actualizar el total
        tv_total.setText("Total: $" + carrito.getTotal());

        // Mostrar u ocultar el botón "Finalizar compra"
        if (carrito.estaVacio()) {
            btn_finalizar.setVisibility(View.GONE);
        } else {
            btn_finalizar.setVisibility(View.VISIBLE);
        }
    }
}
