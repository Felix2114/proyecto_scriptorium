package com.scriptorium.scriptorium.dto;

public class InventarioRequestDTO {

    private int stock;
    private long libroId;

    public InventarioRequestDTO(int stock, long libroId) {
        this.stock = stock;
        this.libroId = libroId;
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
