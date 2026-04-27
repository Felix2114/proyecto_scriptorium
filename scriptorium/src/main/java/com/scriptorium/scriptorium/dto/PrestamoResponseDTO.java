package com.scriptorium.scriptorium.dto;

import java.time.LocalDate;

public class PrestamoResponseDTO {

    private Long idPrestamo;
    private String ficha;
    private Long usuarioId;
    private Long libroId;
    private Long bibliotecarioId;
    private boolean activo;
    private boolean multado;
    private boolean devuelto;
    private String estadoPrestamo;
    private String estadoDevuelto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public PrestamoResponseDTO(Long idPrestamo, String ficha, Long usuarioId, Long libroId, Long bibliotecarioId,
                               boolean activo, boolean multado, boolean devuelto,String estadoPrestamo, String estadoDevuelto,
                               LocalDate fechaInicio, LocalDate fechaFin) {
        this.idPrestamo = idPrestamo;
        this.ficha = ficha;
        this.usuarioId = usuarioId;
        this.libroId = libroId;
        this.bibliotecarioId = bibliotecarioId;
        this.activo = activo;
        this.multado = multado;
        this.devuelto = devuelto;
        this.estadoPrestamo = estadoPrestamo;
        this.estadoDevuelto = estadoDevuelto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y Setters
    

    public Long getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Long idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getLibroId() {
        return libroId;
    }

    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }

    public Long getBibliotecarioId() {
        return bibliotecarioId;
    }

    public void setBibliotecarioId(Long bibliotecarioId) {
        this.bibliotecarioId = bibliotecarioId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isMultado() {
        return multado;
    }

    public void setMultado(boolean multado) {
        this.multado = multado;
    }

    public String getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(String estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }

    public String getEstadoDevuelto() {
        return estadoDevuelto;
    }

    public void setEstadoDevuelto(String estadoDevuelto) {
        this.estadoDevuelto = estadoDevuelto;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isDevuelto() {
        return devuelto;
    }
    
    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

}
