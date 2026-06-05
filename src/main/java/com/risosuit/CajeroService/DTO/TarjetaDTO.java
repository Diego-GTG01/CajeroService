package com.risosuit.CajeroService.DTO;

public class TarjetaDTO {

    private int idBanco;
    private int idRango;
    private int idUsuario;
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
