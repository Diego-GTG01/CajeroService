package com.risosuit.CajeroService.ML;

public class Retiro {

    private int folio;
    private String usuario;
    private String NumCuenta;
    private String NumTarjeta;
    private String Status;
    private int cantidad;
    private String tipo;
    private String denominacion;
    private double subtotal;

    public int getFolio(){
        return folio;
    }
    public void setFolio(int folio){
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
