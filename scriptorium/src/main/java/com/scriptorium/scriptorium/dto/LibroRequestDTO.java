package com.scriptorium.scriptorium.dto;

public class LibroRequestDTO {

    private String titulo;
    private String autor;
    private String isbn;
    private double precio;
    private long generoId;
    private int stock;

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public long getGeneroId() {
        return generoId;
    }

    public void setGeneroId(long generoId) {
        this.generoId = generoId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int strock) {
        this.stock = strock;
    }
}
