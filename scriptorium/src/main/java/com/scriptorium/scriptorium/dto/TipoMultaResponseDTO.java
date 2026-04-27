package com.scriptorium.scriptorium.dto;

public class TipoMultaResponseDTO {
    private Long id;
    private String tipo;
    private String descripcion;

    public TipoMultaResponseDTO() {}

    public TipoMultaResponseDTO(Long id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}
