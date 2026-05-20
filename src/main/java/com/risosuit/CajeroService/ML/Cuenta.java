package com.risosuit.CajeroService.ML;

public class Cuenta {
    private int idCuenta;
    private Usuario usuario;
    private Banco banco;
    private long NumCuenta;
    private float saldo;

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public long getNumCuenta() {
        return NumCuenta;
    }

    public void setNumCuenta(long NumCuenta) {
        this.NumCuenta = NumCuenta;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    
    
    
    
}
