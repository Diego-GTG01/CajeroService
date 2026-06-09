package com.risosuit.CajeroService.ML;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DetalleCajero", description = "Representa el inventario de efectivo disponible en un cajero automático para una denominación específica.")
public class DetalleCajero {

    @Schema(description = "Identificador único del detalle de inventario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int idDetalleCajero;

    @Schema(description = "Cajero al que pertenece el inventario")
    private Cajero cajero;

    @Schema(description = "Denominación de efectivo almacenada en el cajero")
    private Denominacion denominacion;

    @Schema(description = "Cantidad disponible de piezas para esta denominación", example = "150")
    private int cantidadDisponible;

    public int getIdDetalleCajero() {
        return idDetalleCajero;
    }

    public void setIdDetalleCajero(int idDetalleCajero) {
        this.idDetalleCajero = idDetalleCajero;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public Denominacion getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(Denominacion denominacion) {
        this.denominacion = denominacion;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

}
