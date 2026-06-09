
package com.risosuit.CajeroService.ML;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Denominacion", description = "Representa una denominación monetaria disponible en el sistema, ya sea billete o moneda.")
public class Denominacion {

    @Schema(description = "Identificador único de la denominación", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int idDenominacion;

    @Schema(description = "Tipo de denominación", example = "Billete", allowableValues = {
            "Billete",
            "Moneda"
    })
    private String tipo;

    @Schema(description = "Valor monetario de la denominación", example = "200")
    private float denominacion;

    public int getIdDenominacion() {
        return idDenominacion;
    }

    public void setIdDenominacion(int idDenominacion) {
        this.idDenominacion = idDenominacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(float denominacion) {
        this.denominacion = denominacion;
    }

}
