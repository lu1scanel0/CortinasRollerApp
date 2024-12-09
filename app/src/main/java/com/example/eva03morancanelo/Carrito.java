package com.example.eva03morancanelo;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private static Carrito instancia;
    private final List<Productos> productos;

    // Constructor privado para el patrón Singleton
    private Carrito() {
        productos = new ArrayList<>();
    }

    // Obtener la instancia única del carrito
    public static Carrito getInstance() {
        if (instancia == null) {
            instancia = new Carrito();
        }
        return instancia;
    }

    // Agregar producto al carrito
    public void agregarProducto(Productos producto) {
        productos.add(producto);
    }

    // Eliminar producto del carrito
    public void eliminarProducto(Productos producto) {
        productos.remove(producto);
    }

    // Obtener la lista de productos
    public List<Productos> getProductos() {
        return productos;
    }

    // Verificar si el carrito está vacío
    public boolean estaVacio() {
        return productos.isEmpty();
    }

    // Obtener el total del carrito
    public int getTotal() {
        int total = 0;
        for (Productos producto : productos) {
            total += producto.getPrecio();
        }
        return total;
    }

    public void vaciarCarrito() {
        productos.clear();  // Limpia la lista de productos del carrito
    }
}
