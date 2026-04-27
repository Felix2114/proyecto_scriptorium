package com.scriptorium.scriptorium.Service;

import java.util.Map;

public class EstadoLibroMap {

    private static final Map<String, Integer> estadoLibroMap = Map.of(
        "Nuevo", 1,
        "Buena", 2,
        "Aceptable", 3,
        "Dañado", 4
    );

    public static int obtenerNivelEstado(String estado) {
        return estadoLibroMap.getOrDefault(estado, -1);
    }

    public static boolean esEstadoValido(String estado) {
        return estadoLibroMap.containsKey(estado);
    }
}