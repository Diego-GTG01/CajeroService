package com.risosuit.CajeroService.ML;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Cajero", description = "Representa un cajero automático registrado en el sistema.")
public class Cajero {

    @Schema(description = "Identificador único del cajero", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int idCajero;

    @Schema(description = "Ubicación física del cajero", example = "Sucursal Centro - Ciudad de México")
    private String ubicacion;

    @Schema(description = "Estado actual del cajero", example = "ACTIVO", allowableValues = {
            "ACTIVO",
            "INACTIVO",
            "MANTENIMIENTO"
    })
    private String estado;

    @Schema(description = "Monto total de efectivo disponible en el cajero", example = "250000.00")
    private double total;

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
