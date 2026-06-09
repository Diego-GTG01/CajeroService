package com.risosuit.CajeroService.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TarjetaDTO", description = "Datos requeridos para generar una nueva tarjeta bancaria y asociarla a un usuario.")
public class TarjetaDTO {

    @Schema(description = "Identificador del banco emisor de la tarjeta", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int idBanco;

    @Schema(description = "Identificador del rango asignado a la tarjeta", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private int idRango;

    @Schema(description = "Identificador del usuario propietario de la tarjeta", example = "15", requiredMode = Schema.RequiredMode.REQUIRED)
    private int idUsuario;

    @Schema(description = "PIN de seguridad de la tarjeta", example = "1234", minLength = 4, maxLength = 4, writeOnly = true, requiredMode = Schema.RequiredMode.REQUIRED)
    private String pin;

    public TarjetaDTO() {
    }

    public int getIdBanco() {
        return idBanco;
    }

    public int getIdRango() {
        return idRango;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getPin() {
        return pin;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public void setIdRango(int idRango) {
        this.idRango = idRango;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

}
