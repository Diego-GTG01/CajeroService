package com.risosuit.CajeroService.ML;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Transaccion", description = "Representa una operación realizada en un cajero automático, como un retiro de efectivo.")
public class Transaccion {

    @Schema(description = "Identificador único de la transacción", example = "89", accessMode = Schema.AccessMode.READ_ONLY)
    private int idTransaccion;

    @Schema(description = "Tarjeta utilizada para realizar la transacción")
    private Tarjeta tarjeta;

    @Schema(description = "Cajero donde se realizó la transacción")
    private Cajero cajero;

    @Schema(description = "Monto solicitado o procesado en la transacción", example = "1500.00")
    private double monto;

    @Schema(description = "Fecha y hora en que se registró la transacción", example = "2025-06-09T14:30:00")
    private Date fecha;

    @Schema(description = "Estado de la transacción", example = "1", allowableValues = { "0", "1", "2" })
    private int estado;

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

}
