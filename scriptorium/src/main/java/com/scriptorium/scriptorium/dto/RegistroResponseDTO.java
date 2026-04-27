package com.scriptorium.scriptorium.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroResponseDTO {
    
    private long idRegistro;
    private LocalDate diaRegistro;
    private LocalTime horaRegistro;

    private long bibliotecarioId;

    

    public RegistroResponseDTO(long idRegistro, LocalDate diaRegistro, LocalTime horaRegistro, long bibliotecarioId) {
        this.idRegistro = idRegistro;
        this.diaRegistro = diaRegistro;
        this.horaRegistro = horaRegistro;
        this.bibliotecarioId = bibliotecarioId;
    }

    public LocalDate getDiaRegistro() {
        return diaRegistro;
    }

    public void setDiaRegistro(LocalDate diaRegistro) {
        this.diaRegistro = diaRegistro;
    }

    public LocalTime getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(LocalTime horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

    public long getBibliotecarioId() {
        return bibliotecarioId;
    }

    public void setBibliotecarioId(long bibliotecarioId) {
        this.bibliotecarioId = bibliotecarioId;
    }

    public long getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(long idRegistro) {
        this.idRegistro = idRegistro;
    }

    
}
