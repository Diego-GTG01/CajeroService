package com.risosuit.CajeroService.DTO;

import java.util.Date;

import com.risosuit.CajeroService.ML.Banco;
import com.risosuit.CajeroService.ML.Cuenta;
import com.risosuit.CajeroService.ML.Rango;
import com.risosuit.CajeroService.ML.Tarjeta;
import com.risosuit.CajeroService.ML.Usuario;

public class UsuarioCompletoDTO {
    // Datos de Usuario
    private int idUsuario;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String celular;
    private String telefono;
    private String email;
    
    // Datos de Cuenta
    private int idCuenta;
    private long numCuenta;
    private float saldo;
    private int idBancoCuenta;
    private String nombreBancoCuenta;
    
    // Datos de Tarjeta
    private int idTarjeta;
    private String numTarjeta;
    private String pin;
    private Date fechaVencimiento;
    private int status;
    private int idBancoTarjeta;
    private String nombreBancoTarjeta;
    
    // Datos de Rango
    private int idRango;
    private String nombreRango;
    private int minRetiro;
    private int maxRetiro;
    
    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }
    
    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }
    
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public int getIdCuenta() { return idCuenta; }
    public void setIdCuenta(int idCuenta) { this.idCuenta = idCuenta; }
    
    public long getNumCuenta() { return numCuenta; }
    public void setNumCuenta(long numCuenta) { this.numCuenta = numCuenta; }
    
    public float getSaldo() { return saldo; }
    public void setSaldo(float saldo) { this.saldo = saldo; }
    
    public int getIdBancoCuenta() { return idBancoCuenta; }
    public void setIdBancoCuenta(int idBancoCuenta) { this.idBancoCuenta = idBancoCuenta; }
    
    public String getNombreBancoCuenta() { return nombreBancoCuenta; }
    public void setNombreBancoCuenta(String nombreBancoCuenta) { this.nombreBancoCuenta = nombreBancoCuenta; }
    
    public int getIdTarjeta() { return idTarjeta; }
    public void setIdTarjeta(int idTarjeta) { this.idTarjeta = idTarjeta; }
    
    public String getNumTarjeta() { return numTarjeta; }
    public void setNumTarjeta(String numTarjeta) { this.numTarjeta = numTarjeta; }
    
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    
    public Date getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    
    public int getIdBancoTarjeta() { return idBancoTarjeta; }
    public void setIdBancoTarjeta(int idBancoTarjeta) { this.idBancoTarjeta = idBancoTarjeta; }
    
    public String getNombreBancoTarjeta() { return nombreBancoTarjeta; }
    public void setNombreBancoTarjeta(String nombreBancoTarjeta) { this.nombreBancoTarjeta = nombreBancoTarjeta; }
    
    public int getIdRango() { return idRango; }
    public void setIdRango(int idRango) { this.idRango = idRango; }
    
    public String getNombreRango() { return nombreRango; }
    public void setNombreRango(String nombreRango) { this.nombreRango = nombreRango; }
    
    public int getMinRetiro() { return minRetiro; }
    public void setMinRetiro(int minRetiro) { this.minRetiro = minRetiro; }
    
    public int getMaxRetiro() { return maxRetiro; }
    public void setMaxRetiro(int maxRetiro) { this.maxRetiro = maxRetiro; }
    
    // Método para obtener nombre completo
    public String getNombreCompleto() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
    
    // Método para convertir a objeto Usuario
    public Usuario toUsuario() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(this.idUsuario);
        usuario.setNombre(this.nombre);
        usuario.setApellidoPaterno(this.apellidoPaterno);
        usuario.setApellidoMaterno(this.apellidoMaterno);
        usuario.setCelular(this.celular);
        usuario.setTelefono(this.telefono);
        usuario.setEmail(this.email);
        return usuario;
    }
    
    // Método para convertir a objeto Cuenta
    public Cuenta toCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta(this.idCuenta);
        cuenta.setUsuario(toUsuario());
        cuenta.setNumCuenta(this.numCuenta);
        cuenta.setSaldo(this.saldo);
        
        Banco banco = new Banco();
        banco.setIdBanco(this.idBancoCuenta);
        banco.setNombre(this.nombreBancoCuenta);
        cuenta.setBanco(banco);
        
        return cuenta;
    }
    
    // Método para convertir a objeto Tarjeta
    public Tarjeta toTarjeta() {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setIdTarjeta(this.idTarjeta);
        tarjeta.setUsuario(toUsuario());
        tarjeta.setNumTarjeta(this.numTarjeta);
        tarjeta.setPin(this.pin);
        tarjeta.setFechaVencimiento(this.fechaVencimiento);
        tarjeta.setStatus(this.status);
        
        // Asignar banco de la tarjeta
        Banco bancoTarjeta = new Banco();
        bancoTarjeta.setIdBanco(this.idBancoTarjeta);
        bancoTarjeta.setNombre(this.nombreBancoTarjeta);
        tarjeta.setBanco(bancoTarjeta);
        
        // Asignar rango
        Rango rango = new Rango();
        rango.setIdRango(this.idRango);
        rango.setNombre(this.nombreRango);
        rango.setMinRetiro(this.minRetiro);
        rango.setMaxRetiro(this.maxRetiro);
        tarjeta.setRango(rango);
        
        return tarjeta;
    }
}