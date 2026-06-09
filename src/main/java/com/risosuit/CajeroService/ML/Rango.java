package com.risosuit.CajeroService.ML;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Rango", description = "Representa una categoría de cuenta o cliente que define los límites mínimos y máximos permitidos para realizar retiros.")
public class Rango {

    @Schema(description = "Identificador único del rango", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int idRango;

    @Schema(description = "Nombre del rango asignado al cliente o cuenta", example = "Clásica")
    private String nombre;

    @Schema(description = "Monto mínimo permitido por retiro", example = "100")
    private int minRetiro;

    @Schema(description = "Monto máximo permitido por retiro", example = "5000")
    private int maxRetiro;

    public Rango() {
    }

    public Rango(int idRango, String nombre, int minRetiro, int maxRetiro) {
        this.idRango = idRango;
        this.nombre = nombre;
        this.minRetiro = minRetiro;
        this.maxRetiro = maxRetiro;
    }

    public int getIdRango() {
        return idRango;
    }

    public void setIdRango(int idRango) {
        this.idRango = idRango;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMinRetiro() {
        return minRetiro;
    }

    public void setMinRetiro(int minRetiro) {
        this.minRetiro = minRetiro;
    }

    public int getMaxRetiro() {
        return maxRetiro;
    }

    public void setMaxRetiro(int maxRetiro) {
        this.maxRetiro = maxRetiro;
    }

}
