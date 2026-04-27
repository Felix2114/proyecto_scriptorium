package com.scriptorium.scriptorium.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;


@Entity
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idInventario;
    private int stock;
    
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro; 
    
    public long getIdInventario(){
        return idInventario;
    }

    public void setIdIventario(long idInventario){
        this.idInventario = idInventario;
    }

    public int getStock(){
        return stock;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public Libro getLibro(){
        return libro;
    }

    public void setLibro(Libro libro){
        this.libro = libro;
    }
}
