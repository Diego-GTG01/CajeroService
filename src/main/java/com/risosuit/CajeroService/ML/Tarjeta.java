package com.risosuit.CajeroService.ML;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Tarjeta", description = "Representa una tarjeta bancaria asociada a un usuario, cuenta, banco y rango de operación.")
public class Tarjeta {

    @Schema(description = "Identificador único de la tarjeta", example = "15", accessMode = Schema.AccessMode.READ_ONLY)
    private int idTarjeta;

    @Schema(description = "Titular de la tarjeta")
    private Usuario usuario;

    @Schema(description = "Rango asignado a la tarjeta que define límites de retiro")
    private Rango rango;

    @Schema(description = "Banco emisor de la tarjeta")
    private Banco banco;

    @Schema(description = "Número único de la tarjeta", example = "51617216831767158553")
    private String numTarjeta;

    @Schema(description = "PIN de seguridad utilizado para autenticación", example = "1234", writeOnly = true)
    private String pin;

    @Schema(description = "Fecha de vencimiento de la tarjeta", example = "2029-12-31")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaVencimiento;

    @Schema(description = "Estado actual de la tarjeta", example = "1", allowableValues = { "0", "1" })
    private int status;

    public Tarjeta() {
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rango getRango() {
        return rango;
    }

    public void setRango(Rango rango) {
        this.rango = rango;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    @JsonProperty("NumTarjeta")
    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}