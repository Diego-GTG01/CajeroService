package com.risosuit.CajeroService.ML;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Cuenta", description = "Representa una cuenta bancaria asociada a un usuario dentro de una institución bancaria.")
public class Cuenta {

    @Schema(description = "Identificador único de la cuenta", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int idCuenta;

    @Schema(description = "Titular de la cuenta")
    private Usuario usuario;

    @Schema(description = "Banco al que pertenece la cuenta")
    private Banco banco;

    @Schema(description = "Número único de cuenta bancaria", example = "82384018")
    private long NumCuenta;

    @Schema(description = "Saldo actual disponible en la cuenta", example = "5000.50")
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
