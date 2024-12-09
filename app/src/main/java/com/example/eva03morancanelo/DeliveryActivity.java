package com.example.eva03morancanelo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class DeliveryActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextCorreo, editTextDireccion, editTextTelefono;
    private TextView textViewMonto;
    private Button btnConfirmarDespacho;

    private final Carrito carrito = Carrito.getInstance();  // Singleton del carrito

    // Referencias a Firebase
    private DatabaseReference pedidosRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        // Inicializar las vistas
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        textViewMonto = findViewById(R.id.textViewMonto);
        btnConfirmarDespacho = findViewById(R.id.btnConfirmarDespacho);

        // Inicializar Firebase
        pedidosRef = FirebaseDatabase.getInstance().getReference("pedidos");
        mAuth = FirebaseAuth.getInstance();

        // Mostrar el monto total desde el carrito
        int montoTotal = carrito.getTotal();
        textViewMonto.setText(String.format("$%d", montoTotal));

        // Configurar el botón de confirmación con validación
        btnConfirmarDespacho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = editTextNombre.getText().toString().trim();
                String correo = editTextCorreo.getText().toString().trim();
                String direccion = editTextDireccion.getText().toString().trim();
                String telefono = editTextTelefono.getText().toString().trim();

                if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(correo) || TextUtils.isEmpty(direccion) || TextUtils.isEmpty(telefono)) {
                    Toast.makeText(DeliveryActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    confirmarDespacho(nombre, correo, direccion, telefono);
                }
            }
        });
    }

    // Confirmar el despacho y registrar el pedido en Firebase
    private void confirmarDespacho(String nombre, String correo, String direccion, String telefono) {
        String userId = mAuth.getCurrentUser().getUid();
        String pedidoId = pedidosRef.push().getKey();

        HashMap<String, Object> pedidoData = new HashMap<>();
        pedidoData.put("userId", userId);
        pedidoData.put("nombre", nombre);
        pedidoData.put("correo", correo);
        pedidoData.put("direccion", direccion);
        pedidoData.put("telefono", telefono);
        pedidoData.put("montoTotal", carrito.getTotal());
        pedidoData.put("productos", carrito.getProductos());

        pedidosRef.child(pedidoId).setValue(pedidoData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Despacho confirmado. Gracias por su compra.", Toast.LENGTH_LONG).show();

                // Reiniciar el carrito
                carrito.vaciarCarrito();

                // Regresar al MenuPrincipalActivity
                Intent intent = new Intent(DeliveryActivity.this, MenuPrincipalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                // Finalizar esta actividad para evitar que el usuario regrese con "back"
                finish();
            } else {
                Toast.makeText(this, "Error al confirmar el despacho: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
