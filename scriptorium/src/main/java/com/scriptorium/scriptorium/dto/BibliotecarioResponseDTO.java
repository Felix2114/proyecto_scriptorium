package com.scriptorium.scriptorium.dto;

public class BibliotecarioResponseDTO {
    private Long id;
    private String usuario;
    

    public BibliotecarioResponseDTO() {}

    public BibliotecarioResponseDTO(Long id, String usuario) {
        this.id = id;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
