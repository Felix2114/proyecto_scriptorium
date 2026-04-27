package com.scriptorium.scriptorium.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroRequestDTO {
    
    private LocalDate diaRegistro;
    private LocalTime horaRegistro;

    private long bibliotecarioId;

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

    
}
