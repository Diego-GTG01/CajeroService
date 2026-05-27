package com.risosuit.CajeroService.ML;

import java.util.Date;

public class Tarjeta {

    private int idTarjeta;
    private Usuario usuario;
    private Rango rango;
    private Banco banco;
    private String NumTarjeta;
    private String pin;
    private Date fechaVencimiento;
    private int status;

    public Tarjeta() {
        usuario = new Usuario();
        banco = new Banco();
        rango = new Rango();

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

    public String getNumTarjeta() {
        return NumTarjeta;
    }

    public void setNumTarjeta(String NumTarjeta) {
        this.NumTarjeta = NumTarjeta;
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
