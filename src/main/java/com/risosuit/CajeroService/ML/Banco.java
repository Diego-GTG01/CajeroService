package com.risosuit.CajeroService.ML;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Banco", description = "Representa una institución bancaria registrada en el sistema.")
public class Banco {

    @Schema(description = "Identificador único del banco", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int idBanco;

    @Schema(description = "Nombre comercial del banco", example = "Banorte")
    private String nombre;

    public Banco() {
    }

    public Banco(int idBanco, String nombre) {
        this.idBanco = idBanco;
        this.nombre = nombre;
        this.nombre = nombre;

    }

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
