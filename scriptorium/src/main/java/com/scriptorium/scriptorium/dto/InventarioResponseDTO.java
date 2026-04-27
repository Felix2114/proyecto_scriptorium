package com.scriptorium.scriptorium.dto;

public class InventarioResponseDTO {
    private long idInventario;
    private int stock;
    private long libroId;
    
    public InventarioResponseDTO(long idInventario, int stock, long libroId) {
        this.idInventario = idInventario;
        this.stock = stock;
        this.libroId = libroId;
    }

    public long getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(long idInventario) {
        this.idInventario = idInventario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public long getLibroId() {
        return libroId;
    }

    public void setLibroId(long libroId) {
        this.libroId = libroId;
    }

    
}
