package com.risosuit.CajeroService.ML;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Retiro", description = "Representa el desglose de efectivo entregado durante una transacción de retiro.")
public class Retiro {

    @Schema(description = "Folio único de la transacción de retiro", example = "89")
    private int folio;

    @Schema(description = "Nombre completo del titular de la cuenta", example = "Diego Gómez Tagle González")
    private String usuario;

    @Schema(description = "Número de cuenta asociado al retiro", example = "82384018")
    private String NumCuenta;

    @Schema(description = "Número de tarjeta utilizado para realizar el retiro", example = "****8553")
    private String NumTarjeta;

    @Schema(description = "Estatus de la transacción", example = "EXITOSO", allowableValues = {
            "EXITOSO",
            "RECHAZADO",
            "PENDIENTE"
    })
    private String Status;

    @Schema(description = "Cantidad de piezas entregadas de la denominación indicada", example = "2")
    private int cantidad;

    @Schema(description = "Tipo de efectivo entregado", example = "Billete", allowableValues = {
            "Billete",
            "Moneda"
    })
    private String tipo;

    @Schema(description = "Valor de la denominación entregada", example = "200")
    private String denominacion;

    @Schema(description = "Monto total correspondiente a la cantidad y denominación entregada", example = "400.00")
    private double subtotal;

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public String getNumCuenta() {
        return NumCuenta;
    }

    public void setNumCuenta(String NumCuenta) {
        this.NumCuenta = NumCuenta;
    }

    public String getNumTarjeta() {
        return NumTarjeta;
    }

    public void setNumTarjeta(String NumTarjeta) {
        this.NumTarjeta = NumTarjeta;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
