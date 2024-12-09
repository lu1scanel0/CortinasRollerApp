package com.example.eva03morancanelo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuPrincipalActivity extends AppCompatActivity {

    private CardView tarjeta1, tarjeta2, tarjeta3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        // Inicializar las vistas
        tarjeta1 = findViewById(R.id.tarjeta1);
        tarjeta2 = findViewById(R.id.tarjeta2);
        tarjeta3 = findViewById(R.id.tarjeta3);

        // Asignar eventos sin lambdas
        tarjeta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuPrincipalActivity.this, "Catálogo Cortinas", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuPrincipalActivity.this, com.example.eva03morancanelo.ProductosActivity.class));
            }
        });

        tarjeta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuPrincipalActivity.this, "Catálogo Servicios", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuPrincipalActivity.this, com.example.eva03morancanelo.ServiciosActivity.class));
            }
        });

        tarjeta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuPrincipalActivity.this, "Delivery", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuPrincipalActivity.this, com.example.eva03morancanelo.DeliveryActivity.class));
            }
        });

        // Aplicar padding según las ventanas del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new androidx.core.view.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                if (v != null) {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                }
                return insets;
            }
        });
    }
}
