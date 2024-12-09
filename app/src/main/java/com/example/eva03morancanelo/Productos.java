package com.example.eva03morancanelo;

public class Productos {
    // Atributos
    private String id;
    private String nombre;
    private String descripcion;
    private int precio;
    private String imgUrl;

    // Constructor vacío requerido para Firebase
    public Productos() {
    }

    // Constructor completo
    public Productos(String id, String nombre, String descripcion, int precio, String imgUrl) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imgUrl = imgUrl;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
