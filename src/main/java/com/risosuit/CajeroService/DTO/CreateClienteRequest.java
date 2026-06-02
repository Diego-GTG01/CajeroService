/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.CajeroService.DTO;

import com.risosuit.CajeroService.ML.Cuenta;
import com.risosuit.CajeroService.ML.Tarjeta;
import com.risosuit.CajeroService.ML.Usuario;

/**
 *
 * @author ALIEN62
 */
public class CreateClienteRequest {

    private Usuario usuario;
    private Cuenta cuenta;
    private Tarjeta tarjeta;
    
    public CreateClienteRequest(){}

    public CreateClienteRequest(Usuario usuario, Cuenta cuenta, Tarjeta tarjeta) {
        this.usuario = usuario;
        this.cuenta = cuenta;
        this.tarjeta = tarjeta;
    }
    
    

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
}
