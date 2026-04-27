package com.scriptorium.scriptorium.dto;

import java.time.LocalDate;

public class MultaResponseDTO {
    private long multaId;
    private float monto;
    private LocalDate fechaMulta;
    private long prestamoId;
    private long tipoMultaId;
    
    public MultaResponseDTO(long multaId, float monto, LocalDate fechaMulta, long prestamoId, long tipoMultaId) {
        this.multaId = multaId;
        this.monto = monto;
        this.fechaMulta = fechaMulta;
        this.prestamoId = prestamoId;
        this.tipoMultaId = tipoMultaId;
    }
    
    public float getMonto() {
        return monto;
    }
    public void setMonto(float monto) {
        this.monto = monto;
    }
    public LocalDate getFechaMulta() {
        return fechaMulta;
    }
    public void setFechaMulta(LocalDate fechaMulta) {
        this.fechaMulta = fechaMulta;
    }
    public long getPrestamoId() {
        return prestamoId;
    }
    public void setPrestamoId(long prestamoId) {
        this.prestamoId = prestamoId;
    }
    public long getTipoMultaId() {
        return tipoMultaId;
    }
    public void setTipoMultaId(long tipoMultaId) {
        this.tipoMultaId = tipoMultaId;
    }
    public long getMultaId() {
        return multaId;
    }
    public void setMultaId(long multaId) {
        this.multaId = multaId;
    }

    
}
